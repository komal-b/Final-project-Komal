import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./CreateOrderPage.css";

function CreateOrderPage() {
  const [staffUser, setStaffUser] = useState("");
  const [client, setClient] = useState("");
  const [itemIds, setItemIds] = useState(""); // Comma-separated list for simplicity
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("authToken");
    window.location.href = "/login";
  };

  const handleCreateOrder = async () => {
    console.log("Staff Username:", staffUser);
  console.log("Client:", client);
  console.log("Item IDs:", itemIds);

    if (!staffUser || !client || !itemIds) {
      setError("All fields are required.");
      return;
    }

    try {
      const response = await fetch("http://localhost:8080/welcomehome/createOrder", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          staffUser,
          client,
          itemIds: itemIds.split(",").map((id) => parseInt(id.trim())),
        }),
      });

      if (response.ok) {
        const data = await response.json();
        setMessage(`Order created successfully! Order ID: ${data.orderID}`);
        setError("");
      } else {
        const errorData = await response.json();
        setError(`Error: ${errorData.message}`);
      }
    } catch (err) {
      console.error("Error creating order:", err);
      setError("An error occurred while creating the order.");
    }
  };


  return (
    <div className="create-order-container">
      <h1>Create New Order</h1>

      <div className="input-group">
        <label>Staff Username:</label>
        <input
          type="text"
          value={staffUser}
          onChange={(e) => setStaffUser(e.target.value)}
        />
      </div>

      <div className="input-group">
        <label>Client:</label>
        <input
          type="text"
          value={client}
          onChange={(e) => setClient(e.target.value)}
        />
      </div>

      <div className="input-group">
        <label>Item IDs (comma-separated):</label>
        <input
          type="text"
          value={itemIds}
          onChange={(e) => setItemIds(e.target.value)}
        />
      </div>

      <button onClick={handleCreateOrder} className="submit-btn">
        Create Order
      </button>

      {message && <p className="success-message">{message}</p>}
      {error && <p className="error-message">{error}</p>}

      <button onClick={() => navigate("/profile")} className="back-btn">
        Back to Profile
      </button><br /><br />
      <button onClick={handleLogout} className="logout-btn">Logout</button>
    </div>
  );
}

export default CreateOrderPage;
