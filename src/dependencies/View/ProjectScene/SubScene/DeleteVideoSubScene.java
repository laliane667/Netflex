package dependencies.View.ProjectScene.SubScene;

import dependencies.Controller.SceneController.ControlCenterController;
import dependencies.Model.Video;
import dependencies.View.ProjectComponent.Carousel;
import dependencies.View.ProjectComponent.RoundedButton;
import dependencies.View.ProjectComponent.RoundedTextField;
import dependencies.View.ProjectComponent.VideoSelector;
import dependencies.View.ProjectWindow;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DeleteVideoSubScene extends JPanel {
    private ArrayList<Video> originalVideos = null;
    private ArrayList<Video> filteredVideos = null;
    private VideoSelector videoSelector = null;
    public DeleteVideoSubScene(ProjectWindow projectWindow, ControlCenterController controlCenterController, String error) {
        setPreferredSize(new Dimension(400, 540));
        setMaximumSize(new Dimension(400, 540));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(projectWindow.getBackgroundColor());

        JPanel addPanel = new JPanel();
        addPanel.setBackground(projectWindow.getBackgroundColor());
        addPanel.setPreferredSize(new Dimension(400, 25));
        addPanel.setMaximumSize(new Dimension(400, 25));

        JLabel addLabel = new JLabel("Remove video(s):");
        addLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        addLabel.setForeground(Color.WHITE);
        addLabel.setBackground(projectWindow.getBackgroundColor());

        addPanel.add(addLabel);
        add(addPanel);

        ///
        JPanel errorPanel = new JPanel(new BorderLayout());
        errorPanel.setMaximumSize(new Dimension(400, 25));
        errorPanel.setBackground(projectWindow.getBackgroundColor());

        JLabel errorLabel = new JLabel("none");
        errorLabel.setMaximumSize(new Dimension(400, 25));
        errorLabel.setForeground(projectWindow.getBackgroundColor());
        errorLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        errorLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        if(error == "video-selection-removed-successfully"){
            errorLabel.setText("\u2713 Selection removed successfully.");
            errorLabel.setForeground(Color.GREEN);
        }

        errorPanel.add(errorLabel, BorderLayout.WEST);
        add(errorPanel);
        JLabel titleLabel = new JLabel();
        if(error == "video-selection-empty-fields"){
            titleLabel.setText("\u2716 Selection: No video selected.");
            titleLabel.setForeground(Color.RED);
        }else{
            titleLabel.setText("Search title, id, duration or date: ");
            titleLabel.setForeground(Color.WHITE);
        }
        ///
        titleLabel.setBackground(projectWindow.getBackgroundColor());
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        RoundedTextField titleField = new RoundedTextField(1, 15, 15, projectWindow.getBackgroundColor(), Color.WHITE, Color.WHITE);
        titleField.setFont(new Font("Arial", Font.PLAIN, 18));
        titleField.setBorder(BorderFactory.createEmptyBorder());
        titleField.setBackground(projectWindow.getBackgroundColor());
        titleField.setText("Search");
        titleField.setForeground(Color.GRAY);
        titleField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (titleField.getText().equals("Search")) {
                    titleField.setText("");
                    titleField.setForeground(Color.WHITE);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (titleField.getText().isEmpty()) {
                    titleField.setText("Search");
                    titleField.setForeground(Color.GRAY);
                }
            }
        });
        titleField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                updateSearch();
            }
            public void removeUpdate(DocumentEvent e) {
                updateSearch();
            }
            public void insertUpdate(DocumentEvent e) {
                updateSearch();
            }

            public void updateSearch() {
                String searchText = titleField.getText();
                if (searchText.equals("Search")) {
                    searchText = "";
                }
                filterVideos(searchText);
            }
        });


        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(titleField, BorderLayout.CENTER);
        titlePanel.setForeground(Color.WHITE);
        titlePanel.setBackground(projectWindow.getBackgroundColor());
        titlePanel.setMaximumSize(new Dimension(400, 60));
        add(titlePanel);

        add(Box.createVerticalStrut(30));

        JPanel videoSelectorContainer = new JPanel(new BorderLayout());
        videoSelectorContainer.setBackground(Color.RED);
        videoSelectorContainer.setPreferredSize(new Dimension(400, 350));
        videoSelectorContainer.setMinimumSize(new Dimension(400, 350));
        videoSelectorContainer.setMaximumSize(new Dimension(400, 350));
        try {
            originalVideos = controlCenterController.getApplicationController().getConnectionController().getVideoDAO().getAllVideos();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        videoSelector = new VideoSelector(originalVideos, projectWindow);
        videoSelector.setPreferredSize(new Dimension(400, 350));
        videoSelector.setMinimumSize(new Dimension(400, 350));
        videoSelector.setMaximumSize(new Dimension(400, 350));
        videoSelectorContainer.add(videoSelector, BorderLayout.CENTER);
        add(videoSelectorContainer);

        RoundedButton deleteButton = new RoundedButton("delete-videos", "Delete selection", 20, 20, Color.WHITE, projectWindow.getBackgroundColor(), Color.WHITE);
        deleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 18));
        deleteButton.setPreferredSize(new Dimension(100, 25));
        deleteButton.setMaximumSize(new Dimension(100, 25));
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlCenterController.handleSubmitRemoveButton(videoSelector.getSelectedVideoIds());
            }
        });
        JPanel submitButtonPanel = new JPanel(new BorderLayout());
        submitButtonPanel.setMaximumSize(new Dimension(200,25));
        submitButtonPanel.setBackground(projectWindow.getBackgroundColor());
        submitButtonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        submitButtonPanel.add(deleteButton, BorderLayout.CENTER);

        add(submitButtonPanel);
        add(Box.createVerticalGlue());
    }

    private void filterVideos(String searchText) {
        filteredVideos = new ArrayList<>();

        if (originalVideos != null) {
            for (Video video : originalVideos) {
                boolean match = false;

                if (video.getTitle().toLowerCase().contains(searchText.toLowerCase())) {
                    match = true;
                } else {
                    try {
                        int id = Integer.parseInt(searchText);
                        if (video.getId() == id) {
                            match = true;
                        }
                    } catch (NumberFormatException e) {
                        // Ignore, as searchText is not a valid integer
                    }

                    if (!match) {
                        try {
                            int duration = Integer.parseInt(searchText);
                            String searchDuration = String.valueOf(duration);
                            String videoDuration = String.valueOf(video.getDuration());
                            if (videoDuration.contains(searchDuration)) {
                                match = true;
                            }
                        } catch (NumberFormatException e) {
                            // Ignore, as searchText is not a valid integer
                        }
                    }


                    if (!match) {
                        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
                        try {
                            Date searchDate = yearFormat.parse(searchText);
                            String searchYear = yearFormat.format(searchDate);
                            String videoYear = yearFormat.format(video.getYear());
                            if (videoYear.contains(searchYear)) {
                                match = true;
                            }
                        } catch (ParseException e) {
                            // Ignore, as searchText is not a valid year
                        }
                    }
                }

                if (match) {
                    filteredVideos.add(video);
                }
            }
        }

        // Update the video selector with the filtered videos
        videoSelector.setVideos(filteredVideos);
    }

}