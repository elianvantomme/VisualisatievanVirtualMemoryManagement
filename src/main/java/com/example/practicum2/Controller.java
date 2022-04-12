package com.example.practicum2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Controller {
    private int clock = 0;
    private int counter = 0 ;
    private ArrayList<Process> mainMemory;
    XMLParser xmlParser = new XMLParser("virtual memory/Instructions_30_3.xml");


    @FXML
    private Button option2Btn;

    @FXML
    private Text pid;

    @FXML
    private TextField timerField;

    @FXML
    private TextArea pageTableField;

    @FXML
    private Button option1Btn;

    @FXML
    void changeOption1(ActionEvent event) {

        System.out.println("Uitvoeren van optie1");
        clock++;
        timerField.setText(String.valueOf(clock));
        pid.setText(String.valueOf(clock));

    }

    @FXML
    void changeOption2(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert option2Btn != null : "fx:id=\"option2Btn\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert timerField != null : "fx:id=\"timerField\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert option1Btn != null : "fx:id=\"option1Btn\" was not injected: check your FXML file 'hello-view.fxml'.";

        mainMemory = xmlParser.readProcesses();
    }

}
