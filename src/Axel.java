package src;

public class Axel {
    public static final double MAX_FALL_SPEED = -20;
    public static final double JUMP_SPEED = 18;
    public static final double GRAVITY = 1;
    public static final double DIVE_SPEED = 3 * GRAVITY;
    public static final double LATERAL_SPEED = 8;

    private int x, y;

    private double vitesseY = 0;

    private boolean falling;
    private boolean jumping;
    private boolean diving;
    private boolean left;
    private boolean right;

    private boolean surviving;

    private String name;

    private final Field field;

    private int score;
    //private Block lastBlock;

    //private Image image;

    public Axel(Field f, int x, int y) {
        this.field = f;
        this.x = x;
        this.y = y;
        this.surviving = true;
        this.name = "Player";


        this.score= 0;

        /*try {
            this.image = ImageIO.read(getClass().getResource("/axel1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    /*public Image getImage(){
        return this.image;
    }*/

    public void update() { 
        computeMove();
    }

    public void computeMove(){

        if(right) this.x-=vitesseX(true);
        if(left) this.x+=vitesseX(false);
        updateVitesseY(jumping, diving, falling);
        if(jumping){
            this.y+=vitesseY;
            this.jumping = false;

            if(vitesseY > 0) this.falling = true;
        } 
        else if(falling){
            this.y += vitesseY;
        }
        else if(diving) {
            this.y+= vitesseY;
        }
        if (vitesseY == 0 && field.getScore() > 0) {
            this.y -= Hop.speed;
        }

        

    }

    public double vitesseX(boolean toRight){
        for(int i = 1; i <= LATERAL_SPEED; i++){
            checkCollision(((toRight) ? -i : i), 0);
            if(toRight && this.x - i <= GamePanel.BORDER_RIGHT || !toRight && this.x + i >= Hop.WIDTH - GamePanel.BORDER_LEFT) return --i;
            
        }

        return LATERAL_SPEED;
    }

    public void updateVitesseY(boolean jump, boolean dive, boolean fall){
        if(jump){
            if(vitesseY == 0 && !falling) vitesseY = JUMP_SPEED;
        }
        if(fall){
            vitesseY = vitesseY - GRAVITY > MAX_FALL_SPEED ? vitesseY-GRAVITY : MAX_FALL_SPEED;
        }
        if (dive){
            if(vitesseY != 0) vitesseY = vitesseY - DIVE_SPEED > MAX_FALL_SPEED ? vitesseY - DIVE_SPEED : MAX_FALL_SPEED;
        }
        if(vitesseY < 0){
            for(int i = -1; i >= vitesseY; i--){
                checkCollision(0, i);
            }
        }
        
    }

    public void checkCollision(int addX, int addY){

        for(Block b : this.field.ensembleBlocks){
            int xB1 = b.getX();
            int xB2 = xB1 + b.getWidth();
            int yB = b.getY();
            if(x+addX >= xB1 && x+addX <= xB2 && y+addY == yB) {
                this.falling = false;
                vitesseY = 0;
                y = (y + addY);
                this.field.setScore(Math.max(this.field.getScore(), b.getId()*80));
                b.setHabiter(true);
                if(vitesseY == 0) b.effet(this);
                return;
            }else{
                b.setHabiter(false);
            }
        }
        this.falling = true;
    }

/* 
    public boolean libre(int x, int y){
        for(Block b : this.field.ensembleBlocks){
            int xB1 = b.getX();
            int xB2 = xB1 + b.getWidth();
            int yB = b.getY();
            if(x >= xB1 && x <= xB2 && y == yB) {
                this.score = Math.max(score, b.getId()*80);
                b.effet(this);
                return false;
            
            }
        }
        return true;
    }

    public void effet(){

    }

    */

    // SETTERS
    public void setVitesseY(double vitesseY) {
        this.vitesseY = vitesseY;
    }
    public void setDiving(boolean diving) {
        this.diving = diving;
    }
    public void setLeft(boolean left) {
        this.left = left;
    }
    public void setRight(boolean right) {
        this.right = right;
    }
    public void setFalling(boolean falling) {
        this.falling = falling;
    }
    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }
    public void setSurviving(boolean surviving) {
        this.surviving = surviving;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setX(int x) {
        this.x = x;
    }

    // GETTERS

    public boolean getSurviving(){
        return this.surviving;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getScore() {
        return score;
    }
    public String getName() {
        return name;
    }
    public Field getField() {
        return field;
    }
    public double getVitesseY() {
        return vitesseY;
    }
    public boolean isFalling() {
        return falling;
    }
}
