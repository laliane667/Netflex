package dependencies.Model.DAO;

import dependencies.Model.Reply;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReplyDAO {
    private final Connection connection;

    public ReplyDAO(Connection connection) {
        this.connection = connection;
    }

    public void addReply(Reply reply) throws SQLException {
        String query = "INSERT INTO Replies (usersId, commentsId, repliesText, repliesDatePosted) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, reply.getUserId());
            preparedStatement.setInt(2, reply.getCommentId());
            preparedStatement.setString(3, reply.getReplyText());
            preparedStatement.setDate(4, reply.getDatePosted());

            preparedStatement.executeUpdate();
        }
    }

    public ArrayList<Reply> getAllRepliesForComment(int commentId) throws SQLException {
        ArrayList<Reply> replies = new ArrayList<>();
        String query = "SELECT * FROM Replies WHERE commentsId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, commentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("repliesId");
                    int userId = resultSet.getInt("usersId");
                    String replyText = resultSet.getString("repliesText");
                    java.sql.Date datePosted = resultSet.getDate("repliesDatePosted");

                    Reply reply = new Reply(id, userId, commentId, replyText, datePosted);
                    replies.add(reply);
                }
            }
        }
        return replies;
    }

    public boolean replyExists(int replyId) throws SQLException {
        String query = "SELECT * FROM Replies WHERE repliesId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, replyId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public void removeReply(int replyId) throws SQLException {
        String query = "DELETE FROM Replies WHERE repliesId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, replyId);

            preparedStatement.executeUpdate();
        }
    }
}
