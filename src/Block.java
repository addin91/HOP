package src;

public class Block {
    private int id;
    private int x;
    private int y;
    private final int width;

    public Block(int x, int y, int width, int id) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.id = id;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getId() {
        return id;
    }

    public void setX(int x){
         this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }
    
    public int getWidth() {
        return width;
    }
}
