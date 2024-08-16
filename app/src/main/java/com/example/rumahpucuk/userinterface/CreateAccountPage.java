package com.example.rumahpucuk.userinterface;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rumahpucuk.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccountPage extends AppCompatActivity {

    public Button btn_back,btn_kirim;
    public EditText ed_username,ed_pass,ed_repass,ed_email;
    private String user,pass,repass,email;
    private FirebaseAuth fAuth;
    private DatabaseReference database;

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
        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://rumah-pucuk-c1737-default-rtdb.firebaseio.com/");

//        btn_kirim.setOnClickListener(view ->
//                Toast.makeText(this, "Akun belum bisa dibuat", Toast.LENGTH_SHORT).show());

        //Firebase
        btn_kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = ed_username.getText().toString();
                email = ed_email.getText().toString();
                pass = ed_pass.getText().toString();
                repass = ed_repass.getText().toString();
                createAccount(user, email, pass, repass, database,fAuth);
            }
        });

        btn_back.setOnClickListener(view ->
                startActivity(new Intent(this, LoginPage.class)));
    }

    private void createAccount(String user, String pass, String repass, String email, DatabaseReference database2, FirebaseAuth fAuth) {
        if (user.isEmpty() || pass.isEmpty() || repass.isEmpty() || email.isEmpty()){
            Toast.makeText(getApplicationContext(),
                    "Data belum lengkap",Toast.LENGTH_SHORT).show();
        } else if (!pass.equals(repass)) {
            Toast.makeText(getApplicationContext(),
                    "Password tidak sama",Toast.LENGTH_SHORT).show();
        }else{
            fAuth.createUserWithEmailAndPassword(email,pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(CreateAccountPage.this, "Register Successfull", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(CreateAccountPage.this, "Error!!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            //Menambahkan data ke real-time database firebase
            database2 = FirebaseDatabase.getInstance().getReference("users");
            database2.child(user).child("username").setValue(user);
            Toast.makeText(getApplicationContext(),
                    "Register berhasil",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CreateAccountPage.this, LoginPage.class));
        }
    }


}