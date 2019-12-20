package com.example.android.digiteen.Model;

public class AddMenuItem {
    private String mowneritemname;
    private int mowneritemprice;

    public AddMenuItem(String mowneritemname, int mowneritemprice) {
        this.mowneritemname = mowneritemname;
        this.mowneritemprice = mowneritemprice;
    }

    public String getMowneritemname() {
        return mowneritemname;
    }

    public int getMowneritemprice() {
        return mowneritemprice;
    }

    public void setMowneritemname(String mowneritemname) {
        this.mowneritemname = mowneritemname;
    }

    public void setMowneritemprice(int mowneritemprice) {
        this.mowneritemprice = mowneritemprice;
    }
}
