package com.example.practicum2;

public class Instruction {
    private int pId;
    private String operation;
    private int virtualAddress;

    public Instruction(){
        pId = 0;
        operation = "";
        virtualAddress = 0;
    }
    public Instruction(Instruction instruction){
        pId = instruction.pId;
        operation = instruction.operation;
        virtualAddress = instruction.virtualAddress;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setVirtualAddress(int virtualAddress) {
        this.virtualAddress = virtualAddress;
    }

    public int getpId() {
        return pId;
    }

    public int getVirtualAddress() {
        return virtualAddress;
    }

    public String getOperation() {
        return operation;
    }

    public String toString(){
        return "process ID: "+pId+", operation: "+operation+"\n"
                +"virtual address: "+virtualAddress;
    }

}
