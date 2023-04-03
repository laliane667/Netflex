package dependencies.Controller.SceneController;

import dependencies.Controller.*;
import dependencies.View.ProjectComponent.RoundedButton;
import dependencies.View.ProjectScene.LoginScene;
import dependencies.View.ProjectWindow;

import java.sql.SQLException;

public class LoginController extends SceneController {

    private final SceneIdentifier sceneIdentifier = SceneIdentifier.LOGIN;
    private LoginScene loginScene = null;
    public LoginController(ApplicationController applicationController, ProjectWindow projectWindow){
        super.Initialize(applicationController,projectWindow);
        loginScene = new LoginScene(this, super.getProjectWindow(), applicationController.getError());
        super.getProjectWindow().addPanel(loginScene.getContainer());
    }
    @Override
    public SceneIdentifier getActiveSceneIdentifier()
    {
        return sceneIdentifier;
    }
    public void handleButton(RoundedButton button) {
        if(button.getName() == "logToSign"){

            loginScene.changeVisibility(false);
            super.getApplicationController().changeScene(SceneIdentifier.SIGNUP, "none");
        }
    }

    public void handleSubmitButton(RoundedButton button, String uid, String password) {
        if (button.getName() == "submit") {
            if(uid.isBlank() || password.isBlank()){
                loginScene.changeVisibility(false);
                super.getApplicationController().changeScene(SceneIdentifier.LOGIN, "Empty field(s)");
                return;
            }
            System.out.println("UID: " + uid + "\nPassword: "+ password);
            try {
                boolean logResult = super.getApplicationController().getConnectionController().getUserDAO().isUserValid(uid,password);
                System.out.println("Connection state: ");
                loginScene.changeVisibility(false);
                if(logResult){
                    System.out.println("succes");
                    super.getApplicationController().changeScene(SceneIdentifier.MENU, "none");
                }else{
                    System.out.println("error");
                    super.getApplicationController().changeScene(SceneIdentifier.LOGIN, "Wrong username or password");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}