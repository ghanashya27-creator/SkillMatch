package com.resumeranker.service;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SkillOntologyService {

    private final Set<String> KNOWN_SKILLS = new LinkedHashSet<>(Arrays.asList(
        // Programming Languages
        "Java", "Python", "JavaScript", "TypeScript", "C++", "C#", "Go", "Rust", "Kotlin", "Swift", "PHP", "Ruby", "SQL", "HTML", "CSS", "R",
        // Frameworks & Libraries
        "Spring Boot", "Spring Cloud", "React", "Angular", "Vue.js", "Next.js", "Node.js", "Express", "Django", "Flask", "FastAPI", ".NET", "Hibernate", "JPA", "Redux", "Tailwind CSS", "Bootstrap",
        // Cloud & DevOps
        "AWS", "Azure", "GCP", "Docker", "Kubernetes", "Jenkins", "Terraform", "Ansible", "CI/CD", "Linux", "Git", "GitHub Actions", "Nginx", "Helm",
        // Databases & Cache
        "PostgreSQL", "MySQL", "MongoDB", "Redis", "Elasticsearch", "Oracle", "DynamoDB", "Cassandra", "Supabase", "Firebase", "Kafka", "RabbitMQ",
        // AI / ML & Data Science
        "Machine Learning", "Deep Learning", "PyTorch", "TensorFlow", "Pandas", "NumPy", "Scikit-Learn", "NLP", "LLM", "Generative AI", "Groq", "OpenAI", "LangChain", "Data Analysis",
        // Architecture & Concepts
        "REST API", "GraphQL", "Microservices", "System Design", "OOP", "Agile", "Scrum", "TDD", "Unit Testing", "Security", "OAuth2", "JWT",
        // Soft Skills
        "Leadership", "Communication", "Problem Solving", "Teamwork", "Project Management", "Time Management", "Critical Thinking"
    ));

    private final Set<String> SOFT_SKILLS = new LinkedHashSet<>(Arrays.asList(
        "Leadership", "Communication", "Problem Solving", "Teamwork", "Project Management", "Time Management", "Critical Thinking", "Agile", "Scrum"
    ));

    public List<String> extractSkills(String text) {
        if (text == null || text.trim().isEmpty()) return Collections.emptyList();
        
        List<String> matchedSkills = new ArrayList<>();
        String normalizedText = text.toLowerCase();

        for (String skill : KNOWN_SKILLS) {
            String escapedSkill = Pattern.quote(skill.toLowerCase());
            Pattern pattern = Pattern.compile("\\b" + escapedSkill + "\\b");
            Matcher matcher = pattern.matcher(normalizedText);
            if (matcher.find()) {
                matchedSkills.add(skill);
            }
        }
        return matchedSkills;
    }

    public List<String> extractSoftSkills(String text) {
        if (text == null || text.trim().isEmpty()) return Collections.emptyList();
        List<String> matched = new ArrayList<>();
        String normalizedText = text.toLowerCase();
        for (String skill : SOFT_SKILLS) {
            String escapedSkill = Pattern.quote(skill.toLowerCase());
            Pattern pattern = Pattern.compile("\\b" + escapedSkill + "\\b");
            Matcher matcher = pattern.matcher(normalizedText);
            if (matcher.find()) {
                matched.add(skill);
            }
        }
        return matched;
    }

    public Set<String> getKnownSkills() {
        return KNOWN_SKILLS;
    }
}
