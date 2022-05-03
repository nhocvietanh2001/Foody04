package hcmute.spkt.phamvietanh19110151.foodyui;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.List;

public class PhotoSliderAdapter extends PagerAdapter {

    private Context mContext;
    private List<PhotoSlider> mListPhoto;

    public PhotoSliderAdapter(Context mContext, List<PhotoSlider> mListPhoto) {
        this.mContext = mContext;
        this.mListPhoto = mListPhoto;
    }

    public PhotoSliderAdapter(HomeFragment homeFragment, List<PhotoSlider> listPhotoSlider) {
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_photo, container, false);

        ImageView imgPhoto = view.findViewById(R.id.img_photo);

        PhotoSlider photo = mListPhoto.get(position);
        if(photo != null){
            Glide.with(mContext).load(photo.getResourceId()).into(imgPhoto);
        }
        //add view to view group
        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        if(mListPhoto != null){
            return mListPhoto.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       //remove view
        container.removeView((View) object);
    }
}
