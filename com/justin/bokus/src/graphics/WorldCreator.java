package graphics;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;


public class WorldCreator {
    private static String fileName = System.getProperty("user.dir") + "/com/justin/bokus/src/graphics/";
    private static void WriteWorld(String Name) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(fileName + Name + ".java", "UTF-8");
        writer.println("package graphics;");
        writer.println();
        writer.println("public class " + Name + " {");
        writer.println("    public static void test(){");
        writer.println("        System.out.println(\"it works\");");
        writer.println("    }");
        writer.println("}");
        writer.close();
    }

    @SuppressWarnings("Duplicates")
    public static void main(String[] args){
        try {
            WriteWorld("TestFile");

            //Below copiles the track to make sure it is up to date then builds the track
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
            File javaFile = new File(fileName + "TestFile.java"); //finding the file to be compiled
            Iterable<? extends JavaFileObject> compileLocation = fileManager.getJavaFileObjects(javaFile);  //loads the file to be compiled
            Iterable<String> options = new ArrayList<String>(Arrays.asList("-d", System.getProperty("user.dir") + "/target/classes/")); //desired location of the new .class file
            compiler.getTask(null, fileManager, null, options, null, compileLocation).call();   //compiling the file
            File compiledFile = new File(System.getProperty("user.dir") + "/target/classes/");  //loading the compiled track file
            ClassLoader loader = new URLClassLoader(new URL[]{compiledFile.toURI().toURL()});   //finding the track class
            Class<?> f = loader.loadClass("graphics.TestFile");  //loading the track class
            Method t = f.getMethod("test");    //loading the method that builds the track
            t.invoke(t);  //running the method and actually building the track
            fileManager.close();
        }
        catch(Exception e){ System.out.println(e); }
    }
}
