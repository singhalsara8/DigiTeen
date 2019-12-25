package com.example.android.digiteen.Model;

public class OwnerMenu {
    private String itemname;
    private int itemprice;

    public OwnerMenu(String itemname, int itemprice) {
        this.itemname = itemname;
        this.itemprice = itemprice;
    }

    public String getItemname() {
        return itemname;
    }

    public int getItemprice() {
        return itemprice;
    }
}
