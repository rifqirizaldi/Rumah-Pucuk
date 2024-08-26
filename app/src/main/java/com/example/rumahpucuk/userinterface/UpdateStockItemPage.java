package com.example.rumahpucuk.userinterface;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rumahpucuk.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UpdateStockItemPage extends AppCompatActivity {

    public ImageView img_back;
    public EditText ed_amount,ed_price,ed_stock_origin, ed_origin_address;
    public Button btn_send, btn_refresh;
    public Spinner spinner_stockItems;
    public DatabaseReference db, dbPurchasing;
    public ValueEventListener listener_stockItems;
    public ArrayList<String> list_stockItems = new ArrayList<>();
    public ArrayList<String> list_amount = new ArrayList<>();
    public ArrayList<String> list_price = new ArrayList<>();
    public ArrayAdapter adapter;
    public String value, amount, price, stockOrigin, originAddress;
    public Integer temp_amout, total_price;
    public String dateTime, tanggal, tanggal2, upperName;
    public SimpleDateFormat simpleDateFormat;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.update_stock_item_page);
        img_back = findViewById(R.id.img_back_from_update_stock);
        ed_amount = findViewById(R.id.ed_stock_amount_from_update_stock);
        spinner_stockItems = findViewById(R.id.spinner_item_name_from_update_stock);
        ed_price = findViewById(R.id.ed_stock_price_from_update_stock);
        ed_origin_address = findViewById(R.id.ed_origin_address_from_update_stock);
        ed_stock_origin = findViewById(R.id.ed_stock_origin_from_update_stock);
        btn_send = findViewById(R.id.btn_kirim_from_update_stock);
        btn_refresh = findViewById(R.id.btn_refresh_from_update_stock);
        db = FirebaseDatabase.getInstance().getReference("stockItems");
        dbPurchasing = FirebaseDatabase.getInstance().getReference();

        readDatabase();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,list_stockItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_stockItems.setAdapter(adapter);
        spinner_stockItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                value = adapterView.getSelectedItem().toString();
                btn_refresh.performClick();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        img_back.setOnClickListener(view ->
                startActivity(new Intent(
                        UpdateStockItemPage.this, StockItemsPage.class)));

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPrice(value);
            }
        });
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount = ed_amount.getText().toString();
                price = ed_price.getText().toString();
                stockOrigin = ed_stock_origin.getText().toString();
                originAddress = ed_origin_address.getText().toString();
                executeData(amount, value, price, stockOrigin, originAddress);
            }
        });
    }

    private void checkPrice(String value) {
        for (int b = 0; b<list_stockItems.size();b++){
            if (list_stockItems.get(b).equals(value)){
                price = list_price.get(b);
                ed_price.setText(price);
            }
        }
    }

    private void readDatabase() {
        Query query = db.orderByChild("Name Item");
        listener_stockItems = query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    list_stockItems.add(ds.child("Name Item").getValue().toString());
                    list_amount.add(ds.child("Amount").getValue().toString());
                    list_price.add(ds.child("Price").getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void executeData(String amount, String value, String price, String stockOrigin, String originAddress) {
        for (int a=0; a<list_stockItems.size();a++){
            if (list_stockItems.get(a).equals(value)){
                tanggal = setDate(); tanggal2 = setDate2();
                temp_amout = Integer.valueOf(list_amount.get(a));
                temp_amout += Integer.valueOf(amount);
                total_price = Integer.valueOf(amount) * Integer.valueOf(price);

                db = FirebaseDatabase.getInstance().getReference("stockItems");
                db.child(value.toUpperCase()).child("Amount").setValue(String.valueOf(temp_amout));
                db.child(value.toUpperCase()).child("Price").setValue(price);

                dbPurchasing = FirebaseDatabase.getInstance().getReference("purchasingActivity");
                dbPurchasing.child(tanggal2+"_ADMIN").child("Supplier Name").setValue(stockOrigin);
                dbPurchasing.child(tanggal2+"_ADMIN").child("Supplier Address").setValue(originAddress);
                dbPurchasing.child(tanggal2+"_ADMIN").child("Name Item").setValue(value);
                dbPurchasing.child(tanggal2+"_ADMIN").child("Order By").setValue("ADMIN");
                dbPurchasing.child(tanggal2+"_ADMIN").child("Purchase Price").setValue(String.valueOf(total_price));
                dbPurchasing.child(tanggal2+"_ADMIN").child("Date Purchase").setValue(tanggal);
                dbPurchasing.child(tanggal2+"_ADMIN").child("DateKey").setValue(tanggal2);
                dbPurchasing.child(tanggal2+"_ADMIN").child("Amount").setValue(amount);
                dbPurchasing.child(tanggal2+"_ADMIN").child("Transaction Type").setValue("PURCHASING");

                startActivity(new Intent(UpdateStockItemPage.this, StockItemsPage.class));
            }
        }
    }

    private String setDate2() {
//        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("ddLLLLyyyy");
        dateTime = simpleDateFormat.format(new Date());
        return  dateTime;
    }

    private String setDate() {
//        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("ddLLLLyyyy HHmmss aaa z");
        dateTime = simpleDateFormat.format(new Date());
        return  dateTime;
    }
}