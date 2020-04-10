package in.ac.iitr.mdg.Model;

public class OrderSummary {
    private String mitemname;
    private int mitemprice,mitemQuantity,msubtotal;

    public OrderSummary(String mitemname, int mitemprice, int mitemQuantity, int msubtotal) {
        this.mitemname = mitemname;
        this.mitemprice = mitemprice;
        this.mitemQuantity = mitemQuantity;
        this.msubtotal = msubtotal;
    }

    public String getMitemname() {
        return mitemname;
    }

    public int getMitemprice() {
        return mitemprice;
    }

    public int getMitemQuantity() {
        return mitemQuantity;
    }

    public int getMsubtotal() {
        return msubtotal;
    }
}
