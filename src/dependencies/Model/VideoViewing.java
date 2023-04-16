package dependencies.Model;

public class VideoViewing {
    private int viewingsId;
    private int usersId;
    private int videosId;
    private int videosStartTime;

    public VideoViewing(int viewingsId, int usersId, int videosId, int videosStartTime) {
        this.viewingsId = viewingsId;
        this.usersId = usersId;
        this.videosId = videosId;
        this.videosStartTime = videosStartTime;
    }

    public int getViewingsId() {
        return viewingsId;
    }

    public int getUsersId() {
        return usersId;
    }

    public int getVideosId() {
        return videosId;
    }

    public int getVideosStartTime() {
        return videosStartTime;
    }
}
