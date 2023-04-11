package dependencies.View.ProjectScene;

import dependencies.Controller.SceneController.MenuController;
import dependencies.Model.Category;
import dependencies.Model.Video;
import dependencies.View.ProjectComponent.*;
import dependencies.View.ProjectWindow;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.ColorUIResource;
import java.util.Random;

public class MenuScene {
    private JPanel container = null;
    private JScrollPane scrollPane = null;
    private JPanel mainPanel = null;
    private JPanel navBarPanel = null;
    private RoundedButton navBarButton = null;
    private JPanel sidebar = null;
    private RoundedButton closeButton = null;
    private RoundedButton profileButton = null;
    private RoundedButton watchLaterButton = null;
    private RoundedButton likedButton = null;
    private RoundedButton moviesButton = null;
    private RoundedButton logOutButton = null;
    private RoundedButton seriesButton = null;
    private RoundedButton parametersButton = null;
    private RoundedButton historyButton = null;
    private RoundedButton controlCenterButton = null;
    private ArrayList<Carousel> carousels;
    private JTextField searchIpt;
    private JComboBox searchDropdown;
    private JPanel carouselPanelContainer;

    public MenuScene(MenuController menuController, ProjectWindow projectWindow, String error, int scrollValue) {


        container = new JPanel();
        container.setBackground(projectWindow.getBackgroundColor());
        container.setLayout(new BorderLayout());

        JPanel undercontainer = new JPanel();
        undercontainer.setBackground(projectWindow.getBackgroundColor());
        undercontainer.setLayout(new BorderLayout());

        // Create the nav bar panel with the nav bar button
        navBarPanel = new JPanel();
        navBarPanel.setMaximumSize(new Dimension(100, 30));
        navBarPanel.setBackground(new Color(0,0,0,0));
        navBarPanel.setLayout(new BorderLayout());
        navBarPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));

        navBarButton = new RoundedButton("sidebar-toggle", "Menu", 20, 20, Color.WHITE, new Color(0,0,0,0), Color.WHITE);
        navBarButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        navBarButton.setPreferredSize(new Dimension(50, 25));
        navBarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleSidebar();
            }
        });

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridBagLayout());
        searchPanel.setBackground(new Color(0,0,0,0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        searchIpt = new JTextField();
        searchIpt.setPreferredSize(new Dimension(300, 30));
        searchIpt.setMinimumSize(new Dimension(300, 30));
        searchIpt.setFont(new Font("Arial", Font.PLAIN, 20));
        searchIpt.setText("Search");
        searchIpt.setForeground(Color.GRAY);
        searchIpt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchIpt.getText().equals("Search")) {
                    searchIpt.setText("");
                    searchIpt.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchIpt.getText().isEmpty()) {
                    searchIpt.setText("Search");
                    searchIpt.setForeground(Color.GRAY);
                }
            }
        });

        searchPanel.add(searchIpt, gbc);

// Create the dropdown menu

        String[] searchOptions = {"Movies", "Categories"};
        searchDropdown = new JComboBox<>(searchOptions);
        searchDropdown.setMaximumSize(new Dimension(300, 30));
        searchDropdown.setMinimumSize(new Dimension(300, 30));
        searchDropdown.setBorder(null);
        searchDropdown.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                searchIpt.setText("");
                String searchMode = (String) searchDropdown.getSelectedItem();
                updateCarouselsBasedOnSearch(searchIpt.getText(), searchMode);
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
            }

        });
        searchIpt.getDocument().addDocumentListener(new DocumentListener() {
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
                String searchText = searchIpt.getText();
                if (searchText.equals("Search")) {
                    searchText = "";
                }
                String searchMode = (String) searchDropdown.getSelectedItem();
                updateCarouselsBasedOnSearch(searchText, searchMode);
            }
        });

        searchPanel.add(searchIpt);
        searchPanel.add(searchDropdown);
        navBarPanel.add(navBarButton, BorderLayout.WEST);


        undercontainer.add(navBarPanel, BorderLayout.NORTH);


        // Create the main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(projectWindow.getBackgroundColor());

        ArrayList<Category> ids;
        ids = menuController.getApplicationController().getApplication().getDisplayedCategories();
        InitializeCarousels(ids, menuController, projectWindow);

        carouselPanelContainer = new JPanel();
        carouselPanelContainer.setBackground(projectWindow.getBackgroundColor());
        carouselPanelContainer.setLayout(new BoxLayout(carouselPanelContainer, BoxLayout.Y_AXIS));


        scrollPane = new JScrollPane(carouselPanelContainer);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
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

        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        adjustCarouselRightButtonPosition(0); // Reset the position
        InsertCarousels(carouselPanelContainer);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        undercontainer.add(mainPanel, BorderLayout.CENTER);

        // Set the initial scroll value based on the scrollValue variable
        JViewport viewport = scrollPane.getViewport();
        viewport.setViewPosition(new Point(0, scrollValue));

        // Create the sidebar
        sidebar = new JPanel();
        sidebar.setBackground(projectWindow.getMenuBarColor());
        sidebar.setPreferredSize(new Dimension(300, projectWindow.getHeight()));

        JPanel closeButtonContainer = new JPanel();
        closeButtonContainer.setBackground(projectWindow.getMenuBarColor());
        closeButtonContainer.setPreferredSize(new Dimension(250, 25));
        closeButtonContainer.setLayout(new BorderLayout());

        closeButton = new RoundedButton("sidebar-close", "Close", 20, 20, Color.WHITE, projectWindow.getBackgroundColor(), Color.WHITE);
        closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        closeButton.setPreferredSize(new Dimension(50, 25));
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleSidebar();
            }
        });

        closeButtonContainer.add(closeButton, BorderLayout.EAST);
        sidebar.add(closeButtonContainer);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        buttonPanel.setLayout(new GridLayout(9, 1, 5, 20));
        buttonPanel.setPreferredSize(new Dimension(200, 50 * 9));
        buttonPanel.setBackground(projectWindow.getMenuBarColor());

        profileButton = new RoundedButton("profile","Profile", 20, 20, Color.WHITE, projectWindow.getBackgroundColor(), Color.WHITE);
        watchLaterButton = new RoundedButton("watch-later", "Watch Later", 20, 20, Color.WHITE, projectWindow.getBackgroundColor(), Color.WHITE);
        likedButton = new RoundedButton("liked", "Liked", 20, 20, Color.WHITE, projectWindow.getBackgroundColor(), Color.WHITE);
        moviesButton = new RoundedButton("movies", "Movies", 20, 20, Color.WHITE, projectWindow.getBackgroundColor(), Color.WHITE);
        seriesButton = new RoundedButton("series", "Series", 20, 20, Color.WHITE, projectWindow.getBackgroundColor(), Color.WHITE);
        historyButton = new RoundedButton("history", "History", 20, 20, Color.WHITE, projectWindow.getBackgroundColor(), Color.WHITE);
        parametersButton = new RoundedButton("parameters", "Parameters", 20, 20, Color.WHITE, projectWindow.getBackgroundColor(), Color.WHITE);
        controlCenterButton = new RoundedButton("control-center", "Control Center", 20, 20, Color.WHITE, projectWindow.getBackgroundColor(), Color.WHITE);
        logOutButton = new RoundedButton("log-out", "Log Out", 20, 20, Color.WHITE, projectWindow.getBackgroundColor(), Color.WHITE);

        profileButton.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {menuController.handleButton(profileButton);}});
        watchLaterButton.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {menuController.handleButton(watchLaterButton);}});
        likedButton.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {menuController.handleButton(likedButton);}});
        moviesButton.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {menuController.handleButton(moviesButton);}});
        seriesButton.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {menuController.handleButton(seriesButton);}});
        historyButton.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {menuController.handleButton(historyButton);}});
        parametersButton.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {menuController.handleButton(parametersButton);}});
        controlCenterButton.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {menuController.handleButton(controlCenterButton);}});
        logOutButton.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {menuController.handleButton(logOutButton);}});

        profileButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        watchLaterButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        likedButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        moviesButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        seriesButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        historyButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        parametersButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        controlCenterButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logOutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

// Add the rounded buttons to the button panel
        buttonPanel.add(profileButton);
        buttonPanel.add(watchLaterButton);
        buttonPanel.add(likedButton);
        buttonPanel.add(moviesButton);
        buttonPanel.add(seriesButton);
        buttonPanel.add(historyButton);
        buttonPanel.add(parametersButton);
        if(menuController.getApplicationController().getApplicationUser().getUserPrivilege()){
            buttonPanel.add(controlCenterButton);
        }
        buttonPanel.add(logOutButton);
// Add the button panel to the sidebar
        sidebar.add(buttonPanel, BorderLayout.CENTER);
        Border border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );
        sidebar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(1, 0, 1, 1, Color.LIGHT_GRAY),
                        BorderFactory.createMatteBorder(1, 1, 1, 0, projectWindow.getBackgroundColor())
                ),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(10, 10, 10, 10),
                        border
                )
        ));
        sidebar.setVisible(false);



        undercontainer.add(sidebar, BorderLayout.WEST);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        // Set the bounds for the container to fill the entire space
        undercontainer.setBounds(0, 0, projectWindow.getWidth(), projectWindow.getHeight());

        // Calculate the X center and set the bounds for the searchPanel
        int searchPanelWidth = 600;
        int searchPanelHeight = 40;
        int searchPanelX = (projectWindow.getWidth() - searchPanelWidth) / 2;
        int searchPanelY = 0;
        searchPanel.setBounds(searchPanelX, searchPanelY, searchPanelWidth, searchPanelHeight);

        // Add both container and searchPanel to the layered pane
        layeredPane.add(undercontainer, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(searchPanel, JLayeredPane.PALETTE_LAYER);

        // Add the layered pane to the projectWindow
        container.add(layeredPane);
    }

    public void InitializeCarousels(ArrayList<Category> categories, MenuController menuController,ProjectWindow projectWindow){
        carousels = new ArrayList<Carousel>();
        for(Category category : categories){
            int[] carouselVideoIDS;
            try {
                carouselVideoIDS = menuController.getApplicationController().getConnectionController().getCategorizationDAO().getAllVideosInCategory(category);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            shuffleIntArray(carouselVideoIDS);
            ArrayList<Video> carouselVideos;
            try {
                carouselVideos = menuController.getApplicationController().getConnectionController().getVideoDAO().getAllVideosByIds(carouselVideoIDS);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            Carousel carousel = new Carousel(category.getName(),carouselVideos, projectWindow.getBackgroundColor(), Color.WHITE,menuController, projectWindow);
            JLabel carouselLabel = new JLabel(category.getName());
            carouselLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            carouselLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 0));
            carousel.setBackground(projectWindow.getBackgroundColor());
            carouselLabel.setBackground(projectWindow.getBackgroundColor());
            carouselLabel.setForeground(Color.WHITE);
            carousel.add(carouselLabel, BorderLayout.NORTH);
            MouseWheelListener carouselMouseWheelListener = new MouseWheelListener() {
                @Override
                public void mouseWheelMoved(MouseWheelEvent e) {
                    forwardMouseWheelEvent(e);
                }
            };

            carousel.addScrollPaneMouseWheelListener(carouselMouseWheelListener);
            carousels.add(carousel);
        }

        ArrayList<Video> carouselVideos;
        carouselVideos = menuController.getApplicationController().getApplication().getAppUserHistory();

        if(carouselVideos.size() > 0){
            Carousel carousel = new Carousel("History",carouselVideos, projectWindow.getBackgroundColor(), Color.WHITE,menuController, projectWindow);
            JLabel carouselLabel = new JLabel("History");
            carouselLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            carouselLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 0));
            carousel.setBackground(projectWindow.getBackgroundColor());
            carouselLabel.setBackground(projectWindow.getBackgroundColor());
            carouselLabel.setForeground(Color.WHITE);
            carousel.add(carouselLabel, BorderLayout.NORTH);
            MouseWheelListener carouselMouseWheelListener = new MouseWheelListener() {
                @Override
                public void mouseWheelMoved(MouseWheelEvent e) {
                    forwardMouseWheelEvent(e);
                }
            };

            carousel.addScrollPaneMouseWheelListener(carouselMouseWheelListener);
            carousels.add(carousel);
        }

    }

    public void InsertCarousels(JPanel carouselPanelContainer){
        carouselPanelContainer.add(Box.createVerticalStrut(50));
        for (Carousel carousel : carousels){
            carouselPanelContainer.add(carousel);
            carouselPanelContainer.add(Box.createVerticalStrut(60));
        }
    }

    /*private void updateSearch() {
        String searchText = searchIpt.getText();
        String selectedOption = (String) searchDropdown.getSelectedItem();

        if (selectedOption.equals("Movies")) {
            filterVideos(searchText);
        } else if (selectedOption.equals("Categories")) {
            filterCarousels(searchText);
        }
    }*/

    private void filterVideos(String searchText) {
        for (Carousel carousel : carousels) {
            carousel.filterVideos(searchText);
        }
    }
    public void updateCarouselsBasedOnSearch(String searchText, String searchMode) {
        if (searchMode.equals("Movies")) {
            filterVideos(searchText);
        } else if (searchMode.equals("Categories")) {
            filterCarousels(searchText);
        }
    }

    private void filterCarousels(String searchText) {
        carouselPanelContainer.removeAll();

        if (searchText.isEmpty()) {
            InsertCarousels(carouselPanelContainer);
        } else {
            ArrayList<Carousel> filteredCarousels = new ArrayList<>();
            for (Carousel carousel : carousels) {
                if (carousel.getCategoryName().toLowerCase().contains(searchText.toLowerCase())) {
                    filteredCarousels.add(carousel);
                }
            }
            for (Carousel carousel : filteredCarousels) {
                carouselPanelContainer.add(carousel);
                carouselPanelContainer.add(Box.createVerticalStrut(60));
            }
        }
        carouselPanelContainer.revalidate();
        carouselPanelContainer.repaint();
    }
    public JPanel getContainer () {
        return container;
    }

    public void changeVisibility(boolean b){this.container.setVisible(b);}

    private void toggleSidebar () {
        if (sidebar.isVisible()) {
            navBarPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));
            sidebar.setVisible(false);
            navBarButton.setVisible(true);
            adjustCarouselRightButtonPosition(-300); // Reset the position
        } else {
            navBarPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            sidebar.setVisible(true);
            navBarButton.setVisible(false);
            adjustCarouselRightButtonPosition(300); // Adjust based on the sidebar width
        }
    }
    /*public void updateCarousels(){
        for(Carousel carousel : carousels){
            carousel.updateThumbnails();
        }
    }*/
    public void shuffleIntArray(int[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
    private void adjustCarouselRightButtonPosition(int xOffset) {
        for(Carousel carousel : carousels){
            carousel.getRightButton().setLocation(carousel.getRightButton().getX() - xOffset, carousel.getRightButton().getY());
        }
    }
    private void forwardMouseWheelEvent(MouseWheelEvent e) {
        scrollPane.dispatchEvent(SwingUtilities.convertMouseEvent(e.getComponent(), e, scrollPane));
    }

    public ArrayList<VideoThumbnail> getAllVideoThumbnails(){
        ArrayList<VideoThumbnail> videoThumbnails = new ArrayList<VideoThumbnail>();
        for(Carousel carousel : carousels){
            for(VideoThumbnail videoThumbnail : carousel.getVideoThumbnails()){
                videoThumbnails.add(videoThumbnail);
            }
        }
        return videoThumbnails;
    }
    public ArrayList<VideoThumbnail> getVideoThumbnailsById(int id){
        ArrayList<VideoThumbnail> videoThumbnails = new ArrayList<VideoThumbnail>();
        for(Carousel carousel : carousels){
            for(VideoThumbnail videoThumbnail : carousel.getVideoThumbnails()){
                if(videoThumbnail.getVideoId() == id){
                    videoThumbnails.add(videoThumbnail);
                }
            }
        }
        return videoThumbnails;
    }

    public int getScrollValue() {
        return scrollPane.getVerticalScrollBar().getValue();
    }

}
