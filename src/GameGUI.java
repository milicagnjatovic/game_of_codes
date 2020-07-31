import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**Class for making GUI for the game
 * VBox from this class is used in main, GraphicContext represents board for the game.
 * Field RadioButtons, TextField for input and Label for points are potected to manage easier access from class GamePlay (which inherits this one)*/
public class GameGUI {
    private VBox vb;
    private GraphicsContext gc;
    private int size = 200;

    protected RadioButton rbEasy, rbMedium, rbHard;
    protected Button btnPlay;
    protected TextField tfInput;
    protected Label lblPoints;

    public GameGUI(){
        vb = new VBox(10);

        HBox hbDificulty = new HBox(10);
        rbEasy = new RadioButton("Easy");
        rbMedium = new RadioButton("Medium");
        rbHard = new RadioButton("Hard");
        rbEasy.setSelected(true);
        ToggleGroup tgDificulty = new ToggleGroup();
        rbEasy.setToggleGroup(tgDificulty);
        rbMedium.setToggleGroup(tgDificulty);
        rbHard.setToggleGroup(tgDificulty);
        btnPlay = new Button("Play");
        hbDificulty.getChildren().addAll(rbEasy, rbMedium, rbHard, btnPlay);
        hbDificulty.setAlignment(Pos.CENTER);

        lblPoints = new Label("0");

        Canvas cnv = new Canvas(size, size);
        this.gc = cnv.getGraphicsContext2D();
        gc.setFill(Color.gray(0.7, 0.5));
        gc.fillRect(0,0, size, size);

        tfInput = new TextField();
        tfInput.setPrefWidth(size);
        vb.getChildren().addAll(hbDificulty, lblPoints, cnv, tfInput);
        vb.setAlignment(Pos.CENTER);
    }

    public GraphicsContext getGc() {
        return gc;
    }

    public VBox getVb() {
        return vb;
    }

    public int getSize() {
        return size;
    }
}
