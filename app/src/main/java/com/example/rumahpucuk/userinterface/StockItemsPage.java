package com.example.rumahpucuk.userinterface;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rumahpucuk.R;
import com.example.rumahpucuk.adapter.Adapter_rv_stock_items;
import com.example.rumahpucuk.model_class.Model_stock_items;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StockItemsPage extends AppCompatActivity {

    public ImageView img_back;
    public LinearLayout layout_add_new_item, layout_update_stocks;
    public SearchView searchView;
    public RecyclerView rv_stock;
    DatabaseReference database;
    Adapter_rv_stock_items adapter;
    ArrayList<Model_stock_items> list;

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
        rv_stock = findViewById(R.id.rv_list_stock_items);
        database = FirebaseDatabase.getInstance().getReference();

        img_back.setOnClickListener(view -> startActivity(new Intent(StockItemsPage.this, Homepage.class)));
        layout_update_stocks.setOnClickListener(view -> startActivity(new Intent(StockItemsPage.this, UpdateStockItemPage.class)));
        layout_add_new_item.setOnClickListener(view -> startActivity(new Intent(StockItemsPage.this, AddNewTypeItemsPage.class)));

        //Recyclerview
        rv_stock.setHasFixedSize(true);
        rv_stock.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new Adapter_rv_stock_items(this, list);
        rv_stock.setAdapter(adapter);

        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        //Firebase
        database.addValueEventListener(new ValueEventListener() {

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.child("stockItems").getChildren()){
                    String nameItem = dataSnapshot.child("Name Item").getValue(String.class);
                    String priceItem = dataSnapshot.child("Price").getValue(String.class);
                    String amountItem = dataSnapshot.child("Amount").getValue(String.class);

                    Model_stock_items stockItems = new Model_stock_items(nameItem,amountItem,priceItem);
                    list.add(stockItems);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void filterList(String newText) {
        ArrayList<Model_stock_items> filteredList = new ArrayList<>();
        for (Model_stock_items model:list){
            if (model.getNameItems().toUpperCase().contains(newText.toUpperCase())){
                filteredList.add(model);
            }
        }

        adapter.filterListStockItems(filteredList);
    }
}