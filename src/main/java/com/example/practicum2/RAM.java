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


    public void deletePageFromFrame(Process processToDelete, PageTableEntry pageToDelete){
        Page pageToRemove=null;
        for(Page p : frames){
            if(p.getProcessId()==processToDelete.getProcessID() && p.getPageNr()==pageToDelete.getPageNumber()){
                pageToRemove = p;
                break;
            }
        }
        frames.remove(pageToRemove);
    }

    public void insertPageInFrame(Process processToAdd, PageTableEntry pageToInsert){
        frames.add(new Page(processToAdd.getProcessID(), pageToInsert.getPageNumber()));
    }


    public void addProcessToNotFullRam(Process process){
        processesInRam.add(process);
        int amountOfProcessesInRam = processesInRam.size();
        final int AMOUNTOFFRAMES = 12;

        if(processesInRam.size()==1){
            ArrayList<PageTableEntry> pageTable = process.getPageTable();
            for (int i = 0; i < 12; i++) {
                PageTableEntry pageTableEntry = pageTable.get(i);
                pageTableEntry.setFrameNummer(i);
                pageTableEntry.setPresentBit(1);
                Page page = new Page(process.getProcessID(), i);
                frames.add(page);
            }
        }
        else {
            int amountOfFramesToSwap = AMOUNTOFFRAMES / amountOfProcessesInRam;
            for(int i=0; i<amountOfProcessesInRam-1; i++){
                Process processToSwap = processesInRam.get(i);
                List<PageTableEntry> longestNotAccessedFrames = processToSwap.getPageTable().stream()
                        .filter(pageTableEntry -> pageTableEntry.getPresentBit()==1)
                        .sorted((p1,p2) -> p1.getLastAccessTime() - p2.getLastAccessTime())
                        .toList();

                List<PageTableEntry> newProcessPageTable = process.getPageTable();
                for(int j=0; j<amountOfFramesToSwap; j++){
                    //frame uit proces halen
                    PageTableEntry longestNotAccessedFrame = longestNotAccessedFrames.get(j);
                    longestNotAccessedFrame.setPresentBit(0);
                    if(longestNotAccessedFrame.getModifyBit() == 1){
                        processToSwap.increaseAmountToPersistentMemory();
                    }
                    longestNotAccessedFrame.setModifyBit(0);
                    deletePageFromFrame(processToSwap, longestNotAccessedFrame); // delete page from RAM

                    //nu is er frame vrij en is voor het binnenkomende process
                    int frameNumber = longestNotAccessedFrame.getFrameNummer();
                    PageTableEntry pageTableEntry = newProcessPageTable.get(j);
                    pageTableEntry.setPresentBit(1);
                    pageTableEntry.setFrameNummer(frameNumber);
                    frames.add(new Page(process.getProcessID(), pageTableEntry.getPageNumber()));
                }
            }
        }
    }

    public void addProcessToFullRam(Process processToSwapIn){
        //find process to swap out -> process with least recently used frame
        Process processToRemove = null;
        int leastRecentlyUsed = Integer.MAX_VALUE;
        for(Process p : processesInRam){
            List<PageTableEntry> frames = p.getPageTable().stream()
                    .filter(pageTableEntry -> pageTableEntry.getPresentBit()==1)
                    .toList();
            for(PageTableEntry frame : frames){
                if(frame.getLastAccessTime() < leastRecentlyUsed){
                    leastRecentlyUsed = frame.getLastAccessTime();
                    processToRemove = p;
                }
            }
        }

        //now swap out all the frames that this process to remove has
        List<PageTableEntry> pagesToSwapOut = processToRemove.getPageTable().stream()
                .filter(pageTableEntry -> pageTableEntry.getPresentBit()==1)
                .toList();

    }

    public void addProcessToRam(Process process){
        if(processesInRam.size() <= 3){
            addProcessToNotFullRam(process);
        } else {
            addProcessToFullRam(process);
        }
    }

    public Page getEntry(int page){
        return frames.get(page);
    }

    public int addPageToRam(Process process){
        int frameNumber;
        if(processesInRam.size() <= 3){
            frameNumber = addPageToNotFullRam();
        }
        else {
            frameNumber = addPageToFullRam();
        }

        return frameNumber;
    }

    private int addPageToFullRam() {
        int frameNumber;


        return frameNumber;
    }

    private int addPageToNotFullRam() {
        int frameNumber;
        if(processesInRam.size() == 1) {

        }
        else {

        }

        return frameNumber;
    }


}
