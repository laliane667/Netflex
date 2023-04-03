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

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        /*while (rs.next()) {
            User user = new User(
                    rs.getInt("usersUID"),
                    rs.getInt("usersId"),
                    rs.getString("usersName"),
                    rs.getString("usersEmail"),
                    rs.getString("usersPassword"),
                    rs.getDate("usersDateOfBirth")
            );

            //users.add(user);
        }*/

        return users;
    }

    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO users(usersName, usersEmail, usersUID, usersPassword, usersDateOfBirth) " +
                "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, user.getUserName());
        stmt.setString(2, user.getUserEmail());
        stmt.setString(3, user.getUserUID());
        stmt.setString(4, user.getUserPassword());
        stmt.setDate(5, user.getUserDateOfBirth());

        stmt.executeUpdate();
    }

    public boolean isUserValid(String usersUID, String password) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE usersUID = ? AND usersPassword = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, usersUID);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getInt(1) > 0;
    }
}
