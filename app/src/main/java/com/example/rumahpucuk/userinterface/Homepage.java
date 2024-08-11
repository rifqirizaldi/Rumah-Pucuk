package com.example.rumahpucuk.userinterface;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rumahpucuk.R;

public class Homepage extends AppCompatActivity {

    public LinearLayout layout_stock,layout_order,layout_history,layout_update_order;
    public ImageView img_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.homepage);
        layout_order = findViewById(R.id.layout_tambah_pesanan);
        layout_history = findViewById(R.id.layout_history);
        layout_update_order = findViewById(R.id.layout_update_pesanan);
        layout_stock = findViewById(R.id.layout_stok_barang);
        img_logout = findViewById(R.id.img_logout);

        img_logout.setOnClickListener(view -> PopupLogOut());
        layout_stock.setOnClickListener(view ->
                startActivity(new Intent(Homepage.this, StockItemsPage.class)));
        layout_history.setOnClickListener(view ->
                startActivity(new Intent(Homepage.this, HistoryPage.class)));
        layout_update_order.setOnClickListener(view ->
                startActivity(new Intent(Homepage.this, UpdateOrderPage.class)));
    }

    private void PopupLogOut() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title dialog
        alertDialogBuilder.setTitle("Keluar dari aplikasi ?");

        // set pesan dari dialog
        alertDialogBuilder
                .setIcon(R.drawable.logo_rumah_pucuk)
                .setCancelable(false)
                .setPositiveButton("Ya", (dialog, id) -> {
                    // jika tombol diklik, maka akan kembali ke menu login
                    startActivity(new Intent(Homepage.this, LoginPage.class));
                })
                .setNegativeButton("Tidak", (dialog, id) -> {
                    // jika tombol ini diklik, akan menutup dialog
                    // dan tidak terjadi apa2
                    dialog.cancel();
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }
}