package dependencies.Controller.SceneController;

import dependencies.Controller.ApplicationController;
import dependencies.Controller.SceneIdentifier;
import dependencies.View.ProjectComponent.RoundedButton;
import dependencies.View.ProjectScene.ProfileScene;
import dependencies.View.ProjectWindow;

public class ProfileController extends SceneController {
    private final SceneIdentifier sceneIdentifier = SceneIdentifier.PROFILE;

    private ProfileScene profileScene = null;

    public ProfileController(ApplicationController applicationController, ProjectWindow projectWindow){

        super.Initialize(applicationController,projectWindow);
        profileScene = new ProfileScene(this, super.getProjectWindow(), applicationController.getError());
        super.getProjectWindow().addPanel(profileScene.getContainer());
    }

    @Override
    public SceneIdentifier getActiveSceneIdentifier()
    {
        return sceneIdentifier;
    }
    public SceneIdentifier getSceneIdentifier(){return sceneIdentifier;}

    public void handleButton(RoundedButton b){
        if(b.getName() == "back-to-menu"){
            profileScene.changeVisibility(false);
            super.getApplicationController().changeScene(SceneIdentifier.MENU, "none");
        }
    }

    public void Close(){

    }
}
