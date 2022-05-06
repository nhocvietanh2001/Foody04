package hcmute.spkt.phamvietanh19110151.foodyui.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import hcmute.spkt.phamvietanh19110151.foodyui.Model.Category;
import hcmute.spkt.phamvietanh19110151.foodyui.Adapter.CategoryAdapter;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.PhotoSlider;
import hcmute.spkt.phamvietanh19110151.foodyui.Adapter.PhotoSliderAdapter;
import hcmute.spkt.phamvietanh19110151.foodyui.R;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.User;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.UserLocalStore;
import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {

    private ViewPager viewPager, viewPagerSlider;
    private CircleIndicator circleIndicator;
    private PhotoSliderAdapter photoSliderAdapter;
    private RecyclerView rcvCate;
    private CategoryAdapter mCategoryAdapter;
    private TextView tvCustomerName;
    private List<PhotoSlider> mListPhotoSld;
    private Timer mTimer;

    UserLocalStore localStore;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tvCustomerName = view.findViewById(R.id.tvHomeCustomerName);
        viewPagerSlider = view.findViewById(R.id.viewpagerslider);
        circleIndicator = view.findViewById(R.id.circle_indicator_slider);
        mListPhotoSld = getListPhotoSlider();
        photoSliderAdapter = new PhotoSliderAdapter(getActivity(), mListPhotoSld);

        localStore = new UserLocalStore(getActivity());
        user = localStore.getUser();
        tvCustomerName.setText("Hi, " + user.getName());

        viewPagerSlider.setAdapter(photoSliderAdapter);
        circleIndicator.setViewPager(viewPagerSlider);
        photoSliderAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

        rcvCate = view.findViewById(R.id.rcvCate);

        mCategoryAdapter = new CategoryAdapter();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),4);
        rcvCate.setLayoutManager(gridLayoutManager);

        mCategoryAdapter.setData(getListCate());
        rcvCate.setAdapter(mCategoryAdapter);

        autoSlideImage();

        return view;
    }

    private List<Category> getListCate(){
        List<Category> list = new ArrayList<>();
        list.add(new Category(R.drawable.dryfood, "Dry food"));
        list.add(new Category(R.drawable.soup, "Soup"));
        list.add(new Category(R.drawable.salad, "Dessert"));
        list.add(new Category(R.drawable.drink, "Drink"));

        return list;
    }
    //banner slider
    private List<PhotoSlider> getListPhotoSlider(){
        List<PhotoSlider> list = new ArrayList<>();
        //
        list.add(new PhotoSlider(R.drawable.banner1));
        list.add(new PhotoSlider(R.drawable.banner2));
        list.add(new PhotoSlider(R.drawable.banner3));
        list.add(new PhotoSlider(R.drawable.banner4));
        list.add(new PhotoSlider(R.drawable.banner5));
        //
        return list;
    }
    private void autoSlideImage(){
        if(mListPhotoSld == null||mListPhotoSld.isEmpty()||viewPagerSlider == null){
            return;
        }
        //Init
        if(mTimer == null){
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = viewPagerSlider.getCurrentItem();
                        int totalItem = mListPhotoSld.size() - 1;
                        if (currentItem<totalItem){
                            currentItem++;
                            viewPagerSlider.setCurrentItem(currentItem);
                        }
                        else viewPagerSlider.setCurrentItem(0);
                    }
                });
            }
        },1000,3000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mTimer != null){
            mTimer.cancel();
            mTimer = null;
        }
    }
}