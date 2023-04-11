package dependencies.View.ProjectComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SelectableDropdown extends JPanel {
    private final JLabel[] labels;
    private final JPanel dropdownPanel;
    private final JLabel selectedLabel;
    private boolean isOpen = false;
    private final JLayeredPane layeredPane;

    public SelectableDropdown(String[] options) {
        super(new BorderLayout());

        // Create selected label for dropdown
        selectedLabel = new JLabel(options[0]);

        // Create dropdown panel
        dropdownPanel = new JPanel();
        dropdownPanel.setLayout(new BoxLayout(dropdownPanel, BoxLayout.Y_AXIS));
        labels = new JLabel[options.length];
        for (int i = 0; i < options.length; i++) {
            labels[i] = new JLabel(options[i]);
            labels[i].setAlignmentX(Component.LEFT_ALIGNMENT);
            labels[i].setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
            labels[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selectedLabel.setText(((JLabel) e.getSource()).getText());
                    toggleDropdownVisibility();
                }
            });
            dropdownPanel.add(labels[i]);
        }
        dropdownPanel.setMaximumSize(new Dimension(150, options.length * 25));
        dropdownPanel.setVisible(false);

        // Create dropdown button
        JButton dropdownButton = new JButton("\u2193");
        dropdownButton.addActionListener(e -> toggleDropdownVisibility());

        // Create dropdown container panel
        JPanel dropdownContainer = new JPanel(new BorderLayout());
        dropdownContainer.add(selectedLabel, BorderLayout.CENTER);
        dropdownContainer.add(dropdownButton, BorderLayout.EAST);

        // Create layered pane
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(150, 25));

        // Set bounds for dropdown container and panel
        dropdownContainer.setBounds(0, 0, 150, 25);
        dropdownPanel.setBounds(0, 25, 150, dropdownPanel.getPreferredSize().height);

        // Add components to the layered pane
        layeredPane.add(dropdownContainer, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(dropdownPanel, JLayeredPane.POPUP_LAYER);

        // Add components to main panel
        add(layeredPane, BorderLayout.CENTER);
    }

    private void toggleDropdownVisibility() {
        isOpen = !isOpen;
        dropdownPanel.setVisible(isOpen);
        dropdownPanel.setBounds(0, 25, 150, isOpen ? dropdownPanel.getPreferredSize().height : 0);
    }

    public String getSelectedOption() {
        return selectedLabel.getText();
    }
}
