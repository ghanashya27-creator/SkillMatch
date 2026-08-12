package com.resumeranker.dto;

import java.util.List;

public class RankResponse {
    private String jobTitle;
    private int totalCandidatesProcessed;
    private List<MatchResponse> rankedCandidates;

    public RankResponse() {}

    public RankResponse(String jobTitle, int totalCandidatesProcessed, List<MatchResponse> rankedCandidates) {
        this.jobTitle = jobTitle;
        this.totalCandidatesProcessed = totalCandidatesProcessed;
        this.rankedCandidates = rankedCandidates;
    }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public int getTotalCandidatesProcessed() { return totalCandidatesProcessed; }
    public void setTotalCandidatesProcessed(int totalCandidatesProcessed) { this.totalCandidatesProcessed = totalCandidatesProcessed; }

    public List<MatchResponse> getRankedCandidates() { return rankedCandidates; }
    public void setRankedCandidates(List<MatchResponse> rankedCandidates) { this.rankedCandidates = rankedCandidates; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String jobTitle;
        private int totalCandidatesProcessed;
        private List<MatchResponse> rankedCandidates;

        public Builder jobTitle(String jobTitle) { this.jobTitle = jobTitle; return this; }
        public Builder totalCandidatesProcessed(int totalCandidatesProcessed) { this.totalCandidatesProcessed = totalCandidatesProcessed; return this; }
        public Builder rankedCandidates(List<MatchResponse> rankedCandidates) { this.rankedCandidates = rankedCandidates; return this; }

        public RankResponse build() {
            return new RankResponse(jobTitle, totalCandidatesProcessed, rankedCandidates);
        }
    }
}
