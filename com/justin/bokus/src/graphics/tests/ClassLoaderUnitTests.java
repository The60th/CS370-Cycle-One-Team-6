package graphics.tests;

import framework.SimulationBody;
import org.dyn4j.dynamics.World;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;

public class ClassLoaderUnitTests {
    static int MATH_TEST_RESULT = -50;
    static String STRING_TEST_RESULT = "Hello World";
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class[] param = new Class[0];
        //Below copiles the track to make sure it is up to date then builds the track
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        File javaFile = new File(System.getProperty("user.dir") + "/com/justin/bokus/src/graphics/tests/worlds/MathTest" + ".java"); //finding the file to be compiled
        Iterable<? extends JavaFileObject> compileLocation = fileManager.getJavaFileObjects(javaFile);  //loads the file to be compiled
        Iterable<String> options = new ArrayList<>(Arrays.asList("-d", System.getProperty("user.dir") + "/target/classes/")); //location of the desired .class package
        compiler.getTask(null, fileManager, null, options, null, compileLocation).call();   //compiling the file
        File compiledFile = new File(System.getProperty("user.dir") + "/target/classes/");  //loading the compiled track file
        ClassLoader loader = new URLClassLoader(new URL[]{compiledFile.toURI().toURL()});   //finding the track class
        Class<?> f = loader.loadClass("graphics.tests.worlds." + "MathTest");  //loading the track class
        Method t = f.getMethod("getData", param);    //loading the method that builds the track
        Integer intResult = (Integer) t.invoke(t);  //running the method and actually building the track
        fileManager.close();

        if(intResult == MATH_TEST_RESULT) System.out.println("Math test passed.");
        else System.out.println("Math tested failed: Returned: " + intResult + " Expected: " + MATH_TEST_RESULT);

        param = new Class[0];
        //Below copiles the track to make sure it is up to date then builds the track
         compiler = ToolProvider.getSystemJavaCompiler();
         fileManager = compiler.getStandardFileManager(null, null, null);
         javaFile = new File(System.getProperty("user.dir") + "/com/justin/bokus/src/graphics/tests/worlds/StringTest" + ".java"); //finding the file to be compiled
        compileLocation = fileManager.getJavaFileObjects(javaFile);  //loads the file to be compiled
         options = new ArrayList<>(Arrays.asList("-d", System.getProperty("user.dir") + "/target/classes/")); //location of the desired .class package
        compiler.getTask(null, fileManager, null, options, null, compileLocation).call();   //compiling the file
         compiledFile = new File(System.getProperty("user.dir") + "/target/classes/");  //loading the compiled track file
         loader = new URLClassLoader(new URL[]{compiledFile.toURI().toURL()});   //finding the track class
        f = loader.loadClass("graphics.tests.worlds." + "StringTest");  //loading the track class
         t = f.getMethod("getData", param);    //loading the method that builds the track
        String stringResult = (String) t.invoke(t);  //running the method and actually building the track
        fileManager.close();

        if(stringResult == STRING_TEST_RESULT) System.out.println("String test passed.");
        else System.out.println("Math tested failed: Returned: " + stringResult + " Expected: " + STRING_TEST_RESULT);
    }

}
