package dependencies.View.ProjectScene;

import dependencies.Controller.SceneController.VideoPlayerController;
import dependencies.Model.Video;
import dependencies.View.ProjectComponent.RoundedButton;
import dependencies.View.ProjectComponent.VideoPlayer;
import dependencies.View.ProjectWindow;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class VideoPlayerScene {
    private Video video;
    private VideoPlayer videoPlayer;
    private VideoPlayerController videoPlayerController;
    private JPanel container;
    private Timer inactivityTimer;
    private Timer videoTimer;
    private int startTime = 0; // Added start time
    private boolean isMuted = false;

    public VideoPlayerScene(VideoPlayerController videoPlayerController, ProjectWindow projectWindow, String error, Video video, String path) {
        container = new JPanel();
        container.setBackground(projectWindow.getBackgroundColor());
        container.setLayout(new BorderLayout());

        this.video = video;
        this.startTime = video.getStartTime();
        this.videoPlayerController = videoPlayerController;

        JPanel foregroundPanel = new JPanel();
        foregroundPanel.setBackground(new Color(0,0,0,0));
        foregroundPanel.setBounds(0, 0, projectWindow.getWidth(), projectWindow.getHeight());
        foregroundPanel.setOpaque(false);
        foregroundPanel.setLayout(null);

        int progressBarWidth = (int) (projectWindow.getWidth() * 0.90);
        int timerWidth = (int) (projectWindow.getWidth() * 0.05);
        int delay = 0;
        videoTimer = new Timer(delay, null);

        try {
            videoPlayer = new VideoPlayer(projectWindow.getWidth(), projectWindow.getHeight(), videoTimer, path, startTime);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JProgressBar progressBar = new JProgressBar(0, videoPlayer.getTotalImageCounter());
        progressBar.setBounds((projectWindow.getWidth() - progressBarWidth) / 2, projectWindow.getHeight() - 70, progressBarWidth, 30); // Moved 10px up
        foregroundPanel.add(progressBar);

        videoTimer.addActionListener(videoPlayer);

        JPanel backgroundPanel = new JPanel(new BorderLayout());
        backgroundPanel.setBounds(0, 0, projectWindow.getWidth(), projectWindow.getHeight());
        backgroundPanel.add(videoPlayer);

        JLabel timerLabel = new JLabel("00:00");
        timerLabel.setBounds(progressBar.getX() + progressBarWidth, progressBar.getY(), timerWidth, 30); // Moved 10px down
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setBackground(new Color(0,0,0,0));
        timerLabel.setOpaque(true);
        foregroundPanel.add(timerLabel);

        videoTimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int progress = videoPlayer.getCurrentImage();
                progressBar.setValue(progress);

                int seconds = (int) (progress / 24);
                int minutes = seconds / 60;
                seconds %= 60;
                timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
            }
        });

        RoundedButton playButton = new RoundedButton("play", "\u25B6", 20, 20, Color.WHITE, new Color(0, 0, 0, 0), Color.WHITE);
        playButton.setBounds((projectWindow.getWidth() - 100) / 2, (projectWindow.getHeight() - 50) / 2, 40, 40);
        playButton.addActionListener(new ActionListener() {
            boolean isPlaying = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isPlaying) {
                    videoPlayer.startAnimation(startTime); // Updated to playAnimation
                    videoTimer.start();
                    playButton.setText("||");
                } else {
                    videoPlayer.stopAnimation(); // Updated to pauseAnimation
                    videoTimer.stop();
                    playButton.setText("\u25B6");
                }
                isPlaying = !isPlaying;
            }
        });

        foregroundPanel.add(playButton);

        // Added Back button
        RoundedButton backButton = new RoundedButton("back-to-menu", "Back", 20, 20, Color.WHITE, new Color(0, 0, 0, 0), Color.WHITE);
        backButton.setBounds(10, 10, 80, 30);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                videoPlayer.stopAnimation();
                videoTimer.stop();
                videoPlayerController.handleBackButton(video,videoPlayer.getCurrentImage(), true);
            }
        });
        foregroundPanel.add(backButton);

        // Added Mute button
        RoundedButton muteButton = new RoundedButton("mute", "Mute", 20, 20, Color.WHITE, new Color(0, 0, 0, 0), Color.WHITE);
        muteButton.setBounds(100, 10, 80, 30);
        muteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isMuted) {
                    videoPlayer.setMuted(true); // Updated to playAnimation
                    muteButton.setText("Unmute");
                } else {
                    videoPlayer.setMuted(false); // Updated to playAnimation
                    muteButton.setText("Mute");
                }
                isMuted = !isMuted;
            }
        });
        foregroundPanel.add(muteButton);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.add(backgroundPanel, Integer.valueOf(0));
        layeredPane.add(foregroundPanel, Integer.valueOf(1));
        container.add(layeredPane);
        inactivityTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                foregroundPanel.setVisible(false);
            }
        });
        inactivityTimer.setRepeats(false);
        inactivityTimer.start();

        layeredPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (!foregroundPanel.isVisible()) {
                    foregroundPanel.setVisible(true);
                }
                inactivityTimer.restart();
            }
        });

        layeredPane.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (!foregroundPanel.isVisible()) {
                    foregroundPanel.setVisible(true);
                }
                inactivityTimer.restart();
            }
        });
    }

    public void changeVisibility(boolean b){this.container.setVisible(b);}

    public void Close(){
        videoPlayer.stopAnimation();
        videoTimer.stop();
        videoPlayerController.handleBackButton(video,videoPlayer.getCurrentImage(), false);

    }

    public JPanel getContainer() {
        return container;
    }
}
