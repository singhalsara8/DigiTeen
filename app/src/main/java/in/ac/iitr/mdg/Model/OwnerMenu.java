package in.ac.iitr.mdg.Model;

public class OwnerMenu {
    private String itemname;
    private int itemprice;
    private String uri;

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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
