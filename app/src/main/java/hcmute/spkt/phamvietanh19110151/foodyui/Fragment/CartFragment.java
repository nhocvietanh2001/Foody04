package hcmute.spkt.phamvietanh19110151.foodyui.Fragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hcmute.spkt.phamvietanh19110151.foodyui.Adapter.CartAdapter;
import hcmute.spkt.phamvietanh19110151.foodyui.Adapter.VoucherAdapter;
import hcmute.spkt.phamvietanh19110151.foodyui.Database.DBFoody;
import hcmute.spkt.phamvietanh19110151.foodyui.Database.DBHelper;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.CartItem;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.Food;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.Voucher;
import hcmute.spkt.phamvietanh19110151.foodyui.R;

public class CartFragment extends Fragment implements IFragChange, CartAdapter.ButtonListener, VoucherAdapter.voucherOnClick{

    RecyclerView rcvOrdered;
    CartAdapter cartAdapter;
    DBFoody MyDB;
    DBHelper DB;
    TextView tvItemTotal, tvDeliveryServices, tvTotal, tvVoucher;
    ImageView imgVoucher;
    Button btnCheckout;
    List<CartItem> cartItems;
    int shipCost, itemTotal, voucherAmount;
    Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        rcvOrdered = view.findViewById(R.id.listOrdered);
        tvItemTotal = view.findViewById(R.id.txtViewItemTotal);
        tvDeliveryServices = view.findViewById(R.id.txtViewShipCost);
        tvTotal = view.findViewById(R.id.txtViewTotal);
        imgVoucher = view.findViewById(R.id.imgVoucher);
        btnCheckout = view.findViewById(R.id.btnCheckOut);
        tvVoucher = view.findViewById(R.id.tvVoucherCart);

        mapping();

        shipCost = 2000;
        itemTotal = cartAdapter.calTotal(cartAdapter.getCartItems());

        bind(shipCost, itemTotal);

        imgVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_voucher);

                Window window = dialog.getWindow();
                if (window == null)
                    return;
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                WindowManager.LayoutParams windowAttributes = window.getAttributes();
                windowAttributes.gravity = Gravity.BOTTOM;
                window.setAttributes(windowAttributes);

                dialog.setCancelable(true);

                RecyclerView rcvVoucher = dialog.findViewById(R.id.rcvVoucherDialog);
                Button btnCancel = dialog.findViewById(R.id.btnCancelVoucher);
                VoucherAdapter adapter = new VoucherAdapter(getActivity());
                adapter.setVoucherOnClick(CartFragment.this::onVoucherClick);
                List<Voucher> vouchers = new ArrayList<>();

                Cursor voucherData = MyDB.getVouchers();
                while (voucherData.moveToNext()) {
                    String name = voucherData.getString(1);
                    String type = voucherData.getString(2);
                    int amount = voucherData.getInt(3);
                    vouchers.add(new Voucher(name, type, amount));
                }
                adapter.setVouchers(vouchers);
                rcvVoucher.setAdapter(adapter);
                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
                rcvVoucher.setLayoutManager(linearLayoutManager1);

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        return view;
    }

    public void bind(int shipCost, int itemTotal) {
        this.itemTotal = itemTotal;
        this.shipCost = shipCost;
        tvItemTotal.setText("Item Total: " + Integer.toString(itemTotal));
        tvDeliveryServices.setText("Ship cost: " + Integer.toString(shipCost));
        tvTotal.setText("Total: " + Integer.toString(itemTotal + shipCost - voucherAmount));

    }

    public void bindVoucher(String type, int value) {
        if (type.equals("percent")) {
            tvVoucher.setVisibility(View.VISIBLE);
            voucherAmount = itemTotal / 100 * value;
            tvVoucher.setText("Voucher Amount: -" + Integer.toString(itemTotal - voucherAmount));
            return;
        }
        if (type.equals("VND")) {
            tvVoucher.setVisibility(View.VISIBLE);
            voucherAmount = value;
            tvVoucher.setText("Voucher Amount: -" + Integer.toString(value));
            return;
        }
    }

    public void mapping() {

        cartAdapter = new CartAdapter(getActivity());
        cartAdapter.setButtonListener(this::onButtonClick);
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

    @Override
    public void onButtonClick(int total) {
        mapping();
        bind(2000, total);
    }

    public void onVoucherClick(String type, int value) {
        bindVoucher(type, value);
        bind(shipCost, itemTotal);
        dialog.dismiss();
    }
}