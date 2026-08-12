package com.resumeranker.service;

import com.resumeranker.dto.MatchRequest;
import com.resumeranker.dto.MatchResponse;
import com.resumeranker.entity.JobEntity;
import com.resumeranker.entity.MatchHistoryEntity;
import com.resumeranker.repository.JobRepository;
import com.resumeranker.repository.MatchHistoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ResumeMatcherService {

    private final ResumeParserService resumeParserService;
    private final SkillOntologyService skillOntologyService;
    private final TfidfMatchingEngine tfidfMatchingEngine;
    private final GroqAiService groqAiService;
    private final JobRepository jobRepository;
    private final MatchHistoryRepository matchHistoryRepository;

    @Autowired
    public ResumeMatcherService(ResumeParserService resumeParserService, SkillOntologyService skillOntologyService, TfidfMatchingEngine tfidfMatchingEngine, GroqAiService groqAiService, JobRepository jobRepository, MatchHistoryRepository matchHistoryRepository) {
        this.resumeParserService = resumeParserService;
        this.skillOntologyService = skillOntologyService;
        this.tfidfMatchingEngine = tfidfMatchingEngine;
        this.groqAiService = groqAiService;
        this.jobRepository = jobRepository;
        this.matchHistoryRepository = matchHistoryRepository;
    }

    public MatchResponse evaluateMatch(MatchRequest request) {
        String jobTitle = "Target Role";
        String jobDesc = "";
        List<String> requiredSkills = new ArrayList<>();

        if (request.getJobId() != null) {
            Optional<JobEntity> jobOpt = jobRepository.findById(request.getJobId());
            if (jobOpt.isPresent()) {
                JobEntity job = jobOpt.get();
                jobTitle = job.getTitle();
                jobDesc = job.getDescription();
                if (job.getRequiredSkills() != null) {
                    requiredSkills.addAll(Arrays.asList(job.getRequiredSkills().split("\\s*,\\s*")));
                }
            }
        }

        if (request.getCustomJobDescription() != null && !request.getCustomJobDescription().trim().isEmpty()) {
            jobDesc = request.getCustomJobDescription();
            if (request.getCustomJobTitle() != null && !request.getCustomJobTitle().trim().isEmpty()) {
                jobTitle = request.getCustomJobTitle();
            }
        }

        if (jobDesc.trim().isEmpty()) {
            throw new IllegalArgumentException("Job description or valid Job ID must be provided");
        }

        String resumeText = request.getResumeText();
        if (resumeText == null || resumeText.trim().isEmpty()) {
            throw new IllegalArgumentException("Resume content cannot be empty");
        }

        String email = resumeParserService.extractEmail(resumeText);
        String phone = resumeParserService.extractPhone(resumeText);
        String candidateName = (request.getCandidateName() != null && !request.getCandidateName().trim().isEmpty())
                ? request.getCandidateName()
                : resumeParserService.extractCandidateName(resumeText, "Candidate");
        int expYears = resumeParserService.extractExperienceYears(resumeText);
        String education = resumeParserService.extractEducation(resumeText);

        List<String> candidateSkills = skillOntologyService.extractSkills(resumeText);
        List<String> softSkills = skillOntologyService.extractSoftSkills(resumeText);
        double softSkillScore = softSkills.isEmpty() ? 75.0 : Math.min(100.0, softSkills.size() * 25.0);

        List<String> jobSkills = skillOntologyService.extractSkills(jobDesc);
        if (!requiredSkills.isEmpty()) {
            for (String req : requiredSkills) {
                if (!jobSkills.contains(req) && !req.trim().isEmpty()) {
                    jobSkills.add(req);
                }
            }
        }

        List<String> matchedSkills = new ArrayList<>();
        List<String> missingSkills = new ArrayList<>();

        for (String js : jobSkills) {
            boolean found = candidateSkills.stream().anyMatch(cs -> cs.equalsIgnoreCase(js));
            if (found) {
                matchedSkills.add(js);
            } else {
                missingSkills.add(js);
            }
        }

        double skillScore = jobSkills.isEmpty() ? 70.0 : ((double) matchedSkills.size() / jobSkills.size()) * 100.0;
        double semanticScore = tfidfMatchingEngine.calculateCosineSimilarity(resumeText, jobDesc);

        int requiredExpYears = extractRequiredExpFromJob(jobDesc);
        double expScore = (expYears >= requiredExpYears) ? 100.0 : Math.max(30.0, (double) expYears / Math.max(1, requiredExpYears) * 100.0);
        double eduScore = (education.contains("Master") || education.contains("Doctorate")) ? 100.0 : 85.0;

        double overallScore = Math.round((skillScore * 0.40) + (semanticScore * 0.35) + (expScore * 0.15) + (eduScore * 0.10));
        overallScore = Math.min(99.0, Math.max(15.0, overallScore));

        String tier;
        String colorHex;
        if (overallScore >= 85.0) {
            tier = "Top Match";
            colorHex = "#22c55e";
        } else if (overallScore >= 70.0) {
            tier = "Strong Match";
            colorHex = "#3b82f6";
        } else if (overallScore >= 50.0) {
            tier = "Moderate Match";
            colorHex = "#eab308";
        } else {
            tier = "Low Match";
            colorHex = "#ef4444";
        }

        List<String> recommendations = new ArrayList<>();
        if (!missingSkills.isEmpty()) {
            recommendations.add("Incorporate missing key skills: " + String.join(", ", missingSkills.subList(0, Math.min(5, missingSkills.size()))));
        }
        if (expYears < requiredExpYears) {
            recommendations.add("Highlight project achievements to compensate for experience requirement (" + requiredExpYears + "+ yrs required vs " + expYears + " yrs detected).");
        }
        if (semanticScore < 50.0) {
            recommendations.add("Align resume summary phrasing closer to the target job description keywords.");
        }
        if (recommendations.isEmpty()) {
            recommendations.add("Excellent fit! Resume is well-aligned with key job requirements.");
        }

        String aiAdvice = groqAiService.generateAiAdvice(jobTitle, jobDesc, resumeText, missingSkills, request.getGroqApiKey());
        boolean processedWithAi = (aiAdvice != null && !aiAdvice.trim().isEmpty());

        MatchHistoryEntity history = MatchHistoryEntity.builder()
                .candidateName(candidateName)
                .jobTitle(jobTitle)
                .overallScore(overallScore)
                .skillMatchScore(Math.round(skillScore))
                .semanticScore(Math.round(semanticScore))
                .experienceScore(Math.round(expScore))
                .educationScore(Math.round(eduScore))
                .scoreTier(tier)
                .matchedSkills(String.join(", ", matchedSkills))
                .missingSkills(String.join(", ", missingSkills))
                .recommendations(String.join(" | ", recommendations))
                .processedWithAi(processedWithAi)
                .build();
        matchHistoryRepository.save(history);

        return MatchResponse.builder()
                .candidateName(candidateName)
                .candidateEmail(email)
                .candidatePhone(phone)
                .jobTitle(jobTitle)
                .overallScore(overallScore)
                .skillMatchScore(Math.round(skillScore))
                .softSkillScore(Math.round(softSkillScore))
                .semanticScore(Math.round(semanticScore))
                .experienceScore(Math.round(expScore))
                .educationScore(Math.round(eduScore))
                .scoreTier(tier)
                .tierColorHex(colorHex)
                .detectedSkills(candidateSkills)
                .matchedSkills(matchedSkills)
                .missingSkills(missingSkills)
                .extractedExperienceYears(expYears)
                .detectedEducation(education)
                .resumeRawText(resumeText)
                .atsRecommendations(recommendations)
                .aiSummaryAdvice(aiAdvice)
                .processedWithAi(processedWithAi)
                .build();
    }

    private int extractRequiredExpFromJob(String jobDesc) {
        if (jobDesc == null) return 3;
        String lower = jobDesc.toLowerCase();
        if (lower.contains("senior") || lower.contains("lead")) return 5;
        if (lower.contains("entry") || lower.contains("junior")) return 1;
        return 3;
    }
}
