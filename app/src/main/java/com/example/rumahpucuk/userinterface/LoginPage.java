package com.example.rumahpucuk.userinterface;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rumahpucuk.R;
import com.google.firebase.auth.FirebaseAuth;


public class LoginPage extends AppCompatActivity {

    public Button btn_login;
    public TextView txt_create_acc,txt_reset_pass;
    public EditText ed_username, ed_password;
    //firebase
    private FirebaseAuth fAuth;
    private String user, password;

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
        //Firebase
        fAuth = FirebaseAuth.getInstance();

        //Firebase
        txt_reset_pass.setOnClickListener(v -> {
            EditText resetemail = new EditText(v.getContext());
            AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
            passwordResetDialog.setTitle("Reset Password");
            passwordResetDialog.setMessage("Enter Your Email");
            passwordResetDialog.setView(resetemail);

            passwordResetDialog.setPositiveButton("Yes", (dialog, which) -> {
                String mail = resetemail.getText().toString();
                fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(unused -> Toast.makeText(LoginPage.this,
                        "Link telah dikirim ke email", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(LoginPage.this,
                                "Error! Link tidak dapat dikirim"+e.getMessage(),
                                Toast.LENGTH_SHORT).show());
            });

            passwordResetDialog.setNegativeButton("Tidak", (dialog, which) -> {

            });

            passwordResetDialog.create().show();
        });

        txt_create_acc.setOnClickListener(view ->
                startActivity(new Intent(LoginPage.this, CreateAccountPage.class)));


        //Firebase
        btn_login.setOnClickListener(view -> {
            user = ed_username.getText().toString();
            password = ed_password.getText().toString();
            loginProcess(user, password, fAuth);
        });
    }


    //Firebase
    private void loginProcess(String user, String password, FirebaseAuth fAuth) {
        if(user.isEmpty() || password.isEmpty()){
            Toast.makeText(getApplicationContext(),
                    "Username atau Password belum lengkap",Toast.LENGTH_SHORT).show();
        }else{
            fAuth.signInWithEmailAndPassword(user,password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            Toast.makeText(LoginPage.this,
                                    "Login Successfull", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginPage.this, Homepage.class));
                        }else{
                            Toast.makeText(LoginPage.this, "Error!!" +
                                    task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}