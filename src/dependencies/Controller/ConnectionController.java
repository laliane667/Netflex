package dependencies.Controller;

import dependencies.Model.Category;
import dependencies.Model.DAO.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Date;

public class ConnectionController {
    Connection connection = null;
    private final String url = "jdbc:mysql://localhost:8889/NetFlex";
    private final String username = "root";
    private final String password = "root";
    private UserDAO userDAO = null;
    private UserContractDAO userContractDAO = null;
    private VideoDAO videoDAO = null;
    private CategoryDAO categoryDAO = null;
    private CategorizationDAO categorizationDAO = null;
    private WatchLaterDAO watchLaterDAO = null;
    private VideoMarkingsDAO videoMarkingsDAO = null;
    private VideoViewingsDAO videoViewingsDAO = null;
    private LikeDAO likeDAO = null;
    private DislikeDAO dislikeDAO = null;
    private CommentDAO commentDAO = null;
    private ReplyDAO replyDAO = null;

    public ConnectionController(){
        try {
            connection = DriverManager.getConnection(url, username, password);
            userDAO = new UserDAO(connection);
            userContractDAO = new UserContractDAO(connection);
            videoDAO = new VideoDAO(connection);
            categoryDAO = new CategoryDAO(connection);
            categorizationDAO = new CategorizationDAO(connection);
            watchLaterDAO = new WatchLaterDAO(connection);
            videoMarkingsDAO = new VideoMarkingsDAO(connection);
            videoViewingsDAO = new VideoViewingsDAO(connection);
            likeDAO = new LikeDAO(connection);
            dislikeDAO = new DislikeDAO(connection);
            commentDAO = new CommentDAO(connection);
            replyDAO = new ReplyDAO(connection);

            System.out.println("Connection success");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            System.out.println("Closing connection");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Getters
    public Connection getConnection(){return connection;}

    public UserDAO getUserDAO() {return userDAO;}
    public UserContractDAO getUserContractDAO(){return userContractDAO;}
    public VideoDAO getVideoDAO() {return videoDAO;}
    public CategoryDAO getCategoryDAO(){return categoryDAO;}
    public CategorizationDAO getCategorizationDAO(){return categorizationDAO;}
    public WatchLaterDAO getWatchLaterDAO(){return watchLaterDAO;}
    public VideoMarkingsDAO getVideoMarkingsDAO(){return videoMarkingsDAO;}
    public VideoViewingsDAO getVideoViewingsDAO(){return videoViewingsDAO;}
    public LikeDAO getLikeDAO(){return likeDAO;}
    public DislikeDAO getDislikeDAO(){return dislikeDAO;}
    public CommentDAO getCommentDAO(){return commentDAO;}
    public ReplyDAO getReplyDAO(){return replyDAO;}
}
