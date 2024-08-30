package com.example.rumahpucuk.userinterface;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rumahpucuk.R;
import com.example.rumahpucuk.adapter.Adapter_rv_update_order;
import com.example.rumahpucuk.model_class.Model_history;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UpdateOrderPage extends AppCompatActivity {

    public ImageView img_back;
    public SearchView searchView;
    public RecyclerView rv_update_order;
    public DatabaseReference db;
    public Adapter_rv_update_order adapter;
    public ArrayList<Model_history> list = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.update_order_page);
        img_back = findViewById(R.id.img_back_from_update_order);
        searchView = findViewById(R.id.searchview_update_order);
        rv_update_order = findViewById(R.id.rv_list_order_from_update_order);

        readDatabase();

        //Recyclerview
        rv_update_order.setHasFixedSize(true);
        rv_update_order.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter_rv_update_order(this, list);
        rv_update_order.setAdapter(adapter);

        img_back.setOnClickListener(view ->
                startActivity(new Intent(UpdateOrderPage.this, Homepage.class)));

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

    private void filterList(String newText) {
        ArrayList<Model_history> filteredList = new ArrayList<>();
        for (Model_history model:list){
            if (model.getName_order().contains(newText.toUpperCase())){
                filteredList.add(model);
            }
        }

        adapter.filterListUpdateOrder(filteredList);
    }

    private void readDatabase() {
        db = FirebaseDatabase.getInstance().getReference("sellingActivity");
        db.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    if (ds.child("Status Payment").getValue(String.class).equals("HUTANG") ||
                    ds.child("Delivery Status").getValue(String.class).equals("BELUM DIKIRIM")){
                        String name_order = ds.child("Id").getValue(String.class);
                        String delivery_status = ds.child("Delivery Status").getValue(String.class);
                        String payment_amount = ds.child("Total Payment").getValue(String.class);
                        String payment_status = ds.child("Status Payment").getValue(String.class);
                        String transaction_status = ds.child("Transaction Type").getValue(String.class);
                        Model_history model = new Model_history(name_order,delivery_status, payment_amount, payment_status, transaction_status);
                        list.add(model);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}