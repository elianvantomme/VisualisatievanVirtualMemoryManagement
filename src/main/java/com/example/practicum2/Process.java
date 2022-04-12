package com.example.practicum2;

import java.util.ArrayList;

public class Process {
    private int pId;
    private String operation;
    private int virtualAddress;
    private ArrayList<PageTableEntry> pageTable;
    private int amountToPersistentMemory;

    public Process(){
        this.pageTable = new ArrayList<>();
        amountToPersistentMemory = 0;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setVirtualAddress(int virtualAddress) {
        this.virtualAddress = virtualAddress;
    }

    public void increaseAmountToPersistentMemory() {
        amountToPersistentMemory++;
    }
}
