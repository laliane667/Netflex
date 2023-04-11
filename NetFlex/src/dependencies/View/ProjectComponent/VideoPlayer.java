package dependencies.View.ProjectComponent;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class VideoPlayer extends JPanel implements ActionListener {
    private boolean isPlaying = false;

    private int width, height;
    private final int videoOffsetBias = 200;
    private BufferedImage currentImageDisplayed = null;
    private Timer timer;
    private int currentImage = 0;
    private int numImages = 0;
    private File file;
    private AudioInputStream audioInputStream;
    private Clip clip;
    private long startTime;
    private String path;
    private String audioPath;
    float FRAME_RATE = 24F;


    public VideoPlayer(int width, int height, Timer timer, String videoPath, int startTime) throws IOException {
        this.width = width;
        this.height = height;
        this.timer = timer;
        this.startTime = startTime;

        path = "src/resource"+videoPath+"/frames";
        audioPath="src/resource"+videoPath+"/sound.wav";

        File[] files = new File(path).listFiles();
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                String name1 = f1.getName();
                String name2 = f2.getName();
                int number1 = getFrameNumber(name1);
                int number2 = getFrameNumber(name2);
                return Integer.compare(number1, number2);
            }

            private int getFrameNumber(String fileName) {
                try {
                    return Integer.parseInt(fileName.substring(fileName.lastIndexOf("-") + 1, fileName.lastIndexOf(".")));
                } catch (NumberFormatException e) {
                    return -1;
                }
            }
        });

        numImages = files.length;
        System.out.println("Total images: " + numImages);

        file = new File(audioPath);
        try {
            audioInputStream = AudioSystem.getAudioInputStream(file);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        try {
            clip.open(audioInputStream);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        setVideoAndAudioPosition(startTime);
        loadImage(currentImage);
    }

    public void setVideoAndAudioPosition(int frameNumber) {
        // Set the audio position
        long audioPosition = (long) (frameNumber / FRAME_RATE) * 1000000; // Convert frame number to seconds and then to microseconds
        clip.setMicrosecondPosition(audioPosition);

        // Set the image position
        currentImage = frameNumber;
        loadImage(currentImage);
    }
    public static String formatNumber(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Number must be positive.");
        }
        if (n < 10) {
            return "00" + n;
        } else if (n < 100) {
            return "0" + n;
        } else {
            return Integer.toString(n);
        }
    }

    public void startAnimation(int position) {
        isPlaying = true;

        //clip.setFramePosition(position);
        new Thread(new Runnable() {
            @Override
            public void run() {
                clip.start();
            }
        }).start();
        if(clip.getMicrosecondPosition() > 0)
            timer.start();
    }

    public void stopAnimation() {
        timer.stop();
        clip.stop();
    }

    public void actionPerformed(ActionEvent e) {
        syncWithAudio();
        repaint();
    }

    public int getTime(){
        return (int) (currentImage / FRAME_RATE);
    }

    public void setMuted(boolean muted){
        if(muted){
            muteAudio();
        }else{
            unmuteAudio();
        }
    }

    public void loadImage(int index){
        try {
            currentImageDisplayed = ImageIO.read(new File(path + "/frame" + formatNumber(index)+ ".jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void syncWithAudio() {
        if (isPlaying) {
            startTime = clip.getMicrosecondPosition();
            long currentAudioPosition = (startTime + videoOffsetBias) / 1000;
            int newImage = (int) (currentAudioPosition / (1000 / FRAME_RATE));

            if (newImage >= numImages) {
                newImage = numImages - 1;
            }
            currentImage = newImage;
            loadImage(currentImage);
        }
    }

    public void muteAudio() {
        FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volumeControl.setValue(volumeControl.getMinimum());
    }

    public void unmuteAudio() {
        FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volumeControl.setValue(volumeControl.getMaximum());
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(currentImageDisplayed, 0, 0, width, height, null);
    }

    public int getCurrentImage() {
        return currentImage;
    }

    public int getTotalImageCounter() {
        return numImages;
    }

    public void setStartTime(long startTime){
        this.startTime = startTime;
    }
    public long getStartTime(){
        return startTime;
    }
}

//Create video panel and put it inside another panel to crop 1/4 of the top and bottom
        /*JPanel videoPanel = new JPanel();
        videoPanel.setBackground(projectWindow.getBackgroundColor());
        videoPanel.setLayout(new BorderLayout());

        try {
            videoPanel.add(new VideoPlayer(1920, 1080));
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
        JPanel videoContainer = new JPanel(new CardLayout());
        videoContainer.setPreferredSize(new Dimension(1920, 810));
        videoContainer.setBackground(projectWindow.getBackgroundColor());
        videoContainer.setBorder(BorderFactory.createEmptyBorder(-100, 0, 0, 0)); //Crop 1/4 of the top and bottom
        videoContainer.add(videoPanel);

        //Add the cropped video panel to the main panel
        mainPanel.add(videoContainer);*/