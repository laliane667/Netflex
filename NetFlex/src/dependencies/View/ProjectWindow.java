package dependencies.View;

import javax.swing.*;
import java.awt.*;

public class ProjectWindow extends JFrame {
    private int screenWidth;
    private int screenHeight;
    private Color backGroundColor;


    public ProjectWindow(String title){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        screenWidth = (int) toolkit.getScreenSize().getWidth();
        screenHeight = (int) toolkit.getScreenSize().getHeight();
        backGroundColor = new Color(30,35,40,255);

        getContentPane().setBackground(backGroundColor);
        setTitle(title);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        setResizable(false);
        setSize(screenWidth,screenHeight);
        setVisible(true);
    }


    public void addPanel(JPanel p){
        add(p);
        invalidate();
        validate();
    }

    //Getters
    public int getScreenWidth(){return screenWidth;}
    public int getScreenHeight(){return screenHeight;}
    public Color getBackgroundColor(){return backGroundColor;}

}
