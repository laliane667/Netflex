package dependencies.Controller.SceneController;

import dependencies.Controller.ApplicationController;
import dependencies.Controller.SceneIdentifier;
import dependencies.View.ProjectComponent.RoundedButton;
import dependencies.View.ProjectScene.ParametersScene;
import dependencies.View.ProjectWindow;

public class ParametersController extends SceneController {
    private final SceneIdentifier sceneIdentifier = SceneIdentifier.PROFILE;

    private ParametersScene parametersScene = null;

    public ParametersController(ApplicationController applicationController, ProjectWindow projectWindow){

        super.Initialize(applicationController,projectWindow);
        parametersScene = new ParametersScene(this, super.getProjectWindow(), applicationController.getError());
        super.getProjectWindow().addPanel(parametersScene.getContainer());
    }

    @Override
    public SceneIdentifier getActiveSceneIdentifier()
    {
        return sceneIdentifier;
    }
    public SceneIdentifier getSceneIdentifier(){return sceneIdentifier;}

    public void handleButton(RoundedButton b){
        if(b.getName() == "back-to-menu"){
            parametersScene.changeVisibility(false);
            super.getApplicationController().changeScene(SceneIdentifier.MENU, "none");
        }
    }

    public void Close(){

    }
}
