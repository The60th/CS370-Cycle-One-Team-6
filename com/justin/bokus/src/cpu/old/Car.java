package cpu.old;

@Deprecated
public class Car {
    private int curr_x;
    private int curr_y;
    private int width = 10;
    private int height = 10;

    public Car(int x, int y){
        this.curr_x  = x;
        this.curr_y  = y;
    }


    public int getCurr_y() {
        return curr_y;
    }

    public void setCurr_y(int curr_y) {
        this.curr_y = curr_y;
    }

    public int getCurr_x() {
        return curr_x;
    }

    public void setCurr_x(int curr_x) {
        this.curr_x = curr_x;
    }

    public void setXY(int x, int y){
        this.curr_x =x;
        this.curr_y = y;
    }
}
