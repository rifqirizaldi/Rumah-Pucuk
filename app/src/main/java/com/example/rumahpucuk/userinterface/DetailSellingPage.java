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

public class DetailSellingPage extends AppCompatActivity {

    public ImageView btn_back;
    public TextView txt_id, txt_customer_name,
            txt_selling_item, txt_selling_amount, txt_total_payment, txt_payment_status,
            txt_payment_recevied, txt_payment_left, txt_selling_date, txt_delivery_status;
    public DatabaseReference db;
    public Intent intent;
    public String id;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.detail_selling_page);
        btn_back = findViewById(R.id.img_back_from_detail_selling);
        txt_id = findViewById(R.id.txt_id_from_detail_selling);
        txt_total_payment = findViewById(R.id.txt_payment_amount_from_detail_selling);
        txt_selling_amount = findViewById(R.id.txt_selling_amount_from_detail_selling);
        txt_payment_status = findViewById(R.id.txt_payment_staus_from_detail_selling);
        txt_payment_left = findViewById(R.id.txt_recevied_payment_left_from_detail_selling);
        txt_customer_name = findViewById(R.id.txt_customer_name_from_detail_selling);
        txt_selling_item = findViewById(R.id.txt_selling_item_from_detail_selling);
        txt_payment_recevied = findViewById(R.id.txt_recevied_payment_from_detail_selling);
        txt_selling_date = findViewById(R.id.txt_selling_date_from_detail_selling);
        txt_delivery_status = findViewById(R.id.txt_delivery_status_from_detail_selling);
        db = FirebaseDatabase.getInstance().getReference("sellingActivity");

        intent = getIntent();
        id = intent.getStringExtra("Id");
        readDatabase(db, id);


        btn_back.setOnClickListener(view ->
                startActivity(new Intent(DetailSellingPage.this, HistoryPage.class)));
    }

    private void readDatabase(DatabaseReference db, String id) {
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    if (ds.child("Id").getValue(String.class).equals(id)){
                        txt_id.setText(ds.child("Id").getValue(String.class));
                        txt_customer_name.setText(ds.child("Customer Name").getValue(String.class));
                        txt_selling_item.setText(ds.child("Order Item Name").getValue(String.class));
                        txt_selling_date.setText(ds.child("Date").getValue(String.class));
                        txt_selling_amount.setText(ds.child("Amount Item").getValue(String.class));
                        txt_payment_status.setText(ds.child("Status Payment").getValue(String.class));
                        txt_total_payment.setText(ds.child("Total Payment").getValue(String.class));
                        txt_payment_recevied.setText(ds.child("Total Recevied Payment").getValue(String.class));
                        txt_payment_left.setText(ds.child("Total Debt").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}