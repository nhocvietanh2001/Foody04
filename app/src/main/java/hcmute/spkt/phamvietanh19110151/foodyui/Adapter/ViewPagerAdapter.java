package hcmute.spkt.phamvietanh19110151.foodyui.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import hcmute.spkt.phamvietanh19110151.foodyui.Fragment.CartFragment;
import hcmute.spkt.phamvietanh19110151.foodyui.Fragment.HomeFragment;
import hcmute.spkt.phamvietanh19110151.foodyui.Fragment.RestaurantFragment;
import hcmute.spkt.phamvietanh19110151.foodyui.Fragment.SettingFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    FragmentManager fm;
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.fm = fm;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();

            case 1:
                return new RestaurantFragment();

            case 2:
                return new CartFragment();

            case 3:
                return new SettingFragment();

            default:
                return new HomeFragment();
        }
    }

    public String getFragmentTag(int viewId, int position) {
        return "android:switcher:" + viewId + ":" + position;
    }

    public Fragment getFragmentByTag(int containerId, int position) {
        return fm.findFragmentByTag(getFragmentTag(containerId, position));
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Home";
                break;
            case 1:
                title = "Restaurant and Food";
                break;
            case 2:
                title = "Cart";
                break;
            case 3:
                title = "Setting";
                break;
        }
        return title;
    }
}
