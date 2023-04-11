package dependencies.View.ProjectScene;

import dependencies.Controller.SceneController.ParametersController;
import dependencies.Controller.SceneController.ProfileController;
import dependencies.View.ProjectComponent.RoundedButton;
import dependencies.View.ProjectWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ParametersScene {
    private JPanel container = null;
    private JPanel navBarPanel = null;
    private RoundedButton navBarButton = null;

    public ParametersScene(ParametersController parametersController, ProjectWindow projectWindow, String error){
        container = new JPanel();
        container.setBackground(projectWindow.getBackgroundColor());
        container.setLayout(new BorderLayout());

        // Create the nav bar panel with the nav bar button
        navBarPanel = new JPanel();
        navBarPanel.setBackground(projectWindow.getBackgroundColor());
        navBarPanel.setLayout(new BorderLayout());
        navBarPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));

        navBarButton = new RoundedButton("back-to-menu", "Back", 20, 20, Color.WHITE, projectWindow.getBackgroundColor(), Color.WHITE);
        navBarButton.setPreferredSize(new Dimension(50, 25));
        navBarButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        navBarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parametersController.handleButton(navBarButton);
            }
        });

        navBarPanel.add(navBarButton, BorderLayout.WEST);
        container.add(navBarPanel, BorderLayout.NORTH);
    }
    public void changeVisibility(boolean b){this.container.setVisible(b);}
    public JPanel getContainer(){return container;}
}
