package hcmute.spkt.phamvietanh19110151.foodyui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SettingFragment extends Fragment {
    TextView editProfile, viewVoucher, orderStatus, logOut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        editProfile = view.findViewById(R.id.tvCustomerEditProfile);
        viewVoucher = view.findViewById(R.id.tvCustomerViewVoucher);
        orderStatus = view.findViewById(R.id.tvCustomerOrderStatus);
        logOut = view.findViewById(R.id.tvCustomerLogout);

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CustomerProfileActivity.class);
                startActivity(intent);
            }
        });

        viewVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), VoucherActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}