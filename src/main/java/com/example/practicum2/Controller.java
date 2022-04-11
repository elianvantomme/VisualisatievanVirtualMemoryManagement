package com.example.practicum2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller {
    private int clock = 0;


    @FXML
    private Button option2Btn;

    @FXML
    private TextField timerField;

    @FXML
    private Button option1Btn;

    @FXML
    void changeOption1(ActionEvent event) {
        System.out.println("Uitvoeren van optie1");
        clock++;
        timerField.setText(String.valueOf(clock));

    }

    @FXML
    void changeOption2(ActionEvent event) {

    }

}
