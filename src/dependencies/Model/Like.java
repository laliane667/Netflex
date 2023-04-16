package dependencies.Model;

public class Like {
    private int id;
    private int userId;
    private int videoId;

    public Like(int userId, int videoId) {
        this.userId = userId;
        this.videoId = videoId;
    }

    public Like(int id, int userId, int videoId) {
        this.id = id;
        this.userId = userId;
        this.videoId = videoId;
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
}
