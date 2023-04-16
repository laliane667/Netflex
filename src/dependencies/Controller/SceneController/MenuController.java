package dependencies.Controller.SceneController;

import dependencies.Controller.ApplicationController;
import dependencies.Controller.SceneIdentifier;
import dependencies.Model.Dislike;
import dependencies.Model.Like;
import dependencies.Model.Video;
import dependencies.View.ProjectComponent.RoundedButton;
import dependencies.View.ProjectScene.MenuScene;
import dependencies.View.ProjectWindow;

import java.sql.SQLException;

public class MenuController extends SceneController {
    private final SceneIdentifier sceneIdentifier = SceneIdentifier.MENU;

    private MenuScene menuScene = null;

    public MenuController(ApplicationController applicationController, ProjectWindow projectWindow) {

        int potentialScrollValue = getValueAfterStringDelimiter(applicationController.getError(), "set-scroll-value:");
        super.Initialize(applicationController, projectWindow);
        menuScene = new MenuScene(this, super.getProjectWindow(), applicationController.getError(), potentialScrollValue);
        super.getProjectWindow().addPanel(menuScene.getContainer());
    }

    public void reloadThumbnails(Video video){
        menuScene.reloadThumbnails(video, this, getProjectWindow());
    }

    @Override
    public SceneIdentifier getActiveSceneIdentifier() {
        return sceneIdentifier;
    }

    public SceneIdentifier getSceneIdentifier() {
        return sceneIdentifier;
    }

    public void handleButton(RoundedButton b) {
        if (b.getName() == "profile") {
            menuScene.changeVisibility(false);
            super.getApplicationController().changeScene(SceneIdentifier.PROFILE, "none");
        }

        if (b.getName() == "parameters") {
            menuScene.changeVisibility(false);
            super.getApplicationController().changeScene(SceneIdentifier.PARAMETERS, "none");
        }

        if (b.getName() == "control-center") {
            menuScene.changeVisibility(false);
            super.getApplicationController().changeScene(SceneIdentifier.CONTROL_CENTER, "none");
        }

        if (b.getName() == "log-out") {
            menuScene.changeVisibility(false);
            super.getApplicationController().setLogged(false);
            super.getApplicationController().changeScene(SceneIdentifier.LOGIN, "none");
        }
    }

    public void playVideoButton(Video video) {
        System.out.println("PLAY");
        menuScene.changeVisibility(false);
        String request = "get-video-id:" + video.getId();
        super.getApplicationController().changeScene(SceneIdentifier.VIDEO_PLAYER, request);
    }

    public boolean handleWatchLaterButton(Video video, boolean adding) {
        int userId = getApplicationController().getApplicationUser().getUserId();

        if (adding) {
            try {
                getApplicationController().getConnectionController().getWatchLaterDAO().addWatchLater(userId, video.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            getApplicationController().getApplication().AddVideoToWatchList(video);
        } else {
            try {
                getApplicationController().getConnectionController().getWatchLaterDAO().removeWatchLater(userId, video.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            getApplicationController().getApplication().RemoveVideoToWatchList(video);
        }
        menuScene.changeVisibility(false);
        int scrollV = menuScene.getScrollValue();
        super.getApplicationController().changeScene(SceneIdentifier.MENU, "set-scroll-value:" + scrollV);
        return true;
    }

    public boolean handleLikeButton(Video video, boolean liking) {
        int userId = getApplicationController().getApplicationUser().getUserId();
        if(liking){

            removeDislike(video);

            Like like = new Like(userId, video.getId());

            try {
                getApplicationController().getConnectionController().getLikeDAO().addOrAssignLike(like);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                like = getApplicationController().getConnectionController().getLikeDAO().getLike(userId,video.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            getApplicationController().getApplication().addUserLike(like);

        }else{

            removeLike(video);

            Dislike dislike = new Dislike(userId, video.getId());

            try {
                getApplicationController().getConnectionController().getDislikeDAO().addDislike(dislike);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                dislike = getApplicationController().getConnectionController().getDislikeDAO().getDislike(userId, video.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            getApplicationController().getApplication().addUserDislike(dislike);

        }
        //menuScene.changeVisibility(false);
        //reloadThumbnails(video);
        //menuScene.changeVisibility(true);

        //super.getApplicationController().changeScene(SceneIdentifier.MENU, "none");
        return true;
    }

    public boolean removeLike(Video video){
        int userId = getApplicationController().getApplicationUser().getUserId();
        if(getApplicationController().getApplication().isVideoInUserLikeList(video.getId())){
            int likeId = getApplicationController().getApplication().getUserLike(userId, video.getId());
            try {
                getApplicationController().getConnectionController().getLikeDAO().removeLike(likeId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            getApplicationController().getApplication().removeVideoFromUserLikeList(video.getId());
        }else{
            return false;
        }
        return true;
    }

    public boolean removeDislike(Video video) {
        int userId = getApplicationController().getApplicationUser().getUserId();
        if(getApplicationController().getApplication().isVideoInUserDislikeList(video.getId())){
            int dislikeId = getApplicationController().getApplication().getUserDislike(userId, video.getId());
            System.out.println(dislikeId);
            try {
                getApplicationController().getConnectionController().getDislikeDAO().removeDislike(dislikeId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            getApplicationController().getApplication().removeVideoFromUserDislikeList(video.getId());
        }else{
            return false;
        }
        return true;
    }

    public boolean isVideoInUserWatchlist(int videoId){
        int userId = getApplicationController().getApplicationUser().getUserId();
        boolean b;
        try {
            b = getApplicationController().getConnectionController().getWatchLaterDAO().watchLaterExists(userId, videoId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return b;
    }
    public void handleAboutButton(int videoId){
        System.out.println("About video " + videoId);
    }

    public void Close(){

    }
}
