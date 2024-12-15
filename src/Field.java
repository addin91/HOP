package src;

import java.util.ArrayList;
import java.util.List;


public class Field {
    public static final int ALTITUDE_GAP = 80;
    public static final int START_ALTITUDE = 40;

    private final int width, height;
    private List<Block> blockSet;
    private int minBlockWidth;
    private int maxBlockWidth;
    private int score;

    public Field(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.minBlockWidth = 60;
        this.maxBlockWidth = 100;
        this.score = 0;
        this.blockSet = new ArrayList<Block>();
        initialiseEnsembleBlocks();
    }

    /**
     * la méthode initialiseEnsembleBlocks est une méthode privé qui initialise les blocks au début du jeu
     */
    private void initialiseEnsembleBlocks(){
        int y = START_ALTITUDE;
        int startBlockWidth = 100;
        Block b1 = new Block(this.width / 2 - startBlockWidth / 2, y, startBlockWidth, 0);
        this.blockSet.add(b1);

        for (int i = 1; i <= 9; i++) {
            y += ALTITUDE_GAP;
            addBlock(y, i);
            }
    }

    /**
     * la méthode addBlock crée un block dans le jeu
     * @param y la coordonnée y du block
     * @param id l'id du block
     */
    public void addBlock(int y, int id) {
        int widthBlock = minBlockWidth + (int)(Math.random() * ((maxBlockWidth - minBlockWidth) + 1));; // Randomize block width
        int range = (( Hop.WIDTH - GamePanel.BORDER_LEFT - widthBlock) - (GamePanel.BORDER_RIGHT));
     	int x = (int) ((range * Math.random())+GamePanel.BORDER_RIGHT);
        double alea = Math.random();
        Block block;
        if(score >= 2000 && alea < 0.1 ) {  block = new Block(x, y, widthBlock, id, true, false, false); }
        else if(score >= 3200 && alea < 0.25) {  block = new Block(x, y, widthBlock, id, false, true, false); }
        else if(score >= 4800 && alea < 0.5) {  block = new Block(x, y, widthBlock, id, false, false, true); }
        else {  block = new Block(x, y, widthBlock, id); }
        blockSet.add(block);
    }

    /**
     * La méthode update met à jour Field à chaque 40 ms
     * Il gère les blocks sortis et la création des nouveaux blocks
     */
    public void update() {
        if(score > 0){
            List<Block> blockDeplaces = new ArrayList<>();
            for (Block b : this.blockSet){
                if (b.getY() < 0) blockDeplaces.add(b);
                else b.setY(b.getY() - Hop.speed);
            }
            this.blockSet.removeAll(blockDeplaces);
            int nbBlock = this.blockSet.size();
            if(nbBlock < 10) addBlock(this.blockSet.get(nbBlock-1).getY()+ALTITUDE_GAP , this.blockSet.get(nbBlock-1).getId()+1);
        }  
     }

     /**
      * la méthode increaseWidthBlock décrémete les taille maximum et minimum des blocks selon les niveaux
      */
     public void increaseWidthBlock(){
        this.minBlockWidth -= 5;
        this.maxBlockWidth -= 10;
     }



    // GETTERS

    public int getScore() {
        return score;
    }
    
    public List<Block> getBlockSet() {
        return blockSet;
    }

    // SETTERS

    public void setScore(int score) {
        this.score = score;
    }

    public void setBlockSet(List<Block> blockSet) {
        this.blockSet = blockSet;
    }
 
}
 