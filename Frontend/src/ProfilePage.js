import React, { useState, useEffect } from "react";
import './ProfilePage.css'; // Add your styling here
import { useNavigate } from "react-router-dom"; // For navigation to a new page

function ProfilePage() {
  const [itemID, setItemID] = useState("");  // State for itemID
  const [orderID, setOrderID] = useState("");  // State for orderID
  const [userName, setUserName] = useState("");  // State for username
  const [itemData, setItemData] = useState([]); 
  const [orderIDData, setOrderIDData] = useState([]); // State for item search results
  const [orderData, setOrderData] = useState([]);  // State for order search results
  const [errorMessage, setErrorMessage] = useState(""); // Error message state

  const navigate = useNavigate(); // Navigation hook

  // Fetch the username from localStorage when the component mounts
  useEffect(() => {
    const loggedInUserName = localStorage.getItem("userName");
    if (loggedInUserName) {
      setUserName(loggedInUserName);
    }
  }, []);

  // Function to handle itemID search
  const handleItemSearch = async () => {
    if (!itemID) {
      setErrorMessage("Item ID is required");
      return;
    }

    try {
      const response = await fetch(`http://localhost:8080/welcomehome/itemLocation?itemID=${itemID}`);
      if (response.ok) {
        const data = await response.json();
        setItemData(data); // Set data to itemData
        setErrorMessage("");
      } else {
        setErrorMessage("Item not found or an error occurred.");
      }
    } catch (error) {
      console.error("Error during item search:", error);
      setErrorMessage("An error occurred while fetching data.");
    }
  };

  // Function to handle orderID search
  const handleOrderSearch = async () => {
    if (!orderID) {
      setErrorMessage("Order ID is required");
      return;
    }

    try {
      const response = await fetch(`http://localhost:8080/welcomehome/itemOrder?orderID=${orderID}`);
      if (response.ok) {
        const data = await response.json();
        setOrderIDData(data); // Set data to orderData
        setErrorMessage("");
      } else {
        setErrorMessage("Order not found or an error occurred.");
      }
    } catch (error) {
      console.error("Error during order search:", error);
      setErrorMessage("An error occurred while fetching data.");
    }
  };

  // Function to handle username-based order search
  

  // Function to handle username-based order generation (automatically use logged-in username)
  const handleGenerateOrders = async () => {
    if (!userName) {
      setErrorMessage("Username is required");
      return;
    }

    try {
      const response = await fetch(`http://localhost:8080/welcomehome/userOrders?username=${userName}`);
      if (response.ok) {
        const data = await response.json();
        setOrderData(data); // Set data to orderData
        setErrorMessage("");
      } else {
        setErrorMessage("No orders found for this user or an error occurred.");
      }
    } catch (error) {
      console.error("Error during order generation:", error);
      setErrorMessage("An error occurred while fetching orders.");
    }
  };

  // Function to handle logout
  const handleLogout = () => {
    localStorage.removeItem("authToken");
    localStorage.removeItem("userName"); // Remove username from localStorage
    window.location.href = "/login";
  };

  // Function to navigate to the donation page
  const handleDonateClick = () => {
    navigate("/donate");
  };

  return (
    <div className="hi-container">
      <div className="greeting">
        <h1>Welcome Home Search Page</h1>
      </div>

      {/* Item ID Search Section */}
      <div className="search-section">
        <input
          type="text"
          placeholder="Enter Item ID"
          value={itemID}
          onChange={(e) => setItemID(e.target.value)}
          className="search-box"
        />
        <button onClick={handleItemSearch} className="search-btn">Search by Item ID</button>
      </div>

      {/* Order ID Search Section */}
      <div className="search-section">
        <input
          type="text"
          placeholder="Enter Order ID"
          value={orderID}
          onChange={(e) => setOrderID(e.target.value)}
          className="search-box"
        />
        <button onClick={handleOrderSearch} className="search-btn">Search by Order ID</button>
      </div>

      

      {/* Generate Orders by Username */}
      <div className="search-section">
        <button onClick={handleGenerateOrders} className="generate-orders-btn">
          Generate My Orders
        </button>
      </div>

      {errorMessage && <p className="error-message">{errorMessage}</p>}

      {/* Item Search Results */}
      {itemData.length > 0 && (
        <div>
          <h2>Item Search Results</h2>
          <table className="result-table">
            <thead>
              <tr>
                <th>Item ID</th>
                <th>Piece Number</th>
                <th>Description</th>
                <th>Length</th>
                <th>Height</th>
                <th>Room Number</th>
                <th>Shelf Number</th>
                <th>Shelf</th>
                <th>Shelf Description</th>
              </tr>
            </thead>
            <tbody>
              {itemData.map((row, index) => (
                <tr key={index}>
                  <td>{row.piece.itemID}</td>
                  <td>{row.piece.pieceNum}</td>
                  <td>{row.piece.pDescription}</td>
                  <td>{row.piece.length}</td>
                  <td>{row.piece.height}</td>
                  <td>{row.roomNum}</td>
                  <td>{row.shelfNum}</td>
                  <td>{row.shelf}</td>
                  <td>{row.shelfDescription}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

    {orderIDData.length > 0 && (
        <div>
          <h2>Order Search Results</h2>
          <table className="result-table">
            <thead>
              <tr>
                <th>Item ID</th>
                <th>Piece Number</th>
                <th>Description</th>
                <th>Length</th>
                <th>Height</th>
                <th>Room Number</th>
                <th>Shelf Number</th>
                <th>Shelf</th>
                <th>Shelf Description</th>
              </tr>
            </thead>
            <tbody>
              {orderIDData.map((row, index) => (
                <tr key={index}>
                  <td>{row.piece.itemID}</td>
                  <td>{row.piece.pieceNum}</td>
                  <td>{row.piece.pDescription}</td>
                  <td>{row.piece.length}</td>
                  <td>{row.piece.height}</td>
                  <td>{row.roomNum}</td>
                  <td>{row.shelfNum}</td>
                  <td>{row.shelf}</td>
                  <td>{row.shelfDescription}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}  
      {/* Order Search Results */}
      {orderData.length > 0 && (
        <div>
          <h2>Order Search Results</h2>
          <table className="result-table">
            <thead>
              <tr>
                <th>Order ID</th>
                <th>Order Date</th>
                <th>Order Notes</th>
                <th>Supervisor</th>
                <th>Client</th>
                <th>Item ID</th>
                <th>Item Description</th>
                <th>Color</th>
                <th>Material</th>
                <th>Is New</th>
                <th>Relationship</th>
                <th>Delivered By</th>
              </tr>
            </thead>
            <tbody>
              {orderData.map((row, index) => (
                <tr key={index}>
                  <td>{row.orderID}</td>
                  <td>{row.orderDate}</td>
                  <td>{row.orderNotes}</td>
                  <td>{row.supervisor}</td>
                  <td>{row.client}</td>
                  <td>{row.itemID}</td>
                  <td>{row.itemDescription}</td>
                  <td>{row.color}</td>
                  <td>{row.material}</td>
                  <td>{row.isNew ? "Yes" : "No"}</td>
                  <td>{row.relationship}</td>
                  <td>{row.deliveredBy}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {/* Donate Button */}
      <button onClick={handleDonateClick} className="donate-btn">Go to Donation Page</button> <br /><br />
      <button onClick={() => navigate("/create-order")} className="create-order-btn">
        Create Order
      </button><br /><br />

      <button onClick={handleLogout} className="logout-btn">Logout</button>
    </div>
  );
}

export default ProfilePage;
