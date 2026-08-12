package com.resumeranker.dto;

public class MatchRequest {
    private Long jobId;
    private String customJobTitle;
    private String customJobDescription;
    private String resumeText;
    private String candidateName;
    private String groqApiKey;

    public MatchRequest() {}

    public MatchRequest(Long jobId, String customJobTitle, String customJobDescription, String resumeText, String candidateName, String groqApiKey) {
        this.jobId = jobId;
        this.customJobTitle = customJobTitle;
        this.customJobDescription = customJobDescription;
        this.resumeText = resumeText;
        this.candidateName = candidateName;
        this.groqApiKey = groqApiKey;
    }

    public Long getJobId() { return jobId; }
    public void setJobId(Long jobId) { this.jobId = jobId; }

    public String getCustomJobTitle() { return customJobTitle; }
    public void setCustomJobTitle(String customJobTitle) { this.customJobTitle = customJobTitle; }

    public String getCustomJobDescription() { return customJobDescription; }
    public void setCustomJobDescription(String customJobDescription) { this.customJobDescription = customJobDescription; }

    public String getResumeText() { return resumeText; }
    public void setResumeText(String resumeText) { this.resumeText = resumeText; }

    public String getCandidateName() { return candidateName; }
    public void setCandidateName(String candidateName) { this.candidateName = candidateName; }

    public String getGroqApiKey() { return groqApiKey; }
    public void setGroqApiKey(String groqApiKey) { this.groqApiKey = groqApiKey; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long jobId;
        private String customJobTitle;
        private String customJobDescription;
        private String resumeText;
        private String candidateName;
        private String groqApiKey;

        public Builder jobId(Long jobId) { this.jobId = jobId; return this; }
        public Builder customJobTitle(String customJobTitle) { this.customJobTitle = customJobTitle; return this; }
        public Builder customJobDescription(String customJobDescription) { this.customJobDescription = customJobDescription; return this; }
        public Builder resumeText(String resumeText) { this.resumeText = resumeText; return this; }
        public Builder candidateName(String candidateName) { this.candidateName = candidateName; return this; }
        public Builder groqApiKey(String groqApiKey) { this.groqApiKey = groqApiKey; return this; }

        public MatchRequest build() {
            return new MatchRequest(jobId, customJobTitle, customJobDescription, resumeText, candidateName, groqApiKey);
        }
    }
}
