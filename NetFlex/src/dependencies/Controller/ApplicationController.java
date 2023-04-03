package dependencies.Controller;

import dependencies.Controller.SceneController.*;
import dependencies.View.*;

import java.util.EventListener;

public class ApplicationController implements EventListener{
    private ProjectWindow projectWindow;
    private SceneController sceneController;
    private ConnectionController connectionController;

    private boolean isRunning = true;
    private boolean isLogged = false;
    private boolean isClickEnable = true;
    private SceneIdentifier sceneIdentifier = SceneIdentifier.LOGIN;
    private String error = "none";
    public ApplicationController(){
        connectionController = new ConnectionController();

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
        connectionController.closeConnection();
        isRunning = false;
        e.getWindow().dispose();
    }

    //Getters

    public ConnectionController getConnectionController() {return connectionController;}
    public boolean isRunning(){return isRunning;}
    public boolean isLogged(){return isLogged;}
    public boolean isClickEnable(){return isClickEnable;}
    public SceneIdentifier getSceneIdentifier(){return sceneIdentifier;}
    public String getError(){return error;}

    //Setters
    public void setRunning(boolean b){isRunning = b;}
    public void setLogged(boolean b){isLogged = b;}
    public void setClickEnable(boolean b){isClickEnable = b;}
    public void setSceneIdentifier(SceneIdentifier sceneIdentifier){this.sceneIdentifier = sceneIdentifier;}
}
