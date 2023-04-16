package dependencies.View.ProjectComponent;

import dependencies.Controller.SceneController.MenuController;
import dependencies.Model.Video;
import dependencies.View.ProjectWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class VideoThumbnail extends JPanel {
    private Video video;
    private String imagePath;
    private JPanel hoverPanel;
    private Timer hoverTimer;
    private Timer checkMousePositionTimer;

    private int arcWidth = 20;
    private int arcHeight = 20;
    private int borderThickness = 1;
    private Color bg;
    private ProjectWindow projectWindow;
    private MenuController menuController;
    private JPanel infoPanel;
    private DropdownThumbnailOptions dropdownInputField;
    //private Timer watchLaterTimer;
    private RoundedButton watchLaterButton;
    private RoundedButton likeButton;
    private RoundedButton dislikeButton;
    //private boolean isVideoInWatchList;
    //private Timer watchListUpdateTimer;


    public VideoThumbnail(Video video, MenuController menuController, ProjectWindow projectWindow, Color bg, String imagePath, String title, int year) {
        this.bg = bg;
        this.imagePath = imagePath;
        this.projectWindow = projectWindow;
        this.menuController = menuController;
        this.video = video;
        setPreferredSize(new Dimension(334, 250));
        setMaximumSize(new Dimension(334, 250));
        setLayout(new BorderLayout());
        setOpaque(false);

        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            Image scaledImage = image.getScaledInstance(334, 250, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(scaledImage);
            JLabel imageLabel = new JLabel(imageIcon);
            add(imageLabel, BorderLayout.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }

        hoverTimer = new Timer(200, e -> createHoverPanel(title, year));
        hoverTimer.setRepeats(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (hoverPanel == null) {
                    hoverTimer.restart();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (hoverPanel != null && !hoverPanelContainsMouse()) {
                    closeHoverPanel();
                }
            }
        });

        infoPanel = new JPanel();
        infoPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        infoPanel.setOpaque(true);
        infoPanel.setBackground(bg);

        checkMousePositionTimer = new Timer(0, e -> {
            if (hoverPanel != null && !hoverPanelContainsMouse() && !dropdownPanelContainsMouse()) {
                closeHoverPanel();
            }
        });
        checkMousePositionTimer.setRepeats(true);
        checkMousePositionTimer.start();


        setBackground(new Color(0, 0, 0, 0)); // Set transparent background
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(projectWindow.getMenuBarColor());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(borderThickness));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);
    }

    private void createHoverPanel(String title, int year) {
        int width = (int) (getPreferredSize().width * 1.1);
        int height = (int) (getPreferredSize().height * 1 + 30);

        hoverPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(projectWindow.getMenuBarColor());
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(borderThickness));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);
            }
        };
        hoverPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (!hoverPanelContainsMouse()) {
                    closeHoverPanel();
                }
            }
        });
        hoverPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hoverTimer.stop();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!hoverPanelContainsMouse()) {
                    closeHoverPanel();
                }
            }
        });
        hoverPanel.setPreferredSize(new Dimension(width, height));
        hoverPanel.setMaximumSize(new Dimension(width, height));
        hoverPanel.setOpaque(false);

        hoverPanel.setBackground(bg);
        int imageWidth = (int) (getPreferredSize().width * 1.1);
        int imageHeight = (int) (getPreferredSize().height * 1.1);

        JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    BufferedImage image = ImageIO.read(new File(imagePath));
                    Image scaledImage = image.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
                    g.drawImage(scaledImage, 0, 0, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        imagePanel.setOpaque(true);
        imagePanel.setBackground(bg);
        imagePanel.setBounds((width - imageWidth) / 2, ((height - imageHeight) / 2)+35, imageWidth, imageHeight);
        hoverPanel.add(imagePanel);



        JLabel titleLabel = new JLabel(title);
        titleLabel.setPreferredSize(new Dimension(200,20));
        titleLabel.setMinimumSize(new Dimension(200,20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        infoPanel.add(titleLabel);

        JLabel yearLabel = new JLabel(String.valueOf(year));
        yearLabel.setForeground(Color.WHITE);
        yearLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        infoPanel.add(yearLabel);

        RoundedButton playButton = new RoundedButton("play-video", "\u25B6", 20, 20, Color.WHITE, projectWindow.getBackgroundColor(), Color.WHITE);
        playButton.setPreferredSize(new Dimension(30,20));
        playButton.setMinimumSize(new Dimension(30,20));
        playButton.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {menuController.playVideoButton(video);}});
        infoPanel.add(playButton);

        watchLaterButton = new RoundedButton("watchLater", " Watch Later: ", 20, 20, Color.WHITE, projectWindow.getBackgroundColor(), Color.WHITE);
        if(menuController.getApplicationController().getApplication().isVideoInUserWatchlist(video.getId())){
            watchLaterButton.setText(" Watch Later: Yes ");
            watchLaterButton.setForeground(Color.GREEN);
        }else{
            watchLaterButton.setText(" Watch Later: No ");
            watchLaterButton.setForeground(Color.RED);
        }
        watchLaterButton.setPreferredSize(new Dimension(50,20));
        watchLaterButton.setMinimumSize(new Dimension(50,20));
        watchLaterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean adding = menuController.getApplicationController().getApplication().isVideoInUserWatchlist(video.getId());
                closeHoverPanel();
                boolean success = menuController.handleWatchLaterButton(video, !adding);
                watchLaterButton.requestFocus(); // Request focus for the watchLaterButton after performing the action
            }
        });


        likeButton = new RoundedButton("like", " Like ", 20, 20, Color.WHITE, projectWindow.getBackgroundColor(), Color.WHITE);
        likeButton.setPreferredSize(new Dimension(50,20));
        likeButton.setMinimumSize(new Dimension(50,20));
        if(menuController.getApplicationController().getApplication().isVideoInUserLikeList(video.getId())){
            likeButton.setText(" Liked ");
            likeButton.setTextColor(Color.GREEN);
        }else{
            likeButton.setText(" Like ");
            likeButton.setTextColor(Color.WHITE);
        }
        likeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //boolean adding = menuController.getApplicationController().getApplication().isVideoInUserLikeList(video.getId());
                if(likeButton.getTextColor() == Color.WHITE){
                    menuController.handleLikeButton(video, true);
                }else{
                    menuController.removeLike(video);
                }
                likeButton.requestFocus(); // Request focus for the watchLaterButton after performing the action
                closeHoverPanel();
                menuController.reloadThumbnails(video);

            }
        });


        dislikeButton = new RoundedButton("dislike", " Dislike ", 20, 20, Color.WHITE, projectWindow.getBackgroundColor(), Color.WHITE);
        dislikeButton.setPreferredSize(new Dimension(50,20));
        dislikeButton.setMinimumSize(new Dimension(50,20));
        if(menuController.getApplicationController().getApplication().isVideoInUserDislikeList(video.getId())){
            dislikeButton.setText(" Disliked ");
            dislikeButton.setTextColor(Color.RED);
        }else{
            dislikeButton.setText(" Dislike ");
            dislikeButton.setTextColor(Color.WHITE);
        }
        dislikeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //boolean adding = menuController.getApplicationController().getApplication().isVideoInUserLikeList(video.getId());
                if(dislikeButton.getTextColor() == Color.WHITE){
                    menuController.handleLikeButton(video, false);
                }else{
                    menuController.removeDislike(video);
                }
                dislikeButton.requestFocus(); // Request focus for the watchLaterButton after performing the action
                closeHoverPanel();
                menuController.reloadThumbnails(video);

            }
        });

        watchLaterButton.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (dropdownInputField != null) {
                    dropdownInputField.hideDropdownPanel();
                }
            }
        });

        likeButton.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (dropdownInputField != null) {
                    dropdownInputField.hideDropdownPanel();
                }
            }
        });

        dislikeButton.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (dropdownInputField != null) {
                    dropdownInputField.hideDropdownPanel();
                }
            }
        });

        RoundedButton commentButton = new RoundedButton("comment", " Comment ", 20, 20, Color.WHITE, projectWindow.getBackgroundColor(), Color.WHITE);
        commentButton.setPreferredSize(new Dimension(50,20));
        commentButton.setMinimumSize(new Dimension(50,20));
        //commentButton.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {menuController.handleCommentButton(video.getId());}});

        RoundedButton aboutButton = new RoundedButton("about", " About this movie ", 20, 20, Color.WHITE, projectWindow.getBackgroundColor(), Color.WHITE);
        aboutButton.setPreferredSize(new Dimension(50,20));
        aboutButton.setMinimumSize(new Dimension(50,20));
        aboutButton.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {menuController.handleAboutButton(video.getId());}});

        RoundedButton moreButton = new RoundedButton("more", "More", 20, 20, Color.WHITE, projectWindow.getBackgroundColor(), Color.WHITE);
        moreButton.setPreferredSize(new Dimension(50,20));
        moreButton.setMinimumSize(new Dimension(50,20));

        RoundedButton[] options = new RoundedButton[5];
        options[0] = watchLaterButton;
        options[1] = likeButton;
        options[2] = dislikeButton;
        options[3] = commentButton;
        options[4] = aboutButton;

        dropdownInputField = new DropdownThumbnailOptions(options, moreButton, projectWindow);
        dropdownInputField.setPreferredSize(new Dimension(50,20));
        dropdownInputField.setMinimumSize(new Dimension(50,20));

        infoPanel.add(dropdownInputField);

        int infoPanelHeight = 30;
        infoPanel.setBounds(5,3, width-10, infoPanelHeight);
        hoverPanel.add(infoPanel);

        JLayeredPane parentLayeredPane = getLayeredPane();
        int hoverPanelX = getX() + (getWidth() / 2) - (width / 2)+40;
        int hoverPanelY = getY() + (getHeight() / 2) - (height / 2) - 10;
        hoverPanel.setBounds(hoverPanelX, hoverPanelY, width, height);
        parentLayeredPane.add(hoverPanel, new Integer(JLayeredPane.MODAL_LAYER - 1));
        parentLayeredPane.moveToFront(hoverPanel);
    }

    public void updateWatchListStatus(boolean isVideoInWatchList) {
        if (watchLaterButton != null) {
            if (!isVideoInWatchList) {
                watchLaterButton.setText(" Watch Later: Yes ");
                watchLaterButton.setForeground(Color.GREEN);
            } else{
                watchLaterButton.setText(" Watch Later: No ");
                watchLaterButton.setForeground(Color.RED);
            }
        }
    }

    private boolean hoverPanelContainsMouse() {
        Point mousePosition = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(mousePosition, hoverPanel);
        return hoverPanel.contains(mousePosition);
    }

    private boolean dropdownPanelContainsMouse() {
        if (dropdownInputField == null || dropdownInputField.getDropdownPanel() == null) {
            return false;
        }
        Point mousePosition = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(mousePosition, dropdownInputField.getDropdownPanel());
        return dropdownInputField.getDropdownPanel().contains(mousePosition);
    }

    public void closeHoverPanel() {
        if (hoverPanel == null) {
            return; // Return if hoverPanel is null to avoid NullPointerException
        }

        hoverTimer.stop();
        if (hoverPanel != null) {
            getLayeredPane().remove(hoverPanel);
            getLayeredPane().repaint();
            hoverPanel = null;
            if (dropdownInputField != null) {
                dropdownInputField.hideDropdownPanel(); // Close the dropdown when the hover panel is closed
            }
        }
    }


    public int getVideoId(){return video.getId();}

    private JLayeredPane getLayeredPane() {
        Container parent = getParent();
        while (parent != null && !(parent instanceof JLayeredPane)) {
            parent = parent.getParent();
        }
        return (JLayeredPane) parent;
    }
}

