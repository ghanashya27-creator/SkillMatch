package com.resumeranker.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 150)
    private String company;

    @Column(length = 100)
    private String department;

    @Column(length = 50)
    private String experienceLevel;

    @Column(columnDefinition = "TEXT")
    private String requiredSkills;

    @Column(columnDefinition = "TEXT")
    private String preferredSkills;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    private boolean isPreset;

    private LocalDateTime createdAt;

    public JobEntity() {}

    public JobEntity(Long id, String title, String company, String department, String experienceLevel, String requiredSkills, String preferredSkills, String description, boolean isPreset, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.department = department;
        this.experienceLevel = experienceLevel;
        this.requiredSkills = requiredSkills;
        this.preferredSkills = preferredSkills;
        this.description = description;
        this.isPreset = isPreset;
        this.createdAt = createdAt;
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getExperienceLevel() { return experienceLevel; }
    public void setExperienceLevel(String experienceLevel) { this.experienceLevel = experienceLevel; }

    public String getRequiredSkills() { return requiredSkills; }
    public void setRequiredSkills(String requiredSkills) { this.requiredSkills = requiredSkills; }

    public String getPreferredSkills() { return preferredSkills; }
    public void setPreferredSkills(String preferredSkills) { this.preferredSkills = preferredSkills; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isPreset() { return isPreset; }
    public void setPreset(boolean preset) { isPreset = preset; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String title;
        private String company;
        private String department;
        private String experienceLevel;
        private String requiredSkills;
        private String preferredSkills;
        private String description;
        private boolean isPreset;
        private LocalDateTime createdAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder company(String company) { this.company = company; return this; }
        public Builder department(String department) { this.department = department; return this; }
        public Builder experienceLevel(String experienceLevel) { this.experienceLevel = experienceLevel; return this; }
        public Builder requiredSkills(String requiredSkills) { this.requiredSkills = requiredSkills; return this; }
        public Builder preferredSkills(String preferredSkills) { this.preferredSkills = preferredSkills; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder isPreset(boolean isPreset) { this.isPreset = isPreset; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public JobEntity build() {
            return new JobEntity(id, title, company, department, experienceLevel, requiredSkills, preferredSkills, description, isPreset, createdAt);
        }
    }
}
