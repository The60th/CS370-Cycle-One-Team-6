package cpu.tests;

import cpu.CPU;
import cpu.Parser;
import cpu.old.Car;
import cpu.utils.Instruction;
import menu.Menu;

import java.io.IOException;
import java.util.ArrayList;

public class CPUMathTests {
    public static void main(String[] args)
    {
        CPU cpu;
        ArrayList<Instruction> memory = null;
        // write your code here
        Parser paraser = new Parser();

        try {
            memory = paraser.loadToMemory(false, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Debugger.writeDebugFile(paraser.loadToMemory(true));
        ArrayList<ArrayList<Instruction>> master_memory = new ArrayList<>();
        master_memory.add(memory);

        cpu = new CPU(master_memory );

        int registerZeroValue = 0;
        int registerOneValue = 100;
        int registerTwoValue = 200;
        int registerThreeValue = -100;
        int registerFourValue = 500;
        boolean control = true;
        while (control) {
            control = cpu.runAsyncCycle();
        }
        boolean result = cpu.registers[0] == registerZeroValue;
        System.out.println("RegisterZeroTest: " + result);
        result = cpu.registers[1] == registerOneValue;
        System.out.println("RegisterOneTest: " + result);
        result = cpu.registers[2] == registerTwoValue;
        System.out.println("RegisterTwoTest: " + result);
        result = cpu.registers[3] == registerThreeValue;
        System.out.println("RegisterThreeTest: " + result);
        result = cpu.registers[4] == registerFourValue;
        System.out.println("RegisterFourTest: " + result);

    }



}
