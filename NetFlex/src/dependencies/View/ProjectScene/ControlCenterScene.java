package dependencies.View.ProjectScene;

import dependencies.Controller.SceneController.ControlCenterController;
import dependencies.View.ProjectComponent.RoundedButton;
import dependencies.View.ProjectScene.SubScene.AddCategorySubScene;
import dependencies.View.ProjectScene.SubScene.AddVideoSubScene;
import dependencies.View.ProjectScene.SubScene.DeleteVideoSubScene;
import dependencies.View.ProjectWindow;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlCenterScene {
    private JPanel container = null;
    private AddVideoSubScene addVideoSubScene = null;
    private DeleteVideoSubScene deleteVideoSubScene = null;
    private AddCategorySubScene addCategorySubScene = null;
    private JPanel sidebarPanel = null;
    private JPanel globalPanel = null;

    private JPanel addVideoPanelContainer = null;
    private RoundedButton addVideoButton = null;

    private JPanel addCategoryPanelContainer = null;
    private RoundedButton addCategoryButton = null;

    public ControlCenterScene(ControlCenterController controlCenterController, ProjectWindow projectWindow, String error) {
        // Create the main panels
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(projectWindow.getBackgroundColor());

        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setPreferredSize(new Dimension(215, projectWindow.getHeight()));
        sidebarPanel.setBackground(projectWindow.getBackgroundColor());
        sidebarPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));

        globalPanel = new JPanel();
        globalPanel.setBackground(projectWindow.getMenuBarColor());

        addVideoSubScene = new AddVideoSubScene(projectWindow, controlCenterController, error);
        deleteVideoSubScene = new DeleteVideoSubScene(projectWindow, controlCenterController, error);
        addCategorySubScene = new AddCategorySubScene(projectWindow, controlCenterController, error);

        JPanel subSceneContainer = new JPanel();
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Add or Remove Video");
        titledBorder.setTitleColor(Color.WHITE);
        titledBorder.setTitleFont(new Font("Arial", Font.PLAIN, 18));
        subSceneContainer.setBorder(titledBorder);
        subSceneContainer.setPreferredSize(new Dimension(450, 500));
        subSceneContainer.setBackground(projectWindow.getBackgroundColor());


        //=================== Error handling ========================================================
        if(error.contains("video") || error.contains("category")){
            subSceneContainer.setVisible(true);
            if(error.contains("video")){
                subSceneContainer.add(addVideoSubScene, BorderLayout.CENTER);
                subSceneContainer.add(Box.createHorizontalStrut(25), BorderLayout.CENTER);
                subSceneContainer.add(deleteVideoSubScene, BorderLayout.CENTER);
                titledBorder.setTitle("Add or Remove Video");
            }
            if(error.contains("category")) {
                subSceneContainer.add(addCategorySubScene, BorderLayout.CENTER);
                titledBorder.setTitle("New Category");
            }
        }else{
            subSceneContainer.setVisible(false);
        }
        //==================================================================================================


        // Center the blue panel horizontally and vertically within globalPanel
        globalPanel.setLayout(new BorderLayout());
        globalPanel.add(Box.createHorizontalStrut(150), BorderLayout.WEST);
        globalPanel.add(Box.createHorizontalStrut(150), BorderLayout.EAST);
        globalPanel.add(Box.createVerticalStrut(150), BorderLayout.NORTH);
        globalPanel.add(Box.createVerticalStrut(150), BorderLayout.SOUTH);

        //=================== PANEL [BACK] ========================================================
        JPanel backButtonPanel = new JPanel(new BorderLayout());
        backButtonPanel.setBackground(projectWindow.getBackgroundColor());
        backButtonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 0));
        backButtonPanel.setMaximumSize(new Dimension(80,35));

        RoundedButton backButton = new RoundedButton("back-to-menu", "Back", 20, 20, Color.WHITE, projectWindow.getBackgroundColor(), Color.WHITE);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlCenterController.handleButton(backButton);
            }
        });
        backButtonPanel.add(backButton, BorderLayout.CENTER);
        //==================================================================================================

        //=================== PANEL ADD VIDEO [+] ========================================================
        JLabel addVideoLabel = new JLabel("Video");
        addVideoPanelContainer = new JPanel();
        addVideoPanelContainer.setPreferredSize(new Dimension(200, 50));
        addVideoPanelContainer.setMaximumSize(new Dimension(200, 50));
        addVideoPanelContainer.setBackground(projectWindow.getBackgroundColor());
        addVideoButton = new RoundedButton("+", "+", 10, 10, Color.WHITE, projectWindow.getBackgroundColor(), Color.WHITE);
        addVideoButton.setPreferredSize(new Dimension(30, 30));
        addVideoButton.setMaximumSize(new Dimension(30, 30));
        addVideoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isVisible = subSceneContainer.isVisible();
                subSceneContainer.setVisible(!isVisible);
                if (!isVisible) {
                    subSceneContainer.removeAll();
                    subSceneContainer.add(addVideoSubScene, BorderLayout.CENTER);
                    subSceneContainer.add(Box.createHorizontalStrut(25), BorderLayout.CENTER);
                    subSceneContainer.add(deleteVideoSubScene, BorderLayout.CENTER);
                    addVideoButton.setText("-");
                } else {
                    addVideoButton.setText("+");
                }
            }
        });
        JPanel addVideoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        addVideoPanel.setPreferredSize(new Dimension(200, 30));
        addVideoPanel.setMaximumSize(new Dimension(200, 30));
        addVideoPanel.setBackground(projectWindow.getBackgroundColor());
        addVideoLabel.setForeground(Color.WHITE);
        addVideoLabel.setPreferredSize(new Dimension(150, 30));
        addVideoPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        addVideoPanel.add(addVideoLabel);
        addVideoPanel.add(Box.createHorizontalStrut(15)); // Ajoute une marge de 10 pixels
        addVideoPanel.add(addVideoButton);
        addVideoPanel.add(Box.createHorizontalStrut(15)); // Ajoute une marge de 10 pixels
        addVideoPanelContainer.add(addVideoPanel);
        //==================================================================================================

        //=================== PANEL ADD CATEGORY [+] ========================================================
        JLabel addCategoryLabel = new JLabel("Add a new category");
        addCategoryPanelContainer = new JPanel();
        addCategoryPanelContainer.setPreferredSize(new Dimension(200, 50));
        addCategoryPanelContainer.setMaximumSize(new Dimension(200, 50));
        addCategoryPanelContainer.setBackground(projectWindow.getBackgroundColor());
        addCategoryButton = new RoundedButton("+", "+", 10, 10, Color.WHITE, projectWindow.getBackgroundColor(), Color.WHITE);
        addCategoryButton.setPreferredSize(new Dimension(30, 30));
        addCategoryButton.setMaximumSize(new Dimension(30, 30));
        addCategoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isVisible = subSceneContainer.isVisible();
                subSceneContainer.setVisible(!isVisible);
                if (!isVisible) {
                    subSceneContainer.removeAll();
                    subSceneContainer.add(addCategorySubScene, BorderLayout.CENTER);
                    addCategoryButton.setText("-");
                } else {
                    addCategoryButton.setText("+");
                }
            }
        });
        JPanel addCategoryPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        addCategoryPanel.setPreferredSize(new Dimension(200, 30));
        addCategoryPanel.setMaximumSize(new Dimension(200, 30));
        addCategoryPanel.setBackground(projectWindow.getBackgroundColor());
        addCategoryLabel.setForeground(Color.WHITE);
        addCategoryLabel.setPreferredSize(new Dimension(150, 30));
        addCategoryPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        addCategoryPanel.add(addCategoryLabel);
        addCategoryPanel.add(Box.createHorizontalStrut(15)); // Ajoute une marge de 10 pixels
        addCategoryPanel.add(addCategoryButton);
        addCategoryPanel.add(Box.createHorizontalStrut(15)); // Ajoute une marge de 10 pixels
        addCategoryPanelContainer.add(addCategoryPanel);
        //==================================================================================================

        sidebarPanel.add(backButtonPanel);
        sidebarPanel.add(addVideoPanelContainer);
        sidebarPanel.add(addCategoryPanelContainer);

        globalPanel.add(subSceneContainer, BorderLayout.CENTER);
        container = new JPanel(new BorderLayout());
        container.setBackground(projectWindow.getBackgroundColor());
        container.add(sidebarPanel, BorderLayout.WEST);
        container.add(globalPanel, BorderLayout.CENTER);
    }

    public void changeVisibility(boolean b) {
        this.container.setVisible(b);
    }

    public JPanel getContainer() {
        return container;
    }
}
