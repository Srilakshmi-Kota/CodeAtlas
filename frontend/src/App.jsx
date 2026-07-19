import "./App.css";
import { useState } from "react";
import axios from "axios";
import jsPDF from "jspdf";

function App() {
  const [url, setUrl] = useState("");
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);

  // =========================
  // ANALYZE REPOSITORY
  // =========================
  const analyzeRepository = async () => {
    try {
      setLoading(true);
      setResult(null);

      const response = await axios.post(
        "http://localhost:8080/api/repository/analyze",
        {
          url: url,
        }
      );

      setResult(response.data);

      console.log("Repository Analysis:", response.data);
      console.log("Health Score:", response.data.healthScore);
    } catch (error) {
      console.error("Analysis Error:", error);
      alert("Analysis failed.");
    } finally {
      setLoading(false);
    }
  };

  // =========================
  // DOWNLOAD PDF REPORT
  // =========================
  const downloadReport = () => {
    if (!result) return;

    const pdf = new jsPDF();

    // Report Title
    pdf.setFontSize(22);
    pdf.text("CodeAtlas - Repository Analysis Report", 20, 20);

    // Repository Information
    pdf.setFontSize(12);

    pdf.text(`Repository: ${result.repository}`, 20, 35);
    pdf.text(`Owner: ${result.owner}`, 20, 43);
    pdf.text(`Language: ${result.language || "N/A"}`, 20, 51);
    pdf.text(`Stars: ${result.stars ?? 0}`, 20, 59);
    pdf.text(`Forks: ${result.forks ?? 0}`, 20, 67);
    pdf.text(`Watchers: ${result.watchers ?? 0}`, 20, 75);
    pdf.text(`Open Issues: ${result.openIssues ?? 0}`, 20, 83);

    // Repository Health
    pdf.setFontSize(16);
    pdf.text("Repository Health", 20, 98);

    pdf.setFontSize(12);
    pdf.text(
      `Health Score: ${result.healthScore ?? 0}/100`,
      20,
      108
    );

    // Technology Detection
    pdf.text(
      `Java: ${result.javaProject ? "Yes" : "No"}`,
      20,
      118
    );

    pdf.text(
      `Spring Boot: ${result.springBootProject ? "Yes" : "No"}`,
      20,
      126
    );

    pdf.text(
      `Maven: ${result.mavenProject ? "Yes" : "No"}`,
      20,
      134
    );

    pdf.text(
      `Gradle: ${result.gradleProject ? "Yes" : "No"}`,
      20,
      142
    );

    pdf.text(
      `Docker: ${result.dockerProject ? "Yes" : "No"}`,
      20,
      150
    );

    pdf.text(
      `Tests: ${result.hasTests ? "Yes" : "No"}`,
      20,
      158
    );

    // AI Summary
    pdf.setFontSize(16);
    pdf.text("AI Summary", 20, 174);

    pdf.setFontSize(11);

    const summaryLines = pdf.splitTextToSize(
      result.aiSummary || "No AI summary available.",
      170
    );

    pdf.text(summaryLines, 20, 184);

    let yPosition = 184 + summaryLines.length * 6 + 12;

    // Create another page if needed
    if (yPosition > 240) {
      pdf.addPage();
      yPosition = 20;
    }

    // AI Recommendations
    pdf.setFontSize(16);
    pdf.text("AI Recommendations", 20, yPosition);

    yPosition += 10;

    pdf.setFontSize(11);

    const recommendationLines = pdf.splitTextToSize(
      result.aiRecommendations ||
        "No AI recommendations available.",
      170
    );

    pdf.text(recommendationLines, 20, yPosition);

    // Download PDF
    pdf.save(
      `${result.repository || "repository"}-CodeAtlas-Report.pdf`
    );
  };

  return (
    <div className="container">

      {/* HEADER */}

      <h1>🚀 CodeAtlas</h1>

      <p>AI-Powered GitHub Repository Analyzer</p>

      {/* REPOSITORY URL INPUT */}

      <input
        type="text"
        placeholder="Paste GitHub Repository URL..."
        value={url}
        onChange={(e) => setUrl(e.target.value)}
      />

      <button
        onClick={analyzeRepository}
        disabled={loading}
      >
        {loading
          ? "Analyzing..."
          : "Analyze Repository"}
      </button>

      {/* LOADING SCREEN */}

      {loading && (
        <div className="loading-card">

          <h2>🔍 Analyzing Repository...</h2>

          <p>📥 Fetching GitHub repository...</p>

          <p>📂 Scanning project files...</p>

          <p>🤖 AI is generating insights...</p>

          <div className="loader"></div>

        </div>
      )}

      {/* ANALYSIS RESULT */}

      {result && (
        <div className="result-card">

          {/* REPOSITORY HEADER */}

          <div className="repo-header">

            {result.ownerAvatar && (
              <img
                src={result.ownerAvatar}
                alt={`${result.owner} avatar`}
                className="owner-avatar"
              />
            )}

            <div>

              <p className="owner-name">
                {result.owner}
              </p>

              <h2>
                {result.repository}
              </h2>

            </div>

          </div>

          {/* OPEN GITHUB REPOSITORY */}

          <a
            href={`https://github.com/${result.owner}/${result.repository}`}
            target="_blank"
            rel="noopener noreferrer"
            className="github-button"
          >
            🔗 Open Repository on GitHub
          </a>

          {/* TECHNOLOGY INFORMATION */}

          <div className="info-grid">

            <div className="info-item">

              <span>💻 Language</span>

              <strong>
                {result.language || "N/A"}
              </strong>

            </div>

            <div className="info-item">

              <span>⭐ Stars</span>

              <strong>
                {result.stars ?? 0}
              </strong>

            </div>

            <div className="badge">
              ☕ Java{" "}
              {result.javaProject ? "✅" : "❌"}
            </div>

            <div className="badge">
              🌱 Spring Boot{" "}
              {result.springBootProject
                ? "✅"
                : "❌"}
            </div>

            <div className="badge">
              📦 Maven{" "}
              {result.mavenProject ? "✅" : "❌"}
            </div>

            <div className="badge">
              ⚙️ Gradle{" "}
              {result.gradleProject ? "✅" : "❌"}
            </div>

            <div className="badge">
              🐳 Docker{" "}
              {result.dockerProject ? "✅" : "❌"}
            </div>

          </div>

          {/* REPOSITORY HEALTH */}

          <div className="health-card">

            <h3>
              📊 Repository Health
            </h3>

            <div
              style={{
                width: "100%",
                height: "20px",
                backgroundColor: "#444",
                borderRadius: "10px",
                overflow: "hidden",
                margin: "20px 0",
              }}
            >

              <div
                style={{
                  width: `${
                    result.healthScore ?? 0
                  }%`,
                  height: "20px",

                  backgroundColor:
                    result.healthScore >= 80
                      ? "#22c55e"
                      : result.healthScore >= 50
                      ? "#f59e0b"
                      : "#ef4444",

                  transition:
                    "width 0.8s ease",
                }}
              ></div>

            </div>

            <h2>
              {result.healthScore ?? 0}/100
            </h2>

            <p
              style={{
                margin: "10px 0 0",
                fontWeight: "bold",
              }}
            >

              {result.healthScore >= 80
                ? "🟢 Excellent Repository Health"
                : result.healthScore >= 50
                ? "🟡 Good, but Improvements Recommended"
                : "🔴 Needs Significant Improvement"}

            </p>

          </div>

          {/* REPOSITORY STATISTICS */}

          <div className="stats-card">

            <h3>
              📊 Repository Statistics
            </h3>

            <div className="stats-grid">

              <div className="stat-box">

                <h4>⭐ Stars</h4>

                <p>
                  {result.stars ?? 0}
                </p>

              </div>

              <div className="stat-box">

                <h4>🍴 Forks</h4>

                <p>
                  {result.forks ?? 0}
                </p>

              </div>

              <div className="stat-box">

                <h4>👀 Watchers</h4>

                <p>
                  {result.watchers ?? 0}
                </p>

              </div>

              <div className="stat-box">

                <h4>🐞 Issues</h4>

                <p>
                  {result.openIssues ?? 0}
                </p>

              </div>

            </div>

          </div>

          {/* AI SUMMARY */}

          <div className="summary">

            <h3>
              🤖 AI Summary
            </h3>

            <p>
              {result.aiSummary ||
                "No AI summary available."}
            </p>

          </div>
          <div className="insights-card">

  <h3>🔍 Project Insights</h3>

  <div className="insights-grid">

    <div className={`insight-item ${result.hasReadme ? "detected" : "missing"}`}>
      <span>📖 README</span>
      <strong>{result.hasReadme ? "✅ Detected" : "❌ Missing"}</strong>
    </div>

    <div className={`insight-item ${result.hasTests ? "detected" : "missing"}`}>
      <span>🧪 Automated Tests</span>
      <strong>{result.hasTests ? "✅ Detected" : "❌ Missing"}</strong>
    </div>

    <div className={`insight-item ${result.hasCiCd ? "detected" : "missing"}`}>
      <span>⚙️ CI/CD Pipeline</span>
      <strong>{result.hasCiCd ? "✅ Detected" : "❌ Missing"}</strong>
    </div>

    <div className={`insight-item ${result.dockerProject ? "detected" : "missing"}`}>
      <span>🐳 Docker</span>
      <strong>{result.dockerProject ? "✅ Detected" : "❌ Missing"}</strong>
    </div>

    <div className={`insight-item ${result.hasLicense ? "detected" : "missing"}`}>
      <span>📜 License</span>
      <strong>{result.hasLicense ? "✅ Detected" : "❌ Missing"}</strong>
    </div>

    <div
      className={`insight-item ${
        result.hasDocumentation ? "detected" : "missing"
      }`}
    >
      <span>📚 Documentation</span>
      <strong>
        {result.hasDocumentation ? "✅ Detected" : "❌ Missing"}
      </strong>
    </div>

  </div>

</div>
<div className="analysis-overview">

  <div className="strengths-card">
    <h3>💪 Repository Strengths</h3>

    {result.hasTests && <p>✅ Automated testing is configured</p>}

    {result.hasCiCd && <p>✅ CI/CD pipeline is available</p>}

    {result.dockerProject && <p>✅ Docker containerization is supported</p>}

    {result.hasReadme && <p>✅ README documentation is available</p>}

    {result.hasLicense && <p>✅ Repository includes a license</p>}

    {result.springBootProject && (
      <p>✅ Spring Boot framework detected</p>
    )}
  </div>

  <div className="weaknesses-card">
    <h3>⚠️ Areas to Improve</h3>

    {!result.hasTests && <p>❌ Automated tests are missing</p>}

    {!result.hasCiCd && <p>❌ CI/CD pipeline is missing</p>}

    {!result.dockerProject && <p>❌ Docker support is missing</p>}

    {!result.hasReadme && <p>❌ README is missing</p>}

    {!result.hasLicense && <p>❌ License is missing</p>}

    {!result.hasDocumentation && (
      <p>❌ Dedicated project documentation is missing</p>
    )}

    {result.mavenProject && result.gradleProject && (
      <p>⚠️ Multiple build systems detected: Maven and Gradle</p>
    )}
  </div>

</div>
          {/* AI RECOMMENDATIONS */}

          <div className="recommendations-card">

            <h3>
              💡 AI Recommendations
            </h3>

            {result.aiRecommendations ? (

              result.aiRecommendations
                .split("\n")
                .filter(
                  (item) =>
                    item.trim() !== ""
                )
                .map((item, index) => (

                  <div
                    className="recommendation-item"
                    key={index}
                  >

                    <span>✅</span>

                    <span>
                      {item.replace(
                        /^\d+\.\s*/,
                        ""
                      )}
                    </span>

                  </div>

                ))

            ) : (

              <p>
                No recommendations available.
              </p>

            )}

          </div>

          {/* DOWNLOAD PDF */}

          <button
            onClick={downloadReport}
            className="download-button"
          >
            📄 Download Analysis Report
          </button>

        </div>
      )}

    </div>
  );
}

export default App;