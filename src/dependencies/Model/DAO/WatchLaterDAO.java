package dependencies.Model.DAO;

import dependencies.Controller.ConnectionController;
import dependencies.Model.Video;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WatchLaterDAO {
    private final Connection connection;

    public WatchLaterDAO(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<Video> getWatchLaterByUserId(int userId, VideoDAO videoDAO) throws SQLException {
        ArrayList<Video> watchLaterVideos = new ArrayList<>();
        String query = "SELECT videosId FROM watchLists WHERE usersId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int videoId = resultSet.getInt("videosId");
                    Video video = videoDAO.getVideoById(videoId);
                    watchLaterVideos.add(video);
                }
            }
        }

        return watchLaterVideos;
    }

    public void addWatchLater(int userId, int videoId) throws SQLException {
        String query = "INSERT INTO watchLists (usersId, videosId) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, videoId);

            preparedStatement.executeUpdate();
        }
    }

    public boolean watchLaterExists(int userId, int videoId) throws SQLException {
        String query = "SELECT * FROM watchLists WHERE usersId = ? AND videosId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, videoId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public void removeWatchLater(int userId, int videoId) throws SQLException {
        String query = "DELETE FROM watchLists WHERE usersId = ? AND videosId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, videoId);

            preparedStatement.executeUpdate();
        }
    }
}
