package hcmute.spkt.phamvietanh19110151.foodyui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import hcmute.spkt.phamvietanh19110151.foodyui.Adapter.VoucherAdapter;
import hcmute.spkt.phamvietanh19110151.foodyui.Database.DBFoody;
import hcmute.spkt.phamvietanh19110151.foodyui.Database.DBHelper;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.Voucher;

public class VoucherActivity extends AppCompatActivity {

    ImageButton btnBack;
    RecyclerView lvVoucher;
    VoucherAdapter adapter;
    ArrayList<Voucher> arrayVoucher;
    DBFoody DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.customer_voucher);

        btnBack = findViewById(R.id.btnBackYourVoucher);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        lvVoucher = findViewById(R.id.lvCustomerVouchers);
        arrayVoucher = new ArrayList<>();
        adapter = new VoucherAdapter(getApplication(), false);
        lvVoucher.setAdapter(adapter);
        DB = new DBFoody(this);

        Cursor voucherData = DB.getVouchers();
        while (voucherData.moveToNext()) {
            String name = voucherData.getString(1);
            String type = voucherData.getString(2);
            int amount = voucherData.getInt(3);
            arrayVoucher.add(new Voucher(name, type, amount));
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplication());
        lvVoucher.setLayoutManager(linearLayoutManager);
        adapter.setVouchers(arrayVoucher);
    }
}