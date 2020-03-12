package com.justin.bokus.graphics;


import com.justin.bokus.cpu.CPU;
import com.justin.bokus.cpu.Parser;
import com.justin.bokus.cpu.old.Car;
import com.justin.bokus.cpu.utils.Instruction;
import com.justin.bokus.framework.*;
import org.dyn4j.collision.narrowphase.Sat;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;


public class Track1 extends SimulationFrame {

    public CPU cpu;
    boolean firstRun = true;


    private SimulationBody car;
    private SimulationBody car1;
    private static AtomicBoolean isLeft = new AtomicBoolean(false);
    private static AtomicBoolean isRight = new AtomicBoolean(false);
    private static AtomicBoolean isForward = new AtomicBoolean(false);
    private static AtomicBoolean isReverse = new AtomicBoolean(false);
    private static AtomicBoolean isLeft1 = new AtomicBoolean(false);
    private static AtomicBoolean isRight1 = new AtomicBoolean(false);
    private static AtomicBoolean isForward1 = new AtomicBoolean(false);
    private static AtomicBoolean isReverse1 = new AtomicBoolean(false);

    public static void Left(boolean applyThrust){
        isLeft.set(applyThrust);
    }

    public static void Right(boolean applyThrust){
        isRight.set(applyThrust);
    }
    public static void Forward(boolean applyThrust){
        isForward.set(applyThrust);
    }
    public static void Reverse(boolean applyThrust){
        isReverse.set(applyThrust);
    }
    public static void Left1(boolean applyThrust){
        isLeft1.set(applyThrust);
    }
    public static void Right1(boolean applyThrust){
        isRight1.set(applyThrust);
    }
    public static void Forward1(boolean applyThrust){
        isForward1.set(applyThrust);
    }
    public static void Reverse1(boolean applyThrust){
        isReverse1.set(applyThrust);
    }

    public Track1(){
        super("Track1", 1);
    }

    @Override
    protected void initializeWorld(){
        this.world.setGravity(world.ZERO_GRAVITY);    //set the gravity to zero because it is top town
        this.world.setNarrowphaseDetector(new Sat());
        //use rectangles with triangles on the end to mimic a concave polygon

        //track infield
        SimulationBody infield = new SimulationBody(Color.green);
        infield.addFixture(Geometry.createPolygon(
                new Vector2(-200, 300), new Vector2(-300, 275), new Vector2(-375, 250),
                new Vector2(-425, 225), new Vector2(-450, 175), new Vector2(-475, 100),
                new Vector2(-500, 0), new Vector2(-475, -100), new Vector2(-450, -175),
                new Vector2(-425, -225), new Vector2(-375, -250), new Vector2(-300, -275),
                new Vector2(-200, -300), new Vector2(200, -300), new Vector2(300, -275),
                new Vector2(375, -250), new Vector2(425, -225), new Vector2(450, -175),
                new Vector2(475, -100), new Vector2(500, 0), new Vector2(475, 100),
                new Vector2(450, 175), new Vector2(425, 225), new Vector2(375, 250),
                new Vector2(300, 275), new Vector2(200, 300)));
        world.addBody(infield);

        //top left outside corner
        SimulationBody corner1 = new SimulationBody(Color.green);
        corner1.addFixture(Geometry.createPolygon(
                new Vector2(-300, 400), new Vector2(-400, 400), new Vector2(-400, 375)));
        corner1.addFixture(Geometry.createPolygon(
                new Vector2(-400, 400), new Vector2(-475, 400), new Vector2(-475, 350), new Vector2(-400, 375)));
        corner1.addFixture(Geometry.createPolygon(
                new Vector2(-475, 400), new Vector2(-525, 400), new Vector2(-525, 325), new Vector2(-475, 350)));
        corner1.addFixture(Geometry.createPolygon(
                new Vector2(-525, 400), new Vector2(-550, 400), new Vector2(-550, 275), new Vector2(-525, 325)));
        corner1.addFixture(Geometry.createPolygon(
                new Vector2(-550, 400), new Vector2(-575, 400), new Vector2(-575, 200), new Vector2(-550, 275)));
        corner1.addFixture(Geometry.createPolygon(
                new Vector2(-575, 400), new Vector2(-600, 400), new Vector2(-600, 100), new Vector2(-575, 200)));
        world.addBody(corner1);

        //bottom left corner
        SimulationBody corner2 = new SimulationBody(Color.green);
        corner2.addFixture(Geometry.createPolygon(
                new Vector2(-600, -100), new Vector2(-600, -400), new Vector2(-575, -400), new Vector2(-575, -200)));
        corner2.addFixture(Geometry.createPolygon(
                new Vector2(-575, -200), new Vector2(-575, -400), new Vector2(-550, -400), new Vector2(-550, -275)));
        corner2.addFixture(Geometry.createPolygon(
                new Vector2(-550, -275), new Vector2(-550, -400), new Vector2(-525, -400), new Vector2(-525, -325)));
        corner2.addFixture(Geometry.createPolygon(
                new Vector2(-525, -325), new Vector2(-525, -400), new Vector2(-475, -400), new Vector2(-475, -350)));
        corner2.addFixture(Geometry.createPolygon(
                new Vector2(-475, -350), new Vector2(-475, -400), new Vector2(-400, -400), new Vector2(-400, -375)));
        corner2.addFixture(Geometry.createPolygon(
                new Vector2(-400, -375), new Vector2(-400, -400), new Vector2(-300, -400)));
        world.addBody(corner2);

        //bottom right corner
        SimulationBody corner3 = new SimulationBody(Color.green);
        corner3.addFixture(Geometry.createPolygon(
                new Vector2(300, -400), new Vector2(400, -400), new Vector2(400, -375)));
        corner3.addFixture(Geometry.createPolygon(
                new Vector2(400, -400), new Vector2(475, -400), new Vector2(475, -350), new Vector2(400, -375)));
        corner3.addFixture(Geometry.createPolygon(
                new Vector2(475, -400), new Vector2(525, -400), new Vector2(525, -325), new Vector2(475, -350)));
        corner3.addFixture(Geometry.createPolygon(
                new Vector2(525, -400), new Vector2(550, -400), new Vector2(550, -275), new Vector2(525, -325)));
        corner3.addFixture(Geometry.createPolygon(
                new Vector2(550, -400), new Vector2(575, -400), new Vector2(575, -200), new Vector2(550, -275)));
        corner3.addFixture(Geometry.createPolygon(
                new Vector2(575, -400), new Vector2(600, -400), new Vector2(600, -100), new Vector2(575, -200)));
        world.addBody(corner3);

        //top right corner
        SimulationBody corner4 = new SimulationBody(Color.green);
        corner4.addFixture(Geometry.createPolygon(
                new Vector2(300, 400), new Vector2(400, 375), new Vector2(400, 400)));
        corner4.addFixture(Geometry.createPolygon(
                new Vector2(475, 400), new Vector2(400, 400), new Vector2(400, 375), new Vector2(475, 350)));
        corner4.addFixture(Geometry.createPolygon(
                new Vector2(525, 400), new Vector2(475, 400), new Vector2(475, 350), new Vector2(525, 325)));
        corner4.addFixture(Geometry.createPolygon(
                new Vector2(550, 400), new Vector2(525, 400), new Vector2(525, 325), new Vector2(550, 275)));
        corner4.addFixture(Geometry.createPolygon(
                new Vector2(575, 400), new Vector2(550, 400), new Vector2(550, 275), new Vector2(575, 200)));
        corner4.addFixture(Geometry.createPolygon(
                new Vector2(600, 400), new Vector2(575, 400), new Vector2(575, 200), new Vector2(600, 100)));
        this.world.addBody(corner4);

        SimulationBody edgeL = new SimulationBody(Color.green);
        edgeL.addFixture(Geometry.createRectangle(5, 250));
        edgeL.translate(-599, 0);
        world.addBody(edgeL);

        car = new SimulationBody(Color.red);
        car.addFixture(Geometry.createRectangle(25, 50));
        car.setMass(MassType.NORMAL);
        car.translate(-575, -25);
        this.world.addBody(car);

        car1 = new SimulationBody(Color.blue);
        car1.addFixture(Geometry.createRectangle(25, 50));
        car1.setMass(MassType.NORMAL);
        car1.translate(-525, -25);
        this.world.addBody(car1);

    }

    @SuppressWarnings("Duplicates")
    @Override
    protected void update(Graphics2D g, double elapsedTime) {
        super.update(g, elapsedTime);
        if(firstRun){
            ArrayList<Instruction> memory = null;
            // write your code here
            Parser paraser = new Parser();
            try {
                memory = paraser.loadToMemory(false, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Debugger.writeDebugFile(paraser.loadToMemory(true));
            ArrayList<ArrayList<Instruction>> master_memory = new ArrayList<>();
            master_memory.add(memory);

            ArrayList<Car> t = new ArrayList<>();
            this.cpu = new CPU(master_memory,t );
            this.firstRun = false;
        }

        this.cpu.runAsyncCycle();
        //Left(true);
        //Right(true);
        final double force = 5000000 * elapsedTime;

        final Vector2 rotation = new Vector2(car.getTransform().getRotationAngle() + Math.PI * .5);
        final Vector2 center = car.getWorldCenter();

        final Vector2 rotation1 = new Vector2(car1.getTransform().getRotationAngle() + Math.PI * .5);
        final Vector2 center1 = car1.getWorldCenter();

        // apply thrust
        if (isForward.get()) {
            Vector2 f = rotation.product(force);
            car.applyForce(f);
        }
        if (isReverse.get()) {
            Vector2 f = rotation.product(-force);
            car.applyForce(f);
        }
        if (isLeft.get()) {
            Vector2 f = rotation.product(force).left();
            Vector2 p = center.sum(rotation.product(-99));
            // apply a force to the back going left
            car.applyForce(f, p);
        }
        if (isRight.get()) {
            Vector2 f = rotation.product(force).right();
            Vector2 p = center.sum(rotation.product(-99));
            // apply a force to the back going right
            car.applyForce(f, p);
        }
        if (isForward1.get()) {
            Vector2 f = rotation1.product(force);
            car1.applyForce(f);
        }
        if (isReverse1.get()) {
            Vector2 f = rotation1.product(-force);
            car1.applyForce(f);
        }
        if (isLeft1.get()) {
            Vector2 f = rotation1.product(force).left();
            Vector2 p = center1.sum(rotation1.product(-99));
            // apply a force to the back going left
            car1.applyForce(f, p);
        }
        if (isRight1.get()) {
            Vector2 f = rotation1.product(force).right();
            Vector2 p = center1.sum(rotation1.product(-99));
            // apply a force to the back going right
            car1.applyForce(f, p);
        }
    }

    public static void main(String[] args)  {
        Track1 display = new Track1();



        display.run();
    }
}
