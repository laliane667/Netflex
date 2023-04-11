package dependencies.Model.DAO;

import dependencies.Controller.ConnectionController;
import dependencies.Model.VideoViewing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VideoViewingsDAO {
    private final Connection connection;

    public VideoViewingsDAO(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<VideoViewing> getVideoViewingsByUserId(int userId) throws SQLException {
        ArrayList<VideoViewing> videoViewings = new ArrayList<>();
        String query = "SELECT * FROM VideoViewings WHERE usersId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int viewingsId = resultSet.getInt("viewingsId");
                    int videosId = resultSet.getInt("videosId");
                    int videosStartTime = resultSet.getInt("videosStartTime");
                    VideoViewing videoViewing = new VideoViewing(viewingsId, userId, videosId, videosStartTime);
                    videoViewings.add(videoViewing);
                }
            }
        }

        return videoViewings;
    }

    public void insertOrAssignVideoViewing(int userId, int videoId, int videosStartTime) throws SQLException {
        String checkQuery = "SELECT viewingsId FROM VideoViewings WHERE usersId = ? AND videosId = ?";

        try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
            checkStatement.setInt(1, userId);
            checkStatement.setInt(2, videoId);

            try (ResultSet resultSet = checkStatement.executeQuery()) {
                if (resultSet.next()) {
                    int viewingsId = resultSet.getInt("viewingsId");
                    String updateQuery = "UPDATE VideoViewings SET videosStartTime = ? WHERE viewingsId = ?";

                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setInt(1, videosStartTime);
                        updateStatement.setInt(2, viewingsId);

                        updateStatement.executeUpdate();
                    }
                } else {
                    String insertQuery = "INSERT INTO VideoViewings (usersId, videosId, videosStartTime) VALUES (?, ?, ?)";

                    try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                        insertStatement.setInt(1, userId);
                        insertStatement.setInt(2, videoId);
                        insertStatement.setInt(3, videosStartTime);

                        insertStatement.executeUpdate();
                    }
                }
            }
        }
    }

}
