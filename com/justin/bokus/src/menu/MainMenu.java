package menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class MainMenu extends JFrame{
    private JFrame jf;
    private JButton mapSelector;

    private void BuildWindow(){
        jf = new JFrame();
        jf.setTitle("Sloppy Drivers: You Can't Drive!");
        jf.setSize(1200, 800);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setResizable(false);
        jf.setLayout(null);
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) { e.printStackTrace();}

        mapSelector = new JButton("Select a map");
        mapSelector.setBounds(400, 400, 400, 100);
        MapSelectorListener mapListener = new MapSelectorListener();
        mapSelector.addActionListener(mapListener);

        JButton levelBuilderButton = new JButton("Level Builder");
        levelBuilderButton.setBounds(500, 600, 200, 100);
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
