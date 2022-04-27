package com.example.practicum2;

import java.util.ArrayList;
import java.util.List;

public class RAM {
    private ArrayList<Process> processesInRam;
    private ArrayList<Page> frames;

    public RAM(){
        this.processesInRam = new ArrayList<>();
        this.frames = new ArrayList<>();
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
                frames.add(page);
            }
        }

        if(processesInRam.size() == 2){
            //6 - 6
            //get 6 longest not accessed frames from other process
            Process otherProcess = processesInRam.get(0);
            List<PageTableEntry> longestNotAccessedFrames = otherProcess.getPageTable().stream()
                    .filter(pageTableEntry -> pageTableEntry.getPresentBit()==1)
                    .sorted((p1,p2) -> p1.getLastAccessTime() - p2.getLastAccessTime())
                    .toList();

            List<PageTableEntry> newProcessPageTable = process.getPageTable();
            for(int i=0; i<6; i++){
                //frame uit proces halen
                PageTableEntry longestNotAccessedFrame = longestNotAccessedFrames.get(i);
                longestNotAccessedFrame.setPresentBit(0);
                if(longestNotAccessedFrame.getModifyBit() == 1){
                    otherProcess.increaseAmountToPersistentMemory();
                }
                longestNotAccessedFrame.setModifyBit(0);
                Page pageToRemove=null;
                for(Page p : frames){
                    if(p.getProcessId()==otherProcess.getProcessID() && p.getPageNr()==longestNotAccessedFrame.getPageNumber()){
                        pageToRemove = p;
                        break;
                    }
                }
                frames.remove(pageToRemove);

                //nu is er frame vrij en is voor het binnenkomende process
                int frameNumber = longestNotAccessedFrame.getFrameNummer();
                PageTableEntry pageTableEntry = newProcessPageTable.get(i);
                pageTableEntry.setPresentBit(1);
                pageTableEntry.setFrameNummer(frameNumber);
                frames.add(new Page(process.getProcessID(), pageTableEntry.getPageNumber()));
            }
        }
    }
    public Page getEntry(int page){
        return frames.get(page);
    }

}




