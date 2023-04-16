package dependencies.View.ProjectComponent;

import dependencies.Model.Video;
import dependencies.View.ProjectWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class VideoSelector extends JPanel {
    private ArrayList<Video> videos;
    private ArrayList<Integer> selectedVideoIds = new ArrayList<>();
    private JPanel videoContainer;
    private ProjectWindow projectWindow;

    public VideoSelector(ArrayList<Video> videosInput, ProjectWindow projectWindow) {
        videos = videosInput;
        this.projectWindow = projectWindow;
        setLayout(new BorderLayout());
        setBackground(projectWindow.getBackgroundColor());

        videoContainer = new JPanel();
        videoContainer.setBackground(projectWindow.getBackgroundColor());
        videoContainer.setLayout(new BoxLayout(videoContainer, BoxLayout.Y_AXIS));

        updateVideoContainer();

        // Create a JScrollPane to make the video list scrollable
        JScrollPane scrollPane = new JScrollPane(videoContainer);
        scrollPane.setBackground(projectWindow.getBackgroundColor());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(1);
        scrollPane.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int unitsToScroll = e.getUnitsToScroll() * 16; // Adjust the multiplier (16) for faster or slower scrolling
                JViewport viewport = scrollPane.getViewport();
                Point currentPosition = viewport.getViewPosition();
                int newYPosition = currentPosition.y + unitsToScroll;
                int maxYPosition = viewport.getView().getHeight() - viewport.getHeight();

                if (newYPosition < 0) {
                    newYPosition = 0;
                } else if (newYPosition > maxYPosition) {
                    newYPosition = maxYPosition;
                }

                currentPosition.setLocation(currentPosition.x, newYPosition);
                viewport.setViewPosition(currentPosition);
            }
        });

        add(scrollPane, BorderLayout.CENTER);
    }

    public ArrayList<Integer> getSelectedVideoIds() {
        return selectedVideoIds;
    }

    public void setVideos(ArrayList<Video> videos){
        this.videos = videos;
        updateVideoContainer();
        revalidate();
        repaint();
    }

    private void updateVideoContainer() {
        videoContainer.removeAll();

        for (Video video : videos) {
            JPanel videoInfoPanel = new JPanel(new BorderLayout());
            videoInfoPanel.setBackground(projectWindow.getMenuBarColor());
            videoInfoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 105));
            videoInfoPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.WHITE, 1),
                    BorderFactory.createEmptyBorder(0, 0, 0, 0)
            ));

            videoInfoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 105));

            JPanel infoLabelsPanel = new JPanel();
            infoLabelsPanel.setBackground(projectWindow.getMenuBarColor());
            infoLabelsPanel.setLayout(new BoxLayout(infoLabelsPanel, BoxLayout.Y_AXIS));

            JLabel idLabel = new JLabel("ID: " + video.getId());
            JLabel titleLabel = new JLabel("Title: " + video.getTitle());
            JLabel dateLabel = new JLabel("Date: " + video.getYear().toString());
            JLabel durationLabel = new JLabel("Duration: " + video.getDuration());

            idLabel.setBackground(projectWindow.getMenuBarColor());
            titleLabel.setBackground(projectWindow.getMenuBarColor());
            dateLabel.setBackground(projectWindow.getMenuBarColor());
            durationLabel.setBackground(projectWindow.getMenuBarColor());

            idLabel.setForeground(Color.WHITE);
            titleLabel.setForeground(Color.WHITE);
            dateLabel.setForeground(Color.WHITE);
            durationLabel.setForeground(Color.WHITE);

            infoLabelsPanel.add(idLabel);
            infoLabelsPanel.add(titleLabel);
            infoLabelsPanel.add(dateLabel);
            infoLabelsPanel.add(durationLabel);

            videoInfoPanel.add(infoLabelsPanel, BorderLayout.CENTER);

            JCheckBox videoCheckbox = new JCheckBox();
            videoCheckbox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        selectedVideoIds.add(video.getId());
                    } else {
                        selectedVideoIds.remove(Integer.valueOf(video.getId()));
                    }
                }
            });
            videoInfoPanel.add(videoCheckbox, BorderLayout.EAST);

            String thumbnailPath = "src/resource" + video.getThumbnailPath();
            ImageIcon thumbnailIcon = null;
            try {
                BufferedImage thumbnailImage = ImageIO.read(new File(thumbnailPath));
                int originalWidth = thumbnailImage.getWidth();
                int originalHeight = thumbnailImage.getHeight();
                int targetHeight = 80;
                int targetWidth = (int) (originalWidth * ((double) targetHeight / originalHeight));
                Image scaledThumbnail = thumbnailImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
                thumbnailIcon = new ImageIcon(scaledThumbnail);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (thumbnailIcon != null) {
                JLabel thumbnailLabel = new JLabel(thumbnailIcon);
                videoInfoPanel.add(thumbnailLabel, BorderLayout.WEST);
            }

            videoContainer.add(videoInfoPanel);
            videoContainer.add(Box.createVerticalStrut(15));
        }
    }
}
