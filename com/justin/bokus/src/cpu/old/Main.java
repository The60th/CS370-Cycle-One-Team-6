package cpu.old;

import cpu.CPU;
import cpu.utils.Instruction;
import cpu.Parser;

import java.io.IOException;
import java.util.ArrayList;
@Deprecated
public class Main {
    public static cpu.CPU CPU = new CPU();
    private static CPU loaded_cpu;
    public static Debugger debugger = new Debugger();
    @Deprecated
    public static void main(String[] args) {
        ArrayList<Instruction> memory;
	// write your code here
        Parser paraser = new Parser();

        try {
//            paraser.parseFile();
             memory = paraser.loadToMemory(false, false);
             //Debugger.writeDebugFile(paraser.loadToMemory(true));
             ArrayList<ArrayList<Instruction>> master_memory = new ArrayList<>();
             master_memory.add(memory);
             //loaded_cpu = new CPU(master_memory);
             loaded_cpu.runAsyncCycle();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Deprecated
    public static CPU getCPU(){

        return CPU;
    }
    @Deprecated
    public static Debugger getDebugger(){return debugger;}
}
