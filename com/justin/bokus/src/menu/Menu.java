package menu;

import graphics.GameWorld;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

public class Menu extends JFrame {
    private File folder = new File(MainMenu.rootDir + "com/justin/bokus/resources/images/tracks");
    private File[] listOfFiles = folder.listFiles();
    private String[] listOfNames = new String[listOfFiles.length];
    private int mapNum = 0;
    private JButton mapButton;
    private JButton prevButton;
    private JButton nextButton;
    private ImageIcon icon = new ImageIcon(String.valueOf(listOfFiles[mapNum]));
    private JFrame jf;

    private void BuildWindow() {
        for(int i = 0; i < listOfFiles.length; i++){
            listOfNames[i] = listOfFiles[i].getName().substring(0, listOfFiles[i].getName().indexOf('.'));
        }

        jf = new JFrame();
        jf.setTitle("Sloppy Drivers: You Can't Drive!");
        jf.setSize(1200, 800);
        jf.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        jf.addWindowListener(new CustomWindowListener());
        jf.setResizable(false);
        jf.setLayout(null);
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) { e.printStackTrace();}

        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(600, 400, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImage);

        mapButton = new JButton(icon);
        mapButton.setBounds(300, 100, 600, 400);
        MapButtonListener mapListener = new MapButtonListener();
        mapButton.addActionListener(mapListener);

        prevButton = new JButton("Previous");
        prevButton.setBounds(100, 400, 100, 100);
        PreviousButtonListener previousListener = new PreviousButtonListener();
        prevButton.addActionListener(previousListener);

        nextButton = new JButton("Next");
        nextButton.setBounds(1000, 400, 100, 100);
        NextButtonListener nextListener = new NextButtonListener();
        nextButton.addActionListener(nextListener);

        jf.add(mapButton);
        jf.add(prevButton);
        jf.add(nextButton);

        jf.setVisible(true);
    }

    public class MapButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            mapButton.setVisible(false);
            prevButton.setVisible(false);
            nextButton.setVisible(false);
            jf.setVisible(false);
            jf.dispose();
            GameWorld.setFileName(listOfNames[mapNum]);
            GameWorld.main(new String[]{});
        }
    }

    public class PreviousButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (mapNum > 0) {
                mapNum--;
                icon = new ImageIcon(String.valueOf(listOfFiles[mapNum]));
                Image image = icon.getImage();
                Image scaledImage = image.getScaledInstance(600, 400, Image.SCALE_SMOOTH);
                icon = new ImageIcon(scaledImage);

                mapButton.setIcon(icon);
            }
        }
    }

    public class NextButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (mapNum < listOfFiles.length - 1) {
                mapNum++;

                icon = new ImageIcon(String.valueOf(listOfFiles[mapNum]));
                Image image = icon.getImage();
                Image scaledImage = image.getScaledInstance(600, 400, Image.SCALE_SMOOTH);
                icon = new ImageIcon(scaledImage);

                mapButton.setIcon(icon);
            }
        }
    }

    public class CustomWindowListener implements WindowListener {
        public void windowOpened(WindowEvent e) {
        }
        public void windowClosing(WindowEvent e) {
            jf.dispose();
            MainMenu.main(new String[]{});
        }
        public void windowClosed(WindowEvent e) {
        }
        public void windowIconified(WindowEvent e) {
        }
        public void windowDeiconified(WindowEvent e) {
        }
        public void windowActivated(WindowEvent e) {
        }
        public void windowDeactivated(WindowEvent e) {
        }
    }

    public static void main(String[] args) {
        Menu window = new Menu();
        window.BuildWindow();
    }

}
