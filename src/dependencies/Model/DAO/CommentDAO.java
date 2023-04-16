package dependencies.Model.DAO;

import dependencies.Model.Comment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CommentDAO {
    private final Connection connection;

    public CommentDAO(Connection connection) {
        this.connection = connection;
    }

    public void addComment(Comment comment) throws SQLException {
        String query = "INSERT INTO Comments (usersId, videosId, commentsText, commentsDatePosted) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, comment.getUserId());
            preparedStatement.setInt(2, comment.getVideoId());
            preparedStatement.setString(3, comment.getCommentText());
            preparedStatement.setDate(4, comment.getDatePosted());

            preparedStatement.executeUpdate();
        }
    }

    public ArrayList<Comment> getAllCommentsForVideo(int videoId) throws SQLException {
        ArrayList<Comment> comments = new ArrayList<>();
        String query = "SELECT * FROM Comments WHERE videosId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, videoId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("commentsId");
                    int userId = resultSet.getInt("usersId");
                    String commentText = resultSet.getString("commentsText");
                    java.sql.Date datePosted = resultSet.getDate("commentsDatePosted");

                    Comment comment = new Comment(id, userId, videoId, commentText, datePosted);
                    comments.add(comment);
                }
            }
        }
        return comments;
    }

    public boolean commentExists(int commentId) throws SQLException {
        String query = "SELECT * FROM Comments WHERE commentsId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, commentId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public void removeComment(int commentId) throws SQLException {
        String query = "DELETE FROM Comments WHERE commentsId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, commentId);

            preparedStatement.executeUpdate();
        }
    }
}
