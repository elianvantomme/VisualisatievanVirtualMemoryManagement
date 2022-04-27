package com.example.practicum2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Objects;

public class Controller {
    private int clock = 0;
    private int counter = 0 ;
    private ArrayList<Instruction> instructionList;
    private ArrayList<Process> processes;
    private RAM RAM;
    private String instructions = "Instructions_20000_20.xml";
    private int amountOfProcesses;
    private int amountOfInstructions;
    XMLParser xmlParser = new XMLParser("virtual memory/"+instructions);

    public int calculateVPN(int virtualAddress){
        return virtualAddress / 4096;
    }

    public String calculateRealAddress(int virtualAddress, int pid){
        int vpn = calculateVPN(virtualAddress);
        int offset = virtualAddress - vpn * 4096;
        if (virtualAddress != 0){
            Process process = processes.get(pid);
            int frameNumber = process.getEntry(vpn).getFrameNummer();
            if (frameNumber == -1){
                realAddressField.setText("PAGE FAULT");
                //TODO RAM voert LRU uit
            }
            return String.valueOf(frameNumber * 4096 + offset);
        }
        return "Instruction has no address";
    }

    private void printPageTable(Process process) {
        currentProcessPageTable.setText(String.valueOf(process.getProcessID()));
        pageEntry0.setText(process.getEntry(0).toString());
        pageEntry1.setText(process.getEntry(1).toString());
        pageEntry2.setText(process.getEntry(2).toString());
        pageEntry3.setText(process.getEntry(3).toString());
        pageEntry4.setText(process.getEntry(4).toString());
        pageEntry5.setText(process.getEntry(5).toString());
        pageEntry6.setText(process.getEntry(6).toString());
        pageEntry7.setText(process.getEntry(7).toString());
        pageEntry8.setText(process.getEntry(8).toString());
        pageEntry9.setText(process.getEntry(9).toString());
        pageEntry10.setText(process.getEntry(10).toString());
        pageEntry11.setText(process.getEntry(11).toString());
        pageEntry12.setText(process.getEntry(12).toString());
        pageEntry13.setText(process.getEntry(13).toString());
        pageEntry14.setText(process.getEntry(14).toString());
        pageEntry15.setText(process.getEntry(15).toString());
    }
    private void printRam(RAM ram){
        frame0.setText(ram.getEntry(0).toString());
        frame1.setText(ram.getEntry(1).toString());
        frame2.setText(ram.getEntry(2).toString());
        frame3.setText(ram.getEntry(3).toString());
        frame4.setText(ram.getEntry(4).toString());
        frame5.setText(ram.getEntry(5).toString());
        frame6.setText(ram.getEntry(6).toString());
        frame7.setText(ram.getEntry(7).toString());
        frame8.setText(ram.getEntry(8).toString());
        frame9.setText(ram.getEntry(9).toString());
        frame10.setText(ram.getEntry(10).toString());
        frame11.setText(ram.getEntry(11).toString());
    }

    @FXML
    private TextArea frame0;
    @FXML
    private TextArea frame1;
    @FXML
    private TextArea frame2;
    @FXML
    private TextArea frame3;
    @FXML
    private TextArea frame4;
    @FXML
    private TextArea frame5;
    @FXML
    private TextArea frame6;
    @FXML
    private TextArea frame7;
    @FXML
    private TextArea frame8;
    @FXML
    private TextArea frame9;
    @FXML
    private TextArea frame10;
    @FXML
    private TextArea frame11;


    @FXML
    private Text currentProcessPageTable;
    @FXML
    private TextArea pageEntry0;
    @FXML
    private TextArea pageEntry1;
    @FXML
    private TextArea pageEntry2;
    @FXML
    private TextArea pageEntry3;
    @FXML
    private TextArea pageEntry4;
    @FXML
    private TextArea pageEntry5;
    @FXML
    private TextArea pageEntry6;
    @FXML
    private TextArea pageEntry7;
    @FXML
    private TextArea pageEntry8;
    @FXML
    private TextArea pageEntry9;
    @FXML
    private TextArea pageEntry10;
    @FXML
    private TextArea pageEntry11;
    @FXML
    private TextArea pageEntry12;
    @FXML
    private TextArea pageEntry13;
    @FXML
    private TextArea pageEntry14;
    @FXML
    private TextArea pageEntry15;


    @FXML
    private Button option2Btn;

    @FXML
    private Text pid;

    @FXML
    private TextField timerField;

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
        if(counter < amountOfInstructions-1){

            Instruction currentInstruction = instructionList.get(counter);
            Instruction nextInstruction = instructionList.get(counter+1);
            String operation = currentInstruction.getOperation();
            currentInstructionField.setText(currentInstruction.toString());
            nextInstructionField.setText(nextInstruction.toString());
            realAddressField.setText(calculateRealAddress(currentInstruction.getVirtualAddress(), currentInstruction.getpId()));

            switch (operation) {
                case "Start" -> {
                    //At startup make an page table and place the processes pages inside the RAM
                    Process process = new Process(currentInstruction.getpId());
                    process.createPageTable();
                    processes.add(process);
                    RAM.addProcessToRam(process);
                }
                case "Read" -> {
                    Process process = processes.get(currentInstruction.getpId());
                    int virtualAddress = currentInstruction.getVirtualAddress();
                    int vpn = calculateVPN(virtualAddress);
                    calculateRealAddress(currentInstruction.getVirtualAddress(), process.getProcessID());
                    process.editPageTableEntry(vpn, clock, "Read");
                    printPageTable(processes.get(currentInstruction.getpId()));
                }
                case "Write" -> {
                    Process process = processes.get(currentInstruction.getpId());
                    int virtualAddress = currentInstruction.getVirtualAddress();
                    int vpn = calculateVPN(virtualAddress);
                    calculateRealAddress(currentInstruction.getVirtualAddress(), process.getProcessID());
                    process.editPageTableEntry(vpn, clock, "Write");
                    printPageTable(processes.get(currentInstruction.getpId()));
                }
                case "Stop" -> printPageTable(processes.get(currentInstruction.getpId()));
                default -> throw new IllegalStateException("Unexpected value: " + operation);
            }

            printRam(RAM);
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
        instructionList = xmlParser.readProcesses();
        processes = new ArrayList<>();
        RAM = new RAM();

        System.out.println(instructionList);
    }

}
