package com.example.practicum2;

public class Page {
    private int processId;
    private int pageNr;

    public Page(int processId, int pageNr){
        this.processId = processId;
        this.pageNr = pageNr;
    }

    public String toString(){
        return "Pid: "+processId+"\t\t\t"+"\n"+
                "Page Nr: "+pageNr;
    }
}
