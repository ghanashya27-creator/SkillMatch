package com.resumeranker.controller;

import com.resumeranker.dto.*;
import com.resumeranker.entity.JobEntity;
import com.resumeranker.entity.MatchHistoryEntity;
import com.resumeranker.repository.JobRepository;
import com.resumeranker.repository.MatchHistoryRepository;
import com.resumeranker.service.ResumeMatcherService;
import com.resumeranker.service.ResumeParserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Tag(name = "SkillMatch API", description = "Endpoints for Resume Parsing, ATS Job Matching, and Recruiter Ranking")
public class ApiController {

    private final ResumeMatcherService resumeMatcherService;
    private final ResumeParserService resumeParserService;
    private final JobRepository jobRepository;
    private final MatchHistoryRepository matchHistoryRepository;

    @Autowired
    public ApiController(ResumeMatcherService resumeMatcherService, ResumeParserService resumeParserService, JobRepository jobRepository, MatchHistoryRepository matchHistoryRepository) {
        this.resumeMatcherService = resumeMatcherService;
        this.resumeParserService = resumeParserService;
        this.jobRepository = jobRepository;
        this.matchHistoryRepository = matchHistoryRepository;
    }

    @Operation(summary = "Match Single Resume (File upload or raw text) against a Job Description")
    @PostMapping(value = "/match", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MatchResponse> matchResume(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "resumeText", required = false) String resumeText,
            @RequestParam(value = "jobId", required = false) Long jobId,
            @RequestParam(value = "customJobTitle", required = false) String customJobTitle,
            @RequestParam(value = "customJobDescription", required = false) String customJobDescription,
            @RequestParam(value = "candidateName", required = false) String candidateName,
            @RequestParam(value = "groqApiKey", required = false) String groqApiKey
    ) {
        String finalResumeText = resumeText;
        if (file != null && !file.isEmpty()) {
            finalResumeText = resumeParserService.extractText(file);
        }

        if (finalResumeText == null || finalResumeText.trim().isEmpty()) {
            throw new IllegalArgumentException("Please upload a valid PDF/text resume or paste resume text.");
        }

        MatchRequest request = MatchRequest.builder()
                .jobId(jobId)
                .customJobTitle(customJobTitle)
                .customJobDescription(customJobDescription)
                .resumeText(finalResumeText)
                .candidateName(candidateName)
                .groqApiKey(groqApiKey)
                .build();

        MatchResponse response = resumeMatcherService.evaluateMatch(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Rank multiple resumes for Recruiter Mode against a single job description")
    @PostMapping(value = "/rank-bulk", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RankResponse> rankBulkResumes(
            @RequestPart("files") List<MultipartFile> files,
            @RequestParam(value = "jobId", required = false) Long jobId,
            @RequestParam(value = "customJobTitle", required = false) String customJobTitle,
            @RequestParam(value = "customJobDescription", required = false) String customJobDescription,
            @RequestParam(value = "groqApiKey", required = false) String groqApiKey
    ) {
        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException("Please upload at least one resume file for bulk ranking.");
        }

        List<MatchResponse> results = new ArrayList<>();
        String effectiveJobTitle = "Job Position";

        for (MultipartFile file : files) {
            try {
                String extractedText = resumeParserService.extractText(file);
                String candidateName = resumeParserService.extractCandidateName(extractedText, file.getOriginalFilename());

                MatchRequest request = MatchRequest.builder()
                        .jobId(jobId)
                        .customJobTitle(customJobTitle)
                        .customJobDescription(customJobDescription)
                        .resumeText(extractedText)
                        .candidateName(candidateName)
                        .groqApiKey(groqApiKey)
                        .build();

                MatchResponse response = resumeMatcherService.evaluateMatch(request);
                effectiveJobTitle = response.getJobTitle();
                results.add(response);
            } catch (Exception e) {
                System.err.println("Error processing file " + file.getOriginalFilename() + ": " + e.getMessage());
            }
        }

        results.sort(Comparator.comparingDouble(MatchResponse::getOverallScore).reversed());

        RankResponse rankResponse = RankResponse.builder()
                .jobTitle(effectiveJobTitle)
                .totalCandidatesProcessed(results.size())
                .rankedCandidates(results)
                .build();

        return ResponseEntity.ok(rankResponse);
    }

    @Operation(summary = "Get all available Job Description presets and custom postings")
    @GetMapping("/jobs")
    public ResponseEntity<List<JobDto>> getJobs() {
        List<JobEntity> jobs = jobRepository.findAll();
        List<JobDto> dtos = jobs.stream().map(job -> JobDto.builder()
                .id(job.getId())
                .title(job.getTitle())
                .company(job.getCompany())
                .department(job.getDepartment())
                .experienceLevel(job.getExperienceLevel())
                .requiredSkills(job.getRequiredSkills() != null ? Arrays.asList(job.getRequiredSkills().split("\\s*,\\s*")) : Collections.emptyList())
                .preferredSkills(job.getPreferredSkills() != null ? Arrays.asList(job.getPreferredSkills().split("\\s*,\\s*")) : Collections.emptyList())
                .description(job.getDescription())
                .isPreset(job.isPreset())
                .build()
        ).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Create a new custom Job Description posting")
    @PostMapping("/jobs")
    public ResponseEntity<JobDto> createJob(@RequestBody JobDto jobDto) {
        if (jobDto.getTitle() == null || jobDto.getDescription() == null) {
            throw new IllegalArgumentException("Job title and description are required.");
        }

        JobEntity entity = JobEntity.builder()
                .title(jobDto.getTitle())
                .company(jobDto.getCompany() != null ? jobDto.getCompany() : "Custom Company")
                .department(jobDto.getDepartment() != null ? jobDto.getDepartment() : "General")
                .experienceLevel(jobDto.getExperienceLevel() != null ? jobDto.getExperienceLevel() : "Mid-Senior")
                .requiredSkills(jobDto.getRequiredSkills() != null ? String.join(", ", jobDto.getRequiredSkills()) : "")
                .preferredSkills(jobDto.getPreferredSkills() != null ? String.join(", ", jobDto.getPreferredSkills()) : "")
                .description(jobDto.getDescription())
                .isPreset(false)
                .build();

        JobEntity saved = jobRepository.save(entity);
        jobDto.setId(saved.getId());
        jobDto.setPreset(false);
        return ResponseEntity.ok(jobDto);
    }

    @Operation(summary = "Get match history analytics")
    @GetMapping("/history")
    public ResponseEntity<List<MatchHistoryEntity>> getHistory() {
        return ResponseEntity.ok(matchHistoryRepository.findTop20ByOrderByCreatedAtDesc());
    }

    @Operation(summary = "Get sample resumes and job roles for 1-click test drive")
    @GetMapping("/samples")
    public ResponseEntity<Map<String, Object>> getSamples() {
        Map<String, Object> samples = new HashMap<>();

        String sampleJavaResume = """
                ALEX MORGAN
                Email: alex.morgan@example.com | Phone: (555) 234-5678 | San Francisco, CA
                LinkedIn: linkedin.com/in/alexmorgan-dev | GitHub: github.com/alexmorgan-dev

                PROFESSIONAL SUMMARY
                Senior Java Fullstack Engineer with 6+ years of experience designing and scaling cloud-native microservices, REST APIs, and modern React web applications. Proven track record in Spring Boot, PostgreSQL, Docker, AWS, and Agile software development.

                TECHNICAL SKILLS
                - Backend & Core: Java, Spring Boot, Spring Cloud, Hibernate, JPA, REST API, Microservices, System Design
                - Frontend: React, JavaScript, TypeScript, HTML, CSS, Redux, Tailwind CSS
                - Databases & Cloud: PostgreSQL, MySQL, Redis, AWS, Docker, Kubernetes, Git, CI/CD, Unit Testing

                PROFESSIONAL EXPERIENCE
                Lead Software Engineer | TechCorp Solutions (2021 - Present)
                - Architected microservices with Spring Boot & Docker, reducing latency by 35%.
                - Developed responsive React frontend dashboards using Redux and REST API endpoints.
                - Configured AWS deployment pipelines (GitHub Actions, Docker, Kubernetes) handling 2M+ daily active requests.

                Fullstack Developer | Nexus Cloud Inc (2018 - 2021)
                - Built core Java backend modules and implemented PostgreSQL database indexing.
                - Created reusable UI components in React and unit tests using JUnit and Mockito.

                EDUCATION
                B.S. in Computer Science | University of California, Berkeley (2018)
                """;

        String sampleAiResume = """
                DR. ELENA ROSTOVA
                Email: elena.rostova@ai-labs.org | Phone: (555) 876-5432
                GitHub: github.com/erostova-ai

                SUMMARY
                AI & Machine Learning Scientist with 4 years of experience building Deep Learning, NLP, and Large Language Model (LLM) pipelines using PyTorch, Python, and Transformers.

                SKILLS
                Python, PyTorch, TensorFlow, Machine Learning, Deep Learning, NLP, LLM, Pandas, NumPy, Scikit-Learn, Docker, FastAPI, SQL, Git

                EXPERIENCE
                Senior ML Engineer | DeepInsight AI (2022 - Present)
                - Fine-tuned open-source LLMs using PyTorch and Hugging Face Transformers.
                - Deployed NLP inference endpoints via FastAPI and Docker on AWS GPU clusters.
                """;

        samples.put("sampleJavaResume", sampleJavaResume);
        samples.put("sampleAiResume", sampleAiResume);
        return ResponseEntity.ok(samples);
    }
}
