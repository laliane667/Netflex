package dependencies.Controller.SceneController;

import dependencies.Controller.ApplicationController;
import dependencies.Controller.SceneIdentifier;
import dependencies.Model.Categorization;
import dependencies.Model.Category;
import dependencies.Model.User;
import dependencies.Model.Video;
import dependencies.View.ProjectComponent.RoundedButton;
import dependencies.View.ProjectScene.ControlCenterScene;
import dependencies.View.ProjectScene.ParametersScene;
import dependencies.View.ProjectWindow;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ControlCenterController extends SceneController {
    private final SceneIdentifier sceneIdentifier = SceneIdentifier.CONTROL_CENTER;

    private ControlCenterScene controlCenterScene = null;

    public ControlCenterController(ApplicationController applicationController, ProjectWindow projectWindow){

        super.Initialize(applicationController,projectWindow);
        controlCenterScene = new ControlCenterScene(this, super.getProjectWindow(), applicationController.getError());
        super.getProjectWindow().addPanel(controlCenterScene.getContainer());
    }

    @Override
    public SceneIdentifier getActiveSceneIdentifier()
    {
        return sceneIdentifier;
    }
    public SceneIdentifier getSceneIdentifier(){return sceneIdentifier;}

    public void handleButton(RoundedButton b){
        if(b.getName() == "back-to-menu"){
            controlCenterScene.changeVisibility(false);
            super.getApplicationController().changeScene(SceneIdentifier.MENU, "none");
        }
    }

    public void handleSubmitVideoButton(String title, String description, int duration, Date date, String streamPath, String thumbnailPath, ArrayList<String> categories){
        int videoId;
        int categoryNumber = categories.size();
        int[] categoryIDs = new int[categoryNumber];
        for (int i = 0; i < categoryNumber; i++){
            try {
                Category category = getApplicationController().getConnectionController().getCategoryDAO().getCategoryByName(categories.get(i));
                categoryIDs[i] = category.getId();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        java.util.Calendar cal = Calendar.getInstance();
        java.util.Date utilDate = date; // your util date
        cal.setTime(utilDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        java.sql.Date sqlDate = new java.sql.Date(cal.getTime().getTime());
        Video video = new Video(title, description, duration, sqlDate, streamPath, thumbnailPath);
        try {
            videoId = getApplicationController().getConnectionController().getVideoDAO().addVideo(video);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < categoryNumber; i++) {
            Categorization categorization = new Categorization(videoId, categoryIDs[i]);
            try {
                getApplicationController().getConnectionController().getCategorizationDAO().addCategorization(categorization);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        controlCenterScene.changeVisibility(false);
        super.getApplicationController().changeScene(SceneIdentifier.CONTROL_CENTER, "video-added-successfully");
    }

    public void handleSubmitCategoryButton(String name, String description){
        if(name.isBlank() && description.isBlank()){
            controlCenterScene.changeVisibility(false);
            super.getApplicationController().changeScene(SceneIdentifier.CONTROL_CENTER, "category-empty-fields");
            return;
        }
        if(name.isBlank()){
            controlCenterScene.changeVisibility(false);
            super.getApplicationController().changeScene(SceneIdentifier.CONTROL_CENTER, "category-empty-name");
            return;
        }
        if(description.isBlank()){
            controlCenterScene.changeVisibility(false);
            super.getApplicationController().changeScene(SceneIdentifier.CONTROL_CENTER, "category-empty-description");
            return;
        }
        Category category = new Category(name, description);
        try {
            getApplicationController().getConnectionController().getCategoryDAO().addCategory(category);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            category = getApplicationController().getConnectionController().getCategoryDAO().getCategoryByName(name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        getApplicationController().getApplication().addCategory(category);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        controlCenterScene.changeVisibility(false);
        super.getApplicationController().changeScene(SceneIdentifier.CONTROL_CENTER, "category-added-successfully");

    }

    public void handleSubmitRemoveButton(ArrayList<Integer> videoIds){
        if(videoIds.size() == 0){
            controlCenterScene.changeVisibility(false);
            super.getApplicationController().changeScene(SceneIdentifier.CONTROL_CENTER, "video-selection-empty-fields");
            return;
        }
        for(Integer i : videoIds){
            try {
                getApplicationController().getConnectionController().getVideoDAO().deleteVideo(i);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        controlCenterScene.changeVisibility(false);
        super.getApplicationController().changeScene(SceneIdentifier.CONTROL_CENTER, "video-selection-removed-successfully");
    }

    public void Close(){

    }

}
