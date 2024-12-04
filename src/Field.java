package src;

import java.util.ArrayList;
import java.util.List;


public class Field {
    public static final int ALTITUDE_GAP = 80;
    public static final int START_ALTITUDE = 40;

    public final int width, height;
    private int bottom, top; // bottom and top altitude
    public List<Block> ensembleBlocks = new ArrayList<Block>();
    private int minBlockWidth;
    private int maxBlockWidth;
    private int score;

    public Field(int width, int height) {
        this.width = width;
        this.height = height;
        this.minBlockWidth = 50;
        this.maxBlockWidth = 100;
        this.score = 0;
        initialiseEnsembleBlocks();
    }
/* */
    private void initialiseEnsembleBlocks(){
        int y = START_ALTITUDE;
        int startBlockWidth = 100;
        Block b1 = new Block(this.width / 2 - startBlockWidth / 2, y, startBlockWidth, 0);
        this.ensembleBlocks.add(b1);

        for (int i = 1; i <= 100; i++) {

            y += ALTITUDE_GAP;
            addBlock(y, i);
            /*int widthNextBlock = actualBlockWidth + (int)(Math.random() * 41);
            int x = (int)(Math.random() * this.width + 1);
            if (x + widthNextBlock >= this.width) {
                x -= widthBlock;
            }
            Block b = new Block(x, y, widthNextBlock);
            this.ensembleBlocks.add(b);
        */}
        for(Block b : this.ensembleBlocks){
            System.out.println("x : " + b.getX());
            System.out.println("y : " + b.getY());
            System.out.println();
        }
    }

    public void addBlock(int y, int id) {
        int widthBlock = minBlockWidth + (int)(Math.random() * ((maxBlockWidth - minBlockWidth) + 1));; // Randomize block width
        int x = (int) (Math.random() * (width - widthBlock));
        Block block = new Block(x, y, widthBlock, id);
        ensembleBlocks.add(block);
    }

    public boolean libre(int x, int y){
        for(Block b : this.ensembleBlocks){
            int xB1 = b.getX();
            int xB2 = xB1 + b.getWidth();
            int yB = b.getY();
            if(x >= xB1 && x <= xB2 && y == yB) {
                this.score = Math.max(score, b.getId()*80);
                return false;
            
            }
        }
        return true;
    }

    public void update() {
        if(score > 0){
            List<Block> blockDeplaces = new ArrayList<>();
            for (Block b : this.ensembleBlocks){
                if (b.getY() + height < 0) blockDeplaces.add(b);
                else b.setY(b.getY() - Hop.speed);
            }
            this.ensembleBlocks.removeAll(blockDeplaces);
        }
        
        
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

     public void increaseWidthBlock(){
        this.minBlockWidth -= 5;
        this.maxBlockWidth -= 10;
     }



    // GETTERS

    public int getScore() {
        return score;
    }

 
}
 