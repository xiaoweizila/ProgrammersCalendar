package com.lw.programmerscalendar.model;

public class DataModel {
    public String name;
    public String good;
    public String bad;
    public boolean weekend;

    public DataModel(String name, String good, String bad, boolean weekend) {
        this.name = name;
        this.good = good;
        this.bad = bad;
        this.weekend = weekend;
    }
}
