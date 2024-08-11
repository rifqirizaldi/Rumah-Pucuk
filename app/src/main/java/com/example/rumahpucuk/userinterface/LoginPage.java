package com.example.rumahpucuk.userinterface;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rumahpucuk.R;


public class LoginPage extends AppCompatActivity {

    public Button btn_login;
    public TextView txt_create_acc,txt_reset_pass;
    public EditText ed_username, ed_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_page);
        btn_login = findViewById(R.id.btn_login);
        txt_create_acc = findViewById(R.id.txt_buat_akun_baru);
        txt_reset_pass = findViewById(R.id.txt_reset_password);
        ed_password = findViewById(R.id.ed_password);
        ed_username = findViewById(R.id.ed_username);


        txt_reset_pass.setOnClickListener(view ->
                startActivity(new Intent(LoginPage.this, ResetPasswordPage.class)));

        txt_create_acc.setOnClickListener(view ->
                startActivity(new Intent(LoginPage.this, CreateAccountPage.class)));

        btn_login.setOnClickListener(view -> loginProcess());

    }

    private void loginProcess() {
        startActivity(new Intent(LoginPage.this, Homepage.class));
    }
}