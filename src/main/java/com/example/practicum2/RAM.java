package com.example.practicum2;

import java.util.ArrayList;

public class RAM {
    private ArrayList<Process> processesInRam;
    private ArrayList<Page> pagesInRam;

    public RAM(){
        this.processesInRam = new ArrayList<>();
        this.pagesInRam = new ArrayList<>();
    }

    public void addProcessToRam(Process process){
        /*
        The amount of frames a process gets is determined by the amount of other processes
        who are in the RAM, also if the amount of processes in RAM is greater than 4, we need to
        change out the process which
        */

        processesInRam.add(process);
        if (processesInRam.size() == 1){
            /*
            If there is only 1 process in RAM, then process gets al the frames inside the RAM
             */
            ArrayList<PageTableEntry> pageTable = process.getPageTable();
            for (int i = 0; i < 12; i++) {
                PageTableEntry pageTableEntry = pageTable.get(i);
                pageTableEntry.setFrameNummer(i);
                pageTableEntry.setPresentBit(1);
                Page page = new Page(process.getProcessID(), i);
                pagesInRam.add(page);
            }
            //TODO tonen dat de pages ook in het RAM te voorschijnkomen
        }
    }
    public Page getEntry(int page){
        return pagesInRam.get(page);
    }

}




