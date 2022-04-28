package com.example.practicum2;

import java.util.*;

public class RAM {
    private ArrayList<Process> processesInRam;
    private ArrayList<Page> frames;

    private Queue<Process> waitingProcessesQueue = new LinkedList<>();

    public RAM() {
        this.processesInRam = new ArrayList<>();
        this.frames = new ArrayList<>();
    }

    public void deletePagesFromProcess(List<PageTableEntry> pageTableEntrysInRam, Process process) {
        int originalSize = frames.size();
        for (PageTableEntry pte : pageTableEntrysInRam) {
            deletePageFromFrame(process, pte);
        }
        int newSize = frames.size();

        if(!waitingProcessesQueue.isEmpty()){
            Process processToSwapIn = waitingProcessesQueue.remove();
            Process processToRemove = null;
            int leastRecentlyUsed = Integer.MAX_VALUE;
            for (Process p : processesInRam) {
                List<PageTableEntry> frames = p.getPageTable().stream()
                        .filter(pageTableEntry -> pageTableEntry.getPresentBit() == 1)
                        .toList();
                for (PageTableEntry frame : frames) {
                    if (frame.getLastAccessTime() < leastRecentlyUsed) {
                        leastRecentlyUsed = frame.getLastAccessTime();
                        processToRemove = p;
                    }
                }
            }

            //now swap out all the frames that this process to remove has
            List<PageTableEntry> pagesToSwapOut = processToRemove.getPageTable().stream()
                    .filter(pageTableEntry -> pageTableEntry.getPresentBit() == 1)
                    .toList();

            List<PageTableEntry> processToSwapInPageTable = processToSwapIn.getPageTable();
            int i = 0;
            for (PageTableEntry pageToSwapOut : pagesToSwapOut) {
                //swap out page from pageToSwap to disk
                int frameNumber = pageToSwapOut.getFrameNummer();
                pageToSwapOut.setFrameNummer(-1);
                if (pageToSwapOut.getModifyBit() == 1) processToRemove.increaseAmountToPersistentMemory();
                pageToSwapOut.setModifyBit(0);
                pageToSwapOut.setPresentBit(0);

                //swap in page from new process to RAM
                PageTableEntry pageTableEntryToSwapIn = processToSwapInPageTable.get(i);
                pageTableEntryToSwapIn.setPresentBit(1);
                pageTableEntryToSwapIn.setFrameNummer(frameNumber);
                exchangePageFromFrame(processToRemove, pageToSwapOut, processToSwapIn, pageTableEntryToSwapIn);
            }
        }
        else if(!(processesInRam.size() == 0)) {
            for (Process p : processesInRam) {
                int amountOfPagesFromEachRemainingProcessToAdd = (originalSize - newSize) / processesInRam.size();
                List<PageTableEntry> allPTEOfprocess = p.getPageTable().stream()
                        .filter(pageTableEntry -> pageTableEntry.getPresentBit() == 0)
                        .toList();
                for (int i = 0; i < amountOfPagesFromEachRemainingProcessToAdd; i++) {
                    Page newPage = new Page(p.getProcessID(),allPTEOfprocess.get(i).getPageNumber());
                    frames.add(newPage);
                }
            }
        }
    }

    public void deletePageFromFrame(Process processToDelete, PageTableEntry pageToDelete) {
        Page pageToRemove = null;
        for (Page p : frames) {
            if (p.getProcessId() == processToDelete.getProcessID() && p.getPageNr() == pageToDelete.getPageNumber()) {
                pageToRemove = p;
                break;
            }
        }
        frames.remove(pageToRemove);
    }


    public void exchangePageFromFrame(Process processToDelete, PageTableEntry pageToDelete, Process processToInsert, PageTableEntry pageToInsert) {
        Page pageToRemove = null;
        for (Page p : frames) {
            if (p.getProcessId() == processToDelete.getProcessID() && p.getPageNr() == pageToDelete.getPageNumber()) {
                pageToRemove = p;
                break;
            }
        }
        int index = frames.indexOf(pageToRemove);
        frames.set(index, new Page(processToInsert.getProcessID(), pageToInsert.getPageNumber()));
    }

    public void addProcessToNotFullRam(Process process) {
        processesInRam.add(process);
        int amountOfProcessesInRam = processesInRam.size();
        final int AMOUNTOFFRAMES = 12;

        if (processesInRam.size() == 1) {
            ArrayList<PageTableEntry> pageTable = process.getPageTable();
            for (int i = 0; i < 12; i++) {
                PageTableEntry pageTableEntry = pageTable.get(i);
                pageTableEntry.setFrameNummer(i);
                pageTableEntry.setPresentBit(1);
                Page page = new Page(process.getProcessID(), i);
                frames.add(page);
            }
        } else {
            int amountOfFramesToSwapPerProcess = AMOUNTOFFRAMES / amountOfProcessesInRam / (amountOfProcessesInRam - 1);
            for (int i = 0; i < amountOfProcessesInRam - 1; i++) {
                Process processToSwap = processesInRam.get(i);
                List<PageTableEntry> longestNotAccessedFrames = processToSwap.getPageTable().stream()
                        .filter(pageTableEntry -> pageTableEntry.getPresentBit() == 1)
                        .sorted((p1, p2) -> p1.getLastAccessTime() - p2.getLastAccessTime())
                        .toList();

                List<PageTableEntry> newProcessPageTable = process.getPageTable();
                for (int j = 0; j < amountOfFramesToSwapPerProcess; j++) {
                    //frame uit proces halen
                    PageTableEntry longestNotAccessedFrame = longestNotAccessedFrames.get(j);
                    longestNotAccessedFrame.setPresentBit(0);
                    if (longestNotAccessedFrame.getModifyBit() == 1) {
                        processToSwap.increaseAmountToPersistentMemory();
                    }
                    int frameNumber = longestNotAccessedFrame.getFrameNummer();
                    longestNotAccessedFrame.setModifyBit(0);
                    longestNotAccessedFrame.setFrameNummer(-1);


                    //nu is er frame vrij en is voor het binnenkomende process
                    PageTableEntry pageTableEntry = newProcessPageTable.get(j + i * amountOfFramesToSwapPerProcess);
                    pageTableEntry.setPresentBit(1);
                    pageTableEntry.setFrameNummer(frameNumber);
                    //frames.add(new Page(process.getProcessID(), pageTableEntry.getPageNumber()));
                    exchangePageFromFrame(processToSwap, longestNotAccessedFrame, process, pageTableEntry); // delete page from RAM
                }
            }
        }
    }

    public void addProcessToFullRam(Process processToSwapIn) {
        //find process to swap out -> process with least recently used frame
        waitingProcessesQueue.add(processToSwapIn);
    }

    public void addProcessToRam(Process process) {
        if (processesInRam.size() <= 3) {
            addProcessToNotFullRam(process);
        } else {
            addProcessToFullRam(process);
        }
    }

    public Page getEntry(int page) {
        return frames.get(page);
    }

    public ArrayList<Process> getProcessesInRam() {
        return processesInRam;
    }

    public void setProcessesInRam(ArrayList<Process> processesInRam) {
        this.processesInRam = processesInRam;
    }

    public ArrayList<Page> getFrames() {
        return frames;
    }

    public void setFrames(ArrayList<Page> frames) {
        this.frames = frames;
    }

    public int addPageToRam(Process process, int vpn) {
        int frameNumber;
        if (processesInRam.size() <= 3) {
            frameNumber = addPageToNotFullRam(process, vpn);
        } else {
            frameNumber = addPageToFullRam(process);
        }

        return frameNumber;
    }

    private int addPageToFullRam(Process process) {
        int frameNumber = 0;
        // TODO

        return frameNumber;
    }

    private int addPageToNotFullRam(Process process, int vpn) {
        int frameNumber;
        List<PageTableEntry> leastAccessedPages = process.getPageTable().stream()
                .filter(pageTableEntry -> pageTableEntry.getFrameNummer() != -1)
                .sorted(Comparator.comparingInt(PageTableEntry::getLastAccessTime))
                .toList();
        PageTableEntry leastAccessedPage = leastAccessedPages.get(0);
        if (leastAccessedPage.getModifyBit() == 1) {
            process.increaseAmountToPersistentMemory();
        }
        frameNumber = leastAccessedPage.getFrameNummer();
        leastAccessedPage.setFrameNummer(-1);
        leastAccessedPage.setPresentBit(0);

        PageTableEntry pageTableEntry = process.getEntry(vpn);
        pageTableEntry.setFrameNummer(frameNumber);
        pageTableEntry.setPresentBit(1);

        frames.get(frameNumber).setPageNr(vpn);
        return frameNumber;
    }

    @Override
    public String toString() {
        return "RAM{" +
                "processesInRam=" + processesInRam +
                ", frames=" + frames +
                '}';
    }
}




