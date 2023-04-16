package dependencies.Model.DAO;

import dependencies.Controller.ConnectionController;
import dependencies.Model.VideoMarking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VideoMarkingsDAO {
    private final Connection connection;

    public VideoMarkingsDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertOrAssignVideoMarking(int userId, int videoId) throws SQLException {
        String checkQuery = "SELECT viewMarkingsId FROM ViewMarkings WHERE usersId = ? AND videosId = ?";

        try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
            checkStatement.setInt(1, userId);
            checkStatement.setInt(2, videoId);

            try (ResultSet resultSet = checkStatement.executeQuery()) {
                if (!resultSet.next()) {
                    String insertQuery = "INSERT INTO ViewMarkings (usersId, videosId) VALUES (?, ?)";

                    try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                        insertStatement.setInt(1, userId);
                        insertStatement.setInt(2, videoId);

                        insertStatement.executeUpdate();
                    }
                }
            }
        }
    }


    public ArrayList<VideoMarking> getVideoMarkingsByUserId(int userId) throws SQLException {
        ArrayList<VideoMarking> videoMarkings = new ArrayList<>();
        String query = "SELECT * FROM ViewMarkings WHERE usersId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int viewMarkingsId = resultSet.getInt("viewMarkingsId");
                    int videosId = resultSet.getInt("videosId");

                    VideoMarking videoMarking = new VideoMarking(viewMarkingsId, userId, videosId);
                    videoMarkings.add(videoMarking);
                }
            }
        }

        return videoMarkings;
    }

    public ArrayList<VideoMarking> getVideoMarkingsByVideoId(int videoId) throws SQLException {
        ArrayList<VideoMarking> videoMarkings = new ArrayList<>();
        String query = "SELECT * FROM ViewMarkings WHERE videosId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, videoId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int viewMarkingsId = resultSet.getInt("viewMarkingsId");
                    int usersId = resultSet.getInt("usersId");

                    VideoMarking videoMarking = new VideoMarking(viewMarkingsId, usersId, videoId);
                    videoMarkings.add(videoMarking);
                }
            }
        }

        return videoMarkings;
    }
}
