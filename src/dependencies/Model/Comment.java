package dependencies.Model;

import java.sql.Date;

public class Comment {
    private int id;
    private int userId;
    private int videoId;
    private String commentText;
    private Date datePosted;

    public Comment(int userId, int videoId, String commentText, Date datePosted) {
        this.userId = userId;
        this.videoId = videoId;
        this.commentText = commentText;
        this.datePosted = datePosted;
    }

    public Comment(int id, int userId, int videoId, String commentText, Date datePosted) {
        this.id = id;
        this.userId = userId;
        this.videoId = videoId;
        this.commentText = commentText;
        this.datePosted = datePosted;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getVideoId() {
        return videoId;
    }

    public String getCommentText() {
        return commentText;
    }

    public Date getDatePosted() {
        return datePosted;
    }
}
