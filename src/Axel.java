package src;


/**
 * Classe représentant le personnage
 */
public class Axel {
    public static final double MAX_FALL_SPEED = -20;
    public static final double JUMP_SPEED = 18;
    public static final double GRAVITY = 1;
    public static final double DIVE_SPEED = 3 * GRAVITY;
    public static final double LATERAL_SPEED = 8;

    private int x, y;
    private double speedY;
    private String name;

    private boolean falling;
    private boolean jumping;
    private boolean diving;
    private boolean left;
    private boolean right;
    private boolean surviving;

    private final Field field;

    /**
     * Constructeur de la classe Axel.
     * Initialise le personnage.
     */
    public Axel(final Field f, final int x, final int y) {
        this.field = f;
        this.x = x;
        this.y = y;
        this.surviving = true;
        this.speedY = 0;
        this.name = "Player";
    }

    /**
     * La méthode update met à jour Axel à chaque 40 ms
     * Il appelle la méthode computeMove
     */
    public void update() { 
        computeMove();
    }
    
    /**
     * La méhode computeMove met à jour les coordonnées de Axel en fonction des touches enfoncées
     * Il gère les coordonnées X et Y
     * Il teste aussi si le personnage est tombé dans la lave
     */
    public void computeMove(){

        if(right) this.x-=speedX(true);
        if(left) this.x+=speedX(false);
        updateSpeedY(jumping, diving, falling);
        if(jumping){
            this.y+=speedY;
            this.jumping = false;

            if(speedY > 0) this.falling = true;
        } 
        else if(falling){
            this.y += speedY;
        }
        else if(diving) {
            this.y+= speedY;
        }
        if (speedY == 0 && field.getScore() > 0) {
            this.y -= Hop.speed;
        }
        if(y < 10) surviving = false;
    }

    /**
     * La méthode speedX gère la vitesse en X et teste si il y a collision ou pas
     * @param toRight un booléen "Vrai" si le déplacement est vers la droite et "Faux" sinon
     * @return la vitesse de déplacement en X
     */
    public double speedX(boolean toRight){
        for(int i = 1; i <= LATERAL_SPEED; i++){
            checkCollision(((toRight) ? -i : i), 0);
            if(toRight && this.x - i <= GamePanel.BORDER_RIGHT || !toRight && this.x + i >= Hop.WIDTH - GamePanel.BORDER_LEFT) return --i;
        }
        return LATERAL_SPEED;
    }

    /**
     * La méthode updateSpeedY gère la vitesse en Y et teste si il y a collision ou pas
     * @param jump un booléen "Vrai" si Axel saute (si la flèche haut est enfoncé) et "Faux" sinon
     * @param dive un booléen "Vrai" si Axel plonge (si la flèche bas est enfoncé) et "Faux" sinon
     * @param fall un booléen "Vrai" si Axel chute (si vitesse > 0 et il y a pas de collision) et "Faux" sinon
     */
    public void updateSpeedY(boolean jump, boolean dive, boolean fall){
        if(jump){
            if(speedY == 0 && !falling) speedY = JUMP_SPEED;
        }
        if(fall){
            speedY = speedY - GRAVITY > MAX_FALL_SPEED ? speedY-GRAVITY : MAX_FALL_SPEED;
        }
        if (dive){
            if(speedY != 0) speedY = speedY - DIVE_SPEED > MAX_FALL_SPEED ? speedY - DIVE_SPEED : MAX_FALL_SPEED;
        }
        if(speedY < 0){
            for(int i = -1; i >= speedY; i--){
                checkCollision(0, i);
            }
        }
        
    }
    /**
     * la méthode checkCollision gère la collision en X et en Y
     * @param addX la distance supplémentaire en X 
     * @param addY la distance supplémentaire en Y
     */
    public void checkCollision(int addX, int addY){

        for(Block b : this.field.getBlockSet()){
            int xB1 = b.getX();
            int xB2 = xB1 + b.getWidth();
            int yB = b.getY();
            if(x+addX >= xB1 && x+addX <= xB2 && y+addY == yB) {
                this.falling = false;
                speedY = 0;
                y = (y + addY);
                this.field.setScore(Math.max(this.field.getScore(), b.getId()*80));
                b.setUp(true);
                if(speedY == 0) b.effect(this);
                return;
            }else{
                b.setUp(false);
            }
        }
        this.falling = true;
    }


    // SETTERS
    public void setSpeedY(final double speedY) {
        this.speedY = speedY;
    }
    public void setDiving(final boolean diving) {
        this.diving = diving;
    }
    public void setLeft(final boolean left) {
        this.left = left;
    }
    public void setRight(final boolean right) {
        this.right = right;
    }
    public void setFalling(final boolean falling) {
        this.falling = falling;
    }
    public void setJumping(final boolean jumping) {
        this.jumping = jumping;
    }
    public void setSurviving(final boolean surviving) {
        this.surviving = surviving;
    }
    public void setName(final String name) {
        this.name = name;
    }
    public void setX(final int x) {
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
    public String getName() {
        return name;
    }
    public Field getField() {
        return field;
    }
    public double getSpeedY() {
        return speedY;
    }
    public boolean isFalling() {
        return falling;
    }
    public boolean isJumping(){
        return jumping;
    }

    public boolean isLeft(){
        return left;
    }

    public boolean isRight(){
        return right;
    }
    
}
