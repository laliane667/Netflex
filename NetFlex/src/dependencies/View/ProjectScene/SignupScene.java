package dependencies.View.ProjectScene;

import dependencies.Controller.SceneController.SignupController;
import dependencies.View.ProjectComponent.*;
import dependencies.View.ProjectWindow;
import org.jdatepicker.impl.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SignupScene {
    private JPanel container = null;
    private RoundedButton signUpButton = null;
    private RoundedButton submitButton = null;
    public SignupScene(SignupController signupController, ProjectWindow projectWindow){
        JPanel verticalPanel = new JPanel();
        verticalPanel.setBackground( projectWindow.getBackgroundColor() );
        verticalPanel.setMaximumSize( new Dimension(800, projectWindow.getHeight()) );
        verticalPanel.setAlignmentX( Component.CENTER_ALIGNMENT );
        verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));

        JLabel welcomeLabel = new JLabel("Welcome on Netflex !");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 70));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 20));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel choicePanel = new JPanel(new GridLayout(1, 2));
        choicePanel.setBackground( projectWindow.getBackgroundColor() );
        choicePanel.setMaximumSize( new Dimension(800, 100) );
        choicePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        JLabel loginLabel = new JLabel("Already registered ?");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 30));
        loginLabel.setForeground(Color.WHITE);

        signUpButton = new RoundedButton("signToLog","> Log in here", 50, 50, Color.WHITE, projectWindow.getBackgroundColor(), Color.WHITE);
        signUpButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signUpButton.setFont(new Font("Arial", Font.BOLD, 30));
        signUpButton.setPreferredSize(new Dimension(150, 50));
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signupController.handleButton(signUpButton);
            }
        });

        choicePanel.add(loginLabel);
        choicePanel.add(signUpButton);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 10, 0));
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        RoundedTextField usernameIpt = new RoundedTextField(2, 15,15, projectWindow.getBackgroundColor(), Color.WHITE, Color.WHITE);
        usernameIpt.setMaximumSize(new Dimension(350, 40));
        usernameIpt.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel nameLabel = new JLabel("Full name");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        RoundedTextField nameIpt = new RoundedTextField(2, 15,15, projectWindow.getBackgroundColor(), Color.WHITE, Color.WHITE);
        nameIpt.setMaximumSize(new Dimension(350, 40));
        nameIpt.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 20));
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        RoundedTextField emailIpt = new RoundedTextField(2, 15,15, projectWindow.getBackgroundColor(), Color.WHITE, Color.WHITE);
        emailIpt.setMaximumSize(new Dimension(350, 40));
        emailIpt.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 20));
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        RoundedTextField passwordIpt = new RoundedTextField(1, 15,15, projectWindow.getBackgroundColor(), Color.WHITE, Color.WHITE);
        passwordIpt.setMaximumSize(new Dimension(350, 30));
        passwordIpt.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel repeatPasswordLabel = new JLabel("Repeat password");
        repeatPasswordLabel.setFont(new Font("Arial", Font.BOLD, 20));
        repeatPasswordLabel.setForeground(Color.WHITE);
        repeatPasswordLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        repeatPasswordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        RoundedTextField repeatPasswordIpt = new RoundedTextField(1, 15,15, projectWindow.getBackgroundColor(), Color.WHITE, Color.WHITE);
        repeatPasswordIpt.setMaximumSize(new Dimension(350, 30));
        repeatPasswordIpt.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel dateOfBirthLabel = new JLabel("Date of birth");
        dateOfBirthLabel.setFont(new Font("Arial", Font.BOLD, 20));
        dateOfBirthLabel.setForeground(Color.WHITE);
        dateOfBirthLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        dateOfBirthLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.setForeground(Color.WHITE);
        datePicker.setBackground(signupController.getProjectWindow().getBackground());
        datePicker.setMaximumSize(new Dimension(350, 30));
        datePicker.setPreferredSize(new Dimension(350,30));
        datePicker.getComponent(0).setPreferredSize(new Dimension(100,30)); //JFormattedTextField
        datePicker.getComponent(1).setPreferredSize(new Dimension(20,30));//JButton

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
                signupController.handleSubmitButton(submitButton, usernameIpt.getText(), nameIpt.getText(), emailIpt.getText(), passwordIpt.getText(), repeatPasswordIpt.getText(), (Date) datePicker.getModel().getValue());
            }
        });

        verticalPanel.add(welcomeLabel);
        verticalPanel.add(choicePanel);
        verticalPanel.add(usernameLabel);
        verticalPanel.add(usernameIpt);
        verticalPanel.add(nameLabel);
        verticalPanel.add(nameIpt);
        verticalPanel.add(dateOfBirthLabel);
        verticalPanel.add(datePicker);
        verticalPanel.add(emailLabel);
        verticalPanel.add(emailIpt);
        verticalPanel.add(passwordLabel);
        verticalPanel.add(passwordIpt);
        verticalPanel.add(repeatPasswordLabel);
        verticalPanel.add(repeatPasswordIpt);


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
