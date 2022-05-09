package hcmute.spkt.phamvietanh19110151.foodyui.Model;

public class InvoiceFood {
    String fname;
    int fprice;
    int amount;

    public InvoiceFood(String fname, int fprice, int amount) {
        this.fname = fname;
        this.fprice = fprice;
        this.amount = amount;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public int getFprice() {
        return fprice;
    }

    public void setFprice(int fprice) {
        this.fprice = fprice;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int total() {
        return amount * fprice;
    }
}
