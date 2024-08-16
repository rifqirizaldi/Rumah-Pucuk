package com.example.rumahpucuk.userinterface;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rumahpucuk.R;

public class SellingPage extends AppCompatActivity {

    public ImageView img_back;
    public Button btn_add, btn_send;
    public EditText ed_customer,ed_total_payment,ed_address,ed_recevied_money;
    public TextView txt_recevied_money;
    public Spinner spinner;
    final String[] status = {"Hutang", "Lunas"};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.selling_page);
        img_back = findViewById(R.id.img_back_from_selling);
        btn_add = findViewById(R.id.btn_add_order_items);
        btn_send = findViewById(R.id.btn_kirim_from_selling);
        ed_address = findViewById(R.id.ed_delivery_address_from_selling);
        ed_customer = findViewById(R.id.ed_customer_name_from_selling);
        ed_recevied_money = findViewById(R.id.ed_recevied_payment_from_selling);
        ed_total_payment = findViewById(R.id.ed_total_payment_from_selling);
        txt_recevied_money = findViewById(R.id.txt_recevied_payment_from_selling);
        spinner = findViewById(R.id.spiinner_payment_status);

        img_back.setOnClickListener(view ->
                startActivity(new Intent(SellingPage.this, AddOrderPage.class)));
    }
}