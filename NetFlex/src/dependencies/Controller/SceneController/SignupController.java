package dependencies.Controller.SceneController;

import dependencies.Controller.ApplicationController;
import dependencies.Controller.SceneIdentifier;
import dependencies.View.ProjectComponent.RoundedButton;
import dependencies.View.ProjectScene.SignupScene;
import dependencies.View.ProjectWindow;
import dependencies.Model.User;

import java.sql.SQLException;
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

    public void handleSubmitButton(RoundedButton button, String uid, String name, String email, String password, String repeatPassword, Date dateOfBirth) {
        if (button.getName() == "submit") {
            java.util.Calendar cal = Calendar.getInstance();
            java.util.Date utilDate = dateOfBirth; // your util date
            cal.setTime(utilDate);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            java.sql.Date sqlDate = new java.sql.Date(cal.getTime().getTime());
            try {
                User user = new User(uid,name,email,password,sqlDate);
                super.getApplicationController().getConnectionController().getUserDAO().addUser(user);
                System.out.println("User added");
                signupScene.changeVisibility(false);
                super.getApplicationController().changeScene(SceneIdentifier.MENU, "none");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

