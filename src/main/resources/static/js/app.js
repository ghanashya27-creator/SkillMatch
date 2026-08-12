/* ==========================================================================
   SkillMatch — Web Application Controller (JS)
   ========================================================================== */

document.addEventListener('DOMContentLoaded', () => {
    initTabs();
    initDropzones();
    loadJobs();
    loadHistory();
    initEventListeners();
});

let state = {
    jobs: [],
    selectedSingleFile: null,
    selectedBulkFiles: [],
    lastSingleResult: null,
    lastBulkResults: null
};

/* -------------------------------------------------------------------------- */
/* Tab Navigation                                                             */
/* -------------------------------------------------------------------------- */
function initTabs() {
    const tabButtons = document.querySelectorAll('.nav-tab-btn');
    const tabPanels = document.querySelectorAll('.tab-panel');

    tabButtons.forEach(btn => {
        btn.addEventListener('click', () => {
            const targetId = btn.getAttribute('data-tab');

            tabButtons.forEach(b => b.classList.remove('active'));
            tabPanels.forEach(p => p.classList.remove('active'));

            btn.classList.add('active');
            document.getElementById(targetId).classList.add('active');

            if (targetId === 'tab-history') {
                loadHistory();
            } else if (targetId === 'tab-jobs') {
                loadJobs();
            }
        });
    });
}

/* -------------------------------------------------------------------------- */
/* File Dropzones & Inputs                                                    */
/* -------------------------------------------------------------------------- */
function initDropzones() {
    // Single Resume Dropzone
    const dropzoneSingle = document.getElementById('dropzone-single');
    const fileSingleInput = document.getElementById('file-resume-single');
    const badgeSingle = document.getElementById('badge-file-single');

    dropzoneSingle.addEventListener('click', () => fileSingleInput.click());

    dropzoneSingle.addEventListener('dragover', (e) => {
        e.preventDefault();
        dropzoneSingle.classList.add('dragover');
    });

    dropzoneSingle.addEventListener('dragleave', () => dropzoneSingle.classList.remove('dragover'));

    dropzoneSingle.addEventListener('drop', (e) => {
        e.preventDefault();
        dropzoneSingle.classList.remove('dragover');
        if (e.dataTransfer.files.length > 0) {
            state.selectedSingleFile = e.dataTransfer.files[0];
            badgeSingle.textContent = `📄 ${state.selectedSingleFile.name}`;
            badgeSingle.style.display = 'inline-block';
        }
    });

    fileSingleInput.addEventListener('change', (e) => {
        if (e.target.files.length > 0) {
            state.selectedSingleFile = e.target.files[0];
            badgeSingle.textContent = `📄 ${state.selectedSingleFile.name}`;
            badgeSingle.style.display = 'inline-block';
        }
    });

    // Bulk Resume Dropzone
    const dropzoneBulk = document.getElementById('dropzone-bulk');
    const fileBulkInput = document.getElementById('files-bulk');
    const badgeBulk = document.getElementById('badge-bulk-count');

    dropzoneBulk.addEventListener('click', () => fileBulkInput.click());

    dropzoneBulk.addEventListener('dragover', (e) => {
        e.preventDefault();
        dropzoneBulk.classList.add('dragover');
    });

    dropzoneBulk.addEventListener('dragleave', () => dropzoneBulk.classList.remove('dragover'));

    dropzoneBulk.addEventListener('drop', (e) => {
        e.preventDefault();
        dropzoneBulk.classList.remove('dragover');
        if (e.dataTransfer.files.length > 0) {
            state.selectedBulkFiles = Array.from(e.dataTransfer.files);
            badgeBulk.textContent = `${state.selectedBulkFiles.length} Files Selected`;
            badgeBulk.style.display = 'inline-block';
        }
    });

    fileBulkInput.addEventListener('change', (e) => {
        if (e.target.files.length > 0) {
            state.selectedBulkFiles = Array.from(e.target.files);
            badgeBulk.textContent = `${state.selectedBulkFiles.length} Files Selected`;
            badgeBulk.style.display = 'inline-block';
        }
    });
}

/* -------------------------------------------------------------------------- */
/* API Callers & Events                                                       */
/* -------------------------------------------------------------------------- */
function initEventListeners() {
    document.getElementById('btn-load-sample').addEventListener('click', loadSampleData);
    document.getElementById('btn-match-single').addEventListener('click', handleSingleMatch);
    document.getElementById('btn-rank-bulk').addEventListener('click', handleBulkMatch);
    document.getElementById('btn-export-csv').addEventListener('click', exportBulkCsv);
    document.getElementById('btn-refresh-history').addEventListener('click', loadHistory);

    // Modal & Print
    document.getElementById('btn-open-inspect').addEventListener('click', openKeywordInspector);
    document.getElementById('btn-close-inspect').addEventListener('click', () => {
        document.getElementById('modal-inspect').style.display = 'none';
    });
    document.getElementById('btn-print-report').addEventListener('click', () => window.print());

    // Recruiter Filter & Search Listeners
    document.getElementById('input-search-leaderboard').addEventListener('input', applyLeaderboardFilters);
    document.getElementById('select-filter-tier').addEventListener('change', applyLeaderboardFilters);
    document.getElementById('select-sort-by').addEventListener('change', applyLeaderboardFilters);

    // Job Posting Modal toggles
    document.getElementById('btn-open-create-job').addEventListener('click', () => {
        document.getElementById('box-create-job').style.display = 'block';
    });
    document.getElementById('btn-cancel-job').addEventListener('click', () => {
        document.getElementById('box-create-job').style.display = 'none';
    });
    document.getElementById('btn-save-job').addEventListener('click', handleCreateJob);
}

// Load Jobs list for dropdowns and cards
async function loadJobs() {
    try {
        const response = await fetch('/api/jobs');
        if (!response.ok) return;
        const jobs = await response.json();
        state.jobs = jobs;

        const selectSingle = document.getElementById('select-job-preset');
        const selectBulk = document.getElementById('select-bulk-job');

        selectSingle.innerHTML = '<option value="">-- Or Paste Custom Job Description Below --</option>';
        selectBulk.innerHTML = '';

        jobs.forEach(job => {
            const opt1 = document.createElement('option');
            opt1.value = job.id;
            opt1.textContent = `${job.title} (${job.company})`;
            selectSingle.appendChild(opt1);

            const opt2 = document.createElement('option');
            opt2.value = job.id;
            opt2.textContent = `${job.title} (${job.company})`;
            selectBulk.appendChild(opt2);
        });

        renderJobCards(jobs);
    } catch (err) {
        console.error('Failed to load jobs:', err);
    }
}

// 1-Click Sample Loader
async function loadSampleData() {
    try {
        const response = await fetch('/api/samples');
        if (!response.ok) return;
        const data = await response.json();

        const selectSingle = document.getElementById('select-job-preset');
        if (selectSingle.options.length > 1) {
            selectSingle.selectedIndex = 1;
        }

        document.getElementById('input-resume-text').value = data.sampleJavaResume;
        state.selectedSingleFile = null;
        document.getElementById('badge-file-single').style.display = 'none';

        alert('Sample Java Fullstack Engineer resume and job loaded into Candidate Matcher!');
    } catch (err) {
        alert('Could not load sample data.');
    }
}

// Handle Single Resume Match
async function handleSingleMatch() {
    const btn = document.getElementById('btn-match-single');
    const originalText = btn.innerHTML;

    const jobId = document.getElementById('select-job-preset').value;
    const customTitle = document.getElementById('input-custom-title').value;
    const customDesc = document.getElementById('input-custom-desc').value;
    const resumeText = document.getElementById('input-resume-text').value;
    const groqKey = document.getElementById('input-groq-key').value;

    if (!jobId && !customDesc.trim()) {
        alert('Please select a preset job position or paste a custom job description.');
        return;
    }

    if (!state.selectedSingleFile && !resumeText.trim()) {
        alert('Please upload a PDF/text resume file or paste raw resume text.');
        return;
    }

    btn.disabled = true;
    btn.innerHTML = '<span class="loading-spinner"></span> Analyzing Match...';

    const formData = new FormData();
    if (state.selectedSingleFile) {
        formData.append('file', state.selectedSingleFile);
    }
    if (resumeText) formData.append('resumeText', resumeText);
    if (jobId) formData.append('jobId', jobId);
    if (customTitle) formData.append('customJobTitle', customTitle);
    if (customDesc) formData.append('customJobDescription', customDesc);
    if (groqKey) formData.append('groqApiKey', groqKey);

    try {
        const response = await fetch('/api/match', {
            method: 'POST',
            body: formData
        });

        if (!response.ok) {
            const errJson = await response.json();
            throw new Error(errJson.message || 'Match evaluation failed');
        }

        const data = await response.json();
        state.lastSingleResult = data;
        renderSingleMatchResults(data);
    } catch (err) {
        alert('Error: ' + err.message);
    } finally {
        btn.disabled = false;
        btn.innerHTML = originalText;
    }
}

function renderSingleMatchResults(data) {
    document.getElementById('match-results-empty').style.display = 'none';
    document.getElementById('match-results-content').style.display = 'block';

    const scoreVal = Math.round(data.overallScore);
    document.getElementById('text-overall-score').textContent = `${scoreVal}%`;
    const circle = document.getElementById('score-circle-val');
    const circumference = 251.2;
    const offset = circumference - (scoreVal / 100) * circumference;
    circle.style.strokeDashoffset = offset;
    circle.style.stroke = data.tierColorHex || '#6b62f2';

    const tierBadge = document.getElementById('badge-match-tier');
    tierBadge.textContent = data.scoreTier;
    tierBadge.style.backgroundColor = (data.tierColorHex || '#6b62f2') + '25';
    tierBadge.style.color = data.tierColorHex || '#6b62f2';
    tierBadge.style.border = `1px solid ${data.tierColorHex || '#6b62f2'}`;

    const engineBadge = document.getElementById('badge-engine-type');
    engineBadge.textContent = data.processedWithAi ? '✦ Groq AI Enhanced' : 'Offline Local Engine';

    document.getElementById('text-candidate-name').textContent = data.candidateName || 'Candidate Profile';
    document.getElementById('text-candidate-meta').textContent = `${data.candidateEmail} • ${data.candidatePhone} • ${data.extractedExperienceYears} Yrs Exp • ${data.detectedEducation}`;

    document.getElementById('val-skill-score').textContent = `${Math.round(data.skillMatchScore)}%`;
    document.getElementById('val-semantic-score').textContent = `${Math.round(data.semanticScore)}%`;
    document.getElementById('val-exp-score').textContent = `${Math.round(data.experienceScore)}%`;
    document.getElementById('val-edu-score').textContent = `${Math.round(data.educationScore)}%`;

    // Render 5-Axis SVG Pentagon Radar Chart
    renderRadarChart({
        hardSkills: data.skillMatchScore,
        softSkills: data.softSkillScore || 75,
        semantic: data.semanticScore,
        experience: data.experienceScore,
        education: data.educationScore
    });

    const matchedContainer = document.getElementById('container-matched-skills');
    matchedContainer.innerHTML = '';
    if (data.matchedSkills && data.matchedSkills.length > 0) {
        data.matchedSkills.forEach(s => {
            const span = document.createElement('span');
            span.className = 'skill-pill matched';
            span.textContent = `✓ ${s}`;
            matchedContainer.appendChild(span);
        });
    } else {
        matchedContainer.innerHTML = '<span style="font-size:12px; color:var(--color-slate);">No specific skills matched</span>';
    }

    const missingContainer = document.getElementById('container-missing-skills');
    missingContainer.innerHTML = '';
    if (data.missingSkills && data.missingSkills.length > 0) {
        data.missingSkills.forEach(s => {
            const span = document.createElement('span');
            span.className = 'skill-pill missing';
            span.textContent = `⚠ ${s}`;
            missingContainer.appendChild(span);
        });
    } else {
        missingContainer.innerHTML = '<span style="font-size:12px; color:var(--color-slate);">No missing skill gaps detected!</span>';
    }

    const adviceList = document.getElementById('list-ats-advice');
    adviceList.innerHTML = '';
    if (data.atsRecommendations) {
        data.atsRecommendations.forEach(tip => {
            const li = document.createElement('li');
            li.className = 'advice-item';
            li.textContent = tip;
            adviceList.appendChild(li);
        });
    }

    const aiBox = document.getElementById('box-ai-summary');
    if (data.aiSummaryAdvice) {
        document.getElementById('text-ai-advice').textContent = data.aiSummaryAdvice;
        aiBox.style.display = 'block';
    } else {
        aiBox.style.display = 'none';
    }
}

// Draw 5-Axis Pentagon SVG Radar Chart
function renderRadarChart(metrics) {
    const svg = document.getElementById('svg-radar-chart');
    const cx = 110, cy = 100, radius = 70;
    const angles = [-Math.PI/2, -Math.PI/2 + (2*Math.PI/5), -Math.PI/2 + (4*Math.PI/5), -Math.PI/2 + (6*Math.PI/5), -Math.PI/2 + (8*Math.PI/5)];
    const labels = ["Hard Skill", "Soft Skill", "Semantic", "Experience", "Education"];
    const values = [metrics.hardSkills, metrics.softSkills, metrics.semantic, metrics.experience, metrics.education];

    let gridHtml = '';

    // Draw background concentric grid polygons (100%, 75%, 50%, 25%)
    [1.0, 0.75, 0.5, 0.25].forEach(scale => {
        let points = angles.map(a => `${cx + radius * scale * Math.cos(a)},${cy + radius * scale * Math.sin(a)}`).join(' ');
        gridHtml += `<polygon points="${points}" fill="none" stroke="rgba(255,255,255,0.1)" stroke-width="1" />`;
    });

    // Draw axis lines and labels
    angles.forEach((a, i) => {
        let x2 = cx + radius * Math.cos(a);
        let y2 = cy + radius * Math.sin(a);
        gridHtml += `<line x1="${cx}" y1="${cy}" x2="${x2}" y2="${y2}" stroke="rgba(255,255,255,0.15)" stroke-width="1" />`;

        let lx = cx + (radius + 15) * Math.cos(a);
        let ly = cy + (radius + 15) * Math.sin(a);
        gridHtml += `<text x="${lx}" y="${ly}" fill="#c2c2c2" font-size="9" text-anchor="middle" dominant-baseline="middle">${labels[i]}</text>`;
    });

    // Compute Candidate Score Polygon Points
    let scorePoints = angles.map((a, i) => {
        let normVal = Math.min(100, Math.max(10, values[i])) / 100.0;
        let px = cx + radius * normVal * Math.cos(a);
        let py = cy + radius * normVal * Math.sin(a);
        return `${px},${py}`;
    }).join(' ');

    let scorePolygon = `<polygon points="${scorePoints}" fill="rgba(107, 98, 242, 0.35)" stroke="#6b62f2" stroke-width="2" />`;

    svg.innerHTML = gridHtml + scorePolygon;
}

// Open Keyword Inspector Modal
function openKeywordInspector() {
    if (!state.lastSingleResult) {
        alert('Please evaluate a resume first.');
        return;
    }
    const data = state.lastSingleResult;
    const modal = document.getElementById('modal-inspect');
    const box = document.getElementById('box-inspected-text');

    let text = data.resumeRawText || 'No raw text stored.';
    let matched = data.matchedSkills || [];

    // Highlight matched skills in text
    matched.forEach(skill => {
        const regex = new RegExp(`\\b(${skill})\\b`, 'gi');
        text = text.replace(regex, `<span class="kw-matched">$1</span>`);
    });

    box.innerHTML = text;
    modal.style.display = 'flex';
}

// Handle Bulk Resume Ranking (Recruiter Mode)
async function handleBulkMatch() {
    const btn = document.getElementById('btn-rank-bulk');
    const jobId = document.getElementById('select-bulk-job').value;

    if (state.selectedBulkFiles.length === 0) {
        alert('Please select or drop multiple resume files (.PDF or .TXT) to rank.');
        return;
    }

    btn.disabled = true;
    btn.innerHTML = '<span class="loading-spinner"></span> Ranking Candidates...';

    const formData = new FormData();
    state.selectedBulkFiles.forEach(file => formData.append('files', file));
    if (jobId) formData.append('jobId', jobId);

    try {
        const response = await fetch('/api/rank-bulk', {
            method: 'POST',
            body: formData
        });

        if (!response.ok) {
            const errJson = await response.json();
            throw new Error(errJson.message || 'Bulk ranking failed');
        }

        const data = await response.json();
        state.lastBulkResults = data;
        renderLeaderboard(data);
    } catch (err) {
        alert('Error: ' + err.message);
    } finally {
        btn.disabled = false;
        btn.innerHTML = '<span>Process & Rank Candidates</span> ✦';
    }
}

function renderLeaderboard(data) {
    document.getElementById('box-leaderboard').style.display = 'block';
    document.getElementById('btn-export-csv').style.display = 'inline-flex';
    document.getElementById('title-leaderboard-job').textContent = `Candidate Leaderboard — ${data.jobTitle}`;
    document.getElementById('badge-processed-count').textContent = `${data.totalCandidatesProcessed} Candidates Ranked`;

    applyLeaderboardFilters();
}

// Filter, Search, and Sort Recruiter Leaderboard
function applyLeaderboardFilters() {
    if (!state.lastBulkResults || !state.lastBulkResults.rankedCandidates) return;

    const searchTerm = document.getElementById('input-search-leaderboard').value.toLowerCase();
    const filterTier = document.getElementById('select-filter-tier').value;
    const sortBy = document.getElementById('select-sort-by').value;

    let filtered = state.lastBulkResults.rankedCandidates.filter(c => {
        const nameMatch = (c.candidateName || '').toLowerCase().includes(searchTerm);
        const emailMatch = (c.candidateEmail || '').toLowerCase().includes(searchTerm);
        const skillMatch = (c.matchedSkills || []).some(s => s.toLowerCase().includes(searchTerm));
        const matchesSearch = nameMatch || emailMatch || skillMatch;

        const matchesTier = (filterTier === 'ALL') || (c.scoreTier === filterTier);
        return matchesSearch && matchesTier;
    });

    // Multi-sort logic
    if (sortBy === 'SCORE_DESC') {
        filtered.sort((a, b) => b.overallScore - a.overallScore);
    } else if (sortBy === 'SKILL_DESC') {
        filtered.sort((a, b) => b.skillMatchScore - a.skillMatchScore);
    } else if (sortBy === 'EXP_DESC') {
        filtered.sort((a, b) => b.extractedExperienceYears - a.extractedExperienceYears);
    }

    const tbody = document.getElementById('tbody-leaderboard');
    tbody.innerHTML = '';

    filtered.forEach((candidate, index) => {
        const tr = document.createElement('tr');
        const rankMedal = index === 0 ? '🥇 1' : (index === 1 ? '🥈 2' : (index === 2 ? '🥉 3' : `#${index + 1}`));

        tr.innerHTML = `
            <td style="font-weight: 600;">${rankMedal}</td>
            <td style="font-weight: 500; color: var(--color-snow-white);">${candidate.candidateName}</td>
            <td style="font-size: 16px; font-weight: 600; color: ${candidate.tierColorHex || '#6b62f2'};">${Math.round(candidate.overallScore)}%</td>
            <td><span class="tier-badge" style="background:${(candidate.tierColorHex || '#6b62f2')}20; color:${candidate.tierColorHex || '#6b62f2'}; border:1px solid ${candidate.tierColorHex || '#6b62f2'}; font-size:11px;">${candidate.scoreTier}</span></td>
            <td style="font-size: 13px;">${candidate.matchedSkills ? candidate.matchedSkills.slice(0, 3).join(', ') : 'N/A'}</td>
            <td style="font-size: 13px; color: #f87171;">${candidate.missingSkills && candidate.missingSkills.length > 0 ? candidate.missingSkills.slice(0, 2).join(', ') : 'None'}</td>
            <td style="font-size: 12px; color: var(--color-slate);">${candidate.candidateEmail}<br>${candidate.candidatePhone}</td>
        `;
        tbody.appendChild(tr);
    });
}

// Export Bulk Results to CSV
function exportBulkCsv() {
    if (!state.lastBulkResults || !state.lastBulkResults.rankedCandidates) return;

    let csvContent = 'Rank,Candidate Name,Email,Phone,Overall Score %,Score Tier,Matched Skills,Missing Skills\n';

    state.lastBulkResults.rankedCandidates.forEach((c, idx) => {
        const row = [
            idx + 1,
            `"${c.candidateName.replace(/"/g, '""')}"`,
            `"${c.candidateEmail}"`,
            `"${c.candidatePhone}"`,
            Math.round(c.overallScore),
            `"${c.scoreTier}"`,
            `"${c.matchedSkills ? c.matchedSkills.join('; ') : ''}"`,
            `"${c.missingSkills ? c.missingSkills.join('; ') : ''}"`
        ];
        csvContent += row.join(',') + '\n';
    });

    const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
    const url = URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', `SkillMatch_Leaderboard_${state.lastBulkResults.jobTitle.replace(/\s+/g, '_')}.csv`);
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
}

// Load Match History from DB
async function loadHistory() {
    try {
        const response = await fetch('/api/history');
        if (!response.ok) return;
        const history = await response.json();

        const tbody = document.getElementById('tbody-history');
        tbody.innerHTML = '';

        if (history.length === 0) {
            tbody.innerHTML = '<tr><td colspan="8" style="text-align: center; color: var(--color-slate);">No match evaluations in database yet.</td></tr>';
            return;
        }

        history.forEach(item => {
            const tr = document.createElement('tr');
            const dateStr = item.createdAt ? new Date(item.createdAt).toLocaleString() : 'Recent';
            tr.innerHTML = `
                <td>#${item.id}</td>
                <td style="font-size: 12px; color: var(--color-slate);">${dateStr}</td>
                <td style="font-weight: 500;">${item.candidateName}</td>
                <td>${item.jobTitle}</td>
                <td style="font-weight: 600; color: var(--color-snow-white);">${Math.round(item.overallScore)}%</td>
                <td><span class="status-pill" style="font-size: 11px;">${item.scoreTier}</span></td>
                <td style="font-size: 12px;">${item.matchedSkills ? item.matchedSkills.substring(0, 40) + '...' : 'N/A'}</td>
                <td><span style="font-size: 11px; color: ${item.processedWithAi ? '#a5b4fc' : 'var(--color-slate)'};">${item.processedWithAi ? '✦ Groq AI' : 'TF-IDF Engine'}</span></td>
            `;
            tbody.appendChild(tr);
        });
    } catch (err) {
        console.error('Failed to load history:', err);
    }
}

// Render Job Explorer Cards
function renderJobCards(jobs) {
    const container = document.getElementById('container-job-cards');
    container.innerHTML = '';

    jobs.forEach(job => {
        const card = document.createElement('div');
        card.className = 'frosted-card';
        card.style.marginBottom = '0';

        const reqSkillsPills = (job.requiredSkills || []).map(s => `<span class="skill-pill matched" style="font-size: 11px;">${s}</span>`).join(' ');

        card.innerHTML = `
            <div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 8px;">
                <h3 style="font-family: var(--font-geist); font-size: 18px; color: var(--color-snow-white);">${job.title}</h3>
                <span class="status-pill" style="font-size: 11px;">${job.isPreset ? 'Preset' : 'Custom'}</span>
            </div>
            <div style="font-size: 13px; color: var(--color-slate); margin-bottom: 12px;">${job.company} • ${job.department} • ${job.experienceLevel}</div>
            <p style="font-size: 14px; color: var(--color-ash); margin-bottom: 16px; display: -webkit-box; -webkit-line-clamp: 3; -webkit-box-orient: vertical; overflow: hidden;">${job.description}</p>
            <div>
                <label class="form-label" style="font-size: 12px;">Required Skills:</label>
                <div class="skills-container" style="margin-top: 4px;">${reqSkillsPills}</div>
            </div>
        `;
        container.appendChild(card);
    });
}

// Handle Posting New Job Specification
async function handleCreateJob() {
    const title = document.getElementById('new-job-title').value;
    const company = document.getElementById('new-job-company').value;
    const skillsStr = document.getElementById('new-job-skills').value;
    const desc = document.getElementById('new-job-desc').value;

    if (!title || !desc) {
        alert('Job Title and Job Description are required.');
        return;
    }

    const skillsList = skillsStr ? skillsStr.split(',').map(s => s.trim()) : [];

    const jobDto = {
        title: title,
        company: company || 'Custom Company',
        department: 'Engineering',
        experienceLevel: 'Mid-Senior',
        requiredSkills: skillsList,
        description: desc
    };

    try {
        const response = await fetch('/api/jobs', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(jobDto)
        });

        if (!response.ok) throw new Error('Failed to create job posting');

        alert('New job specification saved successfully!');
        document.getElementById('box-create-job').style.display = 'none';
        loadJobs();
    } catch (err) {
        alert('Error: ' + err.message);
    }
}
