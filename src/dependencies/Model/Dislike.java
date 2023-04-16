package dependencies.Model;

public class Dislike {
    private int id;
    private int userId;
    private int videoId;

    public Dislike(int userId, int videoId) {
        this.userId = userId;
        this.videoId = videoId;
    }

    public Dislike(int id, int userId, int videoId) {
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
