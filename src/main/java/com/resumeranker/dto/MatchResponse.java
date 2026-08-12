package com.resumeranker.dto;

import java.util.List;

public class MatchResponse {
    private String candidateName;
    private String candidateEmail;
    private String candidatePhone;
    private String jobTitle;
    
    private double overallScore;
    private double skillMatchScore;
    private double softSkillScore;
    private double semanticScore;
    private double experienceScore;
    private double educationScore;

    private String scoreTier;
    private String tierColorHex;

    // Gatekeeper & Callback Odds fields
    private String gatekeeperDecision; // "Fast-Track", "Needs HR Review", "High Rejection Risk"
    private int callbackOddsPercentage;
    private int dealbreakerCount;
    private List<String> dealbreakersList;
    private List<String> optimizedSkillBullets;

    private List<String> detectedSkills;
    private List<String> matchedSkills;
    private List<String> missingSkills;
    private int extractedExperienceYears;
    private String detectedEducation;
    private String resumeRawText;

    private List<String> atsRecommendations;
    private String aiSummaryAdvice;
    private boolean processedWithAi;

    public MatchResponse() {}

    public MatchResponse(String candidateName, String candidateEmail, String candidatePhone, String jobTitle, double overallScore, double skillMatchScore, double softSkillScore, double semanticScore, double experienceScore, double educationScore, String scoreTier, String tierColorHex, String gatekeeperDecision, int callbackOddsPercentage, int dealbreakerCount, List<String> dealbreakersList, List<String> optimizedSkillBullets, List<String> detectedSkills, List<String> matchedSkills, List<String> missingSkills, int extractedExperienceYears, String detectedEducation, String resumeRawText, List<String> atsRecommendations, String aiSummaryAdvice, boolean processedWithAi) {
        this.candidateName = candidateName;
        this.candidateEmail = candidateEmail;
        this.candidatePhone = candidatePhone;
        this.jobTitle = jobTitle;
        this.overallScore = overallScore;
        this.skillMatchScore = skillMatchScore;
        this.softSkillScore = softSkillScore;
        this.semanticScore = semanticScore;
        this.experienceScore = experienceScore;
        this.educationScore = educationScore;
        this.scoreTier = scoreTier;
        this.tierColorHex = tierColorHex;
        this.gatekeeperDecision = gatekeeperDecision;
        this.callbackOddsPercentage = callbackOddsPercentage;
        this.dealbreakerCount = dealbreakerCount;
        this.dealbreakersList = dealbreakersList;
        this.optimizedSkillBullets = optimizedSkillBullets;
        this.detectedSkills = detectedSkills;
        this.matchedSkills = matchedSkills;
        this.missingSkills = missingSkills;
        this.extractedExperienceYears = extractedExperienceYears;
        this.detectedEducation = detectedEducation;
        this.resumeRawText = resumeRawText;
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

    public double getSoftSkillScore() { return softSkillScore; }
    public void setSoftSkillScore(double softSkillScore) { this.softSkillScore = softSkillScore; }

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

    public String getGatekeeperDecision() { return gatekeeperDecision; }
    public void setGatekeeperDecision(String gatekeeperDecision) { this.gatekeeperDecision = gatekeeperDecision; }

    public int getCallbackOddsPercentage() { return callbackOddsPercentage; }
    public void setCallbackOddsPercentage(int callbackOddsPercentage) { this.callbackOddsPercentage = callbackOddsPercentage; }

    public int getDealbreakerCount() { return dealbreakerCount; }
    public void setDealbreakerCount(int dealbreakerCount) { this.dealbreakerCount = dealbreakerCount; }

    public List<String> getDealbreakersList() { return dealbreakersList; }
    public void setDealbreakersList(List<String> dealbreakersList) { this.dealbreakersList = dealbreakersList; }

    public List<String> getOptimizedSkillBullets() { return optimizedSkillBullets; }
    public void setOptimizedSkillBullets(List<String> optimizedSkillBullets) { this.optimizedSkillBullets = optimizedSkillBullets; }

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

    public String getResumeRawText() { return resumeRawText; }
    public void setResumeRawText(String resumeRawText) { this.resumeRawText = resumeRawText; }

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
        private double softSkillScore;
        private double semanticScore;
        private double experienceScore;
        private double educationScore;
        private String scoreTier;
        private String tierColorHex;
        private String gatekeeperDecision;
        private int callbackOddsPercentage;
        private int dealbreakerCount;
        private List<String> dealbreakersList;
        private List<String> optimizedSkillBullets;
        private List<String> detectedSkills;
        private List<String> matchedSkills;
        private List<String> missingSkills;
        private int extractedExperienceYears;
        private String detectedEducation;
        private String resumeRawText;
        private List<String> atsRecommendations;
        private String aiSummaryAdvice;
        private boolean processedWithAi;

        public Builder candidateName(String candidateName) { this.candidateName = candidateName; return this; }
        public Builder candidateEmail(String candidateEmail) { this.candidateEmail = candidateEmail; return this; }
        public Builder candidatePhone(String candidatePhone) { this.candidatePhone = candidatePhone; return this; }
        public Builder jobTitle(String jobTitle) { this.jobTitle = jobTitle; return this; }
        public Builder overallScore(double overallScore) { this.overallScore = overallScore; return this; }
        public Builder skillMatchScore(double skillMatchScore) { this.skillMatchScore = skillMatchScore; return this; }
        public Builder softSkillScore(double softSkillScore) { this.softSkillScore = softSkillScore; return this; }
        public Builder semanticScore(double semanticScore) { this.semanticScore = semanticScore; return this; }
        public Builder experienceScore(double experienceScore) { this.experienceScore = experienceScore; return this; }
        public Builder educationScore(double educationScore) { this.educationScore = educationScore; return this; }
        public Builder scoreTier(String scoreTier) { this.scoreTier = scoreTier; return this; }
        public Builder tierColorHex(String tierColorHex) { this.tierColorHex = tierColorHex; return this; }
        public Builder gatekeeperDecision(String gatekeeperDecision) { this.gatekeeperDecision = gatekeeperDecision; return this; }
        public Builder callbackOddsPercentage(int callbackOddsPercentage) { this.callbackOddsPercentage = callbackOddsPercentage; return this; }
        public Builder dealbreakerCount(int dealbreakerCount) { this.dealbreakerCount = dealbreakerCount; return this; }
        public Builder dealbreakersList(List<String> dealbreakersList) { this.dealbreakersList = dealbreakersList; return this; }
        public Builder optimizedSkillBullets(List<String> optimizedSkillBullets) { this.optimizedSkillBullets = optimizedSkillBullets; return this; }
        public Builder detectedSkills(List<String> detectedSkills) { this.detectedSkills = detectedSkills; return this; }
        public Builder matchedSkills(List<String> matchedSkills) { this.matchedSkills = matchedSkills; return this; }
        public Builder missingSkills(List<String> missingSkills) { this.missingSkills = missingSkills; return this; }
        public Builder extractedExperienceYears(int extractedExperienceYears) { this.extractedExperienceYears = extractedExperienceYears; return this; }
        public Builder detectedEducation(String detectedEducation) { this.detectedEducation = detectedEducation; return this; }
        public Builder resumeRawText(String resumeRawText) { this.resumeRawText = resumeRawText; return this; }
        public Builder atsRecommendations(List<String> atsRecommendations) { this.atsRecommendations = atsRecommendations; return this; }
        public Builder aiSummaryAdvice(String aiSummaryAdvice) { this.aiSummaryAdvice = aiSummaryAdvice; return this; }
        public Builder processedWithAi(boolean processedWithAi) { this.processedWithAi = processedWithAi; return this; }

        public MatchResponse build() {
            return new MatchResponse(candidateName, candidateEmail, candidatePhone, jobTitle, overallScore, skillMatchScore, softSkillScore, semanticScore, experienceScore, educationScore, scoreTier, tierColorHex, gatekeeperDecision, callbackOddsPercentage, dealbreakerCount, dealbreakersList, optimizedSkillBullets, detectedSkills, matchedSkills, missingSkills, extractedExperienceYears, detectedEducation, resumeRawText, atsRecommendations, aiSummaryAdvice, processedWithAi);
        }
    }
}
