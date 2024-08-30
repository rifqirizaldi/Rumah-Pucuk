package com.example.rumahpucuk.userinterface;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rumahpucuk.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailPurchasingPage extends AppCompatActivity {

    public ImageView btn_back;
    public TextView txt_id, txt_supplier_name, txt_supplier_contact, txt_supplier_address;
    public TextView txt_purchased_date, txt_purchased_item, txt_purchased_amount;
    public TextView txt_payment_status, txt_payment_amount;
    public Intent intent;
    public String id;
    public DatabaseReference db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.detail_purchasing_page);
        btn_back = findViewById(R.id.img_back_from_detail_purchasing);
        txt_id = findViewById(R.id.txt_id_from_detail_purchasing);
        txt_supplier_name = findViewById(R.id.txt_supplier_name_from_detail_purchasing);
        txt_supplier_address = findViewById(R.id.txt_supplier_addres_from_detail_purchasing);
        txt_supplier_contact = findViewById(R.id.txt_supplier_contact_from_detail_purchasing);
        txt_purchased_date = findViewById(R.id.txt_purchased_date_from_detail_purchasing);
        txt_purchased_item = findViewById(R.id.txt_purchased_item_from_detail_purchasing);
        txt_purchased_amount = findViewById(R.id.txt_purchased_amount_from_detail_purchasing);
        txt_payment_status = findViewById(R.id.txt_payment_status_from_detail_purchasing);
        txt_payment_amount = findViewById(R.id.txt_payment_amount_from_detail_purchasing);
        db = FirebaseDatabase.getInstance().getReference("purchasingActivity");

        intent = getIntent();
        id = intent.getStringExtra("Id");
        readDatabase(db, id);

        btn_back.setOnClickListener(view ->
                startActivity(new Intent(DetailPurchasingPage.this, HistoryPage.class)));
    }

    private void readDatabase(DatabaseReference db, String id) {
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    if (ds.child("Id").getValue(String.class).equals(id)){
                        txt_id.setText(ds.child("Id").getValue(String.class));
                        txt_supplier_name.setText(ds.child("Supplier Name").getValue(String.class));
                        txt_supplier_contact.setText(ds.child("Supplier Contact").getValue(String.class));
                        txt_supplier_address.setText(ds.child("Supplier Address").getValue(String.class));
                        txt_purchased_item.setText(ds.child("Name Item").getValue(String.class));
                        txt_purchased_date.setText(ds.child("DateKey").getValue(String.class));
                        txt_purchased_amount.setText(ds.child("Amount").getValue(String.class));
                        txt_payment_status.setText(ds.child("Payment Status").getValue(String.class));
                        txt_payment_amount.setText(ds.child("Purchase Price").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}