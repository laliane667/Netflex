package dependencies.Controller.SceneController;

import dependencies.Controller.ApplicationController;
import dependencies.Controller.SceneIdentifier;
import dependencies.View.ProjectWindow;

public abstract class SceneController {

    private ApplicationController applicationController;
    private ProjectWindow projectWindow;
    public abstract SceneIdentifier getActiveSceneIdentifier();
    public abstract void Close();
    public void Initialize(ApplicationController applicationController, ProjectWindow projectWindow){
        this.applicationController = applicationController;
        this.projectWindow = projectWindow;
    };

    public int getValueAfterStringDelimiter(String string, String delimiter) {
        int delimiterIndex = string.lastIndexOf(delimiter);
        if (delimiterIndex != -1) {
            String idString = string.substring(delimiterIndex + delimiter.length());
            if(idString == "0") return -1;
            try {
                return Integer.parseInt(idString);
            } catch (NumberFormatException e) {
                System.err.println("Error parsing ID: " + idString);
            }
        }
        return -1; // Return -1 if the ID cannot be extracted or parsed
    }

    public ApplicationController getApplicationController(){return applicationController;}
    public ProjectWindow getProjectWindow(){return projectWindow;}
}
