package hcmute.spkt.phamvietanh19110151.foodyui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import hcmute.spkt.phamvietanh19110151.foodyui.Database.DBHelper;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.Restaurant;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.User;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.UserLocalStore;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.Voucher;

public class MainActivity extends AppCompatActivity {

    LinearLayout mContainerView;
    EditText uPhone, uPass;
    Button btnSignIn, btnGoToRegister;
    DBHelper DB;
    CheckBox cbRemember;
    User user;
    UserLocalStore localStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.higreen_login);

        //anh xa
        uPhone = (EditText) findViewById(R.id.editTextPhone);
        uPass = (EditText) findViewById(R.id.editTextPassword);
        btnSignIn = (Button) findViewById(R.id.btnLogin);
        btnGoToRegister = (Button) findViewById(R.id.btnGoToSignUp);
        DB = new DBHelper(this);
        cbRemember = findViewById(R.id.checkBoxRemember);

        localStore = new UserLocalStore(this);

        if (localStore.getRemember()) {
            user = localStore.getUser();
            uPhone.setText(user.getPhone());
            uPass.setText(user.getPass());
            cbRemember.setChecked(localStore.getRemember());
        }
        //
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = uPhone.getText().toString();
                String pass = uPass.getText().toString();


                if(phone.equals("")||pass.equals("")){
                    Toast.makeText(MainActivity.this,"Please enter all fields",Toast.LENGTH_SHORT).show();
                }else{
                    if(DB.checkUserAndPass(phone,pass)){
                        Toast.makeText(MainActivity.this,"Login successfully!",Toast.LENGTH_SHORT).show();
                        initializeDatabase();
                        Cursor userCursor = DB.getUserWithPhone(phone);
                        while (userCursor.moveToNext()) {
                            String name = userCursor.getString(1);
                            String address = userCursor.getString(3);
                            user = new User(phone, name, pass, address);
                        }
                        localStore.storeUser(user);
                        localStore.setRemember(cbRemember.isChecked());
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(MainActivity.this,"Invalid Credentials",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //
        btnGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    private void initializeDatabase() {
        DB.exec("drop Table if exists vouchers");
        DB.exec("create Table vouchers(vid INTEGER primary key, vname TEXT, vtype TEXT, vamount INTEGER)");
        DB.insertVoucher(1, new Voucher("50% OFF", "percent", 50));
        DB.insertVoucher(2, new Voucher("25% OFF", "percent", 25));
        DB.insertVoucher(3, new Voucher("10.000 VND", "VND", 10000));
        DB.insertVoucher(4, new Voucher("20.000 VND", "VND", 20000));
        //DB.updateVoucher(3, new Voucher("10.000 VND", "VND", 10000));

        DB.exec("drop Table if exists restaurants");
        DB.exec("create Table restaurants(rid INTEGER primary key, rname TEXT, raddress TEXT, rphone TEXT)");
        DB.insertRestaurant(1, new Restaurant("Highland", "Di An, Binh Duong", "0123456789"));
        DB.insertRestaurant(2, new Restaurant("Cua Ngon", "Di An, Binh Duong", "0123456789"));
        DB.insertRestaurant(3, new Restaurant("Sushi bar", "Di An, Binh Duong", "0123456789"));
        DB.insertRestaurant(4, new Restaurant("Bun Cha", "Di An, Binh Duong", "0123456789"));
        DB.insertRestaurant(5, new Restaurant("Ong Tay", "Di An, Binh Duong", "0123456789"));
        DB.insertRestaurant(6, new Restaurant("Pho Nuong", "Di An, Binh Duong", "0123456789"));


    }
}