package com.resumeranker.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TfidfMatchingEngine {

    private static final Set<String> STOP_WORDS = new HashSet<>(Arrays.asList(
        "a", "about", "above", "after", "again", "against", "all", "am", "an", "and", "any", "are", "aren't", "as", "at",
        "be", "because", "been", "before", "being", "below", "between", "both", "but", "by", "can", "cannot", "could",
        "did", "do", "does", "doing", "down", "during", "each", "few", "for", "from", "further", "had", "has", "have",
        "having", "he", "her", "here", "hers", "herself", "him", "himself", "his", "how", "i", "if", "in", "into", "is",
        "it", "its", "itself", "just", "me", "more", "most", "my", "myself", "no", "nor", "not", "of", "off", "on", "once",
        "only", "or", "other", "our", "ours", "ourselves", "out", "over", "own", "same", "she", "should", "so", "some",
        "such", "than", "that", "the", "their", "theirs", "them", "themselves", "then", "there", "these", "they", "this",
        "those", "through", "to", "too", "under", "until", "up", "very", "was", "we", "were", "what", "when", "where",
        "which", "while", "who", "whom", "why", "with", "would", "you", "your", "yours", "yourself", "yourselves"
    ));

    public double calculateCosineSimilarity(String text1, String text2) {
        if (text1 == null || text2 == null || text1.trim().isEmpty() || text2.trim().isEmpty()) {
            return 0.0;
        }

        List<String> tokens1 = tokenizeAndClean(text1);
        List<String> tokens2 = tokenizeAndClean(text2);

        Map<String, Integer> freq1 = getTermFrequencies(tokens1);
        Map<String, Integer> freq2 = getTermFrequencies(tokens2);

        Set<String> allWords = new HashSet<>();
        allWords.addAll(freq1.keySet());
        allWords.addAll(freq2.keySet());

        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for (String word : allWords) {
            int count1 = freq1.getOrDefault(word, 0);
            int count2 = freq2.getOrDefault(word, 0);

            dotProduct += count1 * count2;
            normA += Math.pow(count1, 2);
            normB += Math.pow(count2, 2);
        }

        if (normA == 0.0 || normB == 0.0) {
            return 0.0;
        }

        double similarity = dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
        return Math.min(100.0, Math.max(0.0, similarity * 100.0));
    }

    private List<String> tokenizeAndClean(String text) {
        String cleaned = text.toLowerCase().replaceAll("[^a-z0-9\\s]", " ");
        String[] words = cleaned.split("\\s+");
        List<String> result = new ArrayList<>();
        for (String word : words) {
            if (word.length() > 1 && !STOP_WORDS.contains(word)) {
                result.add(word);
            }
        }
        return result;
    }

    private Map<String, Integer> getTermFrequencies(List<String> tokens) {
        Map<String, Integer> freqs = new HashMap<>();
        for (String token : tokens) {
            freqs.put(token, freqs.getOrDefault(token, 0) + 1);
        }
        return freqs;
    }
}
