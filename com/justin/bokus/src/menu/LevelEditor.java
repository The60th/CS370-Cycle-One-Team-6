package menu;

import javax.swing.*;
import java.awt.*;
//click button to get rid of pieces

public class LevelEditor extends JFrame {
    public static void BuildWindow(){
        JButton[] buttonField = new JButton[600];
        JButton saveBtn = new JButton("save");
        JButton loadBtn = new JButton("load");
        JButton previewBtn = new JButton("preview");

        JFrame frame = new JFrame();
        frame.setTitle("Sloppy Drivers Level Editor");
        frame.setSize(1350,900);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JPanel main = new JPanel();
        GridBagLayout windowLayout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        main.setLayout(windowLayout);

        JPanel map = new JPanel();
        map.setPreferredSize(new Dimension(1200, 800));
        map.setLayout(new GridLayout(30,20));
        for(int i = 0; i < 600; i++){
            buttonField[i] = new JButton();
            map.add(buttonField[i]);
        }

        JLabel  title = new JLabel("Welcome to the Sloppy Drivers Level Editor!");
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        main.add(title, c);

        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.LINE_START;
        c.gridheight = 3;
        main.add(map, c);

        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_END;
        c.gridheight = 1;
        c.ipadx = 50;
        main.add(saveBtn, c);

        c.gridx = 1;
        c.gridy = 1;
        main.add(loadBtn, c);

        c.gridx = 1;
        c.gridy = 2;
        main.add(previewBtn, c);

        frame.add(main);
        frame.setVisible(true);
    }

    public static void main(String args[]){
        BuildWindow();
    }
}
