package sample;

public class Pair {
    private int x,y,z;

    public Pair(int x, int y,int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getZ() { return z; }

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setZ(int z) {
        this.z = z;
    }
}
