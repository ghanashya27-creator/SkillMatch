package com.resumeranker.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "resumes")
public class ResumeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String candidateName;

    @Column(length = 150)
    private String email;

    @Column(length = 50)
    private String phone;

    @Column(length = 200)
    private String fileName;

    @Column(columnDefinition = "TEXT")
    private String detectedSkills;

    private int extractedExperienceYears;

    @Column(length = 100)
    private String educationDegree;

    @Column(columnDefinition = "TEXT")
    private String rawText;

    private LocalDateTime uploadedAt;

    public ResumeEntity() {}

    public ResumeEntity(Long id, String candidateName, String email, String phone, String fileName, String detectedSkills, int extractedExperienceYears, String educationDegree, String rawText, LocalDateTime uploadedAt) {
        this.id = id;
        this.candidateName = candidateName;
        this.email = email;
        this.phone = phone;
        this.fileName = fileName;
        this.detectedSkills = detectedSkills;
        this.extractedExperienceYears = extractedExperienceYears;
        this.educationDegree = educationDegree;
        this.rawText = rawText;
        this.uploadedAt = uploadedAt;
    }

    @PrePersist
    protected void onCreate() {
        if (uploadedAt == null) {
            uploadedAt = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCandidateName() { return candidateName; }
    public void setCandidateName(String candidateName) { this.candidateName = candidateName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getDetectedSkills() { return detectedSkills; }
    public void setDetectedSkills(String detectedSkills) { this.detectedSkills = detectedSkills; }

    public int getExtractedExperienceYears() { return extractedExperienceYears; }
    public void setExtractedExperienceYears(int extractedExperienceYears) { this.extractedExperienceYears = extractedExperienceYears; }

    public String getEducationDegree() { return educationDegree; }
    public void setEducationDegree(String educationDegree) { this.educationDegree = educationDegree; }

    public String getRawText() { return rawText; }
    public void setRawText(String rawText) { this.rawText = rawText; }

    public LocalDateTime getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String candidateName;
        private String email;
        private String phone;
        private String fileName;
        private String detectedSkills;
        private int extractedExperienceYears;
        private String educationDegree;
        private String rawText;
        private LocalDateTime uploadedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder candidateName(String candidateName) { this.candidateName = candidateName; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder phone(String phone) { this.phone = phone; return this; }
        public Builder fileName(String fileName) { this.fileName = fileName; return this; }
        public Builder detectedSkills(String detectedSkills) { this.detectedSkills = detectedSkills; return this; }
        public Builder extractedExperienceYears(int extractedExperienceYears) { this.extractedExperienceYears = extractedExperienceYears; return this; }
        public Builder educationDegree(String educationDegree) { this.educationDegree = educationDegree; return this; }
        public Builder rawText(String rawText) { this.rawText = rawText; return this; }
        public Builder uploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; return this; }

        public ResumeEntity build() {
            return new ResumeEntity(id, candidateName, email, phone, fileName, detectedSkills, extractedExperienceYears, educationDegree, rawText, uploadedAt);
        }
    }
}
