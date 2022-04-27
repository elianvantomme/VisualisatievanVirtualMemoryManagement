package com.example.practicum2;

public class PageTableEntry {
    private int processID;
    private int presentBit;
    private int modifyBit;
    private int lastAccessTime;
    private int frameNummer;
    private int pageNumber;

    public PageTableEntry(int pageNumber, int processID){
        this.processID = processID;
        presentBit = 0;
        modifyBit = 0;
        lastAccessTime = -1;
        frameNummer = -1;
        this.pageNumber = pageNumber;

    }

    public void setFrameNummer(int frameNummer) {
        this.frameNummer = frameNummer;
    }

    public void setLastAccessTime(int lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public void setModifyBit(int modifyBit) {
        this.modifyBit = modifyBit;
    }

    public void setPresentBit(int presentBit) {
        this.presentBit = presentBit;
    }

    public int getFrameNummer() {
        return frameNummer;
    }

    public String toString(){
        return "Page: "+pageNumber+
                " |\t Frame Number: "+frameNummer+"\t"+
                "Present Bit: "+presentBit+"\t"+
                "Modify Bit: "+modifyBit+"\t"+
                "Last Access Time: "+lastAccessTime;
    }
}
