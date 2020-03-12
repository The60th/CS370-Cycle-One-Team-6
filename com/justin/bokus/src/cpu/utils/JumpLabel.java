package cpu.utils;

public class JumpLabel {
    private int current_line;
    private int target_line;
    public JumpLabel(int current_line, int target_line){
        this.current_line = current_line;
        this.target_line = target_line;
    }

    public int getCurrent_line(){
        return this.current_line;
    }
    public int getTarget_line(){
        return this.target_line;
    }
}
