package hcmute.spkt.phamvietanh19110151.foodyui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class CustomerProfileActivity extends AppCompatActivity {

    EditText etName, etPhone, etAddress, etPass;
    Button btnUpdate;
    ImageButton btnBack;
    User user;
    UserLocalStore localStore;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.customer_profile);

        btnBack = findViewById(R.id.btnBackYourProfile);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        localStore = new UserLocalStore(this);
        user = localStore.getUser();
        DB = new DBHelper(this);

        etName = findViewById(R.id.etFullName);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        etPass = findViewById(R.id.etPassword);

        etName.setText(user.getName());
        etPhone.setText(user.getPhone());
        etAddress.setText(user.getAddress());
        etPass.setText(user.getPass());

        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = etPhone.getText().toString();
                String name = etName.getText().toString();
                String address = etAddress.getText().toString();
                String pass = etPass.getText().toString();
                user = new User(phone, name, pass, address);
                
                if (DB.updateUser(user)) {
                    Toast.makeText(CustomerProfileActivity.this, "Updated successfully!", Toast.LENGTH_SHORT).show();
                    localStore.storeUser(user);
                }
                else {
                    Toast.makeText(CustomerProfileActivity.this, "Updated failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}