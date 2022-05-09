package hcmute.spkt.phamvietanh19110151.foodyui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hcmute.spkt.phamvietanh19110151.foodyui.Adapter.InvoiceAdapter;
import hcmute.spkt.phamvietanh19110151.foodyui.Database.DBFoody;
import hcmute.spkt.phamvietanh19110151.foodyui.Database.DBHelper;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.CartItem;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.InvoiceFood;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.User;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.UserLocalStore;

public class InvoiceActivity extends AppCompatActivity {

    RecyclerView rcvItem;

    TextView cNameIvc,cPhoneIvc, cAddressIvc, tvInvoiceTotal;
    Button btnAccept, btnDecline;
    User user;
    UserLocalStore localStore;
    DBHelper DB;
    List<InvoiceFood> invoiceFoods;
    InvoiceAdapter invoiceAdapter;
    DBFoody MyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice);

        rcvItem = findViewById(R.id.rcvInvoiceItems);
        cNameIvc = findViewById(R.id.tvInvoiceCustomerName);
        cPhoneIvc = findViewById(R.id.tvInvoiceCustomerPhone);
        cAddressIvc = findViewById(R.id.tvInvoiceCustomerAddress);
        btnAccept = findViewById(R.id.btnAcptInvoice);
        btnDecline = findViewById(R.id.btnDeclInvoice);
        tvInvoiceTotal = findViewById(R.id.tvInvoiceTotalVND);

        localStore = new UserLocalStore(this);
        user = localStore.getUser();
        DB = new DBHelper(this);
        MyDB = new DBFoody(this);
        invoiceFoods = new ArrayList<>();
        invoiceAdapter = new InvoiceAdapter(this);

        int voucherAmount = getIntent().getIntExtra("voucher", 0);

        Cursor invoiceCur = DB.getCartByUphone(user.getPhone());
        while (invoiceCur.moveToNext()) {
            int fid = invoiceCur.getInt(2);
            int amount = invoiceCur.getInt(3);
            Cursor foodCur = MyDB.getFoodsByID(fid);
            while (foodCur.moveToNext()) {
                String name = foodCur.getString(1);
                int price = foodCur.getInt(3);
                invoiceFoods.add(new InvoiceFood(name, price, amount));
            }
        }

        invoiceAdapter.setInvoice(invoiceFoods);

        rcvItem.setAdapter(invoiceAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvItem.setLayoutManager(linearLayoutManager);

        cNameIvc.setText(user.getName());
        cPhoneIvc.setText(user.getPhone());
        cAddressIvc.setText(user.getAddress());
        tvInvoiceTotal.setText(Integer.toString(invoiceAdapter.calTotal()));

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DB.deleteCartByID(user.getPhone());
                Toast.makeText(InvoiceActivity.this, "Checkout Successful!", Toast.LENGTH_SHORT).show();
            }
        });

        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}