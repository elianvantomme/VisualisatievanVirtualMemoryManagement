package com.example.practicum2;

public class Page {
    private int processId;
    private int pageNr;
    private int frameNr;

    public Page(int processId, int pageNr, int frameNr){
        this.processId = processId;
        this.pageNr = pageNr;
        this.frameNr = frameNr;
    }

    public String toString(){
        return "Pid: "+processId+"\n"+
                "Page Nr: "+pageNr;
    }

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public int getPageNr() {
        return pageNr;
    }

    public void setPageNr(int pageNr) {
        this.pageNr = pageNr;
    }

    public int getFrameNr() {
        return frameNr;
    }

    public void setFrameNr(int frameNr) {
        this.frameNr = frameNr;
    }
}
