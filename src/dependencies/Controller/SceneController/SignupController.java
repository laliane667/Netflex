package dependencies.Controller.SceneController;

import dependencies.Controller.ApplicationController;
import dependencies.Controller.SceneIdentifier;
import dependencies.Model.UserContract;
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

            try {
                getApplicationController().getConnectionController().getUserDAO().addUser(user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            int userId;
            try {
                userId = getApplicationController().getConnectionController().getUserDAO().getUserIdByEmailAndPassword(email,password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                user = getApplicationController().getConnectionController().getUserDAO().getUserById(userId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if(user.getUserId() >= 0){
                getApplicationController().setAppUser(user);
                getApplicationController().getApplication().InitializeWatchLater();
                getApplicationController().getApplication().InitializeViewingVideos();
                getApplicationController().getApplication().InitializeViewedVideos();
                getApplicationController().getApplication().InitializeLikedVideos();
                getApplicationController().getApplication().InitializeDislikedVideos();

                java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
                UserContract userContract = new UserContract(userId, currentDate);

                try {
                    getApplicationController().getConnectionController().getUserContractDAO().addUsersContract(userContract);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("User added");

                signupScene.changeVisibility(false);
                super.getApplicationController().changeScene(SceneIdentifier.MENU, "none");
            }
            else{
                //TODO return error
            }

        }
    }

    public void Close(){

    }
}

