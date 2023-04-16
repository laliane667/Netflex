package dependencies.View.ProjectScene.SubScene;

import dependencies.Controller.SceneController.ControlCenterController;
import dependencies.View.ProjectComponent.*;
import dependencies.View.ProjectWindow;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;
import java.util.Properties;

public class AddVideoSubScene extends JPanel {
    public AddVideoSubScene(ProjectWindow projectWindow, ControlCenterController controlCenterController, String error){
        setPreferredSize(new Dimension(400, 540));
        setMaximumSize(new Dimension(400, 540));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(projectWindow.getBackgroundColor());

        JPanel addPanel = new JPanel();
        addPanel.setBackground(projectWindow.getBackgroundColor());
        addPanel.setPreferredSize(new Dimension(400, 25));
        addPanel.setMaximumSize(new Dimension(400, 25));

        JLabel addLabel = new JLabel("Add video:");
        addLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        addLabel.setForeground(Color.WHITE);
        addLabel.setBackground(projectWindow.getBackgroundColor());

        addPanel.add(addLabel);
        add(addPanel);

        JPanel errorPanel = new JPanel(new BorderLayout());
        errorPanel.setMaximumSize(new Dimension(400, 25));
        errorPanel.setBackground(projectWindow.getBackgroundColor());

        JLabel errorLabel = new JLabel("none");
        errorLabel.setMaximumSize(new Dimension(400, 25));
        errorLabel.setForeground(projectWindow.getBackgroundColor());
        errorLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        errorLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        if(error == "video-added-successfully"){
            errorLabel.setText("\u2713 Video added successfully.");
            errorLabel.setForeground(Color.GREEN);
        }

        errorPanel.add(errorLabel, BorderLayout.WEST);
        add(errorPanel);
        JLabel titleLabel = new JLabel();
        if(error == "video-empty-fields" || error == "video-empty-name"){
            titleLabel.setText("\u2716 Title: The field is empty.");
            titleLabel.setForeground(Color.RED);
        }else{
            titleLabel.setText("Title: ");
            titleLabel.setForeground(Color.WHITE);
        }
        ///
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBackground(projectWindow.getBackgroundColor());
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        RoundedTextField titleField = new RoundedTextField(1, 15, 15, projectWindow.getBackgroundColor(), Color.WHITE, Color.WHITE);
        titleField.setFont(new Font("Arial", Font.PLAIN, 18));
        titleField.setBorder(BorderFactory.createEmptyBorder());
        titleField.setBackground(projectWindow.getBackgroundColor());
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(titleField, BorderLayout.CENTER);
        titlePanel.setForeground(Color.WHITE);
        titlePanel.setBackground(projectWindow.getBackgroundColor());
        add(titlePanel);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setForeground(Color.WHITE);
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

        String[] options = controlCenterController.getApplicationController().getApplication().getAllCategories();
        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setForeground(Color.WHITE);
        categoryLabel.setBackground(projectWindow.getBackgroundColor());
        categoryLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        CheckableDropdown dropdownInputField = new CheckableDropdown(options);
        JPanel categoryPanel = new JPanel(new BorderLayout());
        categoryPanel.add(categoryLabel, BorderLayout.NORTH);
        categoryPanel.add(dropdownInputField, BorderLayout.CENTER);
        categoryPanel.setForeground(Color.WHITE);
        categoryPanel.setBackground(projectWindow.getBackgroundColor());
        add(categoryPanel);

        JLabel durationLabel = new JLabel("Duration:");
        durationLabel.setForeground(Color.WHITE);
        durationLabel.setBackground(projectWindow.getBackgroundColor());
        durationLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        RoundedTextField durationField = new RoundedTextField(1, 15, 15, projectWindow.getBackgroundColor(), Color.WHITE, Color.WHITE);
        durationField.setMaximumSize(new Dimension(350, 20));
        durationField.setFont(new Font("Arial", Font.PLAIN, 18));
        durationField.setBorder(BorderFactory.createEmptyBorder());
        JPanel durationPanel = new JPanel(new BorderLayout());
        durationPanel.add(durationLabel, BorderLayout.NORTH);
        durationPanel.add(durationField, BorderLayout.CENTER);
        durationPanel.setForeground(Color.WHITE);
        durationPanel.setBackground(projectWindow.getBackgroundColor());
        add(durationPanel);

        JLabel yearLabel = new JLabel("Year:");
        yearLabel.setForeground(Color.WHITE);
        yearLabel.setBackground(projectWindow.getBackgroundColor());
        yearLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.setForeground(Color.WHITE);
        datePicker.setMaximumSize(new Dimension(350, 30));
        datePicker.setPreferredSize(new Dimension(350,30));
        datePicker.getComponent(0).setPreferredSize(new Dimension(100,30)); //JFormattedTextField
        datePicker.getComponent(1).setPreferredSize(new Dimension(20,30));//JButton
        JPanel yearPanel = new JPanel(new BorderLayout());
        yearPanel.add(yearLabel, BorderLayout.NORTH);
        yearPanel.add(datePicker, BorderLayout.CENTER);
        yearPanel.setForeground(Color.WHITE);
        yearPanel.setBackground(projectWindow.getBackgroundColor());
        add(yearPanel);

        JLabel streamPathLabel = new JLabel("Stream Path:");
        streamPathLabel.setForeground(Color.WHITE);
        streamPathLabel.setBackground(projectWindow.getBackgroundColor());
        streamPathLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        RoundedTextField streamPathField = new RoundedTextField(1, 15, 15, projectWindow.getBackgroundColor(), Color.WHITE, Color.WHITE);
        streamPathField.setMaximumSize(new Dimension(350, 20));
        streamPathField.setFont(new Font("Arial", Font.PLAIN, 18));
        streamPathField.setBorder(BorderFactory.createEmptyBorder());
        JPanel streamPathPanel = new JPanel(new BorderLayout());
        streamPathPanel.add(streamPathLabel, BorderLayout.NORTH);
        streamPathPanel.add(streamPathField, BorderLayout.CENTER);
        JButton streamPathButton = new JButton("Select Stream Path");
        streamPathButton.setBackground(projectWindow.getBackgroundColor());
        streamPathButton.setMaximumSize(new Dimension(200, 20));
        streamPathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                String projectPath = System.getProperty("user.dir"); // Get the current project directory path
                String resourcesPath = projectPath + "/src/resource"; // Append the resources folder path to the project path
                fileChooser.setCurrentDirectory(new File(resourcesPath)); // Set the resources folder as the initial directory for the file chooser
                fileChooser.setDialogTitle("Select Stream Path");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int result = fileChooser.showOpenDialog(projectWindow);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String fullPath = selectedFile.getAbsolutePath();
                    String newPath = cutPathAfterTrigger(fullPath, "/src/resource");
                    streamPathField.setText(newPath);
                }
            }
        });



        streamPathPanel.add(streamPathButton, BorderLayout.EAST);
        streamPathPanel.setForeground(Color.WHITE);
        streamPathPanel.setBackground(projectWindow.getBackgroundColor());
        add(streamPathPanel);

        JLabel thumbnailPathLabel = new JLabel("Thumbnail Path:");
        thumbnailPathLabel.setForeground(Color.WHITE);
        thumbnailPathLabel.setBackground(projectWindow.getBackgroundColor());
        thumbnailPathLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        RoundedTextField thumbnailPathField = new RoundedTextField(1, 15, 15, projectWindow.getBackgroundColor(), Color.WHITE, Color.WHITE );
        thumbnailPathField.setMaximumSize(new Dimension(350, 20));
        thumbnailPathField.setFont(new Font("Arial", Font.PLAIN, 18));
        thumbnailPathField.setBorder(BorderFactory.createEmptyBorder());
        JPanel thumbnailPathPanel = new JPanel(new BorderLayout());
        thumbnailPathPanel.add(thumbnailPathLabel, BorderLayout.NORTH);
        thumbnailPathPanel.add(thumbnailPathField, BorderLayout.CENTER);
        JButton thumbnailPathButton = new JButton("Select Thumbnail Path");
        thumbnailPathButton.setBackground(projectWindow.getBackgroundColor());
        thumbnailPathButton.setMaximumSize(new Dimension(200, 20));
        thumbnailPathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                String projectPath = System.getProperty("user.dir"); // Get the current project directory path
                String resourcesPath = projectPath + "/src/resource"; // Append the resources folder path to the project path
                fileChooser.setCurrentDirectory(new File(resourcesPath)); // Set the resources folder as the initial directory for the file chooser
                fileChooser.setDialogTitle("Select Stream Path");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int result = fileChooser.showOpenDialog(projectWindow);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String fullPath = selectedFile.getAbsolutePath();
                    String newPath = cutPathAfterTrigger(fullPath, "/src/resource");
                    thumbnailPathField.setText(newPath);
                }
            }
        });
        thumbnailPathPanel.add(thumbnailPathButton, BorderLayout.EAST);
        thumbnailPathPanel.setForeground(Color.WHITE);
        thumbnailPathPanel.setBackground(projectWindow.getBackgroundColor());
        add(thumbnailPathPanel);

        RoundedButton submitButton = new RoundedButton("submit-video", "Submit", 20, 20, Color.WHITE, projectWindow.getBackgroundColor(), Color.WHITE);
        submitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        submitButton.setFont(new Font("Arial", Font.PLAIN, 18));
        submitButton.setPreferredSize(new Dimension(50, 25));
        submitButton.setMaximumSize(new Dimension(50, 25));
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlCenterController.handleSubmitVideoButton(titleField.getText(), descriptionField.getText(),  Integer.parseInt(durationField.getText()), (Date) datePicker.getModel().getValue(),  streamPathField.getText(), thumbnailPathField.getText(), dropdownInputField.getCheckedOptions());
            }
        });
        JPanel submitButtonPanel = new JPanel(new BorderLayout());
        submitButtonPanel.setMaximumSize(new Dimension(100,25));
        submitButtonPanel.setBackground(projectWindow.getBackgroundColor());
        submitButtonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        submitButtonPanel.add(submitButton, BorderLayout.CENTER);

        add(submitButtonPanel);
        add(Box.createVerticalStrut(15));
    }

    public static String cutPathAfterTrigger(String path, String trigger) {
        int triggerIndex = path.lastIndexOf(trigger);
        if (triggerIndex != -1) {
            return path.substring(triggerIndex + trigger.length());
        } else {
            return path;
        }
    }
}
