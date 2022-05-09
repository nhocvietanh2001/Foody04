package hcmute.spkt.phamvietanh19110151.foodyui.Fragment;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hcmute.spkt.phamvietanh19110151.foodyui.Adapter.CartAdapter;
import hcmute.spkt.phamvietanh19110151.foodyui.Database.DBFoody;
import hcmute.spkt.phamvietanh19110151.foodyui.Database.DBHelper;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.CartItem;
import hcmute.spkt.phamvietanh19110151.foodyui.R;

public class CartFragment extends Fragment implements IFragChange{

    public static RecyclerView rcvOrdered;
    public static CartAdapter cartAdapter;
    DBFoody MyDB;
    DBHelper DB;
    TextView tvItemTotal, tvDeliveryServices, tvTotal;
    ImageView imgVoucher;
    Button btnCheckout;
    List<CartItem> cartItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        rcvOrdered = view.findViewById(R.id.listOrdered);
        tvItemTotal = view.findViewById(R.id.txtViewItemTotal);
        tvDeliveryServices = view.findViewById(R.id.txtViewShipCost);
        tvTotal = view.findViewById(R.id.txtViewTotal);
        imgVoucher = view.findViewById(R.id.imgFood);
        btnCheckout = view.findViewById(R.id.btnCheckOut);

        mapping();

        return view;
    }

    public void mapping() {

        cartAdapter = new CartAdapter(getActivity());
        MyDB = new DBFoody(getActivity());
        DB = new DBHelper(getActivity());
        cartItems = new ArrayList<>();

        Cursor item = DB.getCart();
        while (item.moveToNext()) {
            int cid = item.getInt(0);
            int phone = item.getInt(1);
            int fid = item.getInt(2);
            int amount = item.getInt(3);
            Cursor food = MyDB.getFoodsByID(fid);
            while (food.moveToNext()) {
                String foodName = food.getString(1);
                int price = food.getInt(3);
                byte[] img = food.getBlob(5);
                cartItems.add(new CartItem(cid, foodName, phone, fid, amount, price, img));
            }
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcvOrdered.setLayoutManager(linearLayoutManager);
        cartAdapter.setItems(cartItems);
        cartItems = new ArrayList<>();
        rcvOrdered.setAdapter(cartAdapter);
    }

    @Override
    public void fragmentBecameVisible() {
        mapping();
    }
}