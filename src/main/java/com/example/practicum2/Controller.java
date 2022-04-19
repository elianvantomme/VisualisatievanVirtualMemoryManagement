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
    private ArrayList<Instruction> mainMemory;
    private String instructions = "Instructions_20000_20.xml";
    private int amountOfProcesses;
    private int amountOfInstructions;
    XMLParser xmlParser = new XMLParser("virtual memory/"+instructions);

    public int calculateRealAddress(int virtualAddress){
        int VPN = virtualAddress / 1024;
        int offset = virtualAddress - VPN * 1024;
        return VPN;
    }

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
    private TextArea currentInstructionField;

    @FXML
    private TextArea nextInstructionField;

    @FXML
    private TextField realAddressField;


    @FXML
    void changeOption1(ActionEvent event) {

        timerField.setText(String.valueOf(clock));
        pid.setText(String.valueOf(clock));
        if(counter < amountOfInstructions-1){

            Instruction currentInstruction = mainMemory.get(counter);
            Instruction nextInstruction = mainMemory.get(counter+1);

            currentInstructionField.setText(currentInstruction.toString());
            nextInstructionField.setText(nextInstruction.toString());
            realAddressField.setText(String.valueOf(calculateRealAddress(currentInstruction.getVirtualAddress())));

            clock++;

        }else if(counter >= amountOfInstructions-1){
            nextInstructionField.setText("END OF INSTRUCTIONS");
            option1Btn.disabledProperty();
            option2Btn.disabledProperty();
        }
        counter++;
    }

    @FXML
    void changeOption2(ActionEvent event) {
        while(counter < amountOfInstructions){
            changeOption1(event);
        }
    }

    @FXML
    void initialize() {

        String[] subStrings = instructions.split("_");
        amountOfInstructions = Integer.parseInt(subStrings[1]);
        amountOfProcesses = Integer.parseInt(subStrings[2].split("\\.")[0]);
        mainMemory = xmlParser.readProcesses();

    }

}
