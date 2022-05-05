package hcmute.spkt.phamvietanh19110151.foodyui;

import android.media.Image;

public class Food {
    private String name;
    private String category;
    private Image image;

    public Food(String name, String category, Image image) {
        this.name = name;
        this.category = category;
        this.image = image;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
