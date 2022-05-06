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
    //Database
    private void initializeDatabase() {
        DB.exec("drop Table if exists vouchers");
        DBfoody.exec("drop Table if exists vouchers");
        DBfoody.exec("create Table vouchers(vid INTEGER primary key, vname TEXT, vtype TEXT, vamount INTEGER)");
        DBfoody.insertVoucher(1, new Voucher("50% OFF", "percent", 50));
        DBfoody.insertVoucher(2, new Voucher("25% OFF", "percent", 25));
        DBfoody.insertVoucher(3, new Voucher("10.000 VND", "VND", 10000));
        DBfoody.insertVoucher(4, new Voucher("20.000 VND", "VND", 20000));
        //DB.updateVoucher(3, new Voucher("10.000 VND", "VND", 10000));


//        //them nha hang
//        DBfoody.exec("drop Table if exists restaurants");
//        DBfoody.exec("create Table restaurants(rid INTEGER primary key, rname TEXT, raddress TEXT, rphone TEXT)");
//        DBfoody.insertRestaurant(new Restaurant(1,"BioGarten", "155 Nam Kỳ Khởi Nghĩa, P. 7, Quận 3", "0123456789"));
//        DBfoody.insertRestaurant(new Restaurant(2,"Chay Ba Lá", "Tầng 1, 32A Cao Bá Nhạ, P. Nguyễn Cư Trinh, Quận 1", "0123456789"));
//        DBfoody.insertRestaurant(new Restaurant(3,"Chay Sen", "171 Nguyễn Thái Học, P. Phạm Ngũ Lão, Quận 1", "0123456789"));
//        DBfoody.insertRestaurant(new Restaurant(4,"Chay Bông Súng", "155 Nguyễn Đình Chiểu, P. 6, Quận 3", "0123456789"));
//        DBfoody.insertRestaurant(new Restaurant(5,"Chay Mandala Sài Gòn", "110 Sương Nguyệt Ánh, P. Bến Thành, Quận 1", "0123456789"));
//        DBfoody.insertRestaurant(new Restaurant(6,"Chay Phương Mai ", "86F Võ Thị Sáu, P. Tân Định, Quận 1", "0123456789"));
//        DBfoody.insertRestaurant(new Restaurant(7,"Chay Hoa Đăng", "38 Huỳnh Khương Ninh, Quận 1", "0123456789"));
//        DBfoody.insertRestaurant(new Restaurant(8,"Chay Garden Vegetarian Restaurant ", "52 Võ Văn Tần, Quận 3", "0123456789"));
//        DBfoody.insertRestaurant(new Restaurant(9,"Chay Bếp xanh An Duyên", "10 Nguyễn Tri Phương, Quận 5", "0123456789"));
//        DBfoody.insertRestaurant(new Restaurant(10,"Chay Đóa Sen Vàng", "253/8 Nguyễn Văn Trỗi, P. 10, Quận Phú Nhuận", "0123456789"));
//
//        //them mon an
//        DBfoody.exec("drop Table if exists foods");
//        DBfoody.exec("create Table foods(fid INTEGER primary key, fname TEXT, fcategory TEXT, fprice TEXT, rid TEXT, fimage BLOB)");
//        //nha hang 1
//        DBfoody.insertFood(1, new Food("Xíu mại chay","Dry food",25000,toByteArray(R.drawable.f1)),1);
//        DBfoody.insertFood(2, new Food("Đậu non sốt nấm chay","Dry food",25000,toByteArray(R.drawable.f2)),1);
//        DBfoody.insertFood(3, new Food("Canh chay ngũ sắc","Soup",20000,toByteArray(R.drawable.f3)),1);
//        DBfoody.insertFood(4, new Food("Chè chuối chiên đường quế","Dessert",16000,toByteArray(R.drawable.f4)),1);
//        DBfoody.insertFood(5, new Food("Nước nha đam","Drink",13000,toByteArray(R.drawable.f5)),1);
//        //nha hang 2
//        DBfoody.insertFood(6, new Food("Nấm đùi gà xào cải thìa","Dry food",25000,toByteArray(R.drawable.f6)),2);
//        DBfoody.insertFood(7, new Food("Chả đậu phụ cuốn lá lốt","Dry food",25000,toByteArray(R.drawable.f7)),2);
//        DBfoody.insertFood(8, new Food("Lẩu cá thác lác chay","Soup",20000,toByteArray(R.drawable.f8)),2);
//        DBfoody.insertFood(9, new Food("Salad xoài","Dessert",16000,toByteArray(R.drawable.f9)),2);
//        DBfoody.insertFood(10, new Food("Nước rau má","Drink",13000,toByteArray(R.drawable.f10)),2);
//        //nha hang 3
//        DBfoody.insertFood(11, new Food("Xá xíu sườn non chay","Dry food",25000,toByteArray(R.drawable.f11)),3);
//        DBfoody.insertFood(12, new Food("Tàu hũ ki cuộn rau ngũ sắc","Dry food",25000,toByteArray(R.drawable.f12)),3);
//        DBfoody.insertFood(13, new Food("Canh chua chay","Soup",20000,toByteArray(R.drawable.f13)),3);
//        DBfoody.insertFood(14, new Food("Chè khoai môn","Dessert",16000,toByteArray(R.drawable.f14)),3);
//        DBfoody.insertFood(15, new Food("Nước sâm","Drink",13000,toByteArray(R.drawable.f15)),3);
//        //nha hang 4
//        DBfoody.insertFood(16, new Food("Heo quay chay","Dry food",25000,toByteArray(R.drawable.f16)),4);
//        DBfoody.insertFood(17, new Food("Xíu mại chay","Dry food",25000,toByteArray(R.drawable.f17)),4);
//        DBfoody.insertFood(18, new Food("Canh khổ qua hầm chay","Soup",20000,toByteArray(R.drawable.f18)),4);
//        DBfoody.insertFood(19, new Food("Salad cải mầm","Dessert",16000,toByteArray(R.drawable.f19)),4);
//        DBfoody.insertFood(20, new Food("Trà xanh","Drink",13000,toByteArray(R.drawable.f20)),4);
//        //nha hang 5
//        DBfoody.insertFood(21, new Food("Tôm chay chiên giòn","Dry food",25000,toByteArray(R.drawable.f21)),5);
//        DBfoody.insertFood(22, new Food("Chả lá lốt chay","Dry food",25000,toByteArray(R.drawable.f22)),5);
//        DBfoody.insertFood(23, new Food("Lẩu nấm","Soup",20000,toByteArray(R.drawable.f23)),5);
//        DBfoody.insertFood(24, new Food("Chè mít","Dessert",16000,toByteArray(R.drawable.f24)),5);
//        DBfoody.insertFood(25, new Food("Nước chanh tươi","Drink",13000,toByteArray(R.drawable.f25)),5);
//        //nha hang 6
//        DBfoody.insertFood(26, new Food("Chả bắp chay chiên giòn","Dry food",25000,toByteArray(R.drawable.f26)),6);
//        DBfoody.insertFood(27, new Food("Hoa chuối chiên giòn sốt me","Dry food",25000,toByteArray(R.drawable.f27)),6);
//        DBfoody.insertFood(28, new Food("Canh hẹ đậu hũ non","Soup",20000,toByteArray(R.drawable.f28)),6);
//        DBfoody.insertFood(29, new Food("Rau câu sơn thủy","Dessert",16000,toByteArray(R.drawable.f29)),6);
//        DBfoody.insertFood(30, new Food("Nước cam","Drink",13000,toByteArray(R.drawable.f30)),6);
//        //nha hang 7
//        DBfoody.insertFood(31, new Food("Cà tím nướng mỡ hành chay","Dry food",25000,toByteArray(R.drawable.f31)),7);
//        DBfoody.insertFood(32, new Food("Mỳ căn xào sả ớt chay","Dry food",25000,toByteArray(R.drawable.f32)),7);
//        DBfoody.insertFood(33, new Food("Canh đu đủ nấm rơm","Soup",20000,toByteArray(R.drawable.f33)),7);
//        DBfoody.insertFood(34, new Food("Rau câu dừa","Dessert",16000,toByteArray(R.drawable.f34)),7);
//        DBfoody.insertFood(35, new Food("Nước cam","Drink",13000,toByteArray(R.drawable.f35)),7);
//        //nha hang 8
//        DBfoody.insertFood(36, new Food("Đậu non sốt nấm chay","Dry food",25000,toByteArray(R.drawable.f36)),8);
//        DBfoody.insertFood(37, new Food("Heo quay chay","Dry food",25000,toByteArray(R.drawable.f37)),8);
//        DBfoody.insertFood(38, new Food("Canh hẹ đậu hũ non","Soup",20000,toByteArray(R.drawable.f38)),8);
//        DBfoody.insertFood(39, new Food("Salad ớt chuông","Dessert",16000,toByteArray(R.drawable.f39)),8);
//        DBfoody.insertFood(40, new Food("Nước nha đam","Drink",13000,toByteArray(R.drawable.f40)),8);
//        //nha hang 9
//        DBfoody.insertFood(41, new Food("Hoa chuối chiên giòn sốt me","Dry food",25000,toByteArray(R.drawable.f41)),9);
//        DBfoody.insertFood(42, new Food("Cà chua nhồi đậu hũ","Dry food",25000,toByteArray(R.drawable.f42)),9);
//        DBfoody.insertFood(43, new Food("Canh chua chay","Soup",20000,toByteArray(R.drawable.f43)),9);
//        DBfoody.insertFood(44, new Food("Rau câu sơn thủy","Dessert",16000,toByteArray(R.drawable.f44)),9);
//        DBfoody.insertFood(45, new Food("Nước rau má","Drink",13000,toByteArray(R.drawable.f45)),9);
//        //nha hang 10
//        DBfoody.insertFood(46, new Food("Nấm đùi gà xào","Dry food",25000,toByteArray(R.drawable.f46)),10);
//        DBfoody.insertFood(47, new Food("Xá xíu sườn non chay","Dry food",25000,toByteArray(R.drawable.f47)),10);
//        DBfoody.insertFood(48, new Food("Lẩu nấm","Soup",20000,toByteArray(R.drawable.f48)),10);
//        DBfoody.insertFood(49, new Food("Salad bắp cải tím","Dessert",16000,toByteArray(R.drawable.f49)),10);
//        DBfoody.insertFood(50, new Food("Trà tắc","Drink",13000,toByteArray(R.drawable.f50)),10);

    }

    private byte[] toByteArray(int id){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id);
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
        byte[] img = byteArray.toByteArray();
        return img;
    }
}