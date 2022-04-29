package com.example.practicum2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class FinalController {

        @FXML
        private TableColumn<Process, Integer> processId;

        @FXML
        private TableColumn<Process, Integer> amountToPersMemory;

        @FXML
        private TableView<Process> table;

        public void displayFinalTable(ArrayList<Process> processes){
            ObservableList<Process> processObservableList = FXCollections.observableArrayList();
            processObservableList.addAll(processes);
            table.setItems(processObservableList);
        }

        @FXML
        void initialize() {
            assert processId != null : "fx:id=\"processId\" was not injected: check your FXML file 'final-view.fxml'.";
            assert amountToPersMemory != null : "fx:id=\"amountToPersMemory\" was not injected: check your FXML file 'final-view.fxml'.";
            assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'final-view.fxml'.";
            processId.setCellValueFactory(new PropertyValueFactory<>("processId"));
            amountToPersMemory.setCellValueFactory(new PropertyValueFactory<>("amountToPersistentMemory"));
            displayFinalTable(new ArrayList<>());
        }

}
