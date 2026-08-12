package com.resumeranker.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "match_history")
public class MatchHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String candidateName;

    @Column(length = 200)
    private String jobTitle;

    private double overallScore;
    private double skillMatchScore;
    private double semanticScore;
    private double experienceScore;
    private double educationScore;

    @Column(length = 50)
    private String scoreTier;

    @Column(columnDefinition = "TEXT")
    private String matchedSkills;

    @Column(columnDefinition = "TEXT")
    private String missingSkills;

    @Column(columnDefinition = "TEXT")
    private String recommendations;

    private boolean processedWithAi;

    private LocalDateTime createdAt;

    public MatchHistoryEntity() {}

    public MatchHistoryEntity(Long id, String candidateName, String jobTitle, double overallScore, double skillMatchScore, double semanticScore, double experienceScore, double educationScore, String scoreTier, String matchedSkills, String missingSkills, String recommendations, boolean processedWithAi, LocalDateTime createdAt) {
        this.id = id;
        this.candidateName = candidateName;
        this.jobTitle = jobTitle;
        this.overallScore = overallScore;
        this.skillMatchScore = skillMatchScore;
        this.semanticScore = semanticScore;
        this.experienceScore = experienceScore;
        this.educationScore = educationScore;
        this.scoreTier = scoreTier;
        this.matchedSkills = matchedSkills;
        this.missingSkills = missingSkills;
        this.recommendations = recommendations;
        this.processedWithAi = processedWithAi;
        this.createdAt = createdAt;
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCandidateName() { return candidateName; }
    public void setCandidateName(String candidateName) { this.candidateName = candidateName; }

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

    public String getMatchedSkills() { return matchedSkills; }
    public void setMatchedSkills(String matchedSkills) { this.matchedSkills = matchedSkills; }

    public String getMissingSkills() { return missingSkills; }
    public void setMissingSkills(String missingSkills) { this.missingSkills = missingSkills; }

    public String getRecommendations() { return recommendations; }
    public void setRecommendations(String recommendations) { this.recommendations = recommendations; }

    public boolean isProcessedWithAi() { return processedWithAi; }
    public void setProcessedWithAi(boolean processedWithAi) { this.processedWithAi = processedWithAi; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String candidateName;
        private String jobTitle;
        private double overallScore;
        private double skillMatchScore;
        private double semanticScore;
        private double experienceScore;
        private double educationScore;
        private String scoreTier;
        private String matchedSkills;
        private String missingSkills;
        private String recommendations;
        private boolean processedWithAi;
        private LocalDateTime createdAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder candidateName(String candidateName) { this.candidateName = candidateName; return this; }
        public Builder jobTitle(String jobTitle) { this.jobTitle = jobTitle; return this; }
        public Builder overallScore(double overallScore) { this.overallScore = overallScore; return this; }
        public Builder skillMatchScore(double skillMatchScore) { this.skillMatchScore = skillMatchScore; return this; }
        public Builder semanticScore(double semanticScore) { this.semanticScore = semanticScore; return this; }
        public Builder experienceScore(double experienceScore) { this.experienceScore = experienceScore; return this; }
        public Builder educationScore(double educationScore) { this.educationScore = educationScore; return this; }
        public Builder scoreTier(String scoreTier) { this.scoreTier = scoreTier; return this; }
        public Builder matchedSkills(String matchedSkills) { this.matchedSkills = matchedSkills; return this; }
        public Builder missingSkills(String missingSkills) { this.missingSkills = missingSkills; return this; }
        public Builder recommendations(String recommendations) { this.recommendations = recommendations; return this; }
        public Builder processedWithAi(boolean processedWithAi) { this.processedWithAi = processedWithAi; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public MatchHistoryEntity build() {
            return new MatchHistoryEntity(id, candidateName, jobTitle, overallScore, skillMatchScore, semanticScore, experienceScore, educationScore, scoreTier, matchedSkills, missingSkills, recommendations, processedWithAi, createdAt);
        }
    }
}
