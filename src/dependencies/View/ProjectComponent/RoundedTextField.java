package dependencies.View.ProjectComponent;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class RoundedTextField extends JTextField {

    private int arcWidth;
    private int arcHeight;
    private Color borderColor;

    public RoundedTextField(int columns, int arcWidth, int arcHeight, Color backgroundColor, Color textColor, Color borderColor) {
        super(columns);
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
        this.borderColor = borderColor;

        setCaretColor(textColor);
        setOpaque(true); // activer l'opacité
        setBackground(backgroundColor); // définir la couleur d'arrière-plan
        setForeground(textColor); // définir la couleur du texte
        setBorder(BorderFactory.createLineBorder(borderColor)); // définir la bordure
    }

    // Redéfinition de la méthode paintComponent pour dessiner la bordure arrondie
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);
        super.paintComponent(g2);
    }

    // Redéfinition de la méthode paintBorder pour dessiner la bordure
    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(borderColor);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);
    }
}
