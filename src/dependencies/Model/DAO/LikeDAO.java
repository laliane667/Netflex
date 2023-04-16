package dependencies.Model.DAO;

import dependencies.Controller.ConnectionController;
import dependencies.Model.Like;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LikeDAO {
    private final Connection connection;

    public LikeDAO(Connection connection) {
        this.connection = connection;
    }

    public void addOrAssignLike(Like like) throws SQLException {
        String query = "INSERT INTO Likes (usersId, videosId) VALUES (?, ?) ON DUPLICATE KEY UPDATE usersId = VALUES(usersId), videosId = VALUES(videosId)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, like.getUserId());
            preparedStatement.setInt(2, like.getVideoId());

            preparedStatement.executeUpdate();
        }
    }

    public ArrayList<Integer> getAllLikesForVideo(int videoId) throws SQLException {
        ArrayList<Integer> usersIds = new ArrayList<>();
        String query = "SELECT usersId FROM Likes WHERE videosId = ?";
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

    public ArrayList<Integer> getAllLikesIdForUser(int userId) throws SQLException {
        ArrayList<Integer> videosIds = new ArrayList<>();
        String query = "SELECT videosId FROM Likes WHERE usersId = ?";
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

    public ArrayList<Like> getAllLikesForUser(int userId) throws SQLException {
        ArrayList<Like> likes = new ArrayList<>();
        String query = "SELECT * FROM Likes WHERE usersId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("likesId");
                    int videosId = resultSet.getInt("videosId");
                    int usersId = resultSet.getInt("usersId");

                    Like like = new Like(id, usersId, videosId);
                    likes.add(like);
                }
            }
        }
        return likes;
    }
    public boolean likeExists(int userId, int videoId) throws SQLException {
        String query = "SELECT * FROM Likes WHERE usersId = ? AND videosId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, videoId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public Like getLike(int userId, int videoId) throws SQLException {
        String query = "SELECT * FROM Likes WHERE usersId = ? AND videosId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, videoId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int likeId = resultSet.getInt("likesId");
                    Like like = new Like(likeId, userId, videoId);
                    return like;
                }
            }
        }
        return null;
    }

    public void removeLike(int likeId) throws SQLException {
        String query = "DELETE FROM Likes WHERE likesId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, likeId);

            preparedStatement.executeUpdate();
        }
    }
}
