package hcmute.spkt.phamvietanh19110151.foodyui.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;

public class Food {
    private String name;
    private String category;
    private byte[] image;
    private int price;
    private int rid;
    private int fid;

    public Food(int fid, String name, String category, int price,int rid, byte[] image) {
        this.fid = fid;
        this.name = name;
        this.category = category;
        this.price = price;
        this.image = image;
        this.rid = rid;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Bitmap getImageBitmap() {
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        return bitmap;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }
}
