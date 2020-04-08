package cpu.utils;

import java.util.ArrayList;

public class Instruction {
    public CpuCommands command;
    public String param1;
    public String param2;
    public JumpLabel jumpLabel;
    public ArrayList<String> paramList = new ArrayList<>();

    public Instruction(){

    }
    public Instruction(CpuCommands command){
        this.command = command;
    }

    public Instruction(CpuCommands command, String param1){
        this.command = command;
        this.param1 = param1;
    }
    public Instruction(CpuCommands command, ArrayList<String> paramList, JumpLabel jumpLabel){
        this.command = command;
        this.paramList = paramList;
        this.jumpLabel = jumpLabel;
    }
    public Instruction(CpuCommands command, String param1, String param2){
        this.command = command;
        this.param1 = param1;
        this.param2 = param2;
    }
    public Instruction(CpuCommands command, ArrayList<String> paramList){
        this.command = command;
        this.paramList = paramList;
    }

    @Override
    public String toString(){
        return new String(command.toString() + " " + param1 + "\n");
    }

}
