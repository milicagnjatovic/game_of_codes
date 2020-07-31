import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/** Class that represents popUp Windows
 * Only field is stage because that's the only thing we need to access when we make it i constructor.
 * To make popUp window we need Owner of that window (that will mostly be primaryStage from main), title and text
 * for the new window, also it's height and width.*/
public class PopUpWindow {
    private Stage stage;

    public PopUpWindow(String text, String title, Stage primaryStage, int h, int w){
        Label lbl = new Label(text);
        lbl.setWrapText(true);

         this.stage = new Stage();
         this.stage.setTitle(title);

        VBox vbInfo = new VBox(10);
        vbInfo.setPadding(new Insets(10));
        vbInfo.getChildren().addAll(lbl);

        Scene infoScene = new Scene(vbInfo, w, h);

        this.stage.initModality(Modality.WINDOW_MODAL);
        this.stage.initOwner(primaryStage);
        this.stage.setScene(infoScene);
    }

    public void showStage(){
        this.stage.show();
    }

    public Stage getStage() {
        return stage;
    }
}
