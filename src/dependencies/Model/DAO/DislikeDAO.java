package dependencies.Model.DAO;

import dependencies.Controller.ConnectionController;
import dependencies.Model.Dislike;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DislikeDAO {
    private final Connection connection;

    public DislikeDAO(Connection connection) {
        this.connection = connection;
    }

    public void addDislike(Dislike dislike) throws SQLException {
        String query = "INSERT INTO Dislikes (usersId, videosId) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, dislike.getUserId());
            preparedStatement.setInt(2, dislike.getVideoId());

            preparedStatement.executeUpdate();
        }
    }

    public ArrayList<Integer> getAllDislikesForVideo(int videoId) throws SQLException {
        ArrayList<Integer> usersIds = new ArrayList<>();
        String query = "SELECT usersId FROM Dislikes WHERE videosId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, videoId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("usersId");
                    usersIds.add(id);
                }
            }
        }
        return usersIds;
    }

    public ArrayList<Integer> getAllDislikesIdForUser(int userId) throws SQLException {
        ArrayList<Integer> videosIds = new ArrayList<>();
        String query = "SELECT videosId FROM Dislikes WHERE usersId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("videosId");
                    videosIds.add(id);
                }
            }
        }
        return videosIds;
    }

    public ArrayList<Dislike> getAllDislikesForUser(int userId) throws SQLException {
        ArrayList<Dislike> dislikes = new ArrayList<>();
        String query = "SELECT * FROM Dislikes WHERE usersId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("dislikesId");
                    int videosId = resultSet.getInt("videosId");
                    int usersId = resultSet.getInt("usersId");

                    Dislike dislike = new Dislike(id, usersId, videosId);
                    dislikes.add(dislike);
                }
            }
        }
        return dislikes;
    }

    public boolean dislikeExists(int userId, int videoId) throws SQLException {
        String query = "SELECT * FROM Dislikes WHERE usersId = ? AND videosId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, videoId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public Dislike getDislike(int userId, int videoId) throws SQLException {
        String query = "SELECT * FROM Dislikes WHERE usersId = ? AND videosId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, videoId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int dislikeId = resultSet.getInt("dislikesId");
                    Dislike dislike = new Dislike(dislikeId, userId, videoId);
                    return dislike;
                }
            }
        }
        return null;
    }

    public void removeDislike(int dislikeId) throws SQLException {
        String query = "DELETE FROM Dislikes WHERE dislikesId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, dislikeId);

            preparedStatement.executeUpdate();
        }
    }
}
