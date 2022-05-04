package hcmute.spkt.phamvietanh19110151.foodyui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {

    private ViewPager viewPager, viewPagerSlider;
    private CircleIndicator circleIndicator;
    private PhotoSliderAdapter photoSliderAdapter;
    private List<PhotoSlider> mListPhotoSlider;
    private RecyclerView rcvCate;
    private CategoryAdapter mCategoryAdapter;
    private TextView tvCustomerName;
    UserLocalStore localStore;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tvCustomerName = view.findViewById(R.id.tvHomeCustomerName);
        viewPagerSlider = view.findViewById(R.id.viewpagerslider);
        circleIndicator = view.findViewById(R.id.circle_indicator_slider);
        photoSliderAdapter = new PhotoSliderAdapter(getActivity(), getListPhotoSlider());

        localStore = new UserLocalStore(getActivity());
        user = localStore.getUser();
        tvCustomerName.setText("Hi, " + user.getName());

        viewPagerSlider.setAdapter(photoSliderAdapter);
        circleIndicator.setViewPager(viewPagerSlider);
        photoSliderAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

        rcvCate = view.findViewById(R.id.rcvCate);
        mCategoryAdapter = new CategoryAdapter(getActivity());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),4);
        rcvCate.setLayoutManager(gridLayoutManager);

        mCategoryAdapter.setData(getListCate());
        rcvCate.setAdapter(mCategoryAdapter);

        return view;
    }

    private List<Category> getListCate(){
        List<Category> list = new ArrayList<>();
        list.add(new Category(R.drawable.dryfood, "Dry food"));
        list.add(new Category(R.drawable.soup, "Soup"));
        list.add(new Category(R.drawable.salad, "Salad"));
        list.add(new Category(R.drawable.drink, "Drink"));

        return list;
    }

    private List<PhotoSlider> getListPhotoSlider(){
        List<PhotoSlider> list = new ArrayList<>();
        //
        list.add(new PhotoSlider(R.drawable.banner1));
        list.add(new PhotoSlider(R.drawable.banner2));
        list.add(new PhotoSlider(R.drawable.banner3));
        list.add(new PhotoSlider(R.drawable.banner4));
        //
        return list;
    }
}