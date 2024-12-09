package com.welcomehome.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {


    private final DataSource dataSource;
    private PreparedStatement stmt;
    private Logger loggers = LoggerFactory.getLogger(UserDao.class);


    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void registerUser(User user){

        String insertQuery = "INSERT INTO PERSON (userName, password, fname, lname, email) VALUES (?, ?, ?, ?, ?)";
        try{
            stmt = connection(insertQuery);
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getFname());
            stmt.setString(4, user.getLname());
            stmt.setString(5, user.getEmail());
            stmt.execute();
            addAct(user, user.getRole());
            addPhone(user, user.getPhoneNumbers());
            loggers.info("Inserted Successfully.");
        } catch (SQLException e) {
            loggers.error("User Dao register: ", e);
            throw new RuntimeException(e);
        }
    }

    public void addPhone(User user, String[] phones) {
        loggers.info(user.toString());
        loggers.info(phones[0]);
        String insertQuery = "INSERT INTO PersonPhone (userName, phone) VALUES (?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
            for ( int i = 0; i < phones.length; i++) {
                stmt.setString(1, user.getUserName());
                stmt.setString(2, phones[i]);
                stmt.addBatch();
            }

            stmt.executeBatch();
        } catch (SQLException e) {
            loggers.error("Error inserting phones for user {}: {}", user.getUserName(), e.getMessage());
            throw new RuntimeException(e);
        }


    }

    public void addAct(User user, String role){
        String insertQuery = "INSERT INTO ACT (userName, roleID) VALUES (?, ?)";
        try{
            stmt = connection(insertQuery);
            stmt.setString(1, user.getUserName());
            stmt.setString(2, role);
            stmt.execute();
            loggers.info("Inserted Act Successfully.");
        } catch (SQLException e) {
            loggers.error("Act error: ", e);
            throw new RuntimeException(e);
        }
    }

    private PreparedStatement connection(String query) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(query);
        return stmt;
    }

    public List<Role> getAllRoles(){
        List<Role> roles = new ArrayList<Role>();
        String selectRoles = "SELECT * FROM ROLE";
        try{
            stmt = connection(selectRoles);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Role role = new Role();
                role.setRoleID(rs.getString("roleID"));
                role.setrDescription(rs.getString("rDescription"));
                roles.add(role);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return roles;
    }

    public User loginUser(User user) {
        User dbUser = new User();
        String userQuery = "SELECT * FROM PERSON WHERE userName = ?";
        try{

            stmt = connection(userQuery);
            stmt.setString(1, user.getUserName());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                dbUser.setUserName(rs.getString("userName"));
                dbUser.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dbUser;
    }

    public boolean isStaff(String staffUser) {
        loggers.info("Staff: ",staffUser);
        String staffQuery = "SELECT * FROM ACT WHERE userName = ? AND roleID = 'R0001'";
        try {
            stmt = connection(staffQuery);
            stmt.setString(1, staffUser);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            loggers.error("Error checking staff role: ", e);
            throw new RuntimeException(e);
        }
    }
    public boolean isClient(String clientUser) {
        String staffQuery = "SELECT * FROM ACT WHERE userName = ? AND roleID = 'R0003'";
        try {
            stmt = connection(staffQuery);
            stmt.setString(1, clientUser);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            loggers.error("Error checking client role: ", e);
            throw new RuntimeException(e);
        }
    }
    public boolean isDonor(String donorName) {
        loggers.info("Donor: ", donorName);
        String donorQuery = "SELECT * FROM ACT WHERE userName = ? AND roleID = 'R0004'";
        try {
            stmt = connection(donorQuery);
            stmt.setString(1,donorName);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            loggers.error("Error checking donor role: ", e);
            throw new RuntimeException(e);
        }
    }



}
