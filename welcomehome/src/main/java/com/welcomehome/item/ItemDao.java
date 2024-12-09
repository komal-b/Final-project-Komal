package com.welcomehome.item;

import com.welcomehome.register.UserDao;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemDao {


    private final DataSource dataSource;
    private PreparedStatement stmt;
    private Logger loggers = LoggerFactory.getLogger(ItemDao.class);
    @Autowired
    private UserDao userDao;

    public ItemDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private PreparedStatement connection(String query) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(query);
        return stmt;
    }

    public List<Location> getItemLocation(int itemID){
        List<Location> data = new ArrayList<>();

        String itemSql = "SELECT * FROM Location l " +
                "JOIN Piece p ON p.roomNum = l.roomNum AND p.shelfNum = l.shelfNum " +
                "WHERE p.itemID = ?";

        try{
            stmt = connection(itemSql);
            stmt.setInt(1, itemID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Location loc = new Location();
                Piece piece = new Piece();
                piece.setItemID(rs.getInt("itemID"));
                piece.setPieceNum(rs.getInt("pieceNum"));
                piece.setpDescription(rs.getString("pDescription"));
                piece.setLength(rs.getInt("length"));
                piece.setHeight(rs.getInt("height"));
                loc.setPiece(piece);
                loc.setRoomNum(rs.getInt("roomNum"));
                loc.setShelfNum(rs.getInt("shelfNum"));
                loc.setShelf(rs.getString("shelf"));
                loc.setShelf(rs.getString("shelfDescription"));
                data.add(loc);
            }


        }catch (Exception e){
            loggers.error("Location not Found: ",e);
        }

        return data;

    }

    public List<Location> getOrdersAndLocation(int orderID) {
        String itemInQuery = "SELECT l.roomNum AS locRoomNum, l.shelfNum AS locShelfNum, " +
                "l.shelf, l.shelfDescription, p.itemID, p.pieceNum, " +
                "p.pDescription, p.length, p.height, p.pNotes, " +
                "p.roomNum AS pieceRoomNum, p.shelfNum AS pieceShelfNum, " +
                "i.iDescription FROM Location l JOIN " +
                "Piece p ON p.roomNum = l.roomNum AND " +
                "p.shelfNum = l.shelfNum JOIN Item i ON " +
                "p.itemID = i.ItemID " +
                "WHERE p.itemID IN (SELECT itemID FROM ItemIn WHERE orderID = ?)";

        List<Location> data = new ArrayList<>();


        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(itemInQuery)
        ) {
            stmt.setInt(1, orderID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Location loc = new Location();
                Piece piece = new Piece();
                piece.setItemID(rs.getInt("itemID"));
                piece.setPieceNum(rs.getInt("pieceNum"));
                piece.setpDescription(rs.getString("pDescription"));
                piece.setLength(rs.getInt("length"));
                piece.setHeight(rs.getInt("height"));
                piece.setRoomNum(rs.getInt("pieceRoomNum"));
                piece.setShelfNum(rs.getInt("pieceShelfNum"));
                loc.setPiece(piece);
                loc.setRoomNum(rs.getInt("locRoomNum"));
                loc.setShelfNum(rs.getInt("locShelfNum"));
                loc.setShelf(rs.getString("shelf"));
                loc.setShelf(rs.getString("shelfDescription"));
                loggers.info(loc.toString());
                loggers.info(loc.getPiece().toString());
                data.add(loc);
            }
        } catch (SQLException e) {
            loggers.error("Error fetching orders and locations: ", e);
        }

        return data;
    }


    public void itemDonate(String username, String donorName, List<Item> items) {
        String insertItemQuery = "INSERT INTO Item (iDescription, color, isNew, hasPieces, material, mainCategory, subCategory) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String insertDonatedByQuery = "INSERT INTO DonatedBy (ItemID, userName, donateDate) VALUES (?, ?, CURDATE())";
        String insertPieceQuery = "INSERT INTO Piece (ItemID, pieceNum, pDescription, length, width, height, roomNum, shelfNum) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";


        // Step 1: Ensure the user is a staff member
        if (userDao.isStaff(username)) {
            throw new RuntimeException("User is not authorized to accept donations.");
        }

        // Step 2: Ensure the donor is registered
        if (!userDao.isDonor(donorName)) {
            throw new RuntimeException("Donor is not registered.");
        }

        try  {
            Connection connection = dataSource.getConnection();
            connection.setAutoCommit(false); // Start transaction

            try  {
                PreparedStatement itemStmt = connection.prepareStatement(insertItemQuery, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement donatedByStmt = connection.prepareStatement(insertDonatedByQuery);
                PreparedStatement pieceStmt = connection.prepareStatement(insertPieceQuery);


                // Step 3: Insert each item
                for (Item item : items) {
                    itemStmt.setString(1, item.getiDescription());
                    itemStmt.setString(2, item.getColor());
                    itemStmt.setBoolean(3, item.isNew());
                    itemStmt.setBoolean(4, item.getHasPieces());
                    itemStmt.setString(5, item.getMaterial());
                    itemStmt.setString(6, item.getMainCategory());
                    itemStmt.setString(7, item.getSubCategory());
                    itemStmt.executeUpdate();

                    ResultSet generatedKeys = itemStmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int itemID = generatedKeys.getInt(1);

                        // Step 4: Link the item to the donor
                        donatedByStmt.setInt(1, itemID);
                        donatedByStmt.setString(2, donorName);
                        donatedByStmt.executeUpdate();


                        // Step 6: Insert pieces
                        if (item.getPiece()!=null && !item.getPiece().isEmpty()) {
                            for (Piece piece : item.getPiece()) {
                                pieceStmt.setInt(1, itemID);
                                pieceStmt.setInt(2, piece.getPieceNum());
                                pieceStmt.setString(3, piece.getpDescription());
                                pieceStmt.setInt(4, piece.getLength());
                                pieceStmt.setInt(5, piece.getWidth());
                                pieceStmt.setInt(6, piece.getHeight());
                                pieceStmt.setInt(7, piece.getRoomNum());
                                pieceStmt.setInt(8, piece.getShelfNum());
                                pieceStmt.addBatch();
                            }
                            pieceStmt.executeBatch();
                        }
                    }
                }

                connection.commit(); // Commit the transaction
                loggers.info("Donation successfully recorded.");

            } catch (SQLException e) {
                connection.rollback(); // Rollback transaction if an error occurs
                loggers.error("Error inserting donation: ", e);
                throw new RuntimeException("Failed to insert donation", e);
            }
        } catch (SQLException e) {
            loggers.error("Database connection error: ", e);
            throw new RuntimeException("Failed to connect to database", e);
        }
    }



    public int createOrder(String staffUser, String client, List<Integer> itemIds, HttpSession session) {
        // SQL query to insert into Ordered table
        String insertOrder = "INSERT INTO Ordered (orderDate, orderNotes, supervisor, client) VALUES (CURDATE(), 'In Progress', ?, ?)";

        // SQL query to insert into ItemIn table
        String insertItemIn = "INSERT INTO ItemIn (ItemID, orderID, found) VALUES (?, ?, true)";

        // Create a KeyHolder to retrieve the generated orderID
        KeyHolder keyHolder = new GeneratedKeyHolder();

        // Step 1: Ensure the staff is authorized
        if (!userDao.isStaff(staffUser)) {
            throw new RuntimeException("User is not authorized.");
        }

        // Step 2: Ensure the client is registered
        if (!userDao.isClient(client)) {
            throw new RuntimeException("Client is not registered.");
        }

        // Step 3: Use a transaction to insert into both tables (Ordered and ItemIn)
        try (Connection connection = dataSource.getConnection()) {
            // Disable auto-commit to handle transaction
            connection.setAutoCommit(false);

            // Step 4: Insert the order into the Ordered table
            try (PreparedStatement stmt = connection.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, staffUser);
                stmt.setString(2, client);
                stmt.executeUpdate();

                // Retrieve the generated orderID
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int orderID = rs.getInt(1); // Get the generated orderID

                    // Step 5: Insert items into the ItemIn table for the created order
                    try (PreparedStatement stmtItemIn = connection.prepareStatement(insertItemIn)) {
                        for (Integer itemId : itemIds) {
                            stmtItemIn.setInt(1, itemId); // Set the item ID
                            stmtItemIn.setInt(2, orderID); // Set the orderID
                            stmtItemIn.addBatch(); // Add to batch
                        }

                        // Execute the batch insert into ItemIn
                        stmtItemIn.executeBatch();
                    }

                    // Commit the transaction if everything is successful
                    connection.commit();

                    // Store the orderID in the session
                    session.setAttribute("orderID", orderID);
                    Integer order = (Integer) session.getAttribute("orderID"); // Correctly cast to Integer
                    loggers.info("Order ID from session: " + order);  // Log the Integer value


                    // Return the generated orderID
                    return orderID;
                } else {
                    throw new SQLException("Failed to retrieve the generated orderID.");
                }
            } catch (SQLException e) {
                // Rollback the transaction in case of an error
                connection.rollback();
                throw new RuntimeException("Error while creating order and inserting items: " + e.getMessage(), e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database connection error: " + e.getMessage(), e);
        }
    }

    public List<Order> getOrdersByUser(String userName) throws SQLException {
        String query = "SELECT " +
                "o.orderID, " +
                "o.orderDate, " +
                "o.orderNotes, " +
                "o.supervisor, " +
                "o.client, " +
                "i.ItemID, " +
                "i.iDescription, " +
                "i.color, " +
                "i.material, " +
                "i.isNew, " +
                "CASE " +
                "    WHEN o.client = ? THEN 'Client Ordered' " +
                "    WHEN o.supervisor = ? THEN 'Staff Supervised' " +
                "    WHEN d.userName = ? THEN 'Volunteer Delivered' " +
                "    ELSE 'No Relationship' " +
                "END AS relationship, " +
                "d.userName AS deliveredBy " +
                "FROM Ordered o " +
                "JOIN ItemIn ii ON o.orderID = ii.orderID " +
                "JOIN Item i ON ii.ItemID = i.ItemID " +
                "LEFT JOIN Delivered d ON o.orderID = d.orderID " +
                "LEFT JOIN Person v ON v.userName = d.userName " +
                "LEFT JOIN Act a ON a.userName = v.userName AND a.roleID = 'R0002' " +
                "WHERE o.client = ? " +
                "   OR o.supervisor = ? " +
                "   OR d.userName = ? " +
                "   OR (v.userName = ? AND a.roleID = 'R0002')";

        List<Order> orders = new ArrayList<>();

        Connection connection = dataSource.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            // Set the parameters in the query
            ps.setString(1, userName);  // o.client
            ps.setString(2, userName);  // o.supervisor
            ps.setString(3, userName);  // v.userName
            ps.setString(4, userName);  // d.userName
            ps.setString(5, userName);  // a.userName
            ps.setString(6, userName);  // o.client
            ps.setString(7, userName);  // o.supervisor
//            ps.setString(8, userName);  // d.userName
//            ps.setString(9, userName);  // v.userName

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setOrderID(rs.getInt("orderID"));
                    order.setOrderDate(rs.getDate("orderDate"));
                    order.setOrderNotes(rs.getString("orderNotes"));
                    order.setSupervisor(rs.getString("supervisor"));
                    order.setClient(rs.getString("client"));
                    order.setItemID(rs.getInt("ItemID"));
                    order.setItemDescription(rs.getString("iDescription"));
                    order.setColor(rs.getString("color"));
                    order.setMaterial(rs.getString("material"));
                    order.setIsNew(rs.getBoolean("isNew"));
                    order.setRelationship(rs.getString("relationship"));
                    order.setDeliveredBy(rs.getString("deliveredBy"));
                    orders.add(order);
                }
            }
        }
        return orders;
    }


}







