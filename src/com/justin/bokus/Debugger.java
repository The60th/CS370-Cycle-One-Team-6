package com.justin.bokus;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Debugger {
    public Debugger(){}
    /**
     * Demo debug file:
     * Examples taken from w3schools for basic I/O interactions:
     * https://www.w3schools.com/java/java_files_create.asp
     *
     * @param memory
     * @return
     */

    public static boolean writeDebugFile(ArrayList<Instruction> memory){
        try {
            File myObj = new File("debug.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter("debug.txt");
            myWriter.write("#Demo debug file showing info how the cpu is viewing it. " + "\n");
            myWriter.write("#The commands in the program start after: Line>1: command" + "\n");
            for(int i =0; i < memory.size(); i++){

              myWriter.write("Line>" +i + ": " + memory.get(i).toString());
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return true;
    }
}
