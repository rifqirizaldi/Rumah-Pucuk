package com.example.rumahpucuk.userinterface;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rumahpucuk.R;

public class AddNewTypeItemsPage extends AppCompatActivity {

    public ImageView img_back;
    public EditText ed_name,ed_amount,ed_price;
    public Button btn_send;

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

        img_back.setOnClickListener(view -> startActivity(new Intent(AddNewTypeItemsPage.this, StockItemsPage.class)));
        btn_send.setOnClickListener(view -> executeData());
    }

    private void executeData() {
        startActivity(new Intent(AddNewTypeItemsPage.this, StockItemsPage.class));
    }
}