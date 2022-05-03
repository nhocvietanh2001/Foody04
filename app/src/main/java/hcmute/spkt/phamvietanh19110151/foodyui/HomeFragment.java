package hcmute.spkt.phamvietanh19110151.foodyui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {

    private ViewPager viewPager, viewPagerSlider;
    private CircleIndicator circleIndicator;
    private PhotoSliderAdapter photoSliderAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewPagerSlider = view.findViewById(R.id.viewpagerslider);
        circleIndicator = view.findViewById(R.id.circle_indicator_slider);
        photoSliderAdapter = new PhotoSliderAdapter(getActivity(), getListPhotoSlider());

        viewPagerSlider.setAdapter(photoSliderAdapter);
        circleIndicator.setViewPager(viewPagerSlider);
        photoSliderAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

        return view;
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