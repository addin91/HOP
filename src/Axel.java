public class Axel {
    public static final double MAX_FALL_SPEED = -20;
    public static final double JUMP_SPEED = 20;
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

    private final Field field;

    //private Image image;

    public Axel(Field f, int x, int y) {
        this.field = f;
        this.x = x;
        this.y = y;
        this.surviving = true;

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
        if(jumping){
            this.y+=updateVitesseY(true, false, false);
            this.falling = true;
        } 
        if(falling){
            this.y += updateVitesseY(false, false, true);
        }
        if(diving) this.y+= updateVitesseY(false, true, false);
        //System.out.println("X : " + this.x);
        //System.out.println("Y : " + this.y);
        checkCollision(x, y);
    }

    public double vitesseX(boolean toRight){
        for(int i = 1; i <= LATERAL_SPEED; i++){
            checkCollision(((toRight) ? -i : i), 0);
        }
        return LATERAL_SPEED;
    }

    public double updateVitesseY(boolean jump, boolean dive, boolean fall){
        if(jump){
            if(vitesseY == 0) vitesseY = JUMP_SPEED;
        }
        if(fall){
            vitesseY = vitesseY - GRAVITY > MAX_FALL_SPEED ? vitesseY-GRAVITY : MAX_FALL_SPEED;
        }
        if (dive){
            if(vitesseY != 0) vitesseY = vitesseY - DIVE_SPEED > MAX_FALL_SPEED ? vitesseY - DIVE_SPEED : MAX_FALL_SPEED;
        }
        if (jump && vitesseY > 0) {
            vitesseY -= GRAVITY;
            if (vitesseY <= 0) {
                jumping = false; 
                falling = true;  
            }
        }
        
        if(vitesseY < 0){
            for(int i = -1; i >= vitesseY; i--){
                checkCollision(0, i);
                if(vitesseY == 0) return i;
            }
        }
        return vitesseY;
        
    }

    public void checkCollision(int addX, int addY){
        if (this.field.libre(x+addX, y+addY)){
            this.falling = true;
        }else{
            this.falling = false;
            vitesseY = 0;
        }
    }

    // SETTERS
    
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
}