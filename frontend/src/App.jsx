import "./App.css";
import { useState } from "react";
import axios from "axios";

function App() {
  const [url, setUrl] = useState("");
  const [result, setResult] = useState(null);
  console.log(result);
  const [loading, setLoading] = useState(false);

  const analyzeRepository = async () => {
  try {
    setLoading(true);

    const response = await axios.post(
      "http://localhost:8080/api/repository/analyze",
      {
        url: url,
      }
    );

    setResult(response.data);
    console.log(response.data);
    console.log("Health Score:", response.data.healthScore);
  } catch (error) {
    console.error(error);
    alert("Analysis failed.");
  } finally {
    setLoading(false);
  }
};

  return (
    <div className="container">
      <h1>🚀 CodeAtlas</h1>

      <p>AI-Powered GitHub Repository Analyzer</p>

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
  {loading ? "Analyzing..." : "Analyze Repository"}
</button>
    {loading && (
  <div className="loading-card">
    <h2>🔍 Analyzing Repository...</h2>

    <p>📥 Fetching GitHub repository...</p>
    <p>📂 Scanning project files...</p>
    <p>🤖 AI is generating insights...</p>

    <div className="loader"></div>
  </div>
)}
      {result && (
  <div className="result-card">

      <div className="repo-header">

  {result.ownerAvatar && (
    <img
      src={result.ownerAvatar}
      alt="Repository Owner"
      className="owner-avatar"
    />
  )}

  <div>
    <p className="owner-name">{result.owner}</p>
    <h2>{result.repository}</h2>
  </div>

</div>

<a
  href={`https://github.com/${result.owner}/${result.repository}`}
  target="_blank"
  rel="noopener noreferrer"
  className="github-button"
>
  🔗 Open Repository on GitHub
</a>

    <div className="info-grid">

      <div className="info-item">
        <span>💻 Language</span>
        <strong>{result.language}</strong>
      </div>

      <div className="info-item">
        <span>⭐ Stars</span>
        <strong>{result.stars}</strong>
      </div>

      <div className="badge">
        ☕ Java {result.javaProject ? "✅" : "❌"}
      </div>
        <div className="badge">
  🌱 Spring Boot {result.springBootProject ? "✅" : "❌"}
</div>
      <div className="badge">
        📦 Maven {result.mavenProject ? "✅" : "❌"}
      </div>

      <div className="badge">
        ⚙️ Gradle {result.gradleProject ? "✅" : "❌"}
      </div>

      <div className="badge">
        🐳 Docker {result.dockerProject ? "✅" : "❌"}
      </div>

    </div>
      <div className="health-card">
  <h3>📊 Repository Health</h3>

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
        width: `${result.healthScore}%`,
        height: "20px",
        backgroundColor:
          result.healthScore >= 80
            ? "#22c55e"
            : result.healthScore >= 50
            ? "#f59e0b"
            : "#ef4444",
        transition: "width 0.8s ease",
      }}
    ></div>
  </div>

  <h2>{result.healthScore}/100</h2>

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
<div className="stats-card">

  <h3>📊 Repository Statistics</h3>

  <div className="stats-grid">

    <div className="stat-box">
      <h4>⭐ Stars</h4>
      <p>{result.stars}</p>
    </div>

    <div className="stat-box">
      <h4>🍴 Forks</h4>
      <p>{result.forks}</p>
    </div>

    <div className="stat-box">
      <h4>👀 Watchers</h4>
      <p>{result.watchers}</p>
    </div>

    <div className="stat-box">
      <h4>🐞 Issues</h4>
      <p>{result.openIssues}</p>
    </div>

  </div>

</div>
    <div className="summary">

      <h3>🤖 AI Summary</h3>

      <p>{result.aiSummary}</p>

    </div>
      <div className="recommendations-card">
  <h3>💡 AI Recommendations</h3>

  {result.aiRecommendations
    ?.split("\n")
    .filter((item) => item.trim() !== "")
    .map((item, index) => (
      <div className="recommendation-item" key={index}>
        <span>✅</span>
        <span>{item.replace(/^\d+\.\s*/, "")}</span>
      </div>
    ))}
</div>
  </div>
)}
 </div>
  );
}

export default App;