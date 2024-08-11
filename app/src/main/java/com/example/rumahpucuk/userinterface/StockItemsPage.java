package com.example.rumahpucuk.userinterface;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rumahpucuk.R;

public class StockItemsPage extends AppCompatActivity {

    public ImageView img_back;
    public LinearLayout layout_add_new_item, layout_update_stocks;
    public SearchView searchView;
//    public RecyclerView rv_stock;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.stock_items_page);
        img_back = findViewById(R.id.img_back_from_stock_items);
        layout_update_stocks = findViewById(R.id.layout_update_stock);
        layout_add_new_item = findViewById(R.id.layout_tambah_jenis_barang_baru);
        searchView = findViewById(R.id.searchview_stock_item);
//        rv_stock = findViewById(R.id.rv_list_stock);

        img_back.setOnClickListener(view -> startActivity(new Intent(StockItemsPage.this, Homepage.class)));
        layout_update_stocks.setOnClickListener(view -> startActivity(new Intent(StockItemsPage.this, UpdateStockItemPage.class)));
        layout_add_new_item.setOnClickListener(view -> startActivity(new Intent(StockItemsPage.this, AddNewTypeItemsPage.class)));

    }
}