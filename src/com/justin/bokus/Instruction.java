package com.justin.bokus;

public class Instruction {
    cpu_commands command;
    String param1;
    String param2;

    public Instruction(){

    }
    public Instruction(cpu_commands command){
        this.command = command;
    }
    public Instruction(cpu_commands command, String param1){
        this.command = command;
        this.param1 = param1;
    }
    public Instruction(cpu_commands command, String param1, String param2){
        this.command = command;
        this.param1 = param1;
        this.param2 = param2;
    }

}
