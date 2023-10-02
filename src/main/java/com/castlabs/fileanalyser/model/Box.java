package com.castlabs.fileanalyser.model;

import java.util.List;

public class Box {
    private int size;
    private String type;
    private List<Box> subBoxes;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Box> getSubBoxes() {
        return subBoxes;
    }

    public void setSubBoxes(List<Box> subBoxes) {
        this.subBoxes = subBoxes;
    }
}
