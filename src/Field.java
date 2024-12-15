package src;

import java.util.ArrayList;
import java.util.List;


public class Field {
    public static final int ALTITUDE_GAP = 80;
    public static final int START_ALTITUDE = 40;

    public final int width, height;
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
    private void initialiseEnsembleBlocks(){
        int y = START_ALTITUDE;
        int startBlockWidth = 100;
        Block b1 = new Block(this.width / 2 - startBlockWidth / 2, y, startBlockWidth, 0);
        this.ensembleBlocks.add(b1);

        for (int i = 1; i <= 9; i++) {
            y += ALTITUDE_GAP;
            addBlock(y, i);
            }
    }

    public void addBlock(int y, int id) {

        System.out.println("Id : " + id);
        System.out.println("Score : "+score);
        System.out.println("Y : " + y);
        try {
            
        System.out.println("Diff y : "+(y-ensembleBlocks.get(id-1).getY()));
        } catch (Exception e) {
            System.out.println("Diff y : "+(y-ensembleBlocks.get(8).getY()));
        }
        int widthBlock = minBlockWidth + (int)(Math.random() * ((maxBlockWidth - minBlockWidth) + 1));; // Randomize block width
        System.out.println("Width : " + widthBlock);
        int range = (( Hop.WIDTH - GamePanel.BORDER_LEFT - widthBlock) - (GamePanel.BORDER_RIGHT));
     	int x = (int) ((range * Math.random())+GamePanel.BORDER_RIGHT);

        double alea = Math.random();
        System.out.println("Alea : "+alea);
        Block block;
        if(score >= 2000 && alea < 0.1 ) {  block = new Block(x, y, widthBlock, id, true, false, false); }
        else if(score >= 3200 && alea < 0.25) {  block = new Block(x, y, widthBlock, id, false, true, false); }
        else if(score >= 4800 && alea < 0.5) {  block = new Block(x, y, widthBlock, id, false, false, true); }
        else {  block = new Block(x, y, widthBlock, id); }
        
        ensembleBlocks.add(block);

        System.out.println();
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
                if (b.getY() < 0) blockDeplaces.add(b);
                else b.setY(b.getY() - Hop.speed);
            }

            this.ensembleBlocks.removeAll(blockDeplaces);
            int nbBlock = this.ensembleBlocks.size();
            if(nbBlock < 10) addBlock(this.ensembleBlocks.get(nbBlock-1).getY()+ALTITUDE_GAP , this.ensembleBlocks.get(nbBlock-1).getId()+1);
        }
        
        
       
     }

     public void increaseWidthBlock(){
        this.minBlockWidth -= 5;
        this.maxBlockWidth -= 10;
     }



    // GETTERS

    public int getScore() {
        return score;
    }
    

    // SETTERS

    public void setScore(int score) {
        this.score = score;
    }
 
}
 