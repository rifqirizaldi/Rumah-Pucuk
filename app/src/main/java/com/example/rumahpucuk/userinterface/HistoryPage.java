package com.example.rumahpucuk.userinterface;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rumahpucuk.R;
import com.example.rumahpucuk.adapter.Adapter_rv_history;
import com.example.rumahpucuk.model_class.Model_history;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryPage extends AppCompatActivity {

    public ImageView img_back;
    public RecyclerView rv_history;
    public SearchView searchView;
    public Spinner spinner_filter;
    public DatabaseReference dbSelling, dbPurchasing;
    public ArrayList<Model_history> list = new ArrayList<>();
    public ArrayList<String> list_filter = new ArrayList<>();
    public Adapter_rv_history adapter_rv;
    public ArrayAdapter adapter_spinner;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.history_page);
        img_back = findViewById(R.id.img_back_from_history);
        rv_history = findViewById(R.id.rv_list_order_from_history);
        searchView = findViewById(R.id.searchview_history);
        spinner_filter = findViewById(R.id.spinner_filter_from_history);

        readDatabase();
        img_back.setOnClickListener(view ->
                startActivity(new Intent(HistoryPage.this, Homepage.class)));

        //Spinner filter
        list_filter.add("-TANPA FILTER-");
        list_filter.add("BERDASARKAN PESANAN TERLAMA");
        list_filter.add("BERDASARKAN PESANAN TERBARU");
        list_filter.add("TRANSAKSI SATU BULAN TERAKHIR");
        list_filter.add("TRANSAKSI TIGA BULAN TERAKHIR");
        adapter_spinner =  new ArrayAdapter(this,android.R.layout.simple_spinner_item,list_filter);
        adapter_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_filter.setAdapter(adapter_spinner);

        //Recyclerview
        rv_history.setHasFixedSize(true);
        rv_history.setLayoutManager(new LinearLayoutManager(this));
        adapter_rv = new Adapter_rv_history(this, list);
        rv_history.setAdapter(adapter_rv);

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

    }

    private void filterList(String Text) {
        ArrayList<Model_history> filteredList = new ArrayList<>();
        for (Model_history model:list){
            if (model.getName_order().contains(Text.toUpperCase())){
                filteredList.add(model);
            }
        }

        adapter_rv.filterListHistory(filteredList);
    }

    private void readDatabase() {
        dbPurchasing = FirebaseDatabase.getInstance().getReference("purchasingActivity");
        dbPurchasing.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    String name_order = ds.child("Id").getValue(String.class);
                    String delivery_status = ds.child("Delivery Status").getValue(String.class);
                    String payment_amount = ds.child("Purchase Price").getValue(String.class);
                    String payment_status = ds.child("Payment Status").getValue(String.class);
                    String transaction_status = ds.child("Transaction Type").getValue(String.class);
                    Model_history model = new Model_history(name_order,delivery_status, payment_amount, payment_status,transaction_status);
                    list.add(model);
                }
                adapter_rv.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dbSelling = FirebaseDatabase.getInstance().getReference("sellingActivity");
        dbSelling.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    String name_order = ds.child("Id").getValue(String.class);
                    String delivery_status = ds.child("Delivery Status").getValue(String.class);
                    String payment_amount = ds.child("Total Payment").getValue(String.class);
                    String payment_status = ds.child("Status Payment").getValue(String.class);
                    String transaction_status = ds.child("Transaction Type").getValue(String.class);
                    Model_history model = new Model_history(name_order,delivery_status, payment_amount, payment_status,transaction_status);
                    list.add(model);
                }
                adapter_rv.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}