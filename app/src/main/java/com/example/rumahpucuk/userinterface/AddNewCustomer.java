package com.example.rumahpucuk.userinterface;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rumahpucuk.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewCustomer extends AppCompatActivity {

    public ImageView img_back;
    public EditText ed_customer_name,ed_customer_address,ed_customer_phone;
    public Button btn_kirim;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.add_new_customer);

        img_back = findViewById(R.id.img_back_from_add_new_customer);
        ed_customer_address = findViewById(R.id.ed_cutomer_address_from_add_new_customer);
        ed_customer_name = findViewById(R.id.ed_customer_name_from_add_new_customer);
        ed_customer_phone = findViewById(R.id.ed_phone_number_from_add_new_customer);
        btn_kirim = findViewById(R.id.btn_kirim_from_add_new_customer);

        img_back.setOnClickListener(view ->
                startActivity(new Intent(AddNewCustomer.this, AddCustomer.class)));
        btn_kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ed_customer_name.getText().toString();
                String address = ed_customer_address.getText().toString();
                String phone = ed_customer_phone.getText().toString();
                addCustomerData(name,address,phone,database);
            }
        });

    }

    private void addCustomerData(String name, String address, String phone, DatabaseReference database) {
        if (name.isEmpty() || address.isEmpty() || phone.isEmpty()){
            Toast.makeText(this, "Data belum lengkap", Toast.LENGTH_SHORT).show();
        } else if (name.contains(".")) {
            ed_customer_name.setError("Nama tidak boleh ada titik");
        } else if (address.contains(".")) {
            ed_customer_address.setError("Alamat tidak boleh ada titik");
        } else{
            database = FirebaseDatabase.getInstance().getReference("customer");
            database.child(name.toUpperCase()).child("Nama").setValue(name.toUpperCase());
            database.child(name.toUpperCase()).child("Alamat").setValue(address);
            database.child(name.toUpperCase()).child("No Telp").setValue(phone);
            startActivity(new Intent(AddNewCustomer.this, AddCustomer.class));
        }
    }
}