package cpu;


import cpu.old.CPULoader;
import cpu.utils.CpuCommands;
import cpu.utils.Instruction;
import cpu.utils.JumpLabel;

import java.io.*;
import java.util.ArrayList;

public class Parser {
    public final String fileName = "com/justin/bokus/res/text/demo.txt";
    public final String fileName2 = "C:\\Users\\Justi\\Desktop\\Test_V2_3\\Test_V2\\src\\demo2.txt";

    public final static char LABEL_SYMBOL = '!';
    public final static char COMMENT_SYMBOL = '#';
    public int no_op_counter = 0;

    //Load a file into a "memory" arraylist to be ran later.
    public ArrayList<Instruction> loadToMemory(boolean dispatch_cpu_command, boolean secondfIle) throws IOException {
        ArrayList<Instruction> memory = new ArrayList<>();
        ArrayList<JumpLabel> jumps = new ArrayList<>();

        File file;
        if (!secondfIle) {
            file = new File(fileName);
        } else {
            file = new File(fileName2);
        }

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        StringBuffer sb = new StringBuffer();
        String line;
        int index = 0;
        while ((line = br.readLine()) != null) {
            Instruction instruction = ParseLine(line, index, jumps);
            if (instruction != null) {
                memory.add(instruction);

            }
            index++;
        }

        fr.close();

        //Demo check to run the commands.
        //@Deprecated
        //if(dispatch_cpu_command) Main.getCPU().run(memory);
        if (dispatch_cpu_command) CPULoader.getCPU().run(memory);

        return memory;
    }

    private String parseToMemory(String string) {
        //if()
        return "test";
    }

    //Does not support jumps or labels.
    @Deprecated
    public void parseFile() throws IOException {

        File file = new File(fileName);

        FileReader fr = new FileReader(file);   //reads the file
        BufferedReader br = new BufferedReader(fr);  //creates a buffering character input stream
        StringBuffer sb = new StringBuffer();    //constructs a string buffer with no characters
        String line;

        while ((line = br.readLine()) != null) {
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


    private Instruction ParseLine(String line, int index, ArrayList<JumpLabel> jumps) {
        if (line.equals("")) return null;
        char char0 = line.charAt(0);
        if (!(Character.isAlphabetic(char0)) && (char0 != LABEL_SYMBOL) && char0 != COMMENT_SYMBOL) {
            System.out.println("Invalid command exiting.");
            System.exit(0);
        }

        String command;
        if (char0 == COMMENT_SYMBOL) return null;

        try {
            command = line.substring(0, line.indexOf(" "));
        } catch (StringIndexOutOfBoundsException e) {
            // System.out.println("Command out of bounds.");
            command = line;
        }
        CpuCommands cpu_command = parseCommand(command);
        if (cpu_command == CpuCommands.jmp) {
            return instructionBuilder(cpu_command, line, index, true, jumps);
        }
        //System.out.println("Command: " + command);


        return instructionBuilder(cpu_command, line, index, false, jumps);
    }


    public Instruction instructionBuilder(CpuCommands command, String line, int index, boolean isJump, ArrayList<JumpLabel> jumps) {
        //System.out.println("Line: " + line);
        String params = line.substring(line.indexOf(command.toString()) + command.toString().length(), line.length());
        if (params.length() < 1) {
            System.out.println("Short");
        }
        // System.out.println("Params: " +  params);

        String param = params.replaceAll("\\s+", "");
        if (isJump) {
            jumps.add(new JumpLabel(index, Integer.parseInt(param)));
            return new Instruction(command, param, new JumpLabel(index, Integer.parseInt(param)));
        }

        return new Instruction(command, param);
    }

    public CpuCommands parseCommand(String command) {
        CpuCommands[] possibleValues = CpuCommands.add.getDeclaringClass().getEnumConstants();
        for (CpuCommands commands : possibleValues) {
            if (command.toString().toLowerCase().contains(commands.toString().toLowerCase())) {
//                System.out.println("Command2: " + command);
                //              System.out.println("Commands2: " + commands);
                return commands;
            }
        }
        return null;
    }

}
