import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import { useEffect, useState } from "react";
import HomePage from './HomePage';
import RegistrationForm from './RegistrationForm';
import LoginForm from "./LoginForm";
import ProfilePage from "./ProfilePage";
import DonatePage from "./DonationPage";
import CreateOrderPage from "./CreateOrderPage";

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(localStorage.getItem("authToken"));

  useEffect(() => {
    // Listen for changes in localStorage and update authentication state accordingly
    const handleStorageChange = () => {
      setIsAuthenticated(localStorage.getItem("authToken"));
    };
    
    window.addEventListener("storage", handleStorageChange);
    
    // Cleanup the event listener
    return () => {
      window.removeEventListener("storage", handleStorageChange);
    };
  }, []);

  return (
    <Router>
      <Routes>
        {/* Public Routes */}
        <Route path="/" element={<HomePage />} />
        <Route path="/register" element={<RegistrationForm />} />
        <Route path="/login" element={<LoginForm setIsAuthenticated={setIsAuthenticated} />} />
        <Route path="/donate" element={<DonatePage />} />
        <Route path="/create-order" element={<CreateOrderPage />} />

        {/* Protected Routes */}
        <Route 
          path="/profile" 
          element={isAuthenticated ? <ProfilePage /> : <Navigate to="/login" replace />} 
        />

        {/* Default Route */}
        <Route path="*" element={<Navigate to={isAuthenticated ? "/profile" : "/login"} replace />} />
      </Routes>
    </Router>
  );
}

export default App;
