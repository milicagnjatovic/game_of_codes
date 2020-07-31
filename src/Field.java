import javafx.scene.paint.Color;

/**Class that represents on field of the board for game
 * it contains x and y coordinates of the top left corner of the field,
 * class sifrarnik (which contains letter of the field and it's code) and color of the field*/
public class Field {
    private double x;
    private double y;
    private Sifrarnik sifrarnik;
    private Color color;

    public Field(double x, double y, Color color, Sifrarnik sifrarnik){
        this.x = x;
        this.y = y;
        this.color = color;
        this.sifrarnik = sifrarnik;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public char getKarakter(){
        return sifrarnik.getKarakter();
    }

    public String getKod(){
        return sifrarnik.getKod();
    }
}
