package dependencies.View.ProjectComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RoundedButton extends JButton implements MouseListener{

    private String name;
    private boolean buttonPressed = false;
    private int arcWidth, arcHeight;
    private Color textColor, backgroundColor, borderColor;

    public RoundedButton(String name, String label, int arcWidth, int arcHeight, Color textColor, Color backgroundColor, Color borderColor) {
        super(label);
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
        this.name = name;

        setOpaque(true); // activer l'opacité
        setBackground(backgroundColor); // définir la couleur d'arrière-plan
        setForeground(textColor); // définir la couleur du texte
        setBorder(BorderFactory.createLineBorder(borderColor)); // définir la bordure

        // Personnaliser l'apparence du bouton en le rendant arrondi
        Dimension size = getPreferredSize();
        size.width = Math.max(size.width, size.height);
        size.height = size.width;
        setPreferredSize(size);
        setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);
        g2.setColor(getForeground());
        g2.drawString(getText(), getWidth() / 2 - g2.getFontMetrics().stringWidth(getText()) / 2, getHeight() / 2 + g2.getFontMetrics().getAscent() / 2 - g2.getFontMetrics().getDescent());
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBorderColor());
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);
        g2.dispose();
    }

    public String getName(){return name;}
    public int getArcWidth() {
        return arcWidth;
    }

    public void setArcWidth(int arcWidth) {
        this.arcWidth = arcWidth;
    }

    public int getArcHeight() {
        return arcHeight;
    }

    public void setArcHeight(int arcHeight) {
        this.arcHeight = arcHeight;
    }

    public Color getTextColor() {
        return textColor;
    }

    public boolean isButtonPressed(){return buttonPressed;}

    public void setButtonPressed(boolean buttonPressed){this.buttonPressed = buttonPressed;}
    public void setTextColor(Color textColor) {
        this.textColor = textColor;
        setForeground(textColor);
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        setBackground(backgroundColor);
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        setBorder(BorderFactory.createLineBorder(borderColor));
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        if(buttonPressed == false){
            
        }
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {

    }

    @Override
    public void mouseExited(MouseEvent arg0) {

    }

    @Override
    public void mousePressed(MouseEvent arg0) {

    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        buttonPressed = false;
    }

}

