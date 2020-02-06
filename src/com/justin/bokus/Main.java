package com.justin.bokus;

import java.io.IOException;

public class Main {
    public static CPU CPU = new CPU();
    public static void main(String[] args) {
	// write your code here
        Parser paraser = new Parser();

        try {
//            paraser.parseFile();
            paraser.loadToMemory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static CPU getCPU(){
        return CPU;
    }
}
