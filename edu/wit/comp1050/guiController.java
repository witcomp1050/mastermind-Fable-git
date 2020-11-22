package edu.wit.comp1050;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class guiController {
    @FXML
    private Button submitButton;

    @FXML
    private void submit(){
        mmind.testUnderlyingCode();
    }

    public void setSubmitButtonText(String text){
        submitButton.setText(text);
    }
}
