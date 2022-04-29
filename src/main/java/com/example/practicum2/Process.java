package com.example.practicum2;

import java.io.Serializable;
import java.util.ArrayList;

public class Process implements Serializable {
    private int processId;
    private ArrayList<PageTableEntry> pageTable;
    private int amountToPersistentMemory;

    public Process(int processId){
        this.processId = processId;
        this.pageTable = new ArrayList<>();
        amountToPersistentMemory = 0;
    }

    public int getProcessId() {
        return processId;
    }

    public int getAmountToPersistentMemory() {
        return amountToPersistentMemory;
    }

    public void createPageTable(){
        for (int i = 0; i < 16; i++) {
            PageTableEntry pageTableEntry = new PageTableEntry(i, processId);
            pageTable.add(pageTableEntry);
        }
    }

    public void increaseAmountToPersistentMemory() {
        amountToPersistentMemory++;
    }

    public ArrayList<PageTableEntry> getPageTable(){
        return pageTable;
    }

    public PageTableEntry getEntry(int placeOfPageTableEntry) {
        return pageTable.get(placeOfPageTableEntry);
    }

    public void editPageTableEntry(int vpn, int clock, String operation) {
        if (operation.equals("Read")){
            pageTable.get(vpn).setLastAccessTime(clock);
        } else if(operation.equals("Write")){
            pageTable.get(vpn).setLastAccessTime(clock);
            pageTable.get(vpn).setModifyBit(1);
        }

    }

    public boolean pagesFromProcessInRAM() {
        for(PageTableEntry p: pageTable){
            if(p.getPresentBit() == 1) return true;
        }
        return false;
    }

    public void deletePageTable() {
        pageTable.clear();
    }

    @Override
    public String toString() {
        return "Process{" +
                "processID=" + processId +
                '}';
    }
}


