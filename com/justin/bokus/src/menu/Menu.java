package menu;

import graphics.GameWorld;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

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
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        JButton levelBuilderButton = new JButton("Level Builder");
        levelBuilderButton.setBounds(500, 600, 200, 100);
        levelBuilderButton.addActionListener(new LevelBuilderButtonListener());

        jf.add(mapButton);
        jf.add(prevButton);
        jf.add(nextButton);
        jf.add(levelBuilderButton);

        System.out.println(MainMenu.rootDir);
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

    public class LevelBuilderButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            jf.dispose();
            LevelEditor.main(new String[]{});
        }
    }
    public static void main(String[] args) {
        Menu window = new Menu();
        window.BuildWindow();
    }

}
