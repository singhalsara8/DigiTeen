package com.example.android.digiteen.Model;

public class PaymentPending {
    private String owner_token_number;
    private int owner_grand_total;

    public PaymentPending(String owner_token_number, int owner_grand_total) {
        this.owner_token_number = owner_token_number;
        this.owner_grand_total = owner_grand_total;
    }

    public String getOwner_token_number() {
        return owner_token_number;
    }

    public int getOwner_grand_total() {
        return owner_grand_total;
    }

    public void setOwner_token_number(String owner_token_number) {
        this.owner_token_number = owner_token_number;
    }

    public void setOwner_grand_total(int owner_grand_total) {
        this.owner_grand_total = owner_grand_total;
    }
}
