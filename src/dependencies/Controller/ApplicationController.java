package dependencies.Controller;

import dependencies.Controller.SceneController.*;
import dependencies.Model.Application;
import dependencies.Model.User;
import dependencies.Model.Video;
import dependencies.View.*;

import java.util.EventListener;

public class ApplicationController implements EventListener{
    private ProjectWindow projectWindow;
    private SceneController sceneController;
    private Application application;
    private boolean isRunning = true;
    private boolean isLogged = false;
    private boolean isClickEnable = true;
    private SceneIdentifier sceneIdentifier = SceneIdentifier.LOGIN;
    private String error = "none";

    public ApplicationController(){
        application = new Application();
        projectWindow = new ProjectWindow("NetFlex");
        sceneController = new LoginController(this, projectWindow);

        projectWindow.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                Close(e);
            }
        });
    }


    public void Run(){
        switch (sceneIdentifier){
            case LOGIN:
                if(sceneController == null){
                    sceneController = new LoginController(this, projectWindow);
                }
            break;

            case SIGNUP:
                if(sceneController == null){
                    sceneController = new SignupController(this, projectWindow);
                }
                break;

            case MENU:
                if(sceneController == null){
                    sceneController = new MenuController(this, projectWindow);
                }
                break;

            case PROFILE:
                if(sceneController == null){
                    sceneController = new ProfileController(this, projectWindow);
                }
                break;

            case PARAMETERS:
                if(sceneController == null){
                    sceneController = new ParametersController(this, projectWindow);
                }
                break;

            case CONTROL_CENTER:
                if(sceneController == null){
                    sceneController = new ControlCenterController(this, projectWindow);
                }
                break;

            case VIDEO_PLAYER:
                if(sceneController == null){
                    sceneController = new VideoPlayerController(this, projectWindow);
                }
                break;
        }
        try
        {
            Thread.sleep(10);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }


    public void changeScene(SceneIdentifier sceneIdentifier, String error){
        sceneController = null;
        setSceneIdentifier(sceneIdentifier);
        this.error = error;
    }

    public void Close(java.awt.event.WindowEvent e){
        sceneController.Close();
        application.closeConnection();
        isRunning = false;
        e.getWindow().dispose();
    }

    //Getters
    public Application getApplication(){return application;}
    public User getApplicationUser() { return application.getApplicationUser(); }
    public ConnectionController getConnectionController() {return application.getConnectionController();}
    public boolean isRunning(){return isRunning;}
    public boolean isLogged(){return isLogged;}
    public boolean isClickEnable(){return isClickEnable;}
    public SceneIdentifier getSceneIdentifier(){return sceneIdentifier;}
    public String getError(){return error;}

    //Setters
    public void setAppUser(User appUser){application.setApplicationUser(appUser);}
    public void setRunning(boolean b){isRunning = b;}
    public void setLogged(boolean b){isLogged = b;}
    public void setClickEnable(boolean b){isClickEnable = b;}
    public void setSceneIdentifier(SceneIdentifier sceneIdentifier){this.sceneIdentifier = sceneIdentifier;}
}
