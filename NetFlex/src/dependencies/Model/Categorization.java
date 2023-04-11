package dependencies.Model;

public class Categorization {
    private int id;
    private int videoId;
    private int categoryId;

    public Categorization(int videoId, int categoryId) {
        this.videoId = videoId;
        this.categoryId = categoryId;
    }

    public Categorization(int id, int videoId, int categoryId) {
        this.id = id;
        this.videoId = videoId;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public int getVideoId() {
        return videoId;
    }

    public int getCategoryId() {
        return categoryId;
    }
}
