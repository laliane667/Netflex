package dependencies.Model;

import java.sql.Date;

public class Reply {
    private int id;
    private int userId;
    private int commentId;
    private String replyText;
    private Date datePosted;

    public Reply(int userId, int commentId, String replyText, Date datePosted) {
        this.userId = userId;
        this.commentId = commentId;
        this.replyText = replyText;
        this.datePosted = datePosted;
    }

    public Reply(int id, int userId, int commentId, String replyText, Date datePosted) {
        this.id = id;
        this.userId = userId;
        this.commentId = commentId;
        this.replyText = replyText;
        this.datePosted = datePosted;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getCommentId() {
        return commentId;
    }

    public String getReplyText() {
        return replyText;
    }

    public Date getDatePosted() {
        return datePosted;
    }
}
