import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**Class for translating codes. Contains radioButton for choosing is user coding or decoding, and two textAreas
 * (one for input and one for output).*/
public class TranslateGUI {
    private VBox GUI;

    public TranslateGUI(Sifrovanje sifrovanje){
        HBox hbTranslateOption = new HBox();

        Button btn = new Button("Code");

        RadioButton rbCode = new RadioButton("Code");
        RadioButton rbDecode= new RadioButton("Decode");
        rbCode.setSelected(true);
        ToggleGroup tgOption = new ToggleGroup();
        rbCode.setToggleGroup(tgOption);
        rbDecode.setToggleGroup(tgOption);

        rbCode.setOnAction(e->{
            btn.setText("Code");
        });
        rbDecode.setOnAction(e->{
            btn.setText("Decode");
        });

        hbTranslateOption.getChildren().addAll(rbCode, rbDecode, btn);
        hbTranslateOption.setAlignment(Pos.CENTER);

        TextArea taInput = new TextArea();
        TextArea taOutput= new TextArea();

        GUI = new VBox(10);
        GUI.getChildren().addAll(hbTranslateOption, taInput ,taOutput);

        btn.setOnAction(e -> {
            String text = taInput.getText();
            if(rbCode.isSelected())
                taOutput.setText(sifrovanje.Sifruj(text));
            else
                taOutput.setText(sifrovanje.Desifruj(text));
        });
    }

    public VBox getGUI() {
        return GUI;
    }
}
