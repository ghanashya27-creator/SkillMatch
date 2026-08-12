package com.resumeranker.dto;

import java.util.List;

public class JobDto {
    private Long id;
    private String title;
    private String company;
    private String department;
    private String experienceLevel;
    private List<String> requiredSkills;
    private List<String> preferredSkills;
    private String description;
    private boolean isPreset;

    public JobDto() {}

    public JobDto(Long id, String title, String company, String department, String experienceLevel, List<String> requiredSkills, List<String> preferredSkills, String description, boolean isPreset) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.department = department;
        this.experienceLevel = experienceLevel;
        this.requiredSkills = requiredSkills;
        this.preferredSkills = preferredSkills;
        this.description = description;
        this.isPreset = isPreset;
    }

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

    public List<String> getRequiredSkills() { return requiredSkills; }
    public void setRequiredSkills(List<String> requiredSkills) { this.requiredSkills = requiredSkills; }

    public List<String> getPreferredSkills() { return preferredSkills; }
    public void setPreferredSkills(List<String> preferredSkills) { this.preferredSkills = preferredSkills; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isPreset() { return isPreset; }
    public void setPreset(boolean preset) { isPreset = preset; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String title;
        private String company;
        private String department;
        private String experienceLevel;
        private List<String> requiredSkills;
        private List<String> preferredSkills;
        private String description;
        private boolean isPreset;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder company(String company) { this.company = company; return this; }
        public Builder department(String department) { this.department = department; return this; }
        public Builder experienceLevel(String experienceLevel) { this.experienceLevel = experienceLevel; return this; }
        public Builder requiredSkills(List<String> requiredSkills) { this.requiredSkills = requiredSkills; return this; }
        public Builder preferredSkills(List<String> preferredSkills) { this.preferredSkills = preferredSkills; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder isPreset(boolean isPreset) { this.isPreset = isPreset; return this; }

        public JobDto build() {
            return new JobDto(id, title, company, department, experienceLevel, requiredSkills, preferredSkills, description, isPreset);
        }
    }
}
