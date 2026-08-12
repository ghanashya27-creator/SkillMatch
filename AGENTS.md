# AGENTS.md — System Architecture & AI Agent Operating Guidelines

This document outlines the architectural principles, layer responsibilities, design system rules, and engineering protocols for developers and AI agents working on **SkillMatch**.

---

## ✦ System Overview

**SkillMatch** is a fullstack Java Spring Boot application that evaluates, scores, and ranks candidate resumes against target job specifications using a hybrid matching engine.

* **Backend Architecture**: Java 17+ with Spring Boot 3, Spring Data JPA, Spring Security, Apache PDFBox, and OpenAPI 3.
* **Frontend Architecture**: Vanilla HTML5, CSS3 Glassmorphism System (`DESIGN.md`), and ES6+ Single Page Application controller (`app.js`).
* **Database Layer**: Spring Data JPA abstraction with zero-configuration embedded database fallback for local execution and PostgreSQL support for cloud production.

---

## 📐 Architectural Principles & Scoping

1. **Strict Layered Separation**:
   * `controller`: Handles REST endpoints (`ApiController.java`). Enforces input validation, HTTP status codes, and Swagger OpenAPI annotations.
   * `service`: Encapsulates business logic, including document text parsing (`ResumeParserService`), TF-IDF Cosine Similarity calculations (`TfidfMatchingEngine`), skill taxonomy extraction (`SkillOntologyService`), and optional Groq AI LLM feedback (`GroqAiService`).
   * `entity` & `repository`: Manages JPA database models (`JobEntity`, `ResumeEntity`, `MatchHistoryEntity`) and Spring Data persistence interfaces.
   * `dto`: Implements strict Data Transfer Objects (`MatchRequest`, `MatchResponse`, `RankResponse`, `JobDto`) with explicit constructors, getters, setters, and builder patterns.
   * `config` & `exception`: Configures Spring Security policies, CORS handling, and centralized `@ControllerAdvice` error masking.

2. **Error Handling & Resilience**:
   * No raw stack trace exposure in public API responses.
   * All runtime and validation exceptions are intercepted by `GlobalExceptionHandler` and converted into structured JSON payloads (`timestamp`, `status`, `error`, `message`).
   * Defensive text extraction handles missing contact details, unformatted experience ranges, or corrupted document streams gracefully.

3. **Design System Integrity (`DESIGN.md`)**:
   * The web interface must strictly maintain the **Dimension** dusk-lit workspace reference:
     - **Canvas Base**: `#0a0a0a` (`--color-void-canvas`).
     - **Elevated Surfaces**: `#161616` (`--color-graphite`).
     - **Frosted Panels**: `rgba(212, 212, 212, 0.08)` with `backdrop-filter: blur(8px)`.
     - **Controls & Buttons**: `9999px` full pill radius (`border-radius: var(--radius-buttons)`).
     - **Primary Action Fill**: Snow white background (`#ffffff` bg, `#000000` text).
     - **Accent Wash**: Dusk violet `#6b62f2` used exclusively as gradient glows, spotlight highlights, or pill borders.

---

## ⚙️ Core Workflows to Preserve

1. **Candidate Single Match Workflow**:
   * Accepts PDF/TXT resume uploads or raw text input.
   * Extracts text, contact metadata, detected skills, experience years, and education level.
   * Calculates a weighted score: **40% Skill Match + 35% Semantic TF-IDF + 15% Experience Fit + 10% Education Score**.
   * Identifies matched vs. missing skills and generates actionable ATS improvement bullet points.

2. **Recruiter Bulk Leaderboard Workflow**:
   * Accepts multi-resume batch uploads for a target job specification.
   * Executes batch evaluations in sequence.
   * Produces a candidate leaderboard sorted by overall compatibility percentage.
   * Generates downloadable CSV ranking reports.

---

## 🐙 Code Contribution Standards

* Maintain clean, conventional commit messages prefixed by scope (`feat`, `fix`, `docs`, `style`, `refactor`).
* Keep commits modular and atomic, grouping related functional changes together.
* Preserve document docstrings, OpenAPI annotations, and inline code documentation across refactoring tasks.
