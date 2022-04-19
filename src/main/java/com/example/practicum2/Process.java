package com.example.practicum2;

import java.util.ArrayList;

public class Process {
    private ArrayList<PageTableEntry> pageTable;
    private int amountToPersistentMemory;

    public Process(){
        this.pageTable = new ArrayList<>();
        amountToPersistentMemory = 0;
    }

    public void increaseAmountToPersistentMemory() {
        amountToPersistentMemory++;
    }
}


