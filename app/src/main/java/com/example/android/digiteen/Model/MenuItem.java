package com.example.android.digiteen.Model;

import android.widget.EditText;
import android.widget.TextView;

public class MenuItem {
    private String mitem;
    private int msubtotal,mnumber,mamount;
    private String url;

    public MenuItem(String mitem, int msubtotal, int mnumber, int mamount) {
        this.mitem = mitem;
        this.msubtotal = msubtotal;
        this.mnumber = mnumber;
        this.mamount = mamount;
    }

    public String getMitem() {
        return mitem;
    }

    public int getMsubtotal() {
        return msubtotal;
    }

    public int getMnumber() {
        return mnumber;
    }

    public int getMamount() {
        return mamount;
    }

    public void setMsubtotal(int msubtotal) {
        this.msubtotal = msubtotal;
    }

    public void setMnumber(int mnumber) {
        this.mnumber = mnumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}