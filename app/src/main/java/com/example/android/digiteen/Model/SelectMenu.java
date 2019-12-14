package com.example.android.digiteen.Model;

public class SelectMenu {
    public static final int MENU_TYPE=1;
    public static final int TOTAL_TYPE=0;
    private Total total;
    private MenuItem menuItem;
    private int type;

    public SelectMenu(int type, Total total) {
        this.type = type;
        this.total = total;
    }


    public SelectMenu(int type, MenuItem menuItem) {
        this.type = type;
        this.menuItem = menuItem;
    }

    public Total getTotal() {
        return total;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public int getType() {
        return type;
    }
}
