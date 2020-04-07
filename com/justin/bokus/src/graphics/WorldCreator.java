package com.justin.bokus.graphics;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class WorldCreator {
    public static void WriteWorld(String Name) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("C:\\Users\\Kiam Kaiser\\Documents\\Computer Science\\CS370\\basic_vm\\src\\com\\justin\\bokus\\graphics\\TestWorld.java", "UTF-8");
        writer.println("package com.justin.bokus.graphics;");
        writer.println();
        writer.println("public class TestWorld {");
        writer.println("    public static void test(){");
        writer.println("        System.out.println('t');");
        writer.println("    }");
        writer.println("}");
        writer.close();
    }

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException{
        //WriteWorld("tetst");
        //TestWorld.test();
    }
}
