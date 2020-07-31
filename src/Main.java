import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import java.awt.Desktop;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
//        Scanner sc = new Scanner(System.in);
//        String text = sc.nextLine();
//
//        Sifrovanje sifra = new Sifrovanje();
//        System.out.println(sifra.Desifruj(text));
    }

    private Sifrovanje sifrovanje = new Sifrovanje();

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox(10);

        HBox hbOption = new HBox(10);
        hbOption.setAlignment(Pos.CENTER);
        Button btnTranslate = new Button("Translate");
        Button btnGame = new Button("Game");
        Button btnTable = new Button("Show table");
        Button btnInputFile = new Button("Input new sheme");
        Button btnInfo = new Button("?");
        btnInfo.setPrefWidth(30);

        hbOption.getChildren().addAll(btnTranslate, btnGame, btnTable, btnInputFile, btnInfo);

        VBox vbField = new VBox(10);

        root.getChildren().addAll(hbOption, vbField);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Coding game");
        primaryStage.show();

        /**Translate button make translateGUI for current sheme sifrovanje*/
        btnTranslate.setOnAction(e->{
            TranslateGUI tg = new TranslateGUI(sifrovanje);
            vbField.getChildren().clear();
            vbField.getChildren().addAll(tg.getGUI());
        });

        /**ShowTable button shows coding sheme (which code represents which character)*/
        btnTable.setOnAction(e->{
            GamePlay.timeline.stop(); //HACK

            vbField.getChildren().clear();
            Label lbl = new Label(sifrovanje.toString());
            vbField.setAlignment(Pos.CENTER);
            vbField.getChildren().addAll(lbl);
        });

        /**Game Button make gamePlay object, which inherites gameGUI class that will meke GUI for the game*/
        btnGame.setOnAction(e->{
            GamePlay.timeline.stop(); //HACK

            vbField.getChildren().clear();
            vbField.getChildren().addAll((new GamePlay(sifrovanje, primaryStage)).getVb());
        });

        String newSheme = "If you want to make your own code you can input coding table " +
                "from the file. \nFile should be specific format. \nEach line should contain character and its code " +
                "separated with blank space. \nIf file isn't this format coding won't be correct.\n" +
                "Make sure there is code for every character you need.";
        PopUpWindow puwNewSheme = new PopUpWindow(newSheme, "Instructions", primaryStage, 200,  400);
        PopUpWindow puwError = new PopUpWindow("Unsucessfull input of code. \n" +
                            "Default code is Morse.", "Error", primaryStage, 100, 200);

        /**InputSheme button first shows instructions for new sheme input,
         * then it opens Dialog for choosing file.
         * If file isn't chosen or there's error while loading it popUp window will appear, saying there was error and
         * sheme will be set to default Morse*/
        btnInputFile.setOnAction(e -> {
            GamePlay.timeline.stop(); //HACK

            puwNewSheme.showStage();

            puwNewSheme.getStage().setOnCloseRequest(ev -> {
                puwNewSheme.getStage().close();
                FileChooser fc = new FileChooser();
                File file = fc.showOpenDialog(primaryStage);
//            System.out.println(file.getAbsolutePath());
                try {
                    sifrovanje = new Sifrovanje(file);
                } catch (Exception ex) {
                    sifrovanje = new Sifrovanje();
                    puwError.showStage();
                }
            });
        });


        String info = "This is meant to make coding fun.\n" +
                "By pressing Translate button you get chance to code or decode some text by using coding sheme. " +
                "Default coding sheme is Morse, but you can as well input your own coding sheme by pressing Input new sheme. " +
                "\nIf you're inputing your sheme make sure file is in correct format and that you have " +
                "all characters you need. " +
                "You can see sheme by pressing ShowTable button. " +
                "\nAnd finally game button. \n" +
                "You can test how well you know some coding sheme by playing game. " +
                "In grey sqare will appear some letters and your task is to as fast as possible " +
                "type code for that letter in text field, press enter to check is correct. " +
                "If you guessed code of some letter that letter will disappear from the board " +
                "and you'll get 10 points. In each game you get 100 randomly chosen letters. " +
                "You can as well choose game difficulty (how fast new letters will appear). " +
                "\nGood luck and have fun :)";
        PopUpWindow puwInfo = new PopUpWindow(info,"Instructions",  primaryStage, 300, 600);

        /**Info button will show window with instructions for using whole app*/
        btnInfo.setOnAction(event -> {
            puwInfo.showStage();
        });
    }

}
