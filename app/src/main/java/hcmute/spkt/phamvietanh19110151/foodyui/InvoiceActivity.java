package hcmute.spkt.phamvietanh19110151.foodyui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import hcmute.spkt.phamvietanh19110151.foodyui.Database.DBHelper;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.User;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.UserLocalStore;

public class InvoiceActivity extends AppCompatActivity {

    TextView cNameIvc,cPhoneIvc, cAddressIvc;
    Button btnDecline;
    User user;
    UserLocalStore localStore;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice);

        cNameIvc = findViewById(R.id.tvInvoiceCustomerName);
        cPhoneIvc = findViewById(R.id.tvInvoiceCustomerPhone);
        cAddressIvc = findViewById(R.id.tvInvoiceCustomerAddress);
        btnDecline = findViewById(R.id.btnDeclInvoice);

        localStore = new UserLocalStore(this);
        user = localStore.getUser();
        DB = new DBHelper(this);
        cNameIvc.setText(user.getName());
        cPhoneIvc.setText(user.getPhone());
        cAddressIvc.setText(user.getAddress());

        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}