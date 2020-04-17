package menu;

import org.dyn4j.geometry.Vector2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Vector;

public class LevelEditor extends JFrame {

    private static File folder = new File("com/justin/bokus/res/images/icons");
    private static File[] listOfFiles = folder.listFiles();
    private ImageIcon[] pieceIcons = {
            new ImageIcon(String.valueOf(listOfFiles[0])),
            new ImageIcon(String.valueOf(listOfFiles[1])),
            new ImageIcon(String.valueOf(listOfFiles[12])),
            new ImageIcon(String.valueOf(listOfFiles[13])),
            new ImageIcon(String.valueOf(listOfFiles[14])),
            new ImageIcon(String.valueOf(listOfFiles[15])),
            new ImageIcon(String.valueOf(listOfFiles[16])),
            new ImageIcon(String.valueOf(listOfFiles[17])),
            new ImageIcon(String.valueOf(listOfFiles[18])),
            new ImageIcon(String.valueOf(listOfFiles[19])),
            new ImageIcon(String.valueOf(listOfFiles[2])),
            new ImageIcon(String.valueOf(listOfFiles[3])),
            new ImageIcon(String.valueOf(listOfFiles[4])),
            new ImageIcon(String.valueOf(listOfFiles[5])),
            new ImageIcon(String.valueOf(listOfFiles[6])),
            new ImageIcon(String.valueOf(listOfFiles[7])),
            new ImageIcon(String.valueOf(listOfFiles[8])),
            new ImageIcon(String.valueOf(listOfFiles[9])),
            new ImageIcon(String.valueOf(listOfFiles[10])),
            new ImageIcon(String.valueOf(listOfFiles[11])),};
    private int numButtons = 600;    //number of rows * number of columns in map
    private JButton[] buttonField = new JButton[numButtons];
    private JLabel[] pieceField = new JLabel[20];
    private JButton saveBtn = new JButton("save");
    private JButton loadBtn = new JButton("load");
    private JButton previewBtn = new JButton("preview");

    private void BuildWindow(){
        JFrame frame = new JFrame();
        frame.setTitle("Sloppy Drivers Level Editor");
        frame.setSize(1600,1000);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        previewBtn.addActionListener(new previewListener());
        saveBtn.addActionListener(new saveListener());

        JPanel main = new JPanel();
        GridBagLayout windowLayout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        main.setLayout(windowLayout);

        //setting up map area
        JPanel mapArea = new JPanel();
        mapArea.setPreferredSize(new Dimension(1200,850));
        JLabel subTitle = new JLabel("click boxes to fill them in then drag and drop to define the corner -->");
        subTitle.setFont(subTitle.getFont().deriveFont(18.0f));
        mapArea.add(subTitle);
        JPanel map = new JPanel();
        map.setPreferredSize(new Dimension(1200, 800));
        map.setLayout(new GridLayout(20,30));
        for(int i = 0; i < numButtons; i++){
            buttonField[i] = new JButton();
            buttonField[i].setBackground(Color.white);
            buttonField[i].addActionListener(new fieldListener());
            buttonField[i].setTransferHandler(new TransferHandler("icon"));
            map.add(buttonField[i]);
        }
        mapArea.add(map);

        //setting up piece selection area
        JPanel pieceArea = new JPanel();
        pieceArea.setPreferredSize(new Dimension(200, 400));
        JLabel pieceLabel0 = new JLabel("Drag and Drop pieces ");
        pieceLabel0.setFont(pieceLabel0.getFont().deriveFont(14.0f));
        JLabel pieceLabel1 = new JLabel("to define corners");
        pieceLabel1.setFont(pieceLabel1.getFont().deriveFont(14.0f));
        pieceArea.add(pieceLabel0);
        pieceArea.add(pieceLabel1);
        JPanel pieces = new JPanel();
        pieces.setPreferredSize(new Dimension(200,160));
        GridLayout pieceLayout = new GridLayout(4, 5);
        pieceLayout.setHgap(5);
        pieceLayout.setVgap(5);
        pieces.setLayout(pieceLayout);
        for(int i = 0; i < 20; i++){
            pieceField[i] = new JLabel(pieceIcons[i]);
            pieceField[i].setTransferHandler(new TransferHandler("icon") );    //makes the label dragable
            pieceField[i].addMouseListener(new MouseDrag());  //allows the user to "pick up" the piece
            pieces.add(pieceField[i]);
        }
        pieceArea.add(pieces);

        JLabel title = new JLabel("Welcome to the Sloppy Drivers Level Editor!");
        title.setFont(title.getFont().deriveFont(32.0f));

        //adding components to the JFrame
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        main.add(title, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.NONE;
        main.add(mapArea, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = .5;
        main.add(saveBtn, c);

        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = .5;
        main.add(loadBtn, c);

        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = .5;
        main.add(previewBtn, c);

        c.gridx = 3;
        c.gridy = 1;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0,50,0,0);
        main.add(pieceArea, c);


        frame.add(main);
        frame.setVisible(true);
    }

    public class fieldListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            for(int i = 0; i < numButtons; i ++){
                if(e.getSource() == buttonField[i]){
                    if(buttonField[i].getBackground() == Color.white){
                        buttonField[i].setIcon(null);
                        buttonField[i].setBackground(Color.green);
                    }
                    else if(buttonField[i].getBackground() == Color.green) {
                        buttonField[i].setIcon(null);
                        buttonField[i].setBackground(Color.white);
                    }
                }
            }
        }
    }

    public class previewListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            for(int i = 0; i < numButtons; i++){
                if(buttonField[i].isBorderPainted())
                    buttonField[i].setBorderPainted(false);
                else if(!buttonField[i].isBorderPainted())
                    buttonField[i].setBorderPainted(true);
            }
        }
    }

    public class saveListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            int x;
            int y;
            for(int i = 0; i < numButtons; i++){
                x = -600+40*(i%30);
                y = 400-40*(i/30);
                if(buttonField[i].getIcon() != null){
                    System.out.println("row:" + (i/30+1));
                    System.out.println("column:" + (i%30+1));
                    Vector2[] bodyPoints = getBodies(x, y, i);
                    System.out.println(bodyPoints[0]);
                    System.out.println(bodyPoints[1]);
                    System.out.println(bodyPoints[2]);
                    System.out.println(bodyPoints[3]);
                }
            }
            System.out.println("saved");
        }
    }

    private class MouseDrag extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            JComponent c = (JComponent)e.getSource();
            TransferHandler h = c.getTransferHandler();
            h.exportAsDrag(c, e, TransferHandler.COPY);
        }
    }

    //points are listed left then right when looking from green
    private Vector2[] getVectors(int x, int y, int pieceNum){
        Vector2[] points = new Vector2[2];
        switch(pieceNum){
            case 0:
                points[0] = new Vector2((x+20), y);
                points[1] = new Vector2(x, (y-40));
                return points;
            case 1:
                points[0] = new Vector2((x+40), y);
                points[1] = new Vector2((x+20), (y-40));
                return points;
            case 2:
                points[0] = new Vector2((x+40), y);
                points[1] = new Vector2(x, (y-40));
                return points;
            case 3:
                points[0] = new Vector2((x+40), (y-20));
                points[1] = new Vector2(x, (y-40));
                return points;
            case 4:
                points[0] = new Vector2((x+40), y);
                points[1] = new Vector2(x, (y-20));
                return points;
            case 5:
                points[0] = new Vector2((x+40), (y-20));
                points[1] = new Vector2(x, y);
                return points;
            case 6:
                points[0] = new Vector2((x+40), (y-40));
                points[1] = new Vector2(x, (y-20));
                return points;
            case 7:
                points[0] = new Vector2((x+40), (y-40));
                points[1] = new Vector2(x, y);
                return points;
            case 8:
                points[0] = new Vector2((x+20), (y-40));
                points[1] = new Vector2(x, y);
                return points;
            case 9:
                points[0] = new Vector2((x+40), (y-40));
                points[1] = new Vector2((x+20), y);
                return points;
            case 10:
                points[0] = new Vector2((x+20), (y-40));
                points[1] = new Vector2((x+40), y);
                return points;
            case 11:
                points[0] = new Vector2(x, (y-40));
                points[1] = new Vector2((x+20), y);
                return points;
            case 12:
                points[0] = new Vector2(x, (y-40));
                points[1] = new Vector2((x+40), y);
                return points;
            case 13:
                points[0] = new Vector2(x, (y-20));
                points[1] = new Vector2((x+40), y);
                return points;
            case 14:
                points[0] = new Vector2(x, (y-40));
                points[1] = new Vector2((x+40), (y-20));
                return points;
            case 15:
                points[0] = new Vector2(x, (y-20));
                points[1] = new Vector2((x+40), (y-40));
                return points;
            case 16:
                points[0] = new Vector2(x, y);
                points[1] = new Vector2((x+40), (y-20));
                return points;
            case 17:
                points[0] = new Vector2(x, y);
                points[1] = new Vector2((x+40), (y-40));
                return points;
            case 18:
                points[0] = new Vector2((x+20), y);
                points[1] = new Vector2((x+40), (y-40));
                return points;
            case 19:
                points[0] = new Vector2(x, y);
                points[1] = new Vector2((x+20), (y-40));
                return points;
            default:
                return points;
        }
    }

    private Vector2[] getBodies(int x, int y, int i){
        Vector2[] piece0Points = getVectors(x, y, getPieceNum(i));
        if(buttonField[i + 1].getBackground() == Color.green){
            i++;
            while(buttonField[i].getBackground() == Color.green){
                if(buttonField[i+1].getIcon() != null){
                    i++;
                    x = -600+40*(i%30);
                    y = 400-40*(i/30);
                    break;
                }
                i++;
            }
        }
        Vector2[] piece1Points = getVectors(x, y, getPieceNum(i));
        Vector2[] bodyPoints= {piece0Points[0], piece0Points[1], piece1Points[0], piece1Points[1]};
        return bodyPoints;
    }

    private int getPieceNum(int i){
        if(buttonField[i].getIcon() == pieceIcons[0]){
            return 0;
        }
        else if(buttonField[i].getIcon() == pieceIcons[1]){
            return 1;
        }
        else if(buttonField[i].getIcon() == pieceIcons[2]){
            return 2;
        }
        else if(buttonField[i].getIcon() == pieceIcons[3]){
            return 3;
        }
        else if(buttonField[i].getIcon() == pieceIcons[4]){
            return 4;
        }
        else if(buttonField[i].getIcon() == pieceIcons[5]){
            return 5;
        }
        else if(buttonField[i].getIcon() == pieceIcons[6]){
            return 6;
        }
        else if(buttonField[i].getIcon() == pieceIcons[7]){
            return 7;
        }
        else if(buttonField[i].getIcon() == pieceIcons[8]){
            return 8;
        }
        else if(buttonField[i].getIcon() == pieceIcons[9]){
            return 9;
        }
        else if(buttonField[i].getIcon() == pieceIcons[10]){
            return 10;
        }
        else if(buttonField[i].getIcon() == pieceIcons[11]){
            return 11;
        }
        else if(buttonField[i].getIcon() == pieceIcons[12]){
            return 12;
        }
        else if(buttonField[i].getIcon() == pieceIcons[13]){
            return 13;
        }
        else if(buttonField[i].getIcon() == pieceIcons[14]){
            return 14;
        }
        else if(buttonField[i].getIcon() == pieceIcons[15]){
            return 15;
        }
        else if(buttonField[i].getIcon() == pieceIcons[16]){
            return 16;
        }
        else if(buttonField[i].getIcon() == pieceIcons[17]){
            return 17;
        }
        else if(buttonField[i].getIcon() == pieceIcons[18]){
            return 18;
        }
        else if(buttonField[i].getIcon() == pieceIcons[19]){
            return 19;
        }
        return 9999;
    }

    public static void main(String[] args){
        LevelEditor LE = new LevelEditor();
        LE.BuildWindow();
    }
}
