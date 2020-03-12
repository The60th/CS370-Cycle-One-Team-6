package menu;

import graphics.Thrust;
import graphics.Track1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Menu extends JFrame {
    private int x = 300;
    private int y = 200;
    private int test = 100;

    static File folder = new File("com/justin/bokus/res/images");
    static File[] listOfFiles = folder.listFiles();
    static int mapNum = 0;

    JButton mapButton;
    JButton prevButton;
    JButton nextButton;
    ImageIcon icon = new ImageIcon(String.valueOf(listOfFiles[mapNum]));
    JFrame jf;

    private void BuildWindow() {
        jf = new JFrame();
        jf.setTitle("Sloppy Drivers: You Can't Drive!");
        jf.setSize(1200, 800);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setResizable(false);
        jf.setLayout(null);


        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(600, 400, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImage);

        mapButton = new JButton(icon);
        mapButton.setBounds(300, 100, 600, 400);
        MapButtonListener mapListener = new MapButtonListener();
        mapButton.addActionListener(mapListener);

        prevButton = new JButton("Previous");
        prevButton.setBounds(100, 600, 100, 100);
        PreviousButtonListener previousListener = new PreviousButtonListener();
        prevButton.addActionListener(previousListener);

        nextButton = new JButton("Next");
        nextButton.setBounds(1000, 600, 100, 100);
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
            switch (mapNum) {
                case 0:
                    Track1.main(new String[]{});
                    break;
                case 1:
                    Thrust.main(new String[]{});
                    break;
                case 2:
                    break;
                default:
                    break;

            }
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

    public static void main(String[] args) {
        Menu window = new Menu();
        window.BuildWindow();
    }

}
