package hcmute.spkt.phamvietanh19110151.foodyui.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hcmute.spkt.phamvietanh19110151.foodyui.Adapter.FoodAdapter;
import hcmute.spkt.phamvietanh19110151.foodyui.Adapter.RestaurantAdapter;
import hcmute.spkt.phamvietanh19110151.foodyui.Database.DBFoody;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.Food;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.Restaurant;
import hcmute.spkt.phamvietanh19110151.foodyui.R;

public class RestaurantFragment extends Fragment{

    private RecyclerView rcvRestaurant, rcvDishes;
    private RestaurantAdapter restaurantAdapter;
    private FoodAdapter foodAdapter;
    private Button btnShowAllDishes;
    Context context;
    DBFoody MyDB;
    List<Restaurant> restaurants;
    List<Food> foods;
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);

        rcvRestaurant = view.findViewById(R.id.rcvRestaurant);
        rcvDishes = view.findViewById(R.id.rcvDishes);
        btnShowAllDishes = view.findViewById(R.id.btnShowAllDishes);
        searchView = view.findViewById(R.id.searchfood);

        MyDB = new DBFoody(getActivity());
        Cursor resCursor = MyDB.getRestaurants();
        restaurants = new ArrayList<>();
        foods = new ArrayList<>();
        context = this.getActivity().getApplication();

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
                searchView.setVisibility(view.GONE);
                int rid = intent.getIntExtra("rid", 0);
                Cursor foodCursor = MyDB.getFoodsAtRestaurant(rid);
                while(foodCursor.moveToNext()) {
                    int fid = foodCursor.getInt(0);
                    String fname = foodCursor.getString(1);
                    String fcategory = foodCursor.getString(2);
                    int fprice = foodCursor.getInt(3);
                    int rid1 = foodCursor.getInt(4);
                    byte[] image = foodCursor.getBlob(5);
                    foods.add(new Food(fid, fname, fcategory, fprice, rid1, image));
                }

                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
                rcvDishes.setLayoutManager(gridLayoutManager);
                foodAdapter.setFoods(foods);
                foods = new ArrayList<>();
                rcvDishes.setAdapter(foodAdapter);

            }
        }, new IntentFilter("restaurant"));

        btnShowAllDishes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setVisibility(view.VISIBLE);
                Cursor foodCursor = MyDB.getFoods();
                while(foodCursor.moveToNext()) {
                    int fid = foodCursor.getInt(0);
                    String fname = foodCursor.getString(1);
                    String fcategory = foodCursor.getString(2);
                    int fprice = foodCursor.getInt(3);
                    int rid = foodCursor.getInt(4);
                    byte[] image = foodCursor.getBlob(5);
                    foods.add(new Food(fid, fname, fcategory, fprice, rid, image));
                }
                GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getActivity(),2);
                rcvDishes.setLayoutManager(gridLayoutManager2);

                foodAdapter.setFoods(foods);
                foods = new ArrayList<>();
                rcvDishes.setAdapter(foodAdapter);
            }
        });

        //search
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                foodAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                foodAdapter.getFilter().filter(s);
                return false;
            }
        });


        return view;
    }
}
