package hcmute.spkt.phamvietanh19110151.foodyui.Model;

import java.util.List;

public class Invoice {
    String iid;
    String uname;
    String uphone;
    String uaddress;
    List<InvoiceFood> invoiceFoods;
    int total;
    int voucherAmount;

    public Invoice(String iid, String uname, String uphone, String uaddress, List<InvoiceFood> invoiceFoods, int total, int voucherAmount) {
        this.iid = iid;
        this.uname = uname;
        this.uphone = uphone;
        this.uaddress = uaddress;
        this.invoiceFoods = invoiceFoods;
        this.total = total;
        this.voucherAmount = voucherAmount;
    }

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public int getVoucherAmount() {
        return voucherAmount;
    }

    public void setVoucherAmount(int voucherAmount) {
        this.voucherAmount = voucherAmount;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUphone() {
        return uphone;
    }

    public void setUphone(String uphone) {
        this.uphone = uphone;
    }

    public String getUaddress() {
        return uaddress;
    }

    public void setUaddress(String uaddress) {
        this.uaddress = uaddress;
    }

    public List<InvoiceFood> getInvoiceFoods() {
        return invoiceFoods;
    }

    public void setInvoiceFoods(List<InvoiceFood> invoiceFoods) {
        this.invoiceFoods = invoiceFoods;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
