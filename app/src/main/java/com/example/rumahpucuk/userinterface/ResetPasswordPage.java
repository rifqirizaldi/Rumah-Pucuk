package com.example.rumahpucuk.userinterface;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rumahpucuk.R;

public class ResetPasswordPage extends AppCompatActivity {

    public Button btn_kirim,btn_back;
    public EditText ed_username,ed_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.reset_password_page);
        btn_kirim = findViewById(R.id.btn_kirim_reset_pass);
        btn_back = findViewById(R.id.btn_back_from_reset_pass);

        btn_kirim.setOnClickListener(view ->
                Toast.makeText(this, "Data belum dikirim", Toast.LENGTH_SHORT).show());

        btn_back.setOnClickListener(view ->
                startActivity(new Intent(ResetPasswordPage.this, LoginPage.class)));
    }
}