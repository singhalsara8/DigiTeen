package com.example.android.digiteen.Model;

public class SelectMenu {
    private int type_total=0;
    private int type_menuitem=1;
    private Total total;
    private MenuItem menuItem;

    public SelectMenu(Total total, MenuItem menuItem) {
        this.total = total;
        this.menuItem = menuItem;
    }

    public Total getTotal() {
        return total;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }
}
