package dependencies.Model.DAO;

import dependencies.Model.UserContract;

import java.sql.*;
import java.util.ArrayList;

public class UserContractDAO {
    private Connection conn;

    public UserContractDAO(Connection conn) {
        this.conn = conn;
    }

    public void addUsersContract(UserContract usersContract) throws SQLException {
        String sql = "INSERT INTO UsersContract(usersId, contractStart) " +
                "VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, usersContract.getUserId());
        stmt.setDate(2, usersContract.getContractStart());

        stmt.executeUpdate();

        ResultSet generatedKeys = stmt.getGeneratedKeys();
        if (generatedKeys.next()) {
            usersContract.setContractId(generatedKeys.getInt(1));
        }
    }

    public UserContract getUsersContractById(int contractsId) throws SQLException {
        String query = "SELECT * FROM UsersContract WHERE contractsId=?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, contractsId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int usersId = resultSet.getInt("usersId");
            Date contractStart = resultSet.getDate("contractStart");
            return new UserContract(contractsId, usersId, contractStart);
        } else {
            return null; // No UsersContract found with the given contractsId
        }
    }

    public ArrayList<UserContract> getAllUserContracts() throws SQLException {
        ArrayList<UserContract> userContracts = new ArrayList<>();
        String query = "SELECT * FROM UsersContract";
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int contractsId = resultSet.getInt("contractsId");
            int usersId = resultSet.getInt("usersId");
            Date contractStart = resultSet.getDate("contractStart");

            UserContract userContract = new UserContract(contractsId, usersId, contractStart);
            userContracts.add(userContract);
        }

        return userContracts;
    }
}
