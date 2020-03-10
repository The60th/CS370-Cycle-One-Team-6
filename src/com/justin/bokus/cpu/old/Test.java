package com.justin.bokus.cpu.old;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
@Deprecated
public class Test extends JPanel implements ActionListener {

        Timer tm = new Timer(100, this);
        int x = 125, velX = 1;
        int y = 150;
        static int sum = 0, sum2 = 0;
        private static Car car;
        static CPULoader cpuLoader;
        boolean loaded = false;

        File file = new File("res/images/test_v2.png");
        BufferedImage image;

        {
            try {
                image = ImageIO.read(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            ImageIcon i = new ImageIcon("res/images/test_v2.png");
            i.paintIcon(this, g, 0, 0);

            if(!loaded) {
                car = new Car(x, y);
                ArrayList<Car> cars = new ArrayList<>();
                cars.add(car);
                cpuLoader = new CPULoader(cars);

                loaded = true;
            }
            g.setColor(Color.BLUE);
            g.fillRect(car.getCurr_x(), car.getCurr_y(), 50, 50);
            tm.start();

        }

        public void actionPerformed(ActionEvent actionEvent) {
            int clr = image.getRGB(x + 5, y);
            int  red   = (clr & 0x00ff0000) >> 16;
            int  green = (clr & 0x0000ff00) >> 8;
            int  blue  =  clr & 0x000000ff;

            sum = red + green + blue;

            int clr2 = image.getRGB(x - 5, y);
            int  red2   = (clr2 & 0x00ff0000) >> 16;
            int  green2 = (clr2 & 0x0000ff00) >> 8;
            int  blue2  =  clr2 & 0x000000ff;

            sum2 = red2 + green2 + blue2;

            if(sum == 0 || sum2 == 0)
                velX = -velX;
            x += velX;

            sum = 0;
            sum2 = 0;
            int set_x = 250, set_y = 250 , width = 10, height = 10;
            //repaint(250,250,10,10);
            x = 250;
            y = 250;
            //car.setCurr_x(x);
            //car.setCurr_y(y);

            cpuLoader.getLoaded_cpu().runAsyncCycle();
            //car.setCurr_y(0);
            repaint();

        }

        public static void main(String[] args)
        {
            Test t = new Test();
            JFrame jf = new JFrame();
            jf.setTitle("YO");
            jf.setSize(512,512);

            jf.setVisible(true);
            jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jf.add(t);

            System.out.println(sum);

        }
}

