package com.example.android.digiteen.Model;

public class OwnerMenu {
    private String itemname;
    private int itemprice;
    private String uri;

    public OwnerMenu(String itemname, int itemprice) {
        this.itemname = itemname;
        this.itemprice = itemprice;
        //this.uri=uri;
    }

    public String getItemname() {
        return itemname;
    }

    public int getItemprice() {
        return itemprice;
    }

//    public String getUri() {
//        return uri;
//    }
}
