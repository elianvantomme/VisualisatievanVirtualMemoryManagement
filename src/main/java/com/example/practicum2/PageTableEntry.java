package com.example.practicum2;

public class PageTableEntry {
    private int presentBit;
    private int modifyBit;
    private int lastAccessTime;
    private int frameNummer;

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

    public String toString(){
        return "Present Bit: "+presentBit+"\n"+
                "Modify Bit: "+modifyBit+"\n"+
                "Last Access Time: "+lastAccessTime+"\n"+
                "Frame Number: "+frameNummer;
    }
}
