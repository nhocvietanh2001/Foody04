package hcmute.spkt.phamvietanh19110151.foodyui.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hcmute.spkt.phamvietanh19110151.foodyui.CustomerProfileActivity;
import hcmute.spkt.phamvietanh19110151.foodyui.R;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.User;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.UserLocalStore;
import hcmute.spkt.phamvietanh19110151.foodyui.VoucherActivity;

public class SettingFragment extends Fragment {
    TextView tvCustomerName, tvEditProfile, tvViewVoucher, tvOrderStatus, tvLogOut;
    UserLocalStore localStore;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        tvCustomerName = view.findViewById(R.id.tvSettingCustomerName);
        tvEditProfile = view.findViewById(R.id.tvCustomerEditProfile);
        tvViewVoucher = view.findViewById(R.id.tvCustomerViewVoucher);
        tvOrderStatus = view.findViewById(R.id.tvCustomerOrderStatus);
        tvLogOut = view.findViewById(R.id.tvCustomerLogout);

        localStore = new UserLocalStore(getActivity());
        user = localStore.getUser();
        tvCustomerName.setText("Hi, " + user.getName());

        tvEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CustomerProfileActivity.class);
                startActivity(intent);
            }
        });

        tvViewVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), VoucherActivity.class);
                startActivity(intent);
            }
        });

        tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        return view;
    }
}