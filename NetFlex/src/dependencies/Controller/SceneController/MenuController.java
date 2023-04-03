package dependencies.Controller.SceneController;

import dependencies.Controller.ApplicationController;
import dependencies.Controller.SceneIdentifier;
import dependencies.View.ProjectScene.LoginScene;
import dependencies.View.ProjectScene.MenuScene;
import dependencies.View.ProjectWindow;

public class MenuController extends SceneController {
    private final SceneIdentifier sceneIdentifier = SceneIdentifier.MENU;

    private MenuScene menuScene = null;

    public MenuController(ApplicationController applicationController, ProjectWindow projectWindow){

        super.Initialize(applicationController,projectWindow);
        menuScene = new MenuScene(this, super.getProjectWindow(), applicationController.getError());
        super.getProjectWindow().addPanel(menuScene.getContainer());
    }

    @Override
    public SceneIdentifier getActiveSceneIdentifier()
    {
        return sceneIdentifier;
    }
    public SceneIdentifier getSceneIdentifier(){return sceneIdentifier;}
}
