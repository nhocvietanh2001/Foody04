package hcmute.spkt.phamvietanh19110151.foodyui.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
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

import hcmute.spkt.phamvietanh19110151.foodyui.Adapter.FoodAdapter;
import hcmute.spkt.phamvietanh19110151.foodyui.Database.DBFoody;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.Category;
import hcmute.spkt.phamvietanh19110151.foodyui.Adapter.CategoryAdapter;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.Food;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.PhotoSlider;
import hcmute.spkt.phamvietanh19110151.foodyui.Adapter.PhotoSliderAdapter;
import hcmute.spkt.phamvietanh19110151.foodyui.R;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.User;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.UserLocalStore;
import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {
    private ViewPager viewPagerSlider;
    private CircleIndicator circleIndicator;
    private PhotoSliderAdapter photoSliderAdapter;
    private RecyclerView rcvCate;
    private CategoryAdapter mCategoryAdapter;
    private TextView tvCustomerName;
    private List<PhotoSlider> mListPhotoSld;
    private Timer mTimer;
    private RecyclerView rcvDishesHome;
    private FoodAdapter foodAdapter;
    TextView tvDishes;
    List<Food> foods;
    DBFoody MyDB;
    Context context;

    UserLocalStore localStore;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tvCustomerName = view.findViewById(R.id.tvHomeCustomerName);
        viewPagerSlider = view.findViewById(R.id.viewpagerslider);
        circleIndicator = view.findViewById(R.id.circle_indicator_slider);
        rcvDishesHome = view.findViewById(R.id.rcvDishesHome);
        tvDishes = view.findViewById(R.id.tvDishes);
        mListPhotoSld = getListPhotoSlider();
        photoSliderAdapter = new PhotoSliderAdapter(getActivity(), mListPhotoSld);

        localStore = new UserLocalStore(getActivity());
        user = localStore.getUser();
        tvCustomerName.setText("Hi, " + user.getName());

        viewPagerSlider.setAdapter(photoSliderAdapter);
        circleIndicator.setViewPager(viewPagerSlider);
        photoSliderAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        context = this.getActivity().getApplication();

        rcvCate = view.findViewById(R.id.rcvCate);

        mCategoryAdapter = new CategoryAdapter(getActivity());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),4);
        rcvCate.setLayoutManager(gridLayoutManager);

        mCategoryAdapter.setData(getListCate());
        rcvCate.setAdapter(mCategoryAdapter);

        autoSlideImage();

        MyDB = new DBFoody(getActivity());
        foods = new ArrayList<>();
        foodAdapter = new FoodAdapter(getActivity());

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String category = intent.getStringExtra("category");
                Cursor foodCursor = MyDB.getFoodsWithCategory(category);
                while(foodCursor.moveToNext()) {
                    String fname = foodCursor.getString(1);
                    String fcategory = foodCursor.getString(2);
                    int fprice = foodCursor.getInt(3);
                    byte[] image = foodCursor.getBlob(5);
                    foods.add(new Food(fname, fcategory, fprice, image));
                }

                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
                rcvDishesHome.setLayoutManager(gridLayoutManager);
                foodAdapter.setFoods(foods);
                foods = new ArrayList<>();
                rcvDishesHome.setAdapter(foodAdapter);
                //tvDishes.setVisibility(View.VISIBLE);
                //rcvDishesHome.setVisibility(View.VISIBLE);

            }
        }, new IntentFilter("category"));

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