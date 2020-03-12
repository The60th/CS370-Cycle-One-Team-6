package com.justin.bokus.cpu;

import com.justin.bokus.cpu.old.Car;
import com.justin.bokus.cpu.utils.Instruction;
import com.justin.bokus.graphics.Track1;

import java.util.ArrayList;


public class CPU {
    private ArrayList<ArrayList<Instruction>> memory = new ArrayList<>();
    private ArrayList<Car> cars = new ArrayList<>();

    private int[] process_index;
    private int current_process_cycles = 0;
    private int current_process;
    private int total_processes;
    private boolean memory_loaded = false;

    public final static int MAX_CYCLES = 1000;
    public final static int CYCLES_PER_PROCESS = 10;

    public CPU(){ }

    public CPU(ArrayList<ArrayList<Instruction>> incoming_memory, ArrayList<Car> incoming_cars){
        this.memory_loaded = true;
        this.memory = incoming_memory;
        this.total_processes = this.memory.size();
        this.process_index = new int[this.total_processes];

        this.cars = incoming_cars;

        for(int i = 0; i < this.total_processes; i++){
            process_index[i] = 0;
        }

        this.current_process = 0;
    }
    public void runAsyncCycle(){
        if(!this.memory_loaded){
            System.out.println("CPU Fatal Error:> Can't runAsyncCycles without loaded memory.");
            System.exit(-1);
        }
        //Swap the current process
        if(this.current_process_cycles > CYCLES_PER_PROCESS){
            this.current_process +=1;
            current_process_cycles = 0;
            if(this.current_process >= this.total_processes) this.current_process = 0;
        }

        int current_instruction = process_index[this.current_process];

        int x = subRun(this.memory.get(this.current_process).get(current_instruction));

        if (x != -1) {
            process_index[this.current_process] = x;
        }else{
            process_index[this.current_process]++;
        }

        current_process_cycles+=1;
    }

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
       // Car car = this.cars.get(this.current_process);
        switch (instruction.command) {
            case no_op:

                break;
            case add:

                break;
            case sub:

                break;
            case print:
                System.out.println("CPU>" + instruction.param1);
                //car.setXY(car.getCurr_x() + 10, car.getCurr_y()+10);
                break;
            case jmp:
                System.out.println("CPU jmp> " + instruction.param1);
                return Integer.parseInt(instruction.param1);
            case up:
               //System.out.println("up");
               Track1.Forward(parseParam(instruction.param1));

                break;
            case down:
                //System.out.println("down");
                Track1.Reverse(parseParam(instruction.param1));

                break;
            case left:
                System.out.println(instruction.param1);
                Track1.Left(parseParam(instruction.param1));

                break;
            case right:
                Track1.Right(parseParam(instruction.param1));
               //System.out.println("right");
               // car.setCurr_x(car.getCurr_x()+Integer.parseInt(instruction.param1));

                break;
            case oneU:
                //System.out.println("up1");
                Track1.Forward1(parseParam(instruction.param1));

                break;
            case oneD:
                //System.out.println("down1");
                Track1.Reverse1(parseParam(instruction.param1));

                break;
            case oneL:
                //System.out.println("left1");
                Track1.Left1(parseParam(instruction.param1));

                break;
            case oneR:
                Track1.Right1(parseParam(instruction.param1));
                //System.out.println("right1");

                break;
        }
        return -1;
    }

    private boolean parseParam(String string){
        return string.equalsIgnoreCase("true");
    }
}
