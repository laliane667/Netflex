package dependencies.Model;

public class VideoMarking {
    private int viewMarkingsId;
    private int usersId;
    private int videosId;

    public VideoMarking(int viewMarkingsId, int usersId, int videosId) {
        this.viewMarkingsId = viewMarkingsId;
        this.usersId = usersId;
        this.videosId = videosId;
    }

    public int getViewMarkingsId() {
        return viewMarkingsId;
    }

    public int getUsersId() {
        return usersId;
    }

    public int getVideosId() {
        return videosId;
    }
}
