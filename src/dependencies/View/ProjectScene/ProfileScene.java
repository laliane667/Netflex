package dependencies.View.ProjectScene;

import dependencies.Controller.SceneController.ProfileController;
import dependencies.View.ProjectComponent.RoundedButton;
import dependencies.View.ProjectWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class ProfileScene {
    private JPanel container = null;
    private JPanel navBarPanel = null;
    private RoundedButton navBarButton = null;
    private JPanel panel1 = null;
    private JPanel panel2 = null;

    public ProfileScene(ProfileController profileController, ProjectWindow projectWindow, String error) {
        container = new JPanel();
        container.setBackground(projectWindow.getBackgroundColor());
        container.setLayout(new BorderLayout());

        // Create the nav bar panel with the nav bar button
        navBarPanel = new JPanel();
        navBarPanel.setBackground(projectWindow.getBackgroundColor());
        navBarPanel.setLayout(new BorderLayout());
        navBarPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));

        navBarButton = new RoundedButton("back-to-menu", "Back", 20, 20, Color.WHITE, projectWindow.getBackgroundColor(), Color.WHITE);
        navBarButton.setPreferredSize(new Dimension(50, 25));
        navBarButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        navBarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                profileController.handleButton(navBarButton);
            }
        });

        navBarPanel.add(navBarButton, BorderLayout.WEST);
        container.add(navBarPanel, BorderLayout.NORTH);

        // Create the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(projectWindow.getBackgroundColor());
        GridBagConstraints gbc = new GridBagConstraints();

// Create the panels
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel1.setBackground(projectWindow.getMenuBarColor());
        panel2.setBackground(projectWindow.getMenuBarColor());

// Set the preferred size for panel1
        panel1.setPreferredSize(new Dimension(mainPanel.getWidth(), 200));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, (int) (projectWindow.getWidth() * 0.05), 15, (int) (projectWindow.getWidth() * 0.05));
        mainPanel.add(panel1, gbc);

        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, (int) (projectWindow.getWidth() * 0.05), 100, (int) (projectWindow.getWidth() * 0.05));
        mainPanel.add(panel2, gbc);

        container.add(mainPanel, BorderLayout.CENTER);

        // Add sub-panels to panel1
        panel1.setLayout(new BorderLayout(15, 0));
        JPanel squarePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // Draw the image inside the circle
                // Replace "path/to/image.jpg" with the actual image path
                ImageIcon icon = new ImageIcon("src/resource/img/profileImage/default.jpeg");
                Image img = icon.getImage();
                BufferedImage resizedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2dImage = resizedImage.createGraphics();
                g2dImage.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2dImage.drawImage(img, 0, 0, getWidth(), getHeight(), null);
                g2dImage.dispose();

                Shape clipShape = new Ellipse2D.Double(0, 0, getWidth(), getHeight());
                g2d.setClip(clipShape);
                g2d.drawImage(resizedImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        squarePanel.setBackground(projectWindow.getMenuBarColor());

        panel1.addHierarchyBoundsListener(new HierarchyBoundsListener() {
            @Override
            public void ancestorMoved(HierarchyEvent e) {
            }

            @Override
            public void ancestorResized(HierarchyEvent e) {
                int squarePanelSize = panel1.getHeight();
                squarePanel.setPreferredSize(new Dimension(squarePanelSize, squarePanelSize));
                squarePanel.setMaximumSize(new Dimension(squarePanelSize, squarePanelSize)); // Set a maximum size for squarePanel
                panel1.revalidate();
            }
        });

        panel1.add(squarePanel, BorderLayout.WEST);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.X_AXIS));
        detailsPanel.setBackground(projectWindow.getMenuBarColor());
        panel1.add(detailsPanel, BorderLayout.CENTER);

        // Add JLabels to detailsPanel
        JLabel nameLabel = new JLabel("Laliane");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        nameLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        JLabel followingLabel = new JLabel("Following: 0");
        followingLabel.setForeground(Color.WHITE);
        followingLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        followingLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        JLabel followersLabel = new JLabel("Followers: 0");
        followersLabel.setForeground(Color.WHITE);
        followersLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        followersLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

        detailsPanel.add(Box.createHorizontalStrut(100));
        detailsPanel.add(nameLabel);
        detailsPanel.add(Box.createHorizontalStrut(150));
        detailsPanel.add(followingLabel);
        detailsPanel.add(Box.createHorizontalStrut(100));
        detailsPanel.add(followersLabel);
    }

    public void changeVisibility(boolean b) {
        this.container.setVisible(b);
    }

    public JPanel getContainer() {
        return container;
    }
}

