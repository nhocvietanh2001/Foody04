package hcmute.spkt.phamvietanh19110151.foodyui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
}