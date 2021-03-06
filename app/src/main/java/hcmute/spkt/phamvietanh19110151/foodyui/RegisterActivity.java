package hcmute.spkt.phamvietanh19110151.foodyui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hcmute.spkt.phamvietanh19110151.foodyui.Database.DBHelper;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.User;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.UserLocalStore;

public class RegisterActivity extends AppCompatActivity {

    EditText uNameRe, uPhoneRe, uPassRe, uAddressRe;
    Button btnSignUp, btnGoToLogin;
    DBHelper DB;
    User user;
    UserLocalStore localStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.higreen_register);

        //anh xa
        uNameRe = (EditText) findViewById(R.id.editTxtNameRe);
        uPhoneRe = (EditText) findViewById(R.id.editTxtPhoneRe);
        uAddressRe = (EditText) findViewById(R.id.editTxtAddressRe);
        uPassRe = (EditText) findViewById(R.id.editTxtPasswordRe);
        btnSignUp = (Button) findViewById(R.id.btnRegister);
        btnGoToLogin = (Button) findViewById(R.id.btnGoToSignIn);
        DB = new DBHelper(this);

        //
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = uPhoneRe.getText().toString();
                String name = uNameRe.getText().toString();
                String pass = uPassRe.getText().toString();
                String address = uAddressRe.getText().toString();

                if (phone.equals("") || name.equals("") || pass.equals("") || pass.equals("") || address.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkExistPhone = DB.checkExistPhone(phone);
                    if (checkExistPhone == false) {
                        user = new User(phone, name, pass, address);
                        Boolean insert = DB.insertUser(user);
                        if (insert == true) {
                            Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                            localStore.storeUser(user);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Phone already existed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        //
        btnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}