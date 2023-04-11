package dependencies.Model.DAO;

import dependencies.Model.Video;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VideoDAO {

    private Connection connection;

    public VideoDAO(Connection connection) {
        this.connection = connection;
    }

    public Video getVideoById(int id) throws SQLException {
        Video video = null;

        String query = "SELECT * FROM videos WHERE videosId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString("videosTitle");
                String description = resultSet.getString("videosDescription");
                int duration = resultSet.getInt("videosDuration");
                Date year = resultSet.getDate("videosYear");
                String streamPath = resultSet.getString("videosStreamPath");
                String thumbnailPath = resultSet.getString("videosThumbnailPath");

                video = new Video(id, title, description, duration, year, streamPath, thumbnailPath);
            }
        }

        return video;
    }

    public int addVideo(Video video) throws SQLException {
        String query = "INSERT INTO videos (videosTitle, videosDescription, videosDuration, videosYear, videosStreamPath, videosThumbnailPath) VALUES (?, ?, ?, ?, ?, ?)";
        int videoId = -1;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, video.getTitle());
            preparedStatement.setString(2, video.getDescription());
            preparedStatement.setInt(3, video.getDuration());
            preparedStatement.setDate(4, video.getYear());
            preparedStatement.setString(5, video.getStreamPath());
            preparedStatement.setString(6, video.getThumbnailPath());

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                videoId = rs.getInt(1);
            }
        }
        return videoId;
    }


    public void updateVideo(Video video) throws SQLException {
        String query = "UPDATE videos SET videosTitle = ?, videosDescription = ?, videosDuration = ?, videosYear = ?, videosStreamPath = ?, videosThumbnailPath = ? WHERE videosId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, video.getTitle());
            preparedStatement.setString(2, video.getDescription());
            preparedStatement.setInt(3, video.getDuration());
            preparedStatement.setDate(4, video.getYear());
            preparedStatement.setString(5, video.getStreamPath());
            preparedStatement.setString(6, video.getThumbnailPath());
            preparedStatement.setInt(7, video.getId());

            preparedStatement.executeUpdate();
        }
    }

    public void deleteVideo(int id) throws SQLException {
        String query = "DELETE FROM videos WHERE videosId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    public ArrayList<Video> getAllVideos() throws SQLException {
        ArrayList<Video> videos = new ArrayList<>();

        String query = "SELECT * FROM videos";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("videosId");
                String title = resultSet.getString("videosTitle");
                String description = resultSet.getString("videosDescription");
                int duration = resultSet.getInt("videosDuration");
                Date year = resultSet.getDate("videosYear");
                String streamPath = resultSet.getString("videosStreamPath");
                String thumbnailPath = resultSet.getString("videosThumbnailPath");

                Video video = new Video(id, title, description, duration, year, streamPath, thumbnailPath);
                videos.add(video);
            }
        }

        return videos;
    }

    public ArrayList<Video> getAllVideosByIds(int[] videoIds) throws SQLException {
        ArrayList<Video> videos = new ArrayList<>();

        String query = "SELECT * FROM videos WHERE videosId IN (" + generateQuestionMarks(videoIds.length) + ")";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < videoIds.length; i++) {
                preparedStatement.setInt(i + 1, videoIds[i]);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("videosId");
                String title = resultSet.getString("videosTitle");
                String description = resultSet.getString("videosDescription");
                int duration = resultSet.getInt("videosDuration");
                Date year = resultSet.getDate("videosYear");
                String streamPath = resultSet.getString("videosStreamPath");
                String thumbnailPath = resultSet.getString("videosThumbnailPath");

                Video video = new Video(id, title, description, duration, year, streamPath, thumbnailPath);
                videos.add(video);
            }
        }

        return videos;
    }

    private String generateQuestionMarks(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append("?");
            if (i < count - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }



}
