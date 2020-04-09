package graphics;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class WorldCreator {
    private static String fileName = System.getProperty("user.dir") + "/com/justin/bokus/src/graphics/";
    public static void WriteWorld(String Name) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(fileName + Name + ".java", "UTF-8");
        writer.println("package graphics;");
        writer.println();
        writer.println("public class " + Name + " {");
        writer.println("    public static void test(){");
        writer.println("        System.out.println(\"it worked\");");
        writer.println("    }");
        writer.println("}");
        writer.close();
    }

    public static void main(String[] args){
        try {
            WriteWorld("test");
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            File test = new File(fileName + "TestFile.java");
            compiler.run(null, null, null, test.getPath());
            File tc = new File(System.getProperty("user.dir") + "/com/justin/bokus/src/");
            ClassLoader loader = new URLClassLoader(new URL[]{tc.toURI().toURL()});
            Class<?> f = loader.loadClass("graphics.TestFile");
            Method t = f.getMethod("test", null);
            t.invoke(t, null);
        }
        catch(Exception e){ System.out.println(e); }
    }
}
