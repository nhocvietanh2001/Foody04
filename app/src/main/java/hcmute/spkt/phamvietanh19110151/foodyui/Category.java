package hcmute.spkt.phamvietanh19110151.foodyui;

public class Category {
    private int rsID;
    private String title;

    public Category(int rsID, String title) {
        this.rsID = rsID;
        this.title = title;
    }

    public int getRsID() {
        return rsID;
    }

    public void setRsID(int rsID) {
        this.rsID = rsID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
