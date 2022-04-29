package com.example.practicum2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private int clock = 0;
    private int counter = 0;
    private ArrayList<Instruction> instructionList;
    private ArrayList<Process> processes;
    private RAM ram;
    private String instructions = "Instructions_20000_20.xml";
    private int amountOfInstructions;

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
    private TextArea virtualPageNumberText;

    @FXML
    private TextArea offsetText;

    @FXML
    private TextArea frameNumberText;

    @FXML
    private Button option2Btn;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {

        String[] subStrings = instructions.split("_");
        amountOfInstructions = Integer.parseInt(subStrings[1]);
        instructionList = xmlParser.readProcesses();
        processes = new ArrayList<>();
        ram = new RAM();


    }


    XMLParser xmlParser = new XMLParser("virtual memory/" + instructions);

    public int calculateOffset(int virtualAddress){
        return virtualAddress - calculateVPN(virtualAddress) * 4096;
    }

    public int calculateVPN(int virtualAddress) {
        return virtualAddress / 4096;
    }

    public String calculateFrameNumber(int virtualAddress, int pid) {
        int vpn = calculateVPN(virtualAddress);
        if (virtualAddress != 0) {
            Process process = processes.get(pid);
            int frameNumber = process.getEntry(vpn).getFrameNummer();
            if (frameNumber == -1) {
                return String.valueOf(ram.addPageToRam(process, vpn));
            }
            return String.valueOf(frameNumber);
        }
        return "/";
    }


    public String calculateRealAddress(int virtualAddress, int pid) {
        int vpn = calculateVPN(virtualAddress);
        int offset = calculateOffset(virtualAddress);
        if (virtualAddress != 0) {
            Process process = processes.get(pid);
            int frameNumber = process.getEntry(vpn).getFrameNummer();
            if (frameNumber == -1) {
                realAddressField.setText("PAGE FAULT");
                int newFrameNumber = ram.addPageToRam(process, vpn);
                return String.valueOf((newFrameNumber * 4096 + offset));
            }
            return String.valueOf(frameNumber * 4096 + offset);
        }
        return "No address";
    }

    private void printPageTable(Process process) {
        if (!process.getPageTable().isEmpty()) {
            currentProcessPageTable.setText(String.valueOf(process.getProcessId()));
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
    }

    private void printPageTableTerminate(String text,Process process) {
        currentProcessPageTable.setText(String.valueOf(process.getProcessId()));
        pageEntry0.setText(text);
        pageEntry1.setText(text);
        pageEntry2.setText(text);
        pageEntry3.setText(text);
        pageEntry4.setText(text);
        pageEntry5.setText(text);
        pageEntry6.setText(text);
        pageEntry7.setText(text);
        pageEntry8.setText(text);
        pageEntry9.setText(text);
        pageEntry10.setText(text);
        pageEntry11.setText(text);
        pageEntry12.setText(text);
        pageEntry13.setText(text);
        pageEntry14.setText(text);
        pageEntry15.setText(text);
    }

    private void printRam(RAM ram) {
        frame0.setText(ram.getEntry(0).toString1());
        frame1.setText(ram.getEntry(1).toString1());
        frame2.setText(ram.getEntry(2).toString1());
        frame3.setText(ram.getEntry(3).toString1());
        frame4.setText(ram.getEntry(4).toString1());
        frame5.setText(ram.getEntry(5).toString1());
        frame6.setText(ram.getEntry(6).toString1());
        frame7.setText(ram.getEntry(7).toString1());
        frame8.setText(ram.getEntry(8).toString1());
        frame9.setText(ram.getEntry(9).toString1());
        frame10.setText(ram.getEntry(10).toString1());
        frame11.setText(ram.getEntry(11).toString1());
    }


    void debug(Instruction currentInstruction) {
        System.out.println("time: " + clock);
        System.out.println("current instruction: " + currentInstruction + " virtual page number: " + calculateVPN(currentInstruction.getVirtualAddress()));
        for(Process p : ram.getProcessesInRam()){
            System.out.println("page table of process " + p.getProcessId());
            p.getPageTable().forEach(System.out::println);
        }
        System.out.println("ram during current instruction");
        List<Integer> processesInRam = ram.getProcessesInRam().stream().map(process -> process.getProcessId()).toList();
        System.out.println("current processes in ram: " + processesInRam);
        for (int i = 0; i < ram.getFrames().size(); i++) {
            System.out.println("frame " + i + ": " + ram.getFrames().get(i));
        }
        for (int i = 0; i < 200; i++) System.out.print("-");
        System.out.println("\n");
    }

    @FXML
    void changeOption1(ActionEvent event) {

        timerField.setText(String.valueOf(clock));
        if (counter < amountOfInstructions - 1) {

            Instruction currentInstruction = instructionList.get(counter);
            Instruction nextInstruction = instructionList.get(counter + 1);
            String operation = currentInstruction.getOperation();
            currentInstructionField.setText(currentInstruction.toString());
            nextInstructionField.setText(nextInstruction.toString());
            realAddressField.setText(calculateRealAddress(currentInstruction.getVirtualAddress(), currentInstruction.getpId()));
            virtualPageNumberText.setText(String.valueOf(calculateVPN(currentInstruction.getVirtualAddress())));
            offsetText.setText(String.valueOf(calculateOffset(currentInstruction.getVirtualAddress())));
            frameNumberText.setText(calculateFrameNumber(currentInstruction.getVirtualAddress(), currentInstruction.getpId()));

            Process process;
            switch (operation) {
                case "Start" -> {
                    //At startup make an page table and place the processes pages inside the RAM
                    process = new Process(currentInstruction.getpId());
                    process.createPageTable();
                    processes.add(process);
                    ram.addProcessToRam(process);
                    printPageTable(processes.get(currentInstruction.getpId()));
                }
                case "Read" -> {
                    process = processes.get(currentInstruction.getpId());
                    int virtualAddress = currentInstruction.getVirtualAddress();
                    int vpn = calculateVPN(virtualAddress);
                    calculateRealAddress(currentInstruction.getVirtualAddress(), process.getProcessId());
                    process.editPageTableEntry(vpn, clock, "Read");
                    printPageTable(processes.get(currentInstruction.getpId()));
                }
                case "Write" -> {
                    process = processes.get(currentInstruction.getpId());
                    int virtualAddress = currentInstruction.getVirtualAddress();
                    int vpn = calculateVPN(virtualAddress);
                    calculateRealAddress(currentInstruction.getVirtualAddress(), process.getProcessId());
                    process.editPageTableEntry(vpn, clock, "Write");
                    printPageTable(processes.get(currentInstruction.getpId()));
                }
                case "Terminate" -> {
                    process = processes.get(currentInstruction.getpId());
                    if (process.pagesFromProcessInRAM()) {
                        List<PageTableEntry> pageTableEntrysInRam = process.getPageTable().stream()
                                .filter(pageTableEntry -> pageTableEntry.getPresentBit()==1)
                                .toList();
                        ram.deletePagesFromProcess(pageTableEntrysInRam, process);
                    }
                    printPageTableTerminate(" --- Pagetable not avaible ---", process);
                    process.deletePageTable();
                }
                default -> throw new IllegalStateException("Unexpected value: " + operation);
            }

            printRam(ram);
            clock++;
            debug(currentInstruction);

        } else if (counter >= amountOfInstructions - 1) {
            nextInstructionField.setText("END OF INSTRUCTIONS");
            option1Btn.disabledProperty();
            option2Btn.disabledProperty();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("final-view.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                FinalController finalController = fxmlLoader.getController();
                finalController.displayFinalTable(processes);
                Stage stage = new Stage();
                stage.setTitle("End screen");
                stage.setScene(new Scene(root1));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        counter++;
    }

    @FXML
    void changeOption2(ActionEvent event) {
        while (counter < amountOfInstructions) {
            changeOption1(event);
        }
    }
}
