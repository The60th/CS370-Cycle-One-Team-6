package cpu;


import cpu.old.Car;
import cpu.utils.CPUException;
import cpu.utils.Instruction;
import graphics.GameWorld;
import graphics.Track1;

import java.util.ArrayList;

import static cpu.utils.CpuCommands.*;


public class CPU {
    private ArrayList<ArrayList<Instruction>> memory = new ArrayList<>();
    private ArrayList<Car> cars = new ArrayList<>();

    private int[] process_index;
    private int current_process_cycles = 0;
    private int current_process;
    private int total_processes;
    private boolean memory_loaded = false;
    private int currNoOp = 0;
    private int targetNoOp = 0;
    private int noOpReturnCode = -2;
    private boolean noOpInProgress = false;
    private boolean fileEnd = false;
    public final static int MAX_CYCLES = 1000;
    public final static int CYCLES_PER_PROCESS = 10;

    private boolean endOfFile = false;

    public int[] registers = new int[]{0,0,0,0,0,0,0,0};

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
    public boolean runAsyncCycle(){
        if(!this.memory_loaded){
            System.out.println("CPU Fatal Error:> Can't runAsyncCycles without loaded memory.");
            System.exit(-1);
        }
        if(this.fileEnd){
            return false;
        }
        //Swap the current process
        if(this.current_process_cycles > CYCLES_PER_PROCESS){
            this.current_process +=1;
            current_process_cycles = 0;
            if(this.current_process >= this.total_processes) this.current_process = 0;
        }

        int current_instruction = process_index[this.current_process];
        if(current_instruction >= this.memory.get(this.current_process).size()){
            try {
                endOfFile = true;
                throw new CPUException("Exceeding memory length.");
            } catch (CPUException e) {
                e.printStackTrace();
            }
        }
        if(endOfFile) return false;
        int x = subRun(this.memory.get(this.current_process).get(current_instruction));
        if(x == noOpReturnCode){
            //Don't increment the process running a null op
        }
        else if (x != -1) {
            process_index[this.current_process] = x;
        }else{
            process_index[this.current_process]++;
        }

        current_process_cycles+=1;
        return true;
    }

    public void run(ArrayList<Instruction> instructions) {
        int cycle_count = 0;
        for (int i = 0; i < instructions.size(); cycle_count++) {

            int x = subRun(instructions.get(i));
            if (x != -1 || x == noOpReturnCode) {
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
        int target;
        int incoming;
       // Car car = this.cars.get(this.current_process);
        switch (instruction.command) {
            case no_op:
              // System.out.println("No_op");
                if(!this.noOpInProgress){
                    this.currNoOp = 1;
                    this.targetNoOp = Integer.parseInt(instruction.paramList.get(0));
                    this.noOpInProgress = true;
                }
                if(this.currNoOp < this.targetNoOp){
                    this.currNoOp++;
                    return noOpReturnCode;
                }else{
                    this.noOpInProgress = false;
                }
                break;
            case add:
                target = Integer.parseInt(instruction.paramList.get(0));
                incoming = Integer.parseInt(instruction.paramList.get(1));
                registers[target] = registers[target] + incoming;
                break;
            case sub:
                target = Integer.parseInt(instruction.paramList.get(0));
                incoming = Integer.parseInt(instruction.paramList.get(1));
                registers[target] = registers[target] - incoming;
                break;
            case read:
                //What should this do?
                //Print a register
                target = Integer.parseInt(instruction.paramList.get(0));
                System.out.println(registers[target]);
                break;
            case print:
                System.out.println("CPU>" + instruction.paramList.get(0));
                //car.setXY(car.getCurr_x() + 10, car.getCurr_y()+10);
                break;
            case jmp:
                //System.out.println("CPU jmp> " + instruction.param1);
                return Integer.parseInt(instruction.paramList.get(0));
            case up:
                GameWorld.Forward(parseParam(instruction.paramList.get(0)));

                break;
            case down:
                GameWorld.Reverse(parseParam(instruction.paramList.get(0)));

                break;
            case left:
                System.out.println(instruction.paramList.get(0));
                GameWorld.Left(parseParam(instruction.paramList.get(0)));

                break;
            case right:
                GameWorld.Right(parseParam(instruction.paramList.get(0)));
               // car.setCurr_x(car.getCurr_x()+Integer.parseInt(instruction.param1));

                break;
            case oneU:
                //System.out.println("up1");
                GameWorld.Forward1(parseParam(instruction.paramList.get(0)));

                break;
            case oneD:
                //System.out.println("down1");
                GameWorld.Reverse1(parseParam(instruction.paramList.get(0)));

                break;
            case oneL:
                //System.out.println("left1");
                GameWorld.Left1(parseParam(instruction.paramList.get(0)));

                break;
            case oneR:
                GameWorld.Right1(parseParam(instruction.paramList.get(0)));
                //System.out.println("right1");

                break;
        }
        return -1;
    }

    private boolean parseParam(String string){
        return string.equalsIgnoreCase("true");
    }
}
