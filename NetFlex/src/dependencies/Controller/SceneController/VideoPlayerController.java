package dependencies.Controller.SceneController;

import dependencies.Controller.ApplicationController;
import dependencies.Controller.SceneIdentifier;
import dependencies.Model.Video;
import dependencies.Model.VideoViewing;
import dependencies.View.ProjectComponent.RoundedButton;
import dependencies.View.ProjectScene.ParametersScene;
import dependencies.View.ProjectScene.VideoPlayerScene;
import dependencies.View.ProjectWindow;

import java.sql.SQLException;

public class VideoPlayerController extends SceneController {
    private final SceneIdentifier sceneIdentifier = SceneIdentifier.VIDEO_PLAYER;

    private VideoPlayerScene videoPlayerScene = null;

    public VideoPlayerController(ApplicationController applicationController, ProjectWindow projectWindow){
        super.Initialize(applicationController,projectWindow);
        String error = applicationController.getError();
        Video video;
        int reqId = getValueAfterStringDelimiter(error, "id:") ;
        try {
            video = getApplicationController().getConnectionController().getVideoDAO().getVideoById(reqId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        video.setStartTime(getVideoStartTime(video,getApplicationController().getApplicationUser().getUserId()));
        String videoPath = video.getStreamPath();
        videoPlayerScene = new VideoPlayerScene(this, super.getProjectWindow(), applicationController.getError(), video,videoPath);
        super.getProjectWindow().addPanel(videoPlayerScene.getContainer());
    }

    @Override
    public SceneIdentifier getActiveSceneIdentifier()
    {
        return sceneIdentifier;
    }
    public SceneIdentifier getSceneIdentifier(){return sceneIdentifier;}

    public int getVideoStartTime(Video video, int userId){
        return getApplicationController().getApplication().getVideoStartTime(video.getId(),userId);
        //if(startTime >= 0 && startTime < video.getDuration()) return startTime;
        //return 0;
    }
    public void handleBackButton(Video video, int timecode, boolean getBackToMenu){
        int userId = getApplicationController().getApplicationUser().getUserId();
        float endOfVideo = (float) ((video.getDuration() * 24) *0.95);
        //System.out.println("Timecode: "+ timecode + "Enf of vid:" + endOfVideo);

        if(timecode > endOfVideo )
        {
            try {
                getApplicationController().getConnectionController().getVideoMarkingsDAO().insertOrAssignVideoMarking(userId, video.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                getApplicationController().getConnectionController().getVideoViewingsDAO().insertOrAssignVideoViewing(userId, video.getId(), 0);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            getApplicationController().getApplication().InitializeViewingVideos();
            getApplicationController().getApplication().InitializeViewedVideos();
            if(getBackToMenu){
                videoPlayerScene.changeVisibility(false);
                super.getApplicationController().changeScene(SceneIdentifier.MENU, "none");
            }
            return;
        }
        try {
            getApplicationController().getConnectionController().getVideoViewingsDAO().insertOrAssignVideoViewing(userId, video.getId(), timecode);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        getApplicationController().getApplication().InitializeViewingVideos();
        if(getBackToMenu) {
            videoPlayerScene.changeVisibility(false);
            super.getApplicationController().changeScene(SceneIdentifier.MENU, "none");
        }
    }
    public void handleButton(RoundedButton b){

    }

    public void Close(){
        videoPlayerScene.Close();
    }
}
