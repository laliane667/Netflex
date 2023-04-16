package dependencies.View.ProjectComponent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CheckableDropdown extends JPanel {
    private final JCheckBox[] checkboxes;
    private final JLabel[] labels;
    private final JPanel dropdownPanel;
    private boolean isOpen = false;

    public CheckableDropdown(String[] options) {
        super(new BorderLayout());

        // Create label for dropdown
        JLabel selectLabel = new JLabel("Select category");

        // Create dropdown panel
        dropdownPanel = new JPanel();
        dropdownPanel.setLayout(new BoxLayout(dropdownPanel, BoxLayout.Y_AXIS));
        checkboxes = new JCheckBox[options.length];
        labels = new JLabel[options.length];
        for (int i = 0; i < options.length; i++) {
            checkboxes[i] = new JCheckBox();
            labels[i] = new JLabel(options[i]);
            labels[i].setAlignmentX(Component.LEFT_ALIGNMENT);
            JPanel checkboxPanel = new JPanel();
            checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.X_AXIS));
            checkboxPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            checkboxPanel.add(checkboxes[i]);
            checkboxPanel.add(labels[i]);
            dropdownPanel.add(checkboxPanel);
            dropdownPanel.setMaximumSize(dropdownPanel.getPreferredSize());
            dropdownPanel.setVisible(false);
        }

        // Create dropdown button
        JButton dropdownButton = new JButton("\u2193");
        dropdownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isOpen = !isOpen;
                dropdownPanel.setVisible(isOpen);
            }
        });

        // Create dropdown container panel
        JPanel dropdownContainer = new JPanel(new BorderLayout());
        dropdownContainer.add(selectLabel, BorderLayout.WEST);
        dropdownContainer.add(dropdownButton, BorderLayout.EAST);

        // Add components to main panel
        add(dropdownContainer, BorderLayout.CENTER);
        add(dropdownPanel, BorderLayout.SOUTH);
    }

    public ArrayList<String> getCheckedOptions() {
        ArrayList<String> checkedOptions = new ArrayList<String>();
        for (int i = 0; i < labels.length; i++) {
            if (checkboxes[i].isSelected()) {
                checkedOptions.add(labels[i].getText());
            }
        }
        return checkedOptions;
    }

}

