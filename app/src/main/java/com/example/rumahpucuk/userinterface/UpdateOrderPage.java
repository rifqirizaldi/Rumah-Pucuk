package com.example.rumahpucuk.userinterface;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rumahpucuk.R;

public class UpdateOrderPage extends AppCompatActivity {

    public ImageView img_back;
    public SearchView searchView;
    public RecyclerView rv_update_order;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.update_order_page);
        img_back = findViewById(R.id.img_back_from_update_order);
        searchView = findViewById(R.id.searchview_update_order);
        rv_update_order = findViewById(R.id.rv_list_order_from_update_order);

        img_back.setOnClickListener(view -> startActivity(new Intent(UpdateOrderPage.this, Homepage.class)));
    }
}