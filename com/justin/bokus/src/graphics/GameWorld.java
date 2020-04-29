package graphics;

import cpu.CPU;
import cpu.Parser;
import cpu.utils.Instruction;
import framework.SimulationBody;
import framework.SimulationFrame;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import menu.Menu;

@SuppressWarnings("Duplicates")
public class GameWorld extends SimulationFrame {
    /*private static String rootDir;{
        try {
            String fileName = new File(GameWorld.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
            fileName = fileName.substring(0, fileName.length() - 39);
            rootDir = new URLDecoder().decode(fileName, "UTF-8");
            System.out.println(Menu.rootDir + " vs " + rootDir);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (java.net.URISyntaxException e) {
            e.printStackTrace();
        }
    }

     */
    public CPU cpu;
    private boolean firstRun = true;
    private SimulationBody car;
    private SimulationBody car1;
    private static String fileName;
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
    public static void setFileName(String inFileName){fileName = inFileName;}
    private GameWorld(){
        super("GameWorld", 1);
    }
    private void LoadWorld(String fileName, World world, SimulationBody car, SimulationBody car1){
        try {
            Class[] param = new Class[3];
            param[0] = World.class;
            param[1] = SimulationBody.class;
            param[2] = SimulationBody.class;

            //Below compiles the track to make sure it is up to date then builds the track
            com.sun.tools.javac.api.JavacTool compiler = com.sun.tools.javac.api.JavacTool.create();
            StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
            //finding the file to be compiled
            File javaFile = new File(Menu.rootDir + "com/justin/bokus/src/graphics/tracks/" + fileName + ".java");
            //loads the file to be compiled
            Iterable<? extends JavaFileObject> compileLocation = fileManager.getJavaFileObjects(javaFile);
            //location of the desired .class package
            Iterable<String> options = new ArrayList<>(Arrays.asList("-d", Menu.rootDir + "/target/classes/"));
            //compiling the file
            compiler.getTask(null, fileManager, null, options, null, compileLocation).call();
            //loading the compiled track file
            File compiledFile = new File(Menu.rootDir + "/target/classes/");
            //finding the track class
            ClassLoader loader = new URLClassLoader(new URL[]{compiledFile.toURI().toURL()});
            //loading the track class
            Class<?> f = loader.loadClass("graphics.tracks." + fileName);
            //loading the method that builds the track
            Method t = f.getMethod("buildWorld", param);
            //running the method and actually building the track
            t.invoke(t, world, car, car1);
            fileManager.close();
        }
        catch(java.lang.ClassNotFoundException e){
            System.out.println(e);
            System.out.println("Is the image of the track in res named the SAME as the java file in tracks?");
        }
        catch(java.lang.NoSuchMethodException e){
            System.out.println(e);
            System.out.println("The chosen class does not have the required method named \"buildWorld\"");
        }
        catch(Exception e){
            System.out.println(e);
            System.out.println("is there an error in the track creation code?");
        }
    }

    @Override
    protected void initializeWorld(){
        this.world.setGravity(world.ZERO_GRAVITY);    //set the gravity to zero because it is top town

        car = new SimulationBody(Color.red);
        car.addFixture(Geometry.createRectangle(25, 50));
        car.setMass(MassType.NORMAL);
        //translate located in world file that tells where the starting postion of the car is
        this.world.addBody(car);

        car1 = new SimulationBody(Color.blue);
        car1.addFixture(Geometry.createRectangle(25, 50));
        car1.setMass(MassType.NORMAL);
        //translate located in world file that tells where the starting position of the car is
        this.world.addBody(car1);

        LoadWorld(fileName, this.world, car, car1); //building the track found in Track1.java
    }

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

            this.cpu = new CPU(master_memory );
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
