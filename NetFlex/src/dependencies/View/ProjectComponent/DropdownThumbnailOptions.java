package dependencies.View.ProjectComponent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class DropdownThumbnailOptions extends JPanel {
    private final RoundedButton[] buttons;
    private final JPanel dropdownPanel;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private boolean isOpen = false;

    private final JFrame parentFrame;
    private JLayeredPane parentLayeredPane;
    private RoundedButton moreButton;

    private final int whiteSpace = 10; // White space of 10px on top of the dropdown panel

    public DropdownThumbnailOptions(RoundedButton[] options, RoundedButton moreButton, JFrame parentFrame) {
        super(new BorderLayout());
        this.parentFrame = parentFrame;
        parentLayeredPane = parentFrame.getLayeredPane();
        this.moreButton = moreButton;
        setBackground(moreButton.getBackgroundColor());
        // Create dropdown panel
        dropdownPanel = new JPanel();
        dropdownPanel.setBackground(new Color(0,0,0,210));
        dropdownPanel.setLayout(new BoxLayout(dropdownPanel, BoxLayout.Y_AXIS));
        dropdownPanel.setPreferredSize(new Dimension(125,100));
        buttons = new RoundedButton[options.length];

        int buttonSpacing = 5; // Add space between buttons
        dropdownPanel.add(Box.createVerticalStrut(10));

        // Create CardLayout and card panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(new Color(0,0,0, 0));
        cardPanel.setBorder(new LineBorder(Color.WHITE, 1)); // Add a white border to the cardLayout


        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                try {
                    // Check if the event source is the "More" button, and if so, ignore the event
                    if (e.getSource() == moreButton) {
                        return;
                    }

                    Rectangle extendedDropdownBounds = new Rectangle(dropdownPanel.getBounds());
                    extendedDropdownBounds.y -= whiteSpace;
                    extendedDropdownBounds.height += whiteSpace;

                    Point mousePosition = MouseInfo.getPointerInfo().getLocation();
                    SwingUtilities.convertPointFromScreen(mousePosition, parentLayeredPane);

                    if (!moreButton.getBounds().contains(mousePosition) && !extendedDropdownBounds.contains(mousePosition)) {
                        hideDropdownPanel();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };


        for (int i = 0; i < options.length; i++) {
            buttons[i] = options[i];
            buttons[i].setAlignmentX(Component.LEFT_ALIGNMENT);
            dropdownPanel.add(buttons[i]);
            dropdownPanel.add(Box.createVerticalStrut(10));
            dropdownPanel.setMaximumSize(dropdownPanel.getPreferredSize());
            dropdownPanel.setVisible(false);
            // Add mouseAdapter to each button
            buttons[i].addMouseListener(mouseAdapter);
        }

        moreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isOpen) {
                    hideDropdownPanel();
                } else {
                    displayDropdownPanel();
                }
            }
        });

        Timer checkMousePositionTimer = new Timer(1000, e -> {
            if (!dropdownMenuContainsMouse() && !moreButtonContainsMouse()) {
                hideDropdownPanel();
            }
        });
        checkMousePositionTimer.setRepeats(true);
        checkMousePositionTimer.start();

        JPanel emptyPanel = new JPanel();
        cardPanel.add(emptyPanel, "EMPTY");
        cardPanel.add(dropdownPanel, "DROPDOWN");
        // Add components to main panel
        add(moreButton, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);
    }

    private void displayDropdownPanel() {
        isOpen = true;

        Point moreButtonLocation = SwingUtilities.convertPoint(moreButton, 0, moreButton.getHeight(), parentFrame);
        int dropdownPanelWidth = 115;
        int dropdownPanelHeight = 50;
        int dropdownPanelX = (int) (moreButtonLocation.x - (dropdownPanelWidth - moreButton.getPreferredSize().getWidth()));
        int dropdownPanelY = moreButtonLocation.y - 30;


        for (Component comp : parentLayeredPane.getComponentsInLayer(JLayeredPane.POPUP_LAYER)) {
            if (comp instanceof JPanel && ((JPanel) comp).isOpaque() == false) {
                parentLayeredPane.remove(comp);
            }
        }
        // Create a transparent panel that covers the area between the "More" button and the dropdown panel
        JPanel transparentPanel = new JPanel();
        transparentPanel.setOpaque(false);
        transparentPanel.setBounds(0, 0, parentFrame.getWidth(), parentFrame.getHeight());
        parentLayeredPane.add(transparentPanel, JLayeredPane.POPUP_LAYER);
        parentLayeredPane.moveToFront(transparentPanel);

        dropdownPanel.setBounds(dropdownPanelX, dropdownPanelY + whiteSpace, dropdownPanel.getPreferredSize().width, dropdownPanel.getPreferredSize().height);
        parentLayeredPane.add(dropdownPanel, JLayeredPane.POPUP_LAYER);
        parentLayeredPane.moveToFront(dropdownPanel);
        dropdownPanel.setVisible(true);

        // Add a MouseAdapter to hide the dropdown panel when the cursor leaves the area
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                try {
                    Rectangle extendedDropdownBounds = new Rectangle(dropdownPanel.getBounds());
                    extendedDropdownBounds.y -= whiteSpace;
                    extendedDropdownBounds.height += whiteSpace;

                    Point mousePosition = MouseInfo.getPointerInfo().getLocation();
                    SwingUtilities.convertPointFromScreen(mousePosition, parentLayeredPane);

                    if (!moreButton.getBounds().contains(mousePosition) && !extendedDropdownBounds.contains(mousePosition)) {
                        hideDropdownPanel();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                // Check if the mouse is outside both the DropdownThumbnailOptions component and the dropdown panel
                if (!contains(e.getPoint()) && (dropdownPanel == null || !dropdownPanel.contains(e.getPoint()))) {
                    hideDropdownPanel();
                }
            }
        });

        dropdownPanel.addMouseListener(mouseAdapter);
        moreButton.addMouseListener(mouseAdapter);
    }

    private boolean dropdownMenuContainsMouse() {
        Point mousePosition = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(mousePosition, dropdownPanel);
        return dropdownPanel.contains(mousePosition);
    }

    private boolean moreButtonContainsMouse() {
        Point mousePosition = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(mousePosition, moreButton);
        return moreButton.contains(mousePosition);
    }
    public void hideDropdownPanel() {
        try {
            isOpen = false;
            dropdownPanel.setVisible(false);
            parentFrame.getLayeredPane().remove(dropdownPanel);
            cardLayout.show(cardPanel, "EMPTY");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public JPanel getDropdownPanel(){return dropdownPanel;}

}
