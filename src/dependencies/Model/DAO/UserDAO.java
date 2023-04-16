package dependencies.Model.DAO;

import dependencies.Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO users(usersFirstName, usersLastName, usersEmail, usersUID, usersPassword, usersDateOfBirth, usersPrivilege) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, user.getUserFirstName());
        stmt.setString(2, user.getUserLastName());
        stmt.setString(3, user.getUserEmail());
        stmt.setString(4, user.getUserUID());
        stmt.setString(5, user.getUserPassword());
        stmt.setDate(6, user.getUserDateOfBirth());
        stmt.setBoolean(7, user.getUserPrivilege());

        stmt.executeUpdate();
    }

    public boolean isUserValid(String usersEmail, String password) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE usersEmail = ? AND usersPassword = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, usersEmail);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getInt(1) > 0;
    }

    public User getUserById(int id) throws SQLException {
        String query = "SELECT * FROM users WHERE usersId=?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String firstName = resultSet.getString("usersFirstName");
            String lastName = resultSet.getString("usersLastName");
            String email = resultSet.getString("usersEmail");
            String uid = resultSet.getString("usersUID");
            String password = resultSet.getString("usersPassword");
            Date dateOfBirth = resultSet.getDate("usersDateOfBirth");
            boolean privilege = resultSet.getBoolean("usersPrivilege");
            return new User(id,firstName, lastName, email, uid, password, dateOfBirth, privilege);
        } else {
            return null; // No user found with the given id
        }
    }

    public int getUserIdByEmailAndPassword(String email, String password) throws SQLException {
        String query = "SELECT usersId FROM users WHERE usersEmail=? AND usersPassword=?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, email);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("usersId");
        } else {
            return -1; // No user found with the given email and password
        }
    }

}
