package hcmute.spkt.phamvietanh19110151.foodyui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.CursorWindow;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;

import hcmute.spkt.phamvietanh19110151.foodyui.Database.DBFoody;
import hcmute.spkt.phamvietanh19110151.foodyui.Database.DBHelper;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.Food;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.Restaurant;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.User;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.UserLocalStore;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.Voucher;

public class MainActivity extends AppCompatActivity {

    LinearLayout mContainerView;
    EditText uPhone, uPass;
    Button btnSignIn, btnGoToRegister;
    DBHelper DB;
    DBFoody DBfoody;
    CheckBox cbRemember;
    User user;
    UserLocalStore localStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.higreen_login);

        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
            e.printStackTrace();
        }

        //anh xa
        uPhone = (EditText) findViewById(R.id.editTextPhone);
        uPass = (EditText) findViewById(R.id.editTextPassword);
        btnSignIn = (Button) findViewById(R.id.btnLogin);
        btnGoToRegister = (Button) findViewById(R.id.btnGoToSignUp);
        DB = new DBHelper(this);
        DBfoody = new DBFoody(this);
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
                        //initializeDatabase();
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
        DBfoody.exec("drop Table if exists vouchers");
        DBfoody.exec("create Table vouchers(vid INTEGER primary key, vname TEXT, vtype TEXT, vamount INTEGER)");
        DBfoody.insertVoucher(1, new Voucher("50% OFF", "percent", 50));
        DBfoody.insertVoucher(2, new Voucher("25% OFF", "percent", 25));
        DBfoody.insertVoucher(3, new Voucher("10.000 VND", "VND", 10000));
        DBfoody.insertVoucher(4, new Voucher("20.000 VND", "VND", 20000));
        //DB.updateVoucher(3, new Voucher("10.000 VND", "VND", 10000));

        DB.exec("drop Table if exists restaurants");
        DBfoody.exec("drop Table if exists restaurants");
        DBfoody.exec("create Table restaurants(rid INTEGER primary key, rname TEXT, raddress TEXT, rphone TEXT)");
        DBfoody.insertRestaurant(new Restaurant(1,"Vegetarian Pro", "Di An, Binh Duong", "0123456789"));
        DBfoody.insertRestaurant(new Restaurant(2,"Trang Sen", "Di An, Binh Duong", "0123456789"));
        DBfoody.insertRestaurant(new Restaurant(3,"Thuyen Vien", "Di An, Binh Duong", "0123456789"));
        DBfoody.insertRestaurant(new Restaurant(4,"Van Duyen", "Di An, Binh Duong", "0123456789"));
        DBfoody.insertRestaurant(new Restaurant(5,"Giac Duyen", "Di An, Binh Duong", "0123456789"));
        DBfoody.insertRestaurant(new Restaurant(6,"Huong Sen", "Di An, Binh Duong", "0123456789"));

        DBfoody.exec("drop Table if exists foods");
        DBfoody.exec("create Table foods(fid INTEGER primary key, fname TEXT, fcategory TEXT, rid TEXT, fimage BLOB)");
        DBfoody.insertFood(1, new Food("Com dau hu", "Dry food", toByteArray(R.drawable.comdauhu)), 1);
        DBfoody.insertFood(2, new Food("Canh dau hu", "Soup", toByteArray(R.drawable.canhdauhu)), 1);
        DBfoody.insertFood(3, new Food("Chuoi", "Dessert", toByteArray(R.drawable.chuoi)), 1);
        DBfoody.insertFood(4, new Food("Rau ma", "Drink", toByteArray(R.drawable.rauma)), 1);

    }

    private byte[] toByteArray(int id){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id);
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
        byte[] img = byteArray.toByteArray();
        return img;
    }
}