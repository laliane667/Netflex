package dependencies.View.ProjectScene.SubScene;

import dependencies.Controller.SceneController.ControlCenterController;
import dependencies.View.ProjectComponent.RoundedButton;
import dependencies.View.ProjectComponent.RoundedTextField;
import dependencies.View.ProjectWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCategorySubScene extends JPanel{
    public AddCategorySubScene(ProjectWindow projectWindow, ControlCenterController controlCenterController, String error){
        setPreferredSize(new Dimension(400, 220));
        setMaximumSize(new Dimension(400, 220));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(projectWindow.getBackgroundColor());

        JLabel errorLabel = new JLabel("none");
        errorLabel.setMaximumSize(new Dimension(400, 25));
        errorLabel.setForeground(projectWindow.getBackgroundColor());
        errorLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        errorLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        if(error == "category-added-successfully"){
            errorLabel.setText("\u2713 Category addedd successfully.");
            errorLabel.setForeground(Color.GREEN);
        }

        add(errorLabel);
        JLabel titleLabel = new JLabel();
        if(error == "category-empty-fields" || error == "category-empty-name"){
            titleLabel.setText("\u2716 Name: The field is empty.");
            titleLabel.setForeground(Color.RED);
        }else{
            titleLabel.setText("Name: ");
            titleLabel.setForeground(Color.WHITE);
        }
        titleLabel.setBackground(projectWindow.getBackgroundColor());
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        RoundedTextField titleField = new RoundedTextField(1, 15, 15, projectWindow.getBackgroundColor(), Color.WHITE, Color.WHITE);
        titleField.setFont(new Font("Arial", Font.PLAIN, 18));
        titleField.setBorder(BorderFactory.createEmptyBorder());
        titleField.setBackground(projectWindow.getBackgroundColor());
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.setForeground(Color.WHITE);
        titlePanel.setForeground(projectWindow.getBackgroundColor());
        titlePanel.add(titleField, BorderLayout.CENTER);
        titlePanel.setForeground(Color.WHITE);
        titlePanel.setBackground(projectWindow.getBackgroundColor());
        add(titlePanel);

        JLabel descriptionLabel = new JLabel();
        if(error == "category-empty-fields" || error == "category-empty-description"){
            descriptionLabel.setText("\u2716 Description: The field is empty.");
            descriptionLabel.setForeground(Color.RED);
        }else{
            descriptionLabel.setText("Description: ");
            descriptionLabel.setForeground(Color.WHITE);
        }
        descriptionLabel.setBackground(projectWindow.getBackgroundColor());
        descriptionLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        RoundedTextField descriptionField = new RoundedTextField(1, 15, 15, projectWindow.getBackgroundColor(), Color.WHITE, Color.WHITE);
        descriptionField.setMaximumSize(new Dimension(350, 20));
        descriptionField.setFont(new Font("Arial", Font.PLAIN, 18));
        descriptionField.setBorder(BorderFactory.createEmptyBorder());
        JPanel descriptionPanel = new JPanel(new BorderLayout());
        descriptionPanel.add(descriptionLabel, BorderLayout.NORTH);
        descriptionPanel.add(descriptionField, BorderLayout.CENTER);
        descriptionPanel.setForeground(Color.WHITE);
        descriptionPanel.setBackground(projectWindow.getBackgroundColor());
        add(descriptionPanel);

        RoundedButton submitButton = new RoundedButton("submit-video", "Submit", 20, 20, Color.WHITE, projectWindow.getBackgroundColor(), Color.WHITE);
        submitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        submitButton.setFont(new Font("Arial", Font.PLAIN, 18));
        submitButton.setPreferredSize(new Dimension(100, 25));
        submitButton.setMaximumSize(new Dimension(100, 25));
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlCenterController.handleSubmitCategoryButton(titleField.getText(), descriptionField.getText());
            }
        });
        JPanel submitButtonPanel = new JPanel(new BorderLayout());
        submitButtonPanel.setPreferredSize(new Dimension(200,40));
        submitButtonPanel.setMinimumSize(new Dimension(200,40));
        JPanel submitButtonCenterPanel = new JPanel();
        submitButtonCenterPanel.setPreferredSize(new Dimension(100,40));
        submitButtonCenterPanel.setMinimumSize(new Dimension(100,40));
        submitButtonCenterPanel.add(submitButton);
        submitButtonCenterPanel.setBackground(projectWindow.getBackgroundColor());
        submitButtonPanel.setBackground(projectWindow.getBackgroundColor());
        submitButtonPanel.add(submitButtonCenterPanel, BorderLayout.CENTER);

        add(Box.createVerticalStrut(15));
        add(submitButtonPanel);
        add(Box.createVerticalStrut(15));
    }
}
