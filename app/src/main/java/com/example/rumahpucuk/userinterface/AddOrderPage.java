package com.example.rumahpucuk.userinterface;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rumahpucuk.R;

import java.util.ArrayList;
import java.util.List;

public class AddOrderPage extends AppCompatActivity {

    public ImageView img_back;
    public LinearLayout layout_selling, layout_buying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.add_order_page);
        img_back = findViewById(R.id.img_back_from_add_order);
        layout_buying = findViewById(R.id.layout_pembelian);
        layout_selling = findViewById(R.id.layout_penjualan);

        img_back.setOnClickListener(view ->
                startActivity(new Intent(AddOrderPage.this, Homepage.class)));

        layout_selling.setOnClickListener(view ->
                startActivity(new Intent(AddOrderPage.this, SellingPage.class)));
    }

}