package hcmute.spkt.phamvietanh19110151.foodyui.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hcmute.spkt.phamvietanh19110151.foodyui.Model.CartItem;
import hcmute.spkt.phamvietanh19110151.foodyui.R;

public class CartFragment extends Fragment {

    RecyclerView rcvOrdered;
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



        return view;
    }
}