//make it so you can use just squares
package menu;

import org.dyn4j.geometry.Vector2;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static java.lang.Character.isDigit;

public class LevelEditor extends JFrame {

    private File folder = new File(MainMenu.rootDir + "com/justin/bokus/resources/images/icons");
    private File[] listOfFiles = folder.listFiles();
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
            new ImageIcon(String.valueOf(listOfFiles[11])),
            new ImageIcon(String.valueOf(listOfFiles[20]))};
    private int numButtons = 600;    //number of rows * number of columns in map
    private JButton[] buttonField = new JButton[numButtons];
    private JLabel[] pieceField = new JLabel[20];
    private JButton saveBtn = new JButton("save");
    private JButton loadBtn = new JButton("load");
    private JButton previewBtn = new JButton("preview");
    private JTextField TrackNameField = new JTextField("Please enter the track name here ie: \"track1\"");
    private JPanel map = new JPanel();
    private JFrame frame = new JFrame();

    private void BuildWindow(){
        frame.setTitle("Sloppy Drivers Level Editor");
        frame.setSize(1600,1000);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new CustomWindowListener());
        frame.setLayout(new FlowLayout());
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) { e.printStackTrace();}

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

        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = .5;
        main.add(TrackNameField, c);

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
            File folder = new File(MainMenu.rootDir + "com/justin/bokus/resources/images/tracks");
            File[] listOfFiles = folder.listFiles();
            String[] listOfNames = new String[listOfFiles.length];
            for(int i = 0; i < listOfFiles.length; i++)
                listOfNames[i] = listOfFiles[i].getName().substring(0, listOfFiles[i].getName().indexOf('.'));
            for(int i = 0; i < listOfNames.length; i++){
                if((TrackNameField.getText()).equalsIgnoreCase(listOfNames[i])){
                    JOptionPane.showMessageDialog(frame, "There is already a track named " + TrackNameField.getText());
                    return;
                }else if(isDigit(TrackNameField.getText().charAt(0))){
                    JOptionPane.showMessageDialog(frame, "Class names cant start with a number");
                    return;
                }else if(TrackNameField.getText().contains(" ")){
                    JOptionPane.showMessageDialog(frame, "Class names cant contain a space");
                    return;
                }else{
                    JOptionPane.showMessageDialog(frame, "Track Saved!");
                    break;
                }
            }
            String name = TrackNameField.getText();

            ArrayList<BodyPoints> BodyPoints = new ArrayList<>();
            BodyPoints.add(new BodyPoints(-1, 1000, new Vector2[0]));

            for(int i = 0; i < numButtons; i++){
                if(buttonField[i].isBorderPainted())
                    buttonField[i].setBorderPainted(false);
                else if(!buttonField[i].isBorderPainted())
                    buttonField[i].setBorderPainted(true);
                if(buttonField[i].getBackground() == Color.green){
                    if(buttonField[i].getIcon() == null)
                        buttonField[i].setIcon(pieceIcons[20]);
                }
            }

            try{
                String fileName = MainMenu.rootDir + "com/justin/bokus/resources/images/tracks/" + name + ".PNG";
                BufferedImage buffImage = new BufferedImage(map.getWidth(), map.getHeight(), TYPE_INT_ARGB);
                map.paint(buffImage.createGraphics());
                File imageFile = new File(fileName);
                ImageIO.write(buffImage, "PNG", imageFile);
            }catch(Exception except){
                System.out.println("an error has occured when screenshotting: " + except);
            }

            for(int i = 0; i < numButtons; i++){
                if(buttonField[i].isBorderPainted())
                    buttonField[i].setBorderPainted(false);
                else if(!buttonField[i].isBorderPainted())
                    buttonField[i].setBorderPainted(true);
            }

            for(int i = 0; i < numButtons; i++){
                x = -600+40*(i%30);
                y = 400-40*(i/30);
                if(buttonField[i].getIcon() != null){
                    getBodies(x, y, i, BodyPoints);
                }
            }
            SortBodies(BodyPoints);
            try{writeTrack(name, BodyPoints);}
            catch(Exception except){
                System.out.print("an error has occured on line " + except.getStackTrace()[0].getLineNumber() + ": ");
                System.out.println(except);
            }
        }
    }

    //points are listed left then right when looking from green
    private Vector2[] getVectors(int x, int y, int pieceNum, int dir){
        Vector2[] points = new Vector2[2];
        Vector2[] points0 = new Vector2[3];
        Vector2[] points1 = new Vector2[4];
        if((dir != 0) && pieceNum == 20){
            switch(dir){
                case 1:
                    points[0] = new Vector2(x+40, (y-40));
                    points[1] = new Vector2((x+40), y);
                    return points;
                case 2:
                    points[0] = new Vector2((x+40), (y-40));
                    points[1] = new Vector2(x, (y-40));
                    return points;
                case 3:
                    points[0] = new Vector2(x, y);
                    points[1] = new Vector2(x, (y-40));
                    return points;
                case 4:
                    points[0] = new Vector2(x, y);
                    points[1] = new Vector2((x+40), y);
                    return points;
            }
        }
        if(dir == -1){
            switch(pieceNum){
                case 0:
                    points0[0] = new Vector2((x+20), y);
                    points0[1] = new Vector2(x, (y-40));
                    points0[2] = new Vector2(x, y);
                    return points0;
                case 1:
                    points1[0] = new Vector2((x+40), y);
                    points1[1] = new Vector2((x+20), (y-40));
                    points1[2] = new Vector2(x, y);
                    points1[3] = new Vector2(x, (y-40));
                    return points1;
                case 2:
                    points0[0] = new Vector2((x+40), y);
                    points0[1] = new Vector2(x, (y-40));
                    points0[2] = new Vector2(x, y);
                    return points0;
                case 3:
                    points1[0] = new Vector2((x+40), (y-20));
                    points1[1] = new Vector2((x+40), y);
                    points1[2] = new Vector2(x, y);
                    points1[3] = new Vector2(x, (y-40));
                    return points1;
                case 4:
                    points0[0] = new Vector2((x+40), y);
                    points0[1] = new Vector2(x, (y-20));
                    points0[2] = new Vector2(x, y);
                    return points0;
                case 5:
                    points0[0] = new Vector2((x+40), (y-20));
                    points0[1] = new Vector2(x, y);
                    points0[2] = new Vector2((x+40), y);
                    return points0;
                case 6:
                    points1[0] = new Vector2((x+40), (y-40));
                    points1[1] = new Vector2(x, (y-20));
                    points1[2] = new Vector2((x+40), y);
                    points1[3] = new Vector2(x, y);
                    return points1;
                case 7:
                    points0[0] = new Vector2((x+40), (y-40));
                    points0[1] = new Vector2(x, y);
                    points0[2] = new Vector2((x+40), y);
                    return points0;
                case 8:
                    points1[0] = new Vector2((x+20), (y-40));
                    points1[1] = new Vector2(x, y);
                    points1[2] = new Vector2((x+40), y);
                    points1[3] = new Vector2((x+40), (y-40));
                    return points1;
                case 9:
                    points0[0] = new Vector2((x+40), (y-40));
                    points0[1] = new Vector2((x+20), y);
                    points0[2] = new Vector2((x+40), y);
                    return points0;
                case 10:
                    points0[0] = new Vector2((x+20), (y-40));
                    points0[1] = new Vector2((x+40), y);
                    points0[2] = new Vector2((x+40), (y-40));
                    return points0;
                case 11:
                    points1[0] = new Vector2(x, (y-40));
                    points1[1] = new Vector2((x+20), y);
                    points1[2] = new Vector2((x+40), (y-40));
                    points1[3] = new Vector2((x+40), y);
                    return points1;
                case 12:
                    points0[0] = new Vector2(x, (y-40));
                    points0[1] = new Vector2((x+40), y);
                    points0[2] = new Vector2((x+40), (y-40));
                    return points0;
                case 13:
                    points1[0] = new Vector2(x, (y-20));
                    points1[1] = new Vector2(x, (y-40));
                    points1[2] = new Vector2((x+40), (y-40));
                    points1[3] = new Vector2((x+40), y);
                    return points1;
                case 14:
                    points0[0] = new Vector2(x, (y-40));
                    points0[1] = new Vector2((x+40), (y-20));
                    points0[2] = new Vector2((x+40), (y-40));
                    return points0;
                case 15:
                    points0[0] = new Vector2(x, (y-20));
                    points0[1] = new Vector2((x+40), (y-40));
                    points0[2] = new Vector2(x, (y-40));
                    return points0;
                case 16:
                    points1[0] = new Vector2(x, y);
                    points1[1] = new Vector2((x+40), (y-20));
                    points1[2] = new Vector2(x, (y-40));
                    points1[3] = new Vector2((x+40), (y-40));
                    return points1;
                case 17:
                    points0[0] = new Vector2(x, y);
                    points0[1] = new Vector2((x+40), (y-40));
                    points0[2] = new Vector2(x, (y-40));
                    return points0;
                case 18:
                    points1[0] = new Vector2((x+20), y);
                    points1[1] = new Vector2((x+40), (y-40));
                    points1[2] = new Vector2(x, (y-40));
                    points1[3] = new Vector2(x, y);
                    return points1;
                case 19:
                    points0[0] = new Vector2(x, y);
                    points0[1] = new Vector2((x+20), (y-40));
                    points0[2] = new Vector2(x, (y-40));
                    return points0;
                case 20:
                    points1[0] = new Vector2(x, y);
                    points1[1] = new Vector2(x+40, y);
                    points1[2] = new Vector2(x, y-40);
                    points1[3] = new Vector2(x+40, y-40);
                    return points1;
            }
        }
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
            case 20:
                return points;
        }
        return points;
    }

    private void getBodies(int x, int y, int i, ArrayList<BodyPoints> BodyPoints){
        int initI = i;
        Vector2[] piecePoints = getVectors(x, y, getPieceNum(i), -1);
        System.out.println("added body: ");
        if(BodyPoints.get(0).i0 == -1){
            BodyPoints.remove(0);
            BodyPoints.add(new BodyPoints(initI, i, piecePoints));
        } else
            BodyPoints.add(new BodyPoints(initI, i, piecePoints));
    }

    private int getPieceNum(int i){
        if(buttonField[i].getIcon() == pieceIcons[0]){
            return 0;
        }else if(buttonField[i].getIcon() == pieceIcons[1]){
            return 1;
        }else if(buttonField[i].getIcon() == pieceIcons[2]){
            return 2;
        }else if(buttonField[i].getIcon() == pieceIcons[3]){
            return 3;
        }else if(buttonField[i].getIcon() == pieceIcons[4]){
            return 4;
        }else if(buttonField[i].getIcon() == pieceIcons[5]){
            return 5;
        }else if(buttonField[i].getIcon() == pieceIcons[6]){
            return 6;
        }else if(buttonField[i].getIcon() == pieceIcons[7]){
            return 7;
        }else if(buttonField[i].getIcon() == pieceIcons[8]){
            return 8;
        }else if(buttonField[i].getIcon() == pieceIcons[9]){
            return 9;
        }else if(buttonField[i].getIcon() == pieceIcons[10]){
            return 10;
        }else if(buttonField[i].getIcon() == pieceIcons[11]){
            return 11;
        }else if(buttonField[i].getIcon() == pieceIcons[12]){
            return 12;
        }else if(buttonField[i].getIcon() == pieceIcons[13]){
            return 13;
        }else if(buttonField[i].getIcon() == pieceIcons[14]){
            return 14;
        }else if(buttonField[i].getIcon() == pieceIcons[15]){
            return 15;
        }else if(buttonField[i].getIcon() == pieceIcons[16]){
            return 16;
        }else if(buttonField[i].getIcon() == pieceIcons[17]){
            return 17;
        }else if(buttonField[i].getIcon() == pieceIcons[18]){
            return 18;
        }else if(buttonField[i].getIcon() == pieceIcons[19]){
            return 19;
        }else if(buttonField[i].getIcon() == pieceIcons[20]){
            return 20;
        /*}else if(i/30 == 0){
            //return 21;
        }else if(i/30 == 19){
            return 22;
        }else if(i%30 == 0){
            return 23;
        }else if(i%30 == 29){
            return 24;

         */
        }

        return -99;
    }

    private void SortBodies(ArrayList<BodyPoints> BodyPoints){
        double xCent;
        double yCent;
        AngleIndex temp;
        ArrayList<Vector2[]> tempList = new ArrayList<>();
        for(int i = 0; i < BodyPoints.size(); i++){
            xCent = 0;
            yCent = 0;
            AngleIndex[] angleList = new AngleIndex[BodyPoints.get(i).bodyPoints.length];
            Vector2[] temp2 = new Vector2[BodyPoints.get(i).bodyPoints.length];
            for(int n = 0; n < BodyPoints.get(i).bodyPoints.length; n++){
                xCent += BodyPoints.get(i).bodyPoints[n].x;
                yCent += BodyPoints.get(i).bodyPoints[n].y;
            }
            xCent = xCent/BodyPoints.get(i).bodyPoints.length;
            yCent = yCent/BodyPoints.get(i).bodyPoints.length;
            //populating angle list
            for(int n = 0; n < angleList.length; n++)
                angleList[n] = new AngleIndex(new Vector2(BodyPoints.get(i).bodyPoints[n], new Vector2(xCent, yCent)).getAngleBetween(new Vector2(new Vector2(xCent, yCent), new Vector2(xCent, yCent + 100))), n);
            //sorting angle list
            for(int n = 0; n < angleList.length; n++){
                for(int q = 0; q < angleList.length; q++){
                    if(angleList[n].getLower(angleList[q]) == angleList[q]){
                        temp = angleList[n];
                        angleList[n] = angleList[q];
                        angleList[q] = temp;
                    }
                }
            }

            //ordering the vectors based off of angle list
            for(int n = 0; n < angleList.length; n++)
                temp2[n] = BodyPoints.get(i).bodyPoints[angleList[n].index];

            BodyPoints.get(i).bodyPoints = temp2;
            //allowing these to be taken care of in garbage collection
            temp = null;
            angleList = null;
            temp2 = null;
        }
    }

    private void writeTrack(String name, ArrayList<BodyPoints> BodyPoints) throws FileNotFoundException, UnsupportedEncodingException {
        String fileName = MainMenu.rootDir + "com/justin/bokus/src/graphics/tracks/";
        PrintWriter writer = new PrintWriter(fileName + name + ".java", "UTF-8");
        writer.println("package graphics.tracks;\n");
        writer.println("import org.dyn4j.dynamics.World;");
        writer.println("import framework.SimulationBody;");
        writer.println("import org.dyn4j.geometry.Geometry;");
        writer.println("import org.dyn4j.geometry.MassType;");
        writer.println("import java.awt.*;");
        writer.println("import org.dyn4j.geometry.Vector2;\n\n");
        writer.println("public class " + name + " {");
        writer.println("    public static void buildWorld(World world, SimulationBody car, SimulationBody car1){");
        for(int i = 0; i < BodyPoints.size(); i++){
            writer.println("        SimulationBody body" + i + " = new SimulationBody(Color.green);");
            writer.println("        body" + i + ".setMass(MassType.INFINITE);");
            writer.println("        body" + i + ".addFixture(Geometry.createPolygon(");
            for (int n = 0; n < BodyPoints.get(i).bodyPoints.length; n++) {
                if(n == BodyPoints.get(i).bodyPoints.length-1){
                    writer.println("new Vector2" + BodyPoints.get(i).bodyPoints[n] + "));");
                    writer.println("        world.addBody(body" + i + ");\n");
                    break;
                }
                if (n == 0)
                    writer.print("        ");
                writer.print("new Vector2" + BodyPoints.get(i).bodyPoints[n] + ", ");
            }
        }
        writer.println("        car.translate(-200, 0);");
        writer.println("        car1.translate(+200, 0);");
        writer.println("    }");
        writer.println("}");
        writer.close();
    }

    private class CustomWindowListener implements WindowListener {
        public void windowOpened(WindowEvent e) {
        }
        public void windowClosing(WindowEvent e) {
            frame.dispose();
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

    private class MouseDrag extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            JComponent c = (JComponent)e.getSource();
            TransferHandler h = c.getTransferHandler();
            h.exportAsDrag(c, e, TransferHandler.COPY);
        }
    }

    private class AngleIndex{
        private double angle;
        private int index;

        private AngleIndex(double inAngle, int inIndex){
            angle = inAngle;
            index = inIndex;
        }

        public void print(){System.out.println(angle + ", " + index);}

        public AngleIndex getGreater(AngleIndex A){
            if(this.angle > A.angle)
                return this;
            else
                    return A;
        }

        private AngleIndex getLower(AngleIndex A){
            if(this.angle < A.angle)
                return this;
            else
                return A;
        }
    }

    private class BodyPoints{
        private int i0;
        private int i1;
        private Vector2[] bodyPoints;

        private BodyPoints(int inI0, int inI1, Vector2[] inBodyPoints){
            i0 = inI0;
            i1 = inI1;
            bodyPoints = inBodyPoints;
        }
        private BodyPoints(){}
    }

    public static void main(String[] args){
        LevelEditor LE = new LevelEditor();
        LE.BuildWindow();
    }
}
