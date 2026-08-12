package com.resumeranker.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ResumeParserService {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    private static final Pattern PHONE_PATTERN = Pattern.compile("(\\+?\\d{1,3}[- .]?)?\\(?\\d{3}\\)?[- .]?\\d{3}[- .]?\\d{4}");
    private static final Pattern EXP_PATTERN = Pattern.compile("(?i)(\\d{1,2})\\+?\\s*(?:years?|yrs?)\\s*(?:of)?\\s*(?:experience|exp)");
    private static final Pattern DEGREE_PATTERN = Pattern.compile("(?i)\\b(B\\.S\\.|M\\.S\\.|B\\.E\\.|M\\.E\\.|B\\.Tech|M\\.Tech|Bachelor|Master|Ph\\.D|Doctorate|Diploma|Associate)\\b");

    public String extractText(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty");
        }

        String fileName = file.getOriginalFilename() != null ? file.getOriginalFilename().toLowerCase() : "";
        
        try {
            if (fileName.endsWith(".pdf")) {
                return parsePdf(file.getInputStream());
            } else {
                // Default to text file parsing
                return new String(file.getBytes(), StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse resume file (" + fileName + "): " + e.getMessage());
        }
    }

    private String parsePdf(InputStream inputStream) throws IOException {
        byte[] bytes = inputStream.readAllBytes();
        try (PDDocument document = Loader.loadPDF(bytes)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    public String extractEmail(String text) {
        if (text == null) return "N/A";
        Matcher matcher = EMAIL_PATTERN.matcher(text);
        return matcher.find() ? matcher.group() : "N/A";
    }

    public String extractPhone(String text) {
        if (text == null) return "N/A";
        Matcher matcher = PHONE_PATTERN.matcher(text);
        return matcher.find() ? matcher.group() : "N/A";
    }

    public String extractCandidateName(String text, String fallbackFileName) {
        if (text == null || text.trim().isEmpty()) {
            return cleanFileName(fallbackFileName);
        }
        
        // Take the first non-empty line as probable candidate name if short
        String[] lines = text.split("\\r?\\n");
        for (String line : lines) {
            String trimmed = line.trim();
            if (!trimmed.isEmpty() && trimmed.length() < 50 && !trimmed.contains("@") && !trimmed.matches(".*\\d.*")) {
                return trimmed;
            }
        }
        return cleanFileName(fallbackFileName);
    }

    private String cleanFileName(String fileName) {
        if (fileName == null || fileName.isEmpty()) return "Candidate Profile";
        String nameWithoutExt = fileName.replaceAll("(?i)\\.(pdf|txt|docx)$", "");
        return nameWithoutExt.replaceAll("[_\\-]", " ");
    }

    public int extractExperienceYears(String text) {
        if (text == null) return 1;
        Matcher matcher = EXP_PATTERN.matcher(text);
        int maxYears = 0;
        while (matcher.find()) {
            try {
                int yrs = Integer.parseInt(matcher.group(1));
                if (yrs > maxYears && yrs < 45) {
                    maxYears = yrs;
                }
            } catch (NumberFormatException ignored) {}
        }
        
        if (maxYears > 0) return maxYears;

        // Fallback: estimate from date ranges like 2018 - 2024
        Pattern yearRangePattern = Pattern.compile("\\b(20[0-2][0-9]|19[89][0-9])\\s*[-–—to]+\\s*(20[0-2][0-9]|Present|Current)\\b", Pattern.CASE_INSENSITIVE);
        Matcher rangeMatcher = yearRangePattern.matcher(text);
        int estimatedYears = 0;
        int currentYear = 2026;
        while (rangeMatcher.find()) {
            try {
                int start = Integer.parseInt(rangeMatcher.group(1));
                String endStr = rangeMatcher.group(2);
                int end = (endStr.equalsIgnoreCase("Present") || endStr.equalsIgnoreCase("Current")) ? currentYear : Integer.parseInt(endStr);
                if (end >= start) {
                    estimatedYears += (end - start);
                }
            } catch (Exception ignored) {}
        }

        return Math.max(estimatedYears, 2); // Default estimate if unspecified
    }

    public String extractEducation(String text) {
        if (text == null) return "Bachelor's Degree";
        Matcher matcher = DEGREE_PATTERN.matcher(text);
        if (matcher.find()) {
            String degree = matcher.group(1);
            if (degree.equalsIgnoreCase("Ph.D") || degree.equalsIgnoreCase("Doctorate")) return "Doctorate / Ph.D";
            if (degree.equalsIgnoreCase("M.S.") || degree.equalsIgnoreCase("M.E.") || degree.equalsIgnoreCase("M.Tech") || degree.equalsIgnoreCase("Master")) return "Master's Degree";
            if (degree.equalsIgnoreCase("B.S.") || degree.equalsIgnoreCase("B.E.") || degree.equalsIgnoreCase("B.Tech") || degree.equalsIgnoreCase("Bachelor")) return "Bachelor's Degree";
            return degree;
        }
        return "Bachelor's Degree";
    }
}
