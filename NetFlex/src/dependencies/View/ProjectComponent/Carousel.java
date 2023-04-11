package dependencies.View.ProjectComponent;

import dependencies.Controller.SceneController.MenuController;
import dependencies.Model.Video;
import dependencies.View.ProjectWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

public class Carousel extends JPanel {
    private JPanel content;
    private JScrollPane scrollPane;
    private RoundedButton rightButton;
    private int currentPosition;
    private int OffsetX, OffsetY;
    private ArrayList<Video> originalVideos;
    private ArrayList<VideoThumbnail> videoThumbnails;
    private String name;
    private ProjectWindow projectWindow;
    private MenuController menuController;
    public Carousel(String name, ArrayList<Video> videos, Color bg, Color fg, MenuController menuController, ProjectWindow projectWindow) {
        this.projectWindow = projectWindow;
        this.menuController = menuController;
        originalVideos = new ArrayList<>(videos);
        videoThumbnails = new ArrayList<VideoThumbnail>();
        currentPosition = 0;
        this.name = name;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(projectWindow.getWidth(), 315));

        content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.X_AXIS));
        content.setBackground(bg);

        populateContent(videos, bg, menuController, projectWindow);

        scrollPane = new JScrollPane(content);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setViewPosition(new Point(0, 0));

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(projectWindow.getWidth(), 315));

        RoundedButton leftButton = new RoundedButton("<", "<", 20, 20, Color.WHITE, projectWindow.getBackgroundColor(), Color.WHITE);

        leftButton.setPreferredSize(new Dimension(25, 25));
        leftButton.setMinimumSize(new Dimension(25, 25));

        rightButton = new RoundedButton(">", ">", 20, 20, Color.WHITE, projectWindow.getBackgroundColor(), Color.WHITE);
        rightButton.setPreferredSize(new Dimension(25, 25));
        rightButton.setMinimumSize(new Dimension(25, 25));

        leftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPosition - 1 >= 0) {
                    currentPosition = (currentPosition - 1);
                }
                scrollPane.getViewport().setViewPosition(new Point((367 * currentPosition) + OffsetX, 0));
                scrollPane.repaint();
            }
        });

        rightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (((currentPosition + 1)) < videos.size() - 3) {
                    currentPosition = (currentPosition + 1);
                }
                scrollPane.getViewport().setViewPosition(new Point((339 * currentPosition) + OffsetX, 0));
                scrollPane.repaint();
            }
        });


        if (videos.size() <= 4) {
            leftButton.setVisible(false);
            rightButton.setVisible(false);
        }

        JLayeredPane parentLayeredPane = getParentLayeredPane();

        if (parentLayeredPane != null) {
            parentLayeredPane.add(leftButton, new Integer(JLayeredPane.POPUP_LAYER + 1));
            parentLayeredPane.add(rightButton, new Integer(JLayeredPane.POPUP_LAYER + 1));
        }

        scrollPane.setBounds(45, 0, projectWindow.getWidth()-90, 315);
        leftButton.setBounds(10, 140, leftButton.getPreferredSize().width, leftButton.getPreferredSize().height);
        rightButton.setBounds(projectWindow.getWidth() - rightButton.getPreferredSize().width - 10, 140, rightButton.getPreferredSize().width, rightButton.getPreferredSize().height);

        layeredPane.add(scrollPane, new Integer(0));
        layeredPane.add(leftButton, new Integer(100));
        layeredPane.add(rightButton, new Integer(100));

        add(layeredPane, BorderLayout.CENTER);


    }

    private void populateContent(ArrayList<Video> videos, Color bg, MenuController menuController, ProjectWindow projectWindow) {
        content.removeAll();
        content.add(Box.createHorizontalStrut(0));
        videoThumbnails.clear();

        for (int i = 0; i < videos.size(); i++) {
            Video video = videos.get(i);
            String thumbnailPath = "src/resource" + video.getThumbnailPath();
            VideoThumbnail videoThumbnail = new VideoThumbnail(video, menuController, projectWindow, projectWindow.getMenuBarColor(), thumbnailPath, video.getTitle(), 2020);
            videoThumbnails.add(videoThumbnail);
            content.add(videoThumbnail);
            content.add(Box.createHorizontalStrut(5));
        }

        int contentWidth = calculateContentWidth(videos.size(), projectWindow.getWidth());
        content.setPreferredSize(new Dimension(contentWidth, 315));
        content.setMaximumSize(new Dimension(contentWidth, 315));
        content.revalidate();
        content.repaint();

        // Set visibility based on whether the carousel is empty or not
        this.setVisible(!videos.isEmpty());

        revalidate();
        repaint();
    }


    public void filterVideos(String searchText) {
        ArrayList<Video> filteredVideos = new ArrayList<>();

        for (Video video : originalVideos) {
            if (video.getTitle().toLowerCase().contains(searchText.toLowerCase())) {
                filteredVideos.add(video);
            }
        }

        populateContent(filteredVideos, content.getBackground(), menuController, projectWindow);
    }
    public RoundedButton getRightButton() {
        return rightButton;
    }
    private JLayeredPane getParentLayeredPane() {
        Container parent = getParent();
        while (parent != null && !(parent instanceof JLayeredPane)) {
            parent = parent.getParent();
        }
        return (JLayeredPane) parent;
    }

    public ArrayList<VideoThumbnail> getVideoThumbnails(){return videoThumbnails;}

    public void addScrollPaneMouseWheelListener(MouseWheelListener listener) {
        scrollPane.addMouseWheelListener(listener);
    }

    private int calculateContentWidth(int panelCount, int screenWidth) {
        int panelWidth = 334;
        int panelSpacing = 25;
        int maxPanelsOnScreen = screenWidth / (panelWidth + panelSpacing);

        if (panelCount <= maxPanelsOnScreen) {
            return screenWidth;
        } else {
            return panelWidth * panelCount + panelSpacing * (panelCount - 1);
        }
    }

    public String getCategoryName(){
        return name;
    }

}