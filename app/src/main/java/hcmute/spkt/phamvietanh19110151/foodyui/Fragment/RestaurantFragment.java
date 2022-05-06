package hcmute.spkt.phamvietanh19110151.foodyui.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import hcmute.spkt.phamvietanh19110151.foodyui.Adapter.CategoryAdapter;
import hcmute.spkt.phamvietanh19110151.foodyui.Adapter.FoodAdapter;
import hcmute.spkt.phamvietanh19110151.foodyui.Adapter.PhotoSliderAdapter;
import hcmute.spkt.phamvietanh19110151.foodyui.Adapter.RestaurantAdapter;
import hcmute.spkt.phamvietanh19110151.foodyui.Database.DBFoody;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.Food;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.PhotoSlider;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.Restaurant;
import hcmute.spkt.phamvietanh19110151.foodyui.R;
import me.relex.circleindicator.CircleIndicator;

public class RestaurantFragment extends Fragment {

    private RecyclerView rcvRestaurant, rcvDishes;
    private RestaurantAdapter restaurantAdapter;
    private FoodAdapter foodAdapter;
    DBFoody MyDB;
    List<Restaurant> restaurants;
    List<Food> foods;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);

        rcvRestaurant = view.findViewById(R.id.rcvRestaurant);
        rcvDishes = view.findViewById(R.id.rcvDishes);

        MyDB = new DBFoody(getActivity());
        Cursor resCursor = MyDB.getRestaurants();
        restaurants = new ArrayList<>();
        foods = new ArrayList<>();

        while (resCursor.moveToNext()) {
            int id = resCursor.getInt(0);
            String name = resCursor.getString(1);
            String address = resCursor.getString(2);
            String phone = resCursor.getString(3);
            restaurants.add(new Restaurant(id, name, address, phone));
        }

        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rcvRestaurant.setLayoutManager(linearLayout);

        restaurantAdapter = new RestaurantAdapter(getActivity());
        restaurantAdapter.setRestaurantList(restaurants);
        rcvRestaurant.setAdapter(restaurantAdapter);

        foodAdapter = new FoodAdapter(getActivity());

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int rid = intent.getIntExtra("rid", 0);
                Cursor foodCursor = MyDB.getFoodsAtRestaurant(rid);
                while(foodCursor.moveToNext()) {
                    String fname = foodCursor.getString(1);
                    String fcategory = foodCursor.getString(2);
                    int fprice = foodCursor.getInt(3);
                    byte[] image = foodCursor.getBlob(5);
                    foods.add(new Food(fname, fcategory, fprice, image));
                }
                LinearLayoutManager linearLayout2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                rcvDishes.setLayoutManager(linearLayout2);

                //foodAdapter.setFoods(foods);
                //rcvDishes.setAdapter(foodAdapter);

                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
                rcvDishes.setLayoutManager(gridLayoutManager);
                foodAdapter.setFoods(foods);
                rcvDishes.setAdapter(foodAdapter);


            }
        }, new IntentFilter("restaurant"));

        return view;
    }
}
