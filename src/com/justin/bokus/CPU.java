package com.justin.bokus;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CPU {
    public final static int MAX_CYCLES = 100;
    public void run(ArrayList<Instruction> instructions) {
        int cycle_count = 0;
        for (int i = 0; i < instructions.size(); cycle_count++) {

            int x = subRun(instructions.get(i));
            if (x != -1) {
                i = x;
            }else{
                i++;
            }


            if(cycle_count > MAX_CYCLES){
                System.out.println("System running too long: System Hung?");
                break;
            }
        }
    }

    private int subRun(Instruction instruction) {
        return runCommand(instruction);
    }

    public void run(Instruction instruction) {
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
