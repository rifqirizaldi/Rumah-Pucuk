package com.example.rumahpucuk.userinterface;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rumahpucuk.R;

public class HistoryPage extends AppCompatActivity {

    public ImageView img_back;
    public RecyclerView rv_history;
    public SearchView searchView;
    public Button btn_apply_filter,btn_reset_filter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.history_page);
        img_back = findViewById(R.id.img_back_from_history);
        rv_history = findViewById(R.id.rv_list_order_from_history);
        searchView = findViewById(R.id.searchview_history);
        btn_apply_filter = findViewById(R.id.btn_apply_filter_from_history);
        btn_reset_filter = findViewById(R.id.btn_reset_filter_from_history);

        img_back.setOnClickListener(view -> startActivity(new Intent(HistoryPage.this, Homepage.class)));


    }
}