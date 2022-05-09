package hcmute.spkt.phamvietanh19110151.foodyui.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class CartItem {
    private String foodName;
    private int uphone;
    private int fid;
    private int price;
    private int amount;
    private byte[] image;

    public CartItem(String foodName, int uname, int fid, int amount, int price, byte[] image) {
        this.foodName = foodName;
        this.uphone = uname;
        this.fid = fid;
        this.price = price;
        this.amount = amount;
        this.image = image;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotal() {
        return price*amount;
    }

    public int getUphone() {
        return uphone;
    }

    public void setUphone(int uphone) {
        this.uphone = uphone;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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
}
