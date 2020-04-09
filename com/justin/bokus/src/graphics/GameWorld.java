package graphics;


import cpu.CPU;
import cpu.Parser;
import cpu.old.Car;
import cpu.utils.Instruction;
import framework.SimulationBody;
import framework.SimulationFrame;
import org.dyn4j.collision.narrowphase.Sat;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

@SuppressWarnings("ALL")
public class GameWorld extends SimulationFrame {

    public CPU cpu;
    boolean firstRun = true;


    public SimulationBody car;
    public SimulationBody car1;
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

    public GameWorld(){
        super("GameWorld", 1);
    }
    public void LoadWorld(String fileName, World world, SimulationBody car, SimulationBody car1){
        try {
            Class[] param = new Class[3];
            param[0] = World.class;
            param[1] = SimulationBody.class;
            param[2] = SimulationBody.class;
            //JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            File test = new File(System.getProperty("user.dir") + "/com/justin/bokus/src/graphics/" + fileName + ".java"); //this is working dir + package info
            //compiler.run(null, null, null, test.getPath());
            File tc = new File(System.getProperty("user.dir") + "/com/justin/bokus/src/");
            ClassLoader loader = new URLClassLoader(new URL[]{tc.toURI().toURL()});
            Class<?> f = loader.loadClass("graphics." + fileName);
            Method t = f.getMethod("buildWorld", param);
            t.invoke(t, world, car, car1);
        }
        catch(Exception e){ System.out.println(e); }
    }

    @Override
    protected void initializeWorld(){
        this.world.setGravity(world.ZERO_GRAVITY);    //set the gravity to zero because it is top town
        this.world.setNarrowphaseDetector(new Sat());

        car = new SimulationBody(Color.red);
        car.addFixture(Geometry.createRectangle(25, 50));
        car.setMass(MassType.NORMAL);
        //translate located in world file that tells where the starting postion of the car is
        world.addBody(car);

        car1 = new SimulationBody(Color.blue);
        car1.addFixture(Geometry.createRectangle(25, 50));
        car1.setMass(MassType.NORMAL);
        //translate located in world file that tells where the starting position of the car is
        this.world.addBody(car1);


        LoadWorld("Track1", this.world, car, car1);
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
        GameWorld display = new GameWorld();

        display.run();
    }
}
