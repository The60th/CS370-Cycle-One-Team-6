package com.justin.bokus;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CPU {

    public void run(ArrayList<Instruction> instructions){

        for (int i = 0; i < instructions.size(); i++) {

            int x = subRun(instructions.get(i));
            if(x != -1) i = x;

        }
    }
    private int subRun(Instruction instruction){
        return runCommand(instruction);
    }
    public void run(Instruction instruction){
        runCommand(instruction);
    }

    //This method will return the desired line we want to run.
    //-1 means do not change what line we would run next.
    private int runCommand(Instruction instruction) {
        switch (instruction.command) {
            case no_op:

                break;
            case add:

                break;
            case sub:

                break;
            case print:
                System.out.println("CPU>" + instruction.param1);
                break;
            case jmp:
                System.out.println("CPU jmp> " + instruction.param1);
                return Integer.parseInt(instruction.param1);
        }
        return -1;
    }
}
