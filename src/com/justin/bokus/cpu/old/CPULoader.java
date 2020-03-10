package com.justin.bokus.cpu.old;

import com.justin.bokus.cpu.CPU;
import com.justin.bokus.cpu.Parser;
import com.justin.bokus.cpu.utils.Instruction;

import java.io.IOException;
import java.util.ArrayList;

public class CPULoader {
    public static com.justin.bokus.cpu.CPU CPU = new CPU();
    private CPU loaded_cpu;
    public static Debugger debugger = new Debugger();
    public CPULoader(ArrayList<Car> cars){
        ArrayList<Instruction> memory;
        // write your code here
        Parser paraser = new Parser();

        try {
//            paraser.parseFile();
            memory = paraser.loadToMemory(false, false);
            //Debugger.writeDebugFile(paraser.loadToMemory(true));
            ArrayList<ArrayList<Instruction>> master_memory = new ArrayList<>();
            master_memory.add(memory);


            ArrayList<Car> master_cars = new ArrayList<>();
            master_cars = cars;
            loaded_cpu = new CPU(master_memory,master_cars);
            //loaded_cpu.runAsyncCycle();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public  CPU getLoaded_cpu(){return loaded_cpu;}
    public static CPU getCPU(){
        return CPU;
    }
    public static Debugger getDebugger(){return debugger;}
}
