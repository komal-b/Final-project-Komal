import React, { useState } from "react";
import './DonationPage.css'; // Add your styling here
import { useNavigate } from "react-router-dom"; // For navigation to a new page

function DonationPage() {
  const [itemID, setItemID] = useState("");  // State for itemID
  const [orderID, setOrderID] = useState("");  // State for orderID
  const [itemData, setItemData] = useState([]);  // State for item search results
  const [orderData, setOrderData] = useState([]);  // State for order search results
  const [donorUsername, setDonorUsername] = useState(""); // Donor username state
  const [donorName, setDonorName] = useState(""); // Donor name state
  const [items, setItems] = useState([{
    iDescription: "",
    color: "",
    isNew: false,
    hasPieces: false,
    material: "",
    mainCategory: "",
    subCategory: "",
    //location: [{ roomNum: "", shelfNum: "", shelf: "", shelfDescription: "" }],
    piece: [{ pieceNum: "", pDescription: "", length: "", width: "", height: "", roomNum: "", shelfNum: "" }]
  }]); // State for items with nested location and pieces
  const [errorMessage, setErrorMessage] = useState("");  // Error message state
  const navigate = useNavigate(); // Navigation hook

  // Function to handle donation submission
  const handleDonationSubmit = async () => {
    if (!donorUsername || !donorName || items.length === 0) {
        setErrorMessage("All fields are required.");
        return;
    }

    const donationData = {
      userName: donorUsername,
      donorUsername: donorName,
      items: items.map(item => ({
        iDescription: item.iDescription,
        color: item.color,
        isNew: item.isNew,
        hasPieces: item.hasPieces,
        material: item.material,
        mainCategory: item.mainCategory,
        subCategory: item.subCategory,
        // location: item.location.map(loc => ({
        //   roomNum: loc.roomNum.trim() !== "" ? loc.roomNum : null,  // Replace empty roomNum with a default "N/A"
        //   shelfNum: loc.shelfNum.trim() !== "" ? loc.shelfNum : null,
        //   shelf: loc.shelf,
        //   shelfDescription: loc.shelfDescription
        // })),
        piece: item.piece.map(p => ({
          pieceNum: p.pieceNum,
          pDescription: p.pDescription,
          length: p.length,
          width: p.width,
          height: p.height,
          roomNum: p.roomNum,
          shelfNum: p.shelfNum
        }))
      }))
    };

    console.log("Donation Data:", JSON.stringify(donationData, null, 2));

    try {
        const response = await fetch("http://localhost:8080/welcomehome/itemDonated", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(donationData),
        });

        if (response.ok) {
            setErrorMessage("");
            alert("Donation submitted successfully!");
            setDonorUsername(""); 
            setDonorName(""); 
            setItems([{
              iDescription: "", color: "", isNew: false, hasPieces: false, material: "", 
              mainCategory: "", subCategory: "",  
              piece: [{ pieceNum: "", pDescription: "", length: "", width: "", height: "", roomNum: "", shelfNum: "" }]
            }]);  // Reset donation form
        } else {
            setErrorMessage("Donation failed. Please try again.");
        }
    } catch (error) {
        console.error("Error during donation submission:", error);
        setErrorMessage("An error occurred while submitting the donation.");
    }
  };

  // Function to handle logout
  const handleLogout = () => {
    localStorage.removeItem("authToken");
    window.location.href = "/login";
  };

  return (
    <div className="hi-container">
      <div className="greeting">
        <h1>Welcome Home Search Page</h1>
      </div>

      {/* Donation Form */}
      <div className="donation-section">
        <h2>Donation Form</h2>
        <input
          type="text"
          value={donorUsername}
          onChange={(e) => setDonorUsername(e.target.value)}
          placeholder="Enter Donor Username"
          className="donation-input"
        />
        <input
          type="text"
          value={donorName}
          onChange={(e) => setDonorName(e.target.value)}
          placeholder="Enter Donor Name"
          className="donation-input"
        />

        {/* Item Details - Add as many items as needed */}
        <div className="item-form">
          {items.map((item, index) => (
            <div key={index} className="item-details">
              <h3>Item {index + 1}</h3>
              <input
                type="text"
                placeholder="Item Description"
                value={item.iDescription}
                onChange={(e) => {
                  const newItems = [...items];
                  newItems[index].iDescription = e.target.value;
                  setItems(newItems);
                }}
              />
              <input
                type="text"
                placeholder="Color"
                value={item.color}
                onChange={(e) => {
                  const newItems = [...items];
                  newItems[index].color = e.target.value;
                  setItems(newItems);
                }}
              />
              <input
                type="checkbox"
                checked={item.isNew}
                onChange={(e) => {
                  const newItems = [...items];
                  newItems[index].isNew = e.target.checked;
                  setItems(newItems);
                }}
              />
              <label>New Item</label>
              <input
                type="checkbox"
                checked={item.hasPieces}
                onChange={(e) => {
                  const newItems = [...items];
                  newItems[index].hasPieces = e.target.checked;
                  setItems(newItems);
                }}
              />
              <label>Has Pieces</label>
              <input
                type="text"
                placeholder="Material"
                value={item.material}
                onChange={(e) => {
                  const newItems = [...items];
                  newItems[index].material = e.target.value;
                  setItems(newItems);
                }}
              />
              <input
                type="text"
                placeholder="Main Category"
                value={item.mainCategory}
                onChange={(e) => {
                  const newItems = [...items];
                  newItems[index].mainCategory = e.target.value;
                  setItems(newItems);
                }}
              />
              <input
                type="text"
                placeholder="Sub Category"
                value={item.subCategory}
                onChange={(e) => {
                  const newItems = [...items];
                  newItems[index].subCategory = e.target.value;
                  setItems(newItems);
                }}
              />

              {/* Location Inputs */}
              {/* <h4>Location</h4>
              <input
                type="text"
                placeholder="Room Number"
                value={item.location[0].roomNum}
                onChange={(e) => {
                  const newItems = [...items];
                  newItems[index].location[0].roomNum = e.target.value;
                  setItems(newItems);
                }}
              />
              <input
                type="text"
                placeholder="Shelf Number"
                value={item.location[0].shelfNum}
                onChange={(e) => {
                  const newItems = [...items];
                  newItems[index].location[0].shelfNum = e.target.value;
                  setItems(newItems);
                }}
              />
              <input
                type="text"
                placeholder="Shelf"
                value={item.location[0].shelf}
                onChange={(e) => {
                  const newItems = [...items];
                  newItems[index].location[0].shelf = e.target.value;
                  setItems(newItems);
                }}
              />
              <input
                type="text"
                placeholder="Shelf Description"
                value={item.location[0].shelfDescription}
                onChange={(e) => {
                  const newItems = [...items];
                  newItems[index].location[0].shelfDescription = e.target.value;
                  setItems(newItems);
                }}
              /> */}

              {/* Piece Inputs */}
              <h4>Pieces</h4>
              {item.piece.map((piece, pieceIndex) => (
                <div key={pieceIndex}>
                  <input
                    type="number"
                    placeholder="Piece Number"
                    value={piece.pieceNum}
                    onChange={(e) => {
                      const newItems = [...items];
                      newItems[index].piece[pieceIndex].pieceNum = e.target.value;
                      setItems(newItems);
                    }}
                  />
                  <input
                    type="text"
                    placeholder="Piece Description"
                    value={piece.pDescription}
                    onChange={(e) => {
                      const newItems = [...items];
                      newItems[index].piece[pieceIndex].pDescription = e.target.value;
                      setItems(newItems);
                    }}
                  />
                  <input
                    type="number"
                    placeholder="Length"
                    value={piece.length}
                    onChange={(e) => {
                      const newItems = [...items];
                      newItems[index].piece[pieceIndex].length = e.target.value;
                      setItems(newItems);
                    }}
                  />
                  <input
                    type="number"
                    placeholder="Width"
                    value={piece.width}
                    onChange={(e) => {
                      const newItems = [...items];
                      newItems[index].piece[pieceIndex].width = e.target.value;
                      setItems(newItems);
                    }}
                  />
                  <input
                    type="number"
                    placeholder="Height"
                    value={piece.height}
                    onChange={(e) => {
                      const newItems = [...items];
                      newItems[index].piece[pieceIndex].height = e.target.value;
                      setItems(newItems);
                    }}
                  />
                  <input
                    type="text"
                    placeholder="Room Number"
                    value={piece.roomNum}
                    onChange={(e) => {
                      const newItems = [...items];
                      newItems[index].piece[pieceIndex].roomNum = e.target.value;
                      setItems(newItems);
                    }}
                  />
                  <input
                    type="text"
                    placeholder="Shelf Number"
                    value={piece.shelfNum}
                    onChange={(e) => {
                      const newItems = [...items];
                      newItems[index].piece[pieceIndex].shelfNum = e.target.value;
                      setItems(newItems);
                    }}
                  />
                </div>
              ))}
              <button
                type="button"
                class="piece-btn"
                onClick={() => {
                  const newItems = [...items];
                  newItems[index].piece.push({
                    pieceNum: "",
                    pDescription: "",
                    length: "",
                    width: "",
                    height: "",
                    roomNum: "",
                    shelfNum: ""
                  });
                  setItems(newItems);
                }}
              >
                Add Piece
              </button>
            </div>
          ))}
        </div>

        {/* Error message display */}
        {errorMessage && <p className="error-message">{errorMessage}</p>}

        <button onClick={handleDonationSubmit} class="submit-btn">Submit Donation</button>
        <button onClick={() => navigate("/profile")} className="back-btn">
        Back to Profile
      </button><br /><br />
      <button onClick={handleLogout} className="logout-btn">Logout</button>
      </div>
    </div>
  );
}

export default DonationPage;
