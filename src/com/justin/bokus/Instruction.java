package com.justin.bokus;

public class Instruction {
    cpu_commands command;
    String param1;
    String param2;
    JumpLabel jumpLabel;

    public Instruction(){

    }
    public Instruction(cpu_commands command){
        this.command = command;
    }

    public Instruction(cpu_commands command, String param1){
        this.command = command;
        this.param1 = param1;
    }
    public Instruction(cpu_commands command, String param1, JumpLabel jumpLabel){
        this.command = command;
        this.param1 = param1;
        this.jumpLabel = jumpLabel;
    }
    public Instruction(cpu_commands command, String param1, String param2){
        this.command = command;
        this.param1 = param1;
        this.param2 = param2;
    }

    @Override
    public String toString(){
        return new String(command.toString() + " " + param1 + "\n");
    }

}
