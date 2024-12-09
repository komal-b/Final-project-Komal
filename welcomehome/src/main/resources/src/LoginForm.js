import React, { useState } from "react";
import { useNavigate } from "react-router-dom"; // Import useNavigate for navigation
import './LoginForm.css';

function LoginForm({ setIsAuthenticated }) {  // Accept setIsAuthenticated as a prop
  const [formData, setFormData] = useState({
    userName: "",
    password: "",
  });

  const [errorMessage, setErrorMessage] = useState(""); // To store error messages
  const navigate = useNavigate(); // Initialize navigation hook

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    setErrorMessage(""); // Clear any previous error messages
  
    try {
      const response = await fetch("http://localhost:8080/welcomehome/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });
  
      const contentType = response.headers.get("Content-Type");
  
      if (contentType && contentType.includes("application/json")) {
        const data = await response.json();
  
        if (response.ok) {
          // Save token and username to localStorage
          localStorage.setItem("authToken", data.token || "dummyToken");
          localStorage.setItem("userName", formData.userName); // Save username
          setIsAuthenticated(true); // Update authentication state immediately
          navigate("/profile"); // Redirect to ProfilePage
        } else {
          setErrorMessage(data.message || "Invalid login credentials.");
        }
      } else {
        setErrorMessage("Unexpected response format. Please try again.");
      }
    } catch (error) {
      console.error("Error during login:", error);
      setErrorMessage("An error occurred. Please try again later.");
    }
  };
  const handleRegister = () => {
    navigate("/register"); // Redirect to the registration page
  };

  return (
    <div className="login-container">
      <div className="login-heading">
        <h1>Login</h1>
      </div>
      <form onSubmit={handleLogin} className="login-form">
        <input
          type="text"
          name="userName"
          placeholder="Username"
          value={formData.userName}
          onChange={handleChange}
          required
        />
        <input
          type="password"
          name="password"
          placeholder="Password"
          value={formData.password}
          onChange={handleChange}
          required
        />
        {errorMessage && <p className="error-message">{errorMessage}</p>}
        <div className="button-group">
          <button type="submit" className="login-btn">Login</button>
          <button type="button" className="register-btn" onClick={handleRegister}>Register</button>
        </div>
      </form>
    </div>
  );
}

export default LoginForm;
