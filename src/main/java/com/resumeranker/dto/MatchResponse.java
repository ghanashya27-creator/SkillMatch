package com.resumeranker.dto;

import java.util.List;

public class MatchResponse {
    private String candidateName;
    private String candidateEmail;
    private String candidatePhone;
    private String jobTitle;
    
    private double overallScore;
    private double skillMatchScore;
    private double semanticScore;
    private double experienceScore;
    private double educationScore;

    private String scoreTier;
    private String tierColorHex;

    private List<String> detectedSkills;
    private List<String> matchedSkills;
    private List<String> missingSkills;
    private int extractedExperienceYears;
    private String detectedEducation;

    private List<String> atsRecommendations;
    private String aiSummaryAdvice;
    private boolean processedWithAi;

    public MatchResponse() {}

    public MatchResponse(String candidateName, String candidateEmail, String candidatePhone, String jobTitle, double overallScore, double skillMatchScore, double semanticScore, double experienceScore, double educationScore, String scoreTier, String tierColorHex, List<String> detectedSkills, List<String> matchedSkills, List<String> missingSkills, int extractedExperienceYears, String detectedEducation, List<String> atsRecommendations, String aiSummaryAdvice, boolean processedWithAi) {
        this.candidateName = candidateName;
        this.candidateEmail = candidateEmail;
        this.candidatePhone = candidatePhone;
        this.jobTitle = jobTitle;
        this.overallScore = overallScore;
        this.skillMatchScore = skillMatchScore;
        this.semanticScore = semanticScore;
        this.experienceScore = experienceScore;
        this.educationScore = educationScore;
        this.scoreTier = scoreTier;
        this.tierColorHex = tierColorHex;
        this.detectedSkills = detectedSkills;
        this.matchedSkills = matchedSkills;
        this.missingSkills = missingSkills;
        this.extractedExperienceYears = extractedExperienceYears;
        this.detectedEducation = detectedEducation;
        this.atsRecommendations = atsRecommendations;
        this.aiSummaryAdvice = aiSummaryAdvice;
        this.processedWithAi = processedWithAi;
    }

    public String getCandidateName() { return candidateName; }
    public void setCandidateName(String candidateName) { this.candidateName = candidateName; }

    public String getCandidateEmail() { return candidateEmail; }
    public void setCandidateEmail(String candidateEmail) { this.candidateEmail = candidateEmail; }

    public String getCandidatePhone() { return candidatePhone; }
    public void setCandidatePhone(String candidatePhone) { this.candidatePhone = candidatePhone; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public double getOverallScore() { return overallScore; }
    public void setOverallScore(double overallScore) { this.overallScore = overallScore; }

    public double getSkillMatchScore() { return skillMatchScore; }
    public void setSkillMatchScore(double skillMatchScore) { this.skillMatchScore = skillMatchScore; }

    public double getSemanticScore() { return semanticScore; }
    public void setSemanticScore(double semanticScore) { this.semanticScore = semanticScore; }

    public double getExperienceScore() { return experienceScore; }
    public void setExperienceScore(double experienceScore) { this.experienceScore = experienceScore; }

    public double getEducationScore() { return educationScore; }
    public void setEducationScore(double educationScore) { this.educationScore = educationScore; }

    public String getScoreTier() { return scoreTier; }
    public void setScoreTier(String scoreTier) { this.scoreTier = scoreTier; }

    public String getTierColorHex() { return tierColorHex; }
    public void setTierColorHex(String tierColorHex) { this.tierColorHex = tierColorHex; }

    public List<String> getDetectedSkills() { return detectedSkills; }
    public void setDetectedSkills(List<String> detectedSkills) { this.detectedSkills = detectedSkills; }

    public List<String> getMatchedSkills() { return matchedSkills; }
    public void setMatchedSkills(List<String> matchedSkills) { this.matchedSkills = matchedSkills; }

    public List<String> getMissingSkills() { return missingSkills; }
    public void setMissingSkills(List<String> missingSkills) { this.missingSkills = missingSkills; }

    public int getExtractedExperienceYears() { return extractedExperienceYears; }
    public void setExtractedExperienceYears(int extractedExperienceYears) { this.extractedExperienceYears = extractedExperienceYears; }

    public String getDetectedEducation() { return detectedEducation; }
    public void setDetectedEducation(String detectedEducation) { this.detectedEducation = detectedEducation; }

    public List<String> getAtsRecommendations() { return atsRecommendations; }
    public void setAtsRecommendations(List<String> atsRecommendations) { this.atsRecommendations = atsRecommendations; }

    public String getAiSummaryAdvice() { return aiSummaryAdvice; }
    public void setAiSummaryAdvice(String aiSummaryAdvice) { this.aiSummaryAdvice = aiSummaryAdvice; }

    public boolean isProcessedWithAi() { return processedWithAi; }
    public void setProcessedWithAi(boolean processedWithAi) { this.processedWithAi = processedWithAi; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String candidateName;
        private String candidateEmail;
        private String candidatePhone;
        private String jobTitle;
        private double overallScore;
        private double skillMatchScore;
        private double semanticScore;
        private double experienceScore;
        private double educationScore;
        private String scoreTier;
        private String tierColorHex;
        private List<String> detectedSkills;
        private List<String> matchedSkills;
        private List<String> missingSkills;
        private int extractedExperienceYears;
        private String detectedEducation;
        private List<String> atsRecommendations;
        private String aiSummaryAdvice;
        private boolean processedWithAi;

        public Builder candidateName(String candidateName) { this.candidateName = candidateName; return this; }
        public Builder candidateEmail(String candidateEmail) { this.candidateEmail = candidateEmail; return this; }
        public Builder candidatePhone(String candidatePhone) { this.candidatePhone = candidatePhone; return this; }
        public Builder jobTitle(String jobTitle) { this.jobTitle = jobTitle; return this; }
        public Builder overallScore(double overallScore) { this.overallScore = overallScore; return this; }
        public Builder skillMatchScore(double skillMatchScore) { this.skillMatchScore = skillMatchScore; return this; }
        public Builder semanticScore(double semanticScore) { this.semanticScore = semanticScore; return this; }
        public Builder experienceScore(double experienceScore) { this.experienceScore = experienceScore; return this; }
        public Builder educationScore(double educationScore) { this.educationScore = educationScore; return this; }
        public Builder scoreTier(String scoreTier) { this.scoreTier = scoreTier; return this; }
        public Builder tierColorHex(String tierColorHex) { this.tierColorHex = tierColorHex; return this; }
        public Builder detectedSkills(List<String> detectedSkills) { this.detectedSkills = detectedSkills; return this; }
        public Builder matchedSkills(List<String> matchedSkills) { this.matchedSkills = matchedSkills; return this; }
        public Builder missingSkills(List<String> missingSkills) { this.missingSkills = missingSkills; return this; }
        public Builder extractedExperienceYears(int extractedExperienceYears) { this.extractedExperienceYears = extractedExperienceYears; return this; }
        public Builder detectedEducation(String detectedEducation) { this.detectedEducation = detectedEducation; return this; }
        public Builder atsRecommendations(List<String> atsRecommendations) { this.atsRecommendations = atsRecommendations; return this; }
        public Builder aiSummaryAdvice(String aiSummaryAdvice) { this.aiSummaryAdvice = aiSummaryAdvice; return this; }
        public Builder processedWithAi(boolean processedWithAi) { this.processedWithAi = processedWithAi; return this; }

        public MatchResponse build() {
            return new MatchResponse(candidateName, candidateEmail, candidatePhone, jobTitle, overallScore, skillMatchScore, semanticScore, experienceScore, educationScore, scoreTier, tierColorHex, detectedSkills, matchedSkills, missingSkills, extractedExperienceYears, detectedEducation, atsRecommendations, aiSummaryAdvice, processedWithAi);
        }
    }
}
