package in.ac.iitr.mdg.Model;

public class MyOrder {
    private String mtoken,mstatus;

    public MyOrder(String mtoken, String mstatus) {
        this.mtoken = mtoken;
        this.mstatus = mstatus;
    }

    public String getMtoken() {
        return mtoken;
    }

    public String getMstatus() {
        return mstatus;
    }
}
