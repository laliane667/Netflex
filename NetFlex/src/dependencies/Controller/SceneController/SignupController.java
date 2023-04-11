package dependencies.Controller.SceneController;

import dependencies.Controller.ApplicationController;
import dependencies.Controller.SceneIdentifier;
import dependencies.View.ProjectComponent.RoundedButton;
import dependencies.View.ProjectScene.SignupScene;
import dependencies.View.ProjectWindow;
import dependencies.Model.User;

import java.util.Calendar;
import java.util.Date;

public class SignupController extends SceneController {
    private final SceneIdentifier sceneIdentifier = SceneIdentifier.SIGNUP;

    SignupScene signupScene = null;
    public SignupController(ApplicationController applicationController, ProjectWindow projectWindow){
        super.Initialize(applicationController,projectWindow);
        signupScene = new SignupScene(this, super.getProjectWindow());
        signupScene.changeVisibility(true);
        System.out.println("OKOK");
        super.getProjectWindow().addPanel(signupScene.getContainer());
    }
    @Override
    public SceneIdentifier getActiveSceneIdentifier()
    {
        return sceneIdentifier;
    }
    public void handleButton(RoundedButton button) {
        if(button.getName() == "signToLog"){
            signupScene.changeVisibility(false);
            super.getApplicationController().changeScene(SceneIdentifier.LOGIN, "none");
        }
    }
    public SceneIdentifier getSceneIdentifier(){return sceneIdentifier;}

    public void handleSubmitButton(RoundedButton button, String firstName, String lastName, String uid, String email, String password, String repeatPassword, Date dateOfBirth) {
        if (button.getName() == "submit") {
            java.util.Calendar cal = Calendar.getInstance();
            java.util.Date utilDate = dateOfBirth; // your util date
            cal.setTime(utilDate);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            java.sql.Date sqlDate = new java.sql.Date(cal.getTime().getTime());
            User user = new User(firstName, lastName, email, uid, password, sqlDate, false);
            getApplicationController().setAppUser(user);
            getApplicationController().getApplication().InitializeWatchLater();
            getApplicationController().getApplication().InitializeViewingVideos();
            getApplicationController().getApplication().InitializeViewedVideos();
            System.out.println("User added");
            signupScene.changeVisibility(false);
            super.getApplicationController().changeScene(SceneIdentifier.MENU, "none");
        }
    }

    public void Close(){

    }
}

