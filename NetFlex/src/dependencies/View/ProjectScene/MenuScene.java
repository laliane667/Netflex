package dependencies.View.ProjectScene;
import dependencies.Controller.SceneController.MenuController;
import dependencies.View.ProjectWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class MenuScene {
    private JPanel container = null;


    public MenuScene(MenuController menuController, ProjectWindow projectWindow, String error){
        JPanel verticalPanel = new JPanel();
        verticalPanel.setBackground( projectWindow.getBackgroundColor() );
        verticalPanel.setMaximumSize( new Dimension(800, projectWindow.getHeight()) );
        verticalPanel.setAlignmentX( Component.CENTER_ALIGNMENT );
        verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));


        JPanel buttonPanel = new JPanel();


        container = new JPanel();
        container.setBackground(projectWindow.getBackgroundColor());
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(buttonPanel);
    }

    public void changeVisibility(boolean b){this.container.setVisible(b);}
    public JPanel getContainer(){return container;}
}
