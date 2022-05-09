package hcmute.spkt.phamvietanh19110151.foodyui.Model;

public class Invoice {
    String iID;
    int uID;
    int iTotalMoney;
    String listOrdered;
    String iTime;

    public Invoice(String iID, int uID, int iTotalMoney, String listOrdered, String iTime) {
        this.iID = iID;
        this.uID = uID;
        this.iTotalMoney = iTotalMoney;
        this.listOrdered = listOrdered;
        this.iTime = iTime;
    }

    public String getiID() {
        return iID;
    }

    public void setiID(String iID) {
        this.iID = iID;
    }

    public int getuID() {
        return uID;
    }

    public void setuID(int uID) {
        this.uID = uID;
    }

    public int getiTotalMoney() {
        return iTotalMoney;
    }

    public void setiTotalMoney(int iTotalMoney) {
        this.iTotalMoney = iTotalMoney;
    }

    public String getListOrdered() {
        return listOrdered;
    }

    public void setListOrdered(String listOrdered) {
        this.listOrdered = listOrdered;
    }

    public String getiTime() {
        return iTime;
    }

    public void setiTime(String iTime) {
        this.iTime = iTime;
    }
}
