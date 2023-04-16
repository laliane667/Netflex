package dependencies.Model.DAO;

import dependencies.Model.Subscription;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SubscriptionDAO {
    private final Connection connection;

    public SubscriptionDAO(Connection connection) {
        this.connection = connection;
    }

    public void addSubscription(Subscription subscription) throws SQLException {
        String query = "INSERT INTO Subscriptions (usersId, subscribersId) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, subscription.getUserId());
            preparedStatement.setInt(2, subscription.getSubscriberId());

            preparedStatement.executeUpdate();
        }
    }

    public ArrayList<Integer> getAllSubscribersForUser(int userId) throws SQLException {
        ArrayList<Integer> subscriberIds = new ArrayList<>();
        String query = "SELECT subscribersId FROM Subscriptions WHERE usersId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int subscriberId = resultSet.getInt("subscribersId");
                    subscriberIds.add(subscriberId);
                }
            }
        }
        return subscriberIds;
    }

    public ArrayList<Integer> getAllUsersSubscribedTo(int subscriberId) throws SQLException {
        ArrayList<Integer> userIds = new ArrayList<>();
        String query = "SELECT usersId FROM Subscriptions WHERE subscribersId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, subscriberId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int userId = resultSet.getInt("usersId");
                    userIds.add(userId);
                }
            }
        }
        return userIds;
    }

    public boolean subscriptionExists(int userId, int subscriberId) throws SQLException {
        String query = "SELECT * FROM Subscriptions WHERE usersId = ? AND subscribersId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, subscriberId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public void removeSubscription(int subscriptionId) throws SQLException {
        String query = "DELETE FROM Subscriptions WHERE subscriptionsId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, subscriptionId);

            preparedStatement.executeUpdate();
        }
    }
}
