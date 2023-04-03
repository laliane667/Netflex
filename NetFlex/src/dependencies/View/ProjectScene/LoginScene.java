package dependencies.View.ProjectScene;

import dependencies.Controller.SceneController.LoginController;
import dependencies.View.ProjectComponent.*;
import dependencies.View.ProjectWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class LoginScene {
    private JPanel container = null;
    private RoundedButton signupButton = null;
    private RoundedButton submitButton = null;
    public LoginScene(LoginController loginController, ProjectWindow projectWindow, String error){
        JPanel verticalPanel = new JPanel();
        verticalPanel.setBackground( projectWindow.getBackgroundColor() );
        verticalPanel.setMaximumSize( new Dimension(800, projectWindow.getHeight()) );
        verticalPanel.setAlignmentX( Component.CENTER_ALIGNMENT );
        verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));

        JLabel welcomeLabel = new JLabel("Welcome on Netflex !");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 70));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(80, 0, 0, 20));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel choicePanel = new JPanel(new GridLayout(1, 2));
        choicePanel.setBackground( projectWindow.getBackgroundColor() );
        choicePanel.setMaximumSize( new Dimension(800, 100) );
        choicePanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 10, 0));

        JLabel loginLabel = new JLabel("Not registered yet?");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 30));
        loginLabel.setForeground(Color.WHITE);

        signupButton = new RoundedButton("logToSign","> Sign up here", 50, 50, Color.WHITE, projectWindow.getBackgroundColor(), Color.WHITE);
        signupButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signupButton.setFont(new Font("Arial", Font.BOLD, 30));
        signupButton.setPreferredSize(new Dimension(150, 50));
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(loginController.getActiveSceneIdentifier());
                loginController.handleButton(signupButton);
            }
        });

        choicePanel.add(loginLabel);
        choicePanel.add(signupButton);

        JLabel errorLabel = new JLabel(error);
        errorLabel.setFont(new Font("Arial", Font.BOLD, 20));
        errorLabel.setForeground(Color.RED);
        errorLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 40, 0));
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel usernameLabel = new JLabel("Username or Email");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        RoundedTextField usernameIpt = new RoundedTextField(2, 15,15, projectWindow.getBackgroundColor(), Color.WHITE, Color.WHITE);
        usernameIpt.setMaximumSize(new Dimension(350, 40));
        usernameIpt.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 20));
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBorder(BorderFactory.createEmptyBorder(35, 0, 15, 0));
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        RoundedTextField passwordIpt = new RoundedTextField(1, 15,15, projectWindow.getBackgroundColor(), Color.WHITE, Color.WHITE);
        passwordIpt.setMaximumSize(new Dimension(350, 30));
        passwordIpt.setFont(new Font("Arial", Font.PLAIN, 20));

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setBackground(projectWindow.getBackgroundColor());

        submitButton = new RoundedButton("submit", "Submit", 20, 20, Color.WHITE, projectWindow.getBackgroundColor(), Color.WHITE);
        submitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        submitButton.setFont(new Font("Arial", Font.PLAIN, 20));
        submitButton.setPreferredSize(new Dimension(200, 40));


        centerPanel.setOpaque(false);
        centerPanel.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginController.handleSubmitButton(submitButton, usernameIpt.getText(), passwordIpt.getText());
            }
        });


        verticalPanel.add(welcomeLabel);
        verticalPanel.add(choicePanel);
        if(error != "none"){
            verticalPanel.add(errorLabel);
        }else{
            verticalPanel.add(Box.createVerticalStrut(90)); // Ajoute une marge de 10 pixels
        }
        verticalPanel.add(usernameLabel);
        verticalPanel.add(usernameIpt);
        verticalPanel.add(passwordLabel);
        verticalPanel.add(passwordIpt);
        verticalPanel.add(Box.createVerticalStrut(70)); // Ajoute une marge de 10 pixels
        verticalPanel.add(centerPanel);

        container = new JPanel();
        container.setBackground(projectWindow.getBackgroundColor());
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(verticalPanel);
    }
    public void changeVisibility(boolean b){this.container.setVisible(b);}
    public JPanel getContainer(){return container;}
}
