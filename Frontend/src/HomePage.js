import React from "react";
import { useNavigate } from "react-router-dom";
import './HomePage.css'; // Optional: Create a CSS file for styling

function HomePage() {
  const navigate = useNavigate();

  return (
    <div className="home-container">
      <h1>Welcome Home</h1>
      <div className="button-group">
        <button onClick={() => navigate("/login")} className="home-btn">
          Login
        </button>
        <button onClick={() => navigate("/register")} className="home-btn">
          Register
        </button>
      </div>
    </div>
  );
}

export default HomePage;
