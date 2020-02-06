package com.justin.bokus;

import java.io.*;
import java.util.ArrayList;

public class Parser {
    //Desktop
    //public final String fileName = "C:\\Users\\Justi\\IdeaProjects\\CS370\\basic_vm\\src\\com\\justin\\bokus\\demo.txt";
    //Laptop
    public final String fileName = "C:\\Users\\Justi\\IdeaProjects\\basic_vm\\src\\com\\justin\\bokus\\demo.txt";
    public final static  char LABEL_SYMBOL = '!';
    public final static char COMMENT_SYMBOL = '#';


    //Load a file into a "memory" arraylist to be ran later.
    public ArrayList<Instruction> loadToMemory() throws  IOException{
        ArrayList<Instruction> memory = new ArrayList<>();
        ArrayList<JumpLabel> jumps = new ArrayList<>();

        File file = new File(fileName);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        StringBuffer sb = new StringBuffer();
        String line;
        int index = 0;
        while ((line = br.readLine()) != null){
            Instruction instruction = ParseLine(line,index,jumps);
            if(instruction != null) memory.add(instruction);
            index++;
        }

        fr.close();

        //Demo check to run the commands.
        Main.getCPU().run(memory);

        return memory;
    }

    public String parseLabels(String line, int index){

        return "null";
    }

    private String parseToMemory(String string){
        //if()
        return "test";
    }
    //Does not support jumps or labels.
    @Deprecated
    public void parseFile() throws IOException {

        File file = new File(fileName);

        FileReader fr=new FileReader(file);   //reads the file
        BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream
        StringBuffer sb=new StringBuffer();    //constructs a string buffer with no characters
        String line;

        while((line=br.readLine())!=null)
        {
            //System.out.println(line);

            //Uncomment to fix.
            //Instruction instruction = ParseLine(line);
            //Uncomment to fix.
            //Main.getCPU().run(instruction);


            //sb.append(line);      //appends line to string buffer
            //sb.append("\n");     //line feed

        }

        fr.close();

        //System.out.println("Contents of File: ");
        //System.out.println(sb.toString());   //returns a string that textually represents the object
    }


    private Instruction ParseLine(String line, int index ,ArrayList<JumpLabel> jumps){
        if(line.equals("")) return null;
        char char0 = line.charAt(0);
        if(!(Character.isAlphabetic(char0)) && (char0 != LABEL_SYMBOL) && char0 != COMMENT_SYMBOL){
            System.out.println("Invalid command exiting.");
            System.exit(0);
        }

        String command;
        if(char0 == COMMENT_SYMBOL) return null;

        try {
            command = line.substring(0, line.indexOf(" "));
        }catch(StringIndexOutOfBoundsException e){
           // System.out.println("Command out of bounds.");
            command = line;
        }
        cpu_commands cpu_command = parseCommand(command);
        if(cpu_command == cpu_commands.jmp){
            return instructionBuilder(cpu_command,line,index,true,jumps);
        }
        //System.out.println("Command: " + command);


        return instructionBuilder(cpu_command, line, index, false,jumps);
    }

    private String[] getCommands(){
        return new String[]{"no_op","print","add","sub","jmp"};
    }

    public Instruction instructionBuilder(cpu_commands command, String line, int index, boolean isJump, ArrayList<JumpLabel> jumps){
        //System.out.println("Line: " + line);
        String params = line.substring(line.indexOf(command.toString())+command.toString().length(), line.length() );
        if(params.length() < 1){
            System.out.println("Short");
        }
       // System.out.println("Params: " +  params);

        String param =  params.replaceAll("\\s+","");
        if(isJump){
            jumps.add(new JumpLabel(index,Integer.parseInt(param)));
            return new Instruction(command,param,new JumpLabel(index, Integer.parseInt(param)));
        }

        return new Instruction(command, param);
    }

    public cpu_commands parseCommand(String command){
        cpu_commands[] possibleValues = cpu_commands.add.getDeclaringClass().getEnumConstants();
        for (cpu_commands commands: possibleValues) {
            if(command.toString().toLowerCase().contains(commands.toString().toLowerCase())) {
//                System.out.println("Command2: " + command);
  //              System.out.println("Commands2: " + commands);
                return commands;
            }
        }
        return null;
    }

}
