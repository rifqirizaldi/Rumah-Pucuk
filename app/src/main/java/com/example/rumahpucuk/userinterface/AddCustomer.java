package com.example.rumahpucuk.userinterface;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rumahpucuk.R;
import com.example.rumahpucuk.adapter.Adapter_rv_customer;
import com.example.rumahpucuk.model_class.Model_customer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddCustomer extends AppCompatActivity {

    public SearchView searchView;
    public RecyclerView rv_customer;
    public ImageView img_back;
    public LinearLayout layout_add_new_customer;
    DatabaseReference database;
    Adapter_rv_customer adapter;
    ArrayList<Model_customer> list;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.add_customer);
        searchView = findViewById(R.id.searchview_customer);
        rv_customer = findViewById(R.id.rv_list_customer);
        img_back = findViewById(R.id.img_back_from_customer);
        layout_add_new_customer = findViewById(R.id.layout_tambah_customer);
        database = FirebaseDatabase.getInstance().getReference();

        img_back.setOnClickListener(view ->
                startActivity(new Intent(AddCustomer.this, Homepage.class)));
        layout_add_new_customer.setOnClickListener(view ->
                startActivity(new Intent(AddCustomer.this, AddNewCustomer.class)));

        rv_customer.setHasFixedSize(true);
        rv_customer.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new Adapter_rv_customer(this, list);
        rv_customer.setAdapter(adapter);

        database.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.child("customer").getChildren()){
                    String name = data.child("Nama").getValue(String.class);
                    String address = data.child("Alamat").getValue(String.class);
                    String phone = data.child("No Telp").getValue(String.class);

                    Model_customer customer = new Model_customer(name,address,phone);
                    list.add(customer);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}