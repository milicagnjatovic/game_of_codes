import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;
import java.util.List;

/**Class extends GameGui which creates GUI for the game.
 * Size - size of whole field for game, step is size divided by 10 and it represents one fields width/height
 * polja - map with char key and values that are Lists of Filed objects, every character that's on board is in map and
 * it's position on board is given with one Field object, List is because one character can appear more than once on the board.
 * If player guess character only one Field should disappear, if List has one member then delete key from map, otherwise
 * delete only one field from list for guessed character.
 * sifrovanje - represents coding sheme.
 * karakteri - list of all possible characters for the board, every character that has code in sheme is in this list.
 * i and j are coordinates od the top left corner of the field that contains charcter.
 * Points contains number of points that player has at the moment.
 * timeline - timeline is used to make sure that letter appear in exact time, needs to be static because only one gamme can
 * be played at the moment. If game is stopped for any reason (eg pressing any button) timline should stop, stopping timeline
 * could be handled better (this way in every button's event handler needs to be line for stopping timeline)*/
public class GamePlay extends GameGUI{
    private double size;
    private double step;
    private Map<Character, List<Field>> polja;
    private static Random random = new Random();
    private Sifrovanje sifrovanje;
    private List<Character> karakteri;
    private int i = 0;
    private int j = 0;

    private int points = 0;

    public static Timeline timeline = new Timeline();

    public GamePlay(Sifrovanje sifrovanje, Stage primaryStage){
        super();
        this.sifrovanje = sifrovanje;
        this.karakteri = sifrovanje.getListOfCharacters();
        this.polja = new TreeMap<>();

        GraphicsContext gc = getGc();
        size = getSize();
        step = size/10;

//        for grid on the board, optional
//        gc.setFill(Color.BLACK);
//        for (int i = 0; i <= size; i+=step)
//            gc.strokeLine(0, i, size, i);
//        for (int i = 0; i <= size; i+=step)
//            gc.strokeLine(i, 0, i, size);

       btnPlay.setOnAction(e->{
           //setting starting position, has to be done here because button can be pressed multiple times and game will keep going from where it left off last time, instead starting over
            points = 0;
            lblPoints.setText("0");
            timeline.stop(); //if there was some unfinished game we have to make sure it's timer stop
            gc.clearRect(0, 0, size, size);
            gc.setFill(Color.gray(0.7, 0.5));
            gc.fillRect(0,0, size, size);
            i = 0;
            j = 0;

            //difficulty represents number of seconds between two letter appearances
            int difficulty = 10;
            if(rbMedium.isSelected())
                difficulty = 5;
            if(rbHard.isSelected())
                difficulty = 2;

            timeline = new Timeline(new KeyFrame(Duration.seconds(difficulty), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Field newField = addCharacter();
                    gc.setFill(newField.getColor());
                    gc.fillRect(newField.getX(), newField.getY(), step, step);
                    gc.setFill(Color.BLACK);
                    gc.fillText(newField.getKarakter()+" ", newField.getX()+step/10, newField.getY()+step-step/10);
                }
            }));

           Field newField = addCharacter();
           gc.setFill(newField.getColor());
           gc.fillRect(newField.getX(), newField.getY(), step, step);
           gc.setFill(Color.BLACK);
           gc.fillText(newField.getKarakter()+" ", newField.getX()+step/10, newField.getY()+step-step/10);

           timeline.setCycleCount(100); //there's 100 fields on the board
           timeline.play();
           timeline.setOnFinished(ex->{
               Timeline tl = new Timeline(new KeyFrame(Duration.seconds(15))); //timeline that will postpone appearing gemeOver window
               tl.setCycleCount(1);
               tl.play();
               PopUpWindow endGame = new PopUpWindow("Well done, you got " + points + " points\n" +
                       "Now you can keep trying to guess the rest of the letters, if there's any left ;)", "GameOver", primaryStage, 200, 200);
               endGame.showStage();
           });
        });

       //every enter press in input field should initiate chcecking of the input
       tfInput.setOnAction(e->{
           String text = tfInput.getText().trim();
           tfInput.clear();
           if(!sifrovanje.getKodovi().containsKey(text))
               return; //ako uopste ne postoji uneti kod
           //karakter za uneti kod
           Sifrarnik s = sifrovanje.getKodovi().get(text);
           char karakter = s.getKarakter();
           if(polja.containsKey(karakter))
               pogodjenoPolje(polja.get(karakter));
       });
    }

    //if char for input code is on the board, one Field that represents that character should disappear
    private void pogodjenoPolje(List<Field> fields){
        points += 10;
        Field field = fields.get(0);
        fields.remove(0);
        getGc().clearRect(field.getX(), field.getY(), step, step);
        if(polja.get(field.getKarakter()).isEmpty())
            polja.remove(field.getKarakter());
        lblPoints.setText(points+" ");
    }

    private Field addCharacter(){
        int n = random.nextInt(sifrovanje.getNumCodes()-1);
        Sifrarnik onNewField = sifrovanje.getKarakteri().get(karakteri.get(n));
        Field newField = new Field(i, j, Color.ALICEBLUE , onNewField);
        if (!polja.containsKey(onNewField.getKarakter())) //if letter is not already on the field we need to create list of Fileds
            polja.put(onNewField.getKarakter(), new ArrayList<>());
        polja.get(onNewField.getKarakter()).add(newField);
        if(i+step >= size){ //if there's no more space in one row we should start filling next one
            i=0;
            j+=step;
        }
        else
            i += step;
        return newField;
    }
}
