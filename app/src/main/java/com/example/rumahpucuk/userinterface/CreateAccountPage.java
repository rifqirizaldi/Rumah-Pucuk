package com.example.rumahpucuk.userinterface;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rumahpucuk.R;

public class CreateAccountPage extends AppCompatActivity {

    public Button btn_back,btn_kirim;
    public EditText ed_username,ed_pass,ed_repass,ed_email;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.create_account_page);
        btn_back = findViewById(R.id.btn_back_from_create_acc);
        btn_kirim = findViewById(R.id.btn_kirim_from_create_acc);
        ed_email = findViewById(R.id.ed_email_from_create_acc);
        ed_username = findViewById(R.id.ed_username_from_create_acc);
        ed_pass = findViewById(R.id.ed_password_from_create_acc);
        ed_repass = findViewById(R.id.ed_repassword_from_create_acc);

        btn_kirim.setOnClickListener(view ->
                Toast.makeText(this, "Akun belum bisa dibuat", Toast.LENGTH_SHORT).show());

        btn_back.setOnClickListener(view ->
                startActivity(new Intent(this, LoginPage.class)));
    }


}