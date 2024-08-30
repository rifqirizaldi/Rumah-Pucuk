package com.example.rumahpucuk.userinterface;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rumahpucuk.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddNewTypeItemsPage extends AppCompatActivity {

    public ImageView img_back;
    public EditText ed_name,ed_amount,ed_price;
    public Button btn_send;
    private DatabaseReference database;
    public String nameItems,price,amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.add_new_type_items_page);
        img_back = findViewById(R.id.img_back_from_add_new_item);
        ed_amount = findViewById(R.id.ed_stock_amount_from_add_new_item);
        ed_name = findViewById(R.id.ed_item_name_from_add_new_item);
        ed_price = findViewById(R.id.ed_stock_price_from_add_new_item);
        btn_send = findViewById(R.id.btn_kirim_from_add_new_item);
        database = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://rumah-pucuk-c1737-default-rtdb.firebaseio.com/");

        img_back.setOnClickListener(view -> startActivity(new Intent(AddNewTypeItemsPage.this, StockItemsPage.class)));

        // btn_send.setOnClickListener(view -> executeData());

        //Firebase
        btn_send.setOnClickListener(view -> {
            nameItems = ed_name.getText().toString();
            price = ed_price.getText().toString();
            amount = ed_amount.getText().toString();
            executeData(nameItems,price,amount);
        });
    }

    private void executeData(String nameItems, String price, String amount) {
        database = FirebaseDatabase.getInstance().getReference("stockItems");
        database.child(nameItems.toUpperCase()).child("Name Item").setValue(nameItems.toUpperCase());
        database.child(nameItems.toUpperCase()).child("Price").setValue(price);
        database.child(nameItems.toUpperCase()).child("Amount").setValue(amount);
        Toast.makeText(getApplicationContext(),
                "Penambahan Item berhasil",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(AddNewTypeItemsPage.this, StockItemsPage.class));
    }
}