package src;

import java.util.ArrayList;
import java.util.List;

import src.Block;

public class Field {
    public static final int ALTITUDE_GAP = 80;
    public static final int START_ALTITUDE = 40;

    public final int width, height;
    private int bottom, top; // bottom and top altitude
    public List<Block> ensembleBlocks = new ArrayList<Block>();

    public Field(int width, int height) {
        this.width = width;
        this.height = height;
        initialiseEnsembleBlocks();
    }

    private void initialiseEnsembleBlocks(){
        int y = 40;
        int widthBlock = 50;
        Block b1 = new Block(this.width/2-widthBlock/2, y, widthBlock);
        this.ensembleBlocks.add(b1);

        for(int i = 0; i<15; i++){
            y+=40;
            int x = (int)(Math.random() * this.width +1);
            Block b = new Block(x+widthBlock >= this.width ? x-widthBlock : x, y, widthBlock);        
            this.ensembleBlocks.add(b);
        }
    }

    public boolean libre(int x, int y){
        for(Block b : this.ensembleBlocks){
            int xB1 = b.getX();
            int xB2 = xB1 + b.getWidth();
            int yB = b.getY();
            if(x > xB1 && x < xB2 && y == yB) return false;
        }
        return true;
    }

    public void update() { }
}
