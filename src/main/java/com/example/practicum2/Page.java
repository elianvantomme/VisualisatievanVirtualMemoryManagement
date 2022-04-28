package com.example.practicum2;

public class Page {
    private int processId;
    private int pageNr;

    public Page(int processId, int pageNr){
        this.processId = processId;
        this.pageNr = pageNr;
    }

    @Override
    public String toString() {
        return "Page{" +
                "processId=" + processId +
                ", pageNr=" + pageNr +
                '}';
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

}
