package dependencies.Controller.SceneController;

import dependencies.Controller.ApplicationController;
import dependencies.Controller.SceneIdentifier;
import dependencies.View.ProjectWindow;

public abstract class SceneController {

    private ApplicationController applicationController;
    private ProjectWindow projectWindow;
    public abstract SceneIdentifier getActiveSceneIdentifier();
    public void Initialize(ApplicationController applicationController, ProjectWindow projectWindow){
        this.applicationController = applicationController;
        this.projectWindow = projectWindow;
    };

    public ApplicationController getApplicationController(){return applicationController;}
    public ProjectWindow getProjectWindow(){return projectWindow;}
}
