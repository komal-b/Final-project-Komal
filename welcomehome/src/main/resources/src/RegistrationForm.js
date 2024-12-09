import React, { useEffect, useState } from "react";
import './RegistrationForm.css'; // You can create a separate CSS file for styling

function RegistrationForm() {
  const [formData, setFormData] = useState({
    userName: "",
    password: "",
    fname: "",
    lname: "",
    email: "",
    role: "",
    phoneNumbers: [], // State for multiple phone numbers
  });

  const [roles, setRoles] = useState([]);

  useEffect(() => {
    const fetchRoles = async () => {
      try {
        const response = await fetch("http://localhost:8080/welcomehome/roles");
        if (response.ok) {
          const data = await response.json();
          setRoles(data);
        } else {
          console.error("Failed to fetch roles");
        }
      } catch (error) {
        console.error("Error fetching roles:", error);
      }
    };
    fetchRoles();
  }, []);

  const handleChange = (e) => {
    const { name, value, dataset } = e.target;
    if (name === "role") {
      setFormData({
        ...formData,
        role: value,
      });
    } else if (name.startsWith("phoneNumber")) {
      const index = dataset.index; // Get the index from data-index attribute
      const updatedPhoneNumbers = [...formData.phoneNumbers];
      updatedPhoneNumbers[index] = value; // Update specific phone number by index
      setFormData({
        ...formData,
        phoneNumbers: updatedPhoneNumbers,
      });
    } else {
      setFormData({
        ...formData,
        [name]: value,
      });
    }
  };

  const handleAddPhoneNumber = () => {
    setFormData({
      ...formData,
      phoneNumbers: [...formData.phoneNumbers, ""], // Add a new empty phone number field
    });
  };

  const handleRemovePhoneNumber = (index) => {
    const updatedPhoneNumbers = formData.phoneNumbers.filter((_, i) => i !== index);
    setFormData({
      ...formData,
      phoneNumbers: updatedPhoneNumbers,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
  
    try {
      const response = await fetch("http://localhost:8080/welcomehome/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });
  
      if (response.ok) {
        const contentType = response.headers.get("Content-Type");
        if (contentType && contentType.includes("application/json")) {
          const result = await response.json();
          alert(result.message || "Registered Successfully!");
        } else {
          alert("Registered Successfully (No response body)!");
        }
        // Redirect to login page after success
        window.location.href = "/login";
      } else {
        alert(`Registration failed: ${response.statusText}`);
      }
    } catch (error) {
      console.error("Error during registration:", error);
    }
  };
  

  return (
    <div className="form-container">
      <div className="welcome-heading">
        <h1>Welcome Home</h1>
      </div>
      <form onSubmit={handleSubmit} className="registration-form">
        <h2>Registration</h2>
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
        <input
          type="text"
          name="fname"
          placeholder="First Name"
          value={formData.fname}
          onChange={handleChange}
          required
        />
        <input
          type="text"
          name="lname"
          placeholder="Last Name"
          value={formData.lname}
          onChange={handleChange}
          required
        />
        <input
          type="email"
          name="email"
          placeholder="Email"
          value={formData.email}
          onChange={handleChange}
          required
        />
        
        {/* Role Selection */}
        <select
          name="role"
          value={formData.role}
          onChange={handleChange}
          required
        >
          <option value="" disabled>Select Role</option>
          {roles.map((role) => (
            <option key={role.roleID} value={role.roleID}>
              {role.rDescription}
            </option>
          ))}
        </select>

        {/* Phone Numbers Section */}
        <div className="phone-numbers-section">
          <h3>Phone Numbers</h3>
          {formData.phoneNumbers.map((phoneNumber, index) => (
            <div key={index} className="phone-number-field">
              <input
                type="text"
                name={`phoneNumber${index}`}
                value={phoneNumber}
                onChange={handleChange}
                data-index={index}
                placeholder={`Phone Number ${index + 1}`}
                required
              />
              <button
                type="button"
                onClick={() => handleRemovePhoneNumber(index)}
                className="remove-phone-btn"
              >
                Remove
              </button>
            </div>
          ))}
          <button
            type="button"
            onClick={handleAddPhoneNumber}
            className="add-phone-btn"
          >
            Add Phone Number
          </button>
        </div>

        {/* Submit Button */}
        <button type="submit" className="submit-btn">Register</button>

        {/* Login Button */}
        <button
          type="button"
          className="login-btn"
          onClick={() => window.location.href = "/login"} // Redirect to login page
        >
          Login
        </button>
      </form>
    </div>
  );
}

export default RegistrationForm;
