package com.resumeranker.service;

import com.resumeranker.entity.JobEntity;
import com.resumeranker.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final JobRepository jobRepository;

    @Autowired
    public DataInitializer(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public void run(String... args) {
        if (jobRepository.count() == 0) {
            seedInitialJobs();
        }
    }

    private void seedInitialJobs() {
        JobEntity javaFullstack = JobEntity.builder()
                .title("Senior Java Fullstack Engineer")
                .company("Acme Enterprise Tech")
                .department("Engineering")
                .experienceLevel("Senior (5+ yrs)")
                .requiredSkills("Java, Spring Boot, React, REST API, SQL, PostgreSQL, Docker, Git, Microservices")
                .preferredSkills("AWS, Kubernetes, Redis, Kafka, TypeScript, CI/CD, Unit Testing")
                .description("We are looking for a Senior Java Fullstack Engineer to design and build scalable cloud-native microservices using Spring Boot and high-performance React frontends. Requirements: 5+ years of software development experience with Java, Spring Boot, RESTful APIs, relational databases (PostgreSQL/MySQL), and modern JavaScript/React. Experience with Docker, Kubernetes, and AWS is a major plus.")
                .isPreset(true)
                .build();

        JobEntity dataScientist = JobEntity.builder()
                .title("AI / Machine Learning Engineer")
                .company("DeepInsight AI")
                .department("AI Research")
                .experienceLevel("Mid-Senior (3+ yrs)")
                .requiredSkills("Python, PyTorch, TensorFlow, Machine Learning, Deep Learning, NLP, LLM, SQL, Pandas")
                .preferredSkills("Docker, FastAPI, Scikit-Learn, AWS, Groq, LangChain, Git")
                .description("Seeking a passionate AI / ML Engineer to develop state-of-the-art NLP and Generative AI applications. Key responsibilities include training deep neural networks with PyTorch/TensorFlow, fine-tuning Large Language Models (LLMs), and deploying scalable inference pipelines via FastAPI and Docker. Proficiency in Python, Pandas, SQL, and Machine Learning algorithms is required.")
                .isPreset(true)
                .build();

        JobEntity devOps = JobEntity.builder()
                .title("DevOps & Cloud Infrastructure Specialist")
                .company("CloudScale Ops")
                .department("Infrastructure")
                .experienceLevel("Mid-Level (3+ yrs)")
                .requiredSkills("AWS, Docker, Kubernetes, Terraform, CI/CD, Jenkins, Linux, Shell, Git")
                .preferredSkills("Ansible, Prometheus, Grafana, GCP, Python, Security, Helm")
                .description("Join our cloud platform team as a DevOps Engineer. You will automate CI/CD pipelines, manage Kubernetes clusters on AWS, write Infrastructure-as-Code using Terraform, and optimize system monitoring and reliability. Must have strong Linux, Docker, Kubernetes, and cloud security experience.")
                .isPreset(true)
                .build();

        JobEntity frontend = JobEntity.builder()
                .title("Lead Frontend Architect (React / TypeScript)")
                .company("Nova UI Labs")
                .department("Frontend Core")
                .experienceLevel("Senior (5+ yrs)")
                .requiredSkills("React, TypeScript, JavaScript, HTML, CSS, Redux, Next.js, REST API, Web Performance")
                .preferredSkills("Tailwind CSS, GraphQL, Jest, Micro-frontends, Vite, UI/UX Design")
                .description("Looking for a Lead Frontend Architect to champion UI design excellence and build high-performance web applications using React, TypeScript, and Next.js. You will lead component library design, optimize rendering speeds, and integrate sleek REST and GraphQL endpoints.")
                .isPreset(true)
                .build();

        JobEntity pm = JobEntity.builder()
                .title("Technical Product Manager")
                .company("Vanguard Digital")
                .department("Product")
                .experienceLevel("Mid-Senior (4+ yrs)")
                .requiredSkills("Product Management, Agile, Scrum, Technical Writing, Data Analysis, System Design, Communication, Leadership")
                .preferredSkills("SQL, Jira, Roadmap Strategy, User Research, API Integration")
                .description("We are seeking a Technical Product Manager to lead cross-functional engineering teams in shipping innovative software products. Responsibilities include defining product roadmaps, gathering stakeholder requirements, facilitating Agile Scrum sprints, and tracking user engagement metrics.")
                .isPreset(true)
                .build();

        jobRepository.saveAll(List.of(javaFullstack, dataScientist, devOps, frontend, pm));
    }
}
