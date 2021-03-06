package menu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class MainMenu extends JFrame{
    private JFrame jf;
    private JButton mapSelector;
    public static String rootDir;{
        try{
            String fileName = new File(Menu.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
            for(int i = 0; i < fileName.length(); i++){
                if(fileName.substring(fileName.length()-(i+9), fileName.length()-(i)).equals("basic_vm\\")){
                    fileName = fileName.substring(0, fileName.length()-i);
                    break;
                }
            }
            rootDir = new URLDecoder().decode(fileName, "UTF-8");
        }catch(UnsupportedEncodingException e){e.printStackTrace();
        }catch(java.net.URISyntaxException e){e.printStackTrace();}

    }

    private void BuildWindow(){
        jf = new JFrame();
        jf.setTitle("Sloppy Drivers: You Can't Drive!");
        jf.setSize(1200, 800);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //System.out.println(rootDir/com/justin/bokus/resources/images/backgrounds/MainMenu.png);
        try {
            jf.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File(rootDir + "com/justin/bokus/resources/images/backgrounds/MainMenu.png")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        jf.setResizable(false);
        jf.setLayout(null);
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) { e.printStackTrace();}

        mapSelector = new JButton("Select a map");
        mapSelector.setBounds(400, 375, 400, 100);
        try {
            mapSelector.setIcon(new ImageIcon(ImageIO.read(new File(rootDir + "com/justin/bokus/resources/images/backgrounds/MapSelect.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        MapSelectorListener mapListener = new MapSelectorListener();
        mapSelector.addActionListener(mapListener);

        JButton levelBuilderButton = new JButton("Level Builder");
        levelBuilderButton.setBounds(400, 525, 400, 100);
        try {
            levelBuilderButton.setIcon(new ImageIcon(ImageIO.read(new File(rootDir + "com/justin/bokus/resources/images/backgrounds/LevelBuilderSelect.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        levelBuilderButton.addActionListener(new LevelBuilderButtonListener());

        jf.add(mapSelector);
        jf.add(levelBuilderButton);
        jf.setVisible(true);
    }

    public class MapSelectorListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            jf.dispose();
            Menu.main(new String[]{});
        }
    }

    public class LevelBuilderButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            jf.dispose();
            LevelEditor.main(new String[]{});
        }
    }

    public static void main(String[] args)
    {
        MainMenu window = new MainMenu();
        window.BuildWindow();
    }
}
