package com.example.rumahpucuk.userinterface;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rumahpucuk.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddOrderPage extends AppCompatActivity {

    public ImageView img_back;
    public LinearLayout layout_selling, layout_buying;
    DatabaseReference database;
    public ArrayList<String> list_stock_name,list_stock_price,list_stock_amount, list_customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.add_order_page);
        img_back = findViewById(R.id.img_back_from_add_order);
        layout_buying = findViewById(R.id.layout_pembelian);
        layout_selling = findViewById(R.id.layout_penjualan);
        database = FirebaseDatabase.getInstance().getReference();

//
//        database.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                list_stock_name = new ArrayList<>();
//                list_customer = new ArrayList<>();
//                list_stock_price = new ArrayList<>();
//                list_stock_amount = new ArrayList<>();
//                for (DataSnapshot dataSnapshot : snapshot.child("stockItems").getChildren()){
//                    String nameItem = dataSnapshot.child("Name Item").getValue(String.class);
//                    String priceItem = dataSnapshot.child("Price").getValue(String.class);
//                    String amountItem = dataSnapshot.child("Amount").getValue(String.class);
//
////                    Model_stock_items stockItems = new Model_stock_items(nameItem,amountItem,priceItem);
////                    list_stock.add(stockItems);
//                    list_stock_name.add(nameItem);
//                    list_stock_amount.add(amountItem);
//                    list_stock_price.add(priceItem);
//                }
//
//                for (DataSnapshot data : snapshot.child("customer").getChildren()){
//                    String name = data.child("Nama").getValue(String.class);
//                    list_customer.add(name);
//                }
//
//                Intent intent = getIntent();
//                intent.putExtra("list_stock_name",list_stock_name);
//                intent.putExtra("list_stock_price",list_stock_price);
//                intent.putExtra("list_stock_amount", list_stock_amount);
//                intent.putExtra("list_customer",list_customer);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        img_back.setOnClickListener(view ->
                startActivity(new Intent(AddOrderPage.this, Homepage.class)));

        layout_selling.setOnClickListener(view ->
                startActivity(new Intent(AddOrderPage.this, SellingPage.class)));

    }

}