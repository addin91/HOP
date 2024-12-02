package src;

import java.util.ArrayList;
import java.util.List;


public class Field {
    public static final int ALTITUDE_GAP = 80;
    public static final int START_ALTITUDE = 40;

    public final int width, height;
    private int bottom, top; // bottom and top altitude
    public List<Block> ensembleBlocks = new ArrayList<Block>();
    private int blockWidth;

    public Field(int width, int height) {
        this.width = width;
        this.height = height;
        this.blockWidth = 50;
        initialiseEnsembleBlocks();
    }
/* */
    private void initialiseEnsembleBlocks(){
        int y = START_ALTITUDE;
        Block b1 = new Block(this.width / 2 - blockWidth / 2, y, blockWidth);
        this.ensembleBlocks.add(b1);

        for (int i = 0; i <100; i++) {
            addBlock(y);
            y += ALTITUDE_GAP;
            /*int widthNextBlock = actualBlockWidth + (int)(Math.random() * 41);
            int x = (int)(Math.random() * this.width + 1);
            if (x + widthNextBlock >= this.width) {
                x -= widthBlock;
            }
            Block b = new Block(x, y, widthNextBlock);
            this.ensembleBlocks.add(b);
        */}
    }

    public void addBlock(int y) {
        int widthBlock = Math.max(20, blockWidth + (int) (Math.random() * 11 - 5)); // Randomize block width
        int x = (int) (Math.random() * (width - widthBlock));
        Block block = new Block(x, y, widthBlock);
        ensembleBlocks.add(block);
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

    public void update() {
        List<Block> blockDeplaces = new ArrayList<>();
        for (Block b : this.ensembleBlocks){
            if (b.getY() + height < 0) blockDeplaces.add(b);
            else b.setY(b.getY() - Hop.speed);
        }
        this.ensembleBlocks.removeAll(blockDeplaces);
        
        // L'erreur des blocks qui se rajoute au debut vient du code ci-dessous
        /* 
        if (this.top + ALTITUDE_GAP <= height) {
            this.top += ALTITUDE_GAP;
            int widthNextBlock = 30 + (int)(Math.random() * 41);
            int x = (int)(Math.random() * this.width + 1);
            if (x + widthNextBlock >= this.width) {
                x -= widthNextBlock;
            }
            Block b = new Block(x, this.top, widthNextBlock);
            this.ensembleBlocks.add(b);
        }
        */
     }

     public void increaseDifficulty() {
        Hop.speed++;
        this.blockWidth = Math.max(20, this.blockWidth - 5);
    }
}
 