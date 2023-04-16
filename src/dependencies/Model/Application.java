package dependencies.Model;

import dependencies.Controller.ConnectionController;

import java.sql.SQLException;
import java.util.ArrayList;

public class Application {
    private ConnectionController connectionController;
    private User applicationUser = null;

    private ArrayList<Like> likedVideo = null;
    private ArrayList<Dislike> dislikedVideo = null;
    private ArrayList<Video> watchLater = null;
    private ArrayList<VideoMarking> viewedVideos = null;
    private ArrayList<VideoViewing> viewingVideos = null;
    private ArrayList<Category> categories = null;
    private ArrayList<Category> displayedCategories = null;

    private Reporting reporting = null;
    public Application(){
        connectionController = new ConnectionController();
        InitializeCategories();
    }

    public void displayWatchList(){
        System.out.print("Videos in wl: ");
        for(Video video : watchLater){
            System.out.print("["+video.getId()+"]");
        }
        System.out.println("");
    }

    public void displayLiked(){
        for(Like like : likedVideo){
            System.out.println(like.getUserId() + " <- user / video -> " + like.getVideoId());
        }
    }
    public void InitializeLikedVideos(){
        try {
            likedVideo = connectionController.getLikeDAO().getAllLikesForUser(applicationUser.getUserId());
            //System.out.println("Size: "+likedVideo.size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void InitializeDislikedVideos(){
        try {
            dislikedVideo = connectionController.getDislikeDAO().getAllDislikesForUser(applicationUser.getUserId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void InitializeWatchLater(){
        try {
            watchLater = connectionController.getWatchLaterDAO().getWatchLaterByUserId(applicationUser.getUserId(), connectionController.getVideoDAO());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void InitializeViewingVideos(){
        try {
            viewingVideos = connectionController.getVideoViewingsDAO().getVideoViewingsByUserId(applicationUser.getUserId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void InitializeViewedVideos(){
        try {
            viewedVideos = connectionController.getVideoMarkingsDAO().getVideoMarkingsByUserId(applicationUser.getUserId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void InitializeReporting(){
        reporting = new Reporting(connectionController);
    }

    public int getVideoStartTime(int videoId, int userId){
        for(VideoViewing videoViewing : viewingVideos){
            if(videoViewing.getVideosId() == videoId && videoViewing.getUsersId() == userId) return videoViewing.getVideosStartTime();
        }
        return 0;
    }
    private void InitializeCategories(){
        categories = connectionController.getCategoryDAO().getAllCategories();
        try {
            displayedCategories = connectionController.getCategorizationDAO().getAllCategories(connectionController);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void closeConnection(){
        connectionController.closeConnection();
    }

    public void addCategory(Category category){categories.add(category);}
    public void AddVideoToWatchList(Video video){watchLater.add(video); displayWatchList();}
    public void RemoveVideoToWatchList(Video video){
        int removeId = -1;
        for(int i = 0; i < watchLater.size(); i++){
            if(watchLater.get(i).getId() == video.getId()) removeId = i;
        }
        if( removeId >= 0) watchLater.remove(removeId);
        displayWatchList();
    }

    //Getters
    public User getApplicationUser() { return applicationUser; }
    public Reporting getReporting(){ return reporting;}
    public boolean isVideoInUserWatchlist(int videoId){for(Video video : watchLater){if(video.getId() == videoId) return true;}return false;}

    public void addUserLike(Like like){likedVideo.add(like);}
    public boolean isVideoInUserLikeList(int videoId){ for(int i = 0; i < likedVideo.size(); i++){if(likedVideo.get(i).getVideoId() == videoId) return true;} return false;}
    public int getUserLike(int userId, int videoId){for(int i = 0; i < likedVideo.size(); i++){if(likedVideo.get(i).getUserId() == userId && likedVideo.get(i).getVideoId() == videoId){return likedVideo.get(i).getId();}}return -1;}
    public void removeVideoFromUserLikeList(int videoId){
        int userId = getApplicationUser().getUserId();
        for(int i = 0; i < likedVideo.size(); i++) if(likedVideo.get(i).getUserId() == userId && likedVideo.get(i).getVideoId() == videoId) likedVideo.remove(i);
    }

    public void addUserDislike(Dislike dislike){dislikedVideo.add(dislike);}
    public boolean isVideoInUserDislikeList(int videoId){for(Dislike dislike : dislikedVideo){if(dislike.getVideoId()== videoId) return true;} return false;}
    public int getUserDislike(int userId, int videoId){for(int i = 0; i < dislikedVideo.size(); i++){if(dislikedVideo.get(i).getUserId() == userId && dislikedVideo.get(i).getVideoId() == videoId){return dislikedVideo.get(i).getId();}}return -1;}
    public void removeVideoFromUserDislikeList(int videoId){
        int userId = getApplicationUser().getUserId();
        for(int i = 0; i < dislikedVideo.size(); i++) if(dislikedVideo.get(i).getUserId() == userId && dislikedVideo.get(i).getVideoId() == videoId) dislikedVideo.remove(i);
    }
    public ArrayList<Category> getDisplayedCategories(){return displayedCategories;}
    public ArrayList<Video> getAppUserHistory(){
        ArrayList<Video> history = new ArrayList<Video>();
        for(VideoMarking videoMarking : viewedVideos){
            if(videoMarking.getUsersId() == applicationUser.getUserId()){
                Video video;
                try {
                    video = getConnectionController().getVideoDAO().getVideoById(videoMarking.getVideosId());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                history.add(video);
            }
        }
        return history;
    }
    public String[] getAllCategories(){
        int catNum = categories.size();
        String[] list = new String[catNum];
        for(int i = 0; i < catNum; i++) list[i] = categories.get(i).getName();
        return list;
    }

    public ConnectionController getConnectionController(){return connectionController;}

    //Setters
    public void setApplicationUser(User u){applicationUser = u;}
}
