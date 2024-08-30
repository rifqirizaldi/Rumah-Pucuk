package com.example.rumahpucuk.userinterface;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rumahpucuk.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class EditSellingDataPage extends AppCompatActivity {

    public Button btn_kirim, btn_refresh;
    public ImageView btn_back;
    public TextView txt_id, txt_customer_name, txt_selling_item, txt_recevied_payment;
    public EditText ed_selling_amount, ed_total_payment, ed_payment_recevied;
    public Spinner spn_delivery, spn_payment;
    public ArrayList<String> list_del = new ArrayList<>();
    public ArrayList<String> list_pay = new ArrayList<>();
    public DatabaseReference db, dbUpdate;
    public ValueEventListener listener_selling;
    public ArrayAdapter adapter_del, adapter_pay;
    public Intent intent;
    public Integer tem_amount, tem_price, tem_satuan_price, final_price, temp_debt, temp_recevied_pay, dummy1, dummy2;
    public String value_del, value_pay, value_intent, dateTime, result_del, result_pay, amount, total_pay, total_debt, total_recevied_pay;
    public String final_amount, final_total_pay, final_del_status, final_pay_status, final_pay_left, final_debt, dummy3, dummy4, final_pay_recevied;
    public SimpleDateFormat simpleDateFormat;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.edit_selling_data_page);
        btn_kirim = findViewById(R.id.btn_kirim_from_edit_selling);
        btn_back = findViewById(R.id.img_back_from_edit_selling);
        btn_refresh = findViewById(R.id.btn_refresh_from_edit_Selling);
        txt_id = findViewById(R.id.txt_id_from_edit_selling);
        txt_customer_name = findViewById(R.id.txt_customer_name_from_edit_selling);
        txt_selling_item = findViewById(R.id.txt_selling_item_from_edit_selling);
        txt_recevied_payment = findViewById(R.id.txt_recevied_payment_from_edit_selling);
        ed_payment_recevied = findViewById(R.id.ed_recevied_payment_from_edit_selling);
        ed_selling_amount = findViewById(R.id.ed_selling_amount_from_edit_selling);
        ed_total_payment = findViewById(R.id.ed_total_payment_from_edit_selling);
        spn_delivery = findViewById(R.id.spinner_delivery_status_from_edit_selling);
        spn_payment = findViewById(R.id.spinner_payment_status_from_edit_selling);
        db = FirebaseDatabase.getInstance().getReference("sellingActivity");

        intent = getIntent();
        value_intent = intent.getStringExtra("Id");

        readDatabase(db, value_intent);


        list_del.add("BELUM DIKIRIM");
        list_del.add("SUDAH DIKIRIM");
        list_del.add("SUDAH DIAMBIL OLEH PEMBELI");
        adapter_del =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list_del);
        adapter_del.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_delivery.setAdapter(adapter_del);
        if (value_del != null){
            int position = adapter_del.getPosition(value_del);
            spn_delivery.setSelection(position);
        }
        spn_delivery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                result_del = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                result_del = value_del;
            }
        });

        list_pay.add("HUTANG");
        list_pay.add("LUNAS");
        adapter_pay =
                new ArrayAdapter(this,android.R.layout.simple_spinner_item,list_pay);
        adapter_pay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_payment.setAdapter(adapter_pay);
        if (value_pay != null){
            int position = adapter_del.getPosition(value_pay);
            spn_payment.setSelection(position);
            checkStatus(value_pay);
        }
        spn_payment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                result_pay = adapterView.getSelectedItem().toString();
                checkStatus(result_pay);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                result_pay = value_pay;
                checkStatus(result_pay);
            }
        });

        ed_total_payment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btn_refresh.performClick();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        btn_back.setOnClickListener(view ->
                startActivity(new Intent(EditSellingDataPage.this, UpdateOrderPage.class)));

        btn_refresh.setOnClickListener(view -> {
            amount = ed_selling_amount.getText().toString();
            countPrice(value_intent);
        });

        btn_kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditSellingDataPage.this, "Testing", Toast.LENGTH_SHORT).show();
                final_amount = ed_selling_amount.getText().toString();
                final_total_pay = ed_total_payment.getText().toString();
                final_del_status = result_del;
                final_pay_status = result_pay;
                final_pay_recevied = ed_payment_recevied.getText().toString();
                final_debt = String.valueOf(Integer.parseInt(final_total_pay) - Integer.parseInt(final_pay_recevied));
                processData(value_intent, final_amount, final_total_pay, final_del_status, final_pay_status, final_debt, final_pay_recevied);
            }
        });

    }

    private void processData(String value_intent, String final_amount, String final_total_pay,
                             String final_del_status, String final_pay_status,
                             String final_debt, String final_pay_recevied) {
        if(final_amount.isEmpty()){
            ed_total_payment.setError("Data tidak valid");
        }else if (final_pay_recevied.isEmpty()){
            ed_payment_recevied.setError("Data tidak valid");
        }else {
            HashMap order= new HashMap();
            order.put("Amount Item", final_amount);
            order.put("Delivery Status", final_del_status);
            order.put("Status Payment", final_pay_status);
            order.put("Total Debt", final_debt);
            order.put("Total Payment", final_total_pay);
            order.put("Total Recevied Payment", final_pay_recevied);

            dbUpdate = FirebaseDatabase.getInstance().getReference("sellingActivity");
            dbUpdate.child(value_intent).updateChildren(order).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()){
                        Toast.makeText(EditSellingDataPage.this, "Successfull update data", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditSellingDataPage.this, UpdateOrderPage.class));
                    }else {
                        Toast.makeText(EditSellingDataPage.this, "Failed update data", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void countPrice(String value_intent) {
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    if (ds.child("Id").getValue(String.class).equals(value_intent)){
                        tem_amount = Integer.parseInt(ds.child("Amount Item").getValue(String.class));
                        tem_price = Integer.parseInt(ds.child("Total Payment").getValue(String.class));
                        if (ed_selling_amount.getText().toString().equals("0") || ed_selling_amount.getText().toString().isEmpty()){
                            tem_satuan_price = 0;
                            final_price = 0;
                        }else {
                            tem_satuan_price = tem_price / tem_amount;
                            final_price = Integer.parseInt(ed_selling_amount.getText().toString()) * tem_satuan_price;
                        }
                        ed_total_payment.setText(String.valueOf(final_price));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void checkStatus(String resultPay) {
        if (resultPay.equals("HUTANG")){
            txt_recevied_payment.setVisibility(View.VISIBLE);
            ed_payment_recevied.setVisibility(View.VISIBLE);
        }else {
            txt_recevied_payment.setVisibility(View.GONE);
            ed_payment_recevied.setVisibility(View.GONE);
        }
    }


    private void readDatabase(DatabaseReference db, String value_intent) {
        listener_selling = db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    if (ds.child("Id").getValue(String.class).equals(value_intent)){
                        txt_id.setText(ds.child("Id").getValue(String.class));
                        txt_customer_name.setText(ds.child("Customer Name").getValue(String.class));
                        txt_selling_item.setText(ds.child("Order Item Name").getValue(String.class));
                        ed_selling_amount.setText(ds.child("Amount Item").getValue(String.class));
                        ed_total_payment.setText(ds.child("Total Payment").getValue(String.class));
                        ed_payment_recevied.setText(ds.child("Total Recevied Payment").getValue(String.class));
                        value_del = ds.child("Delivery Status").getValue(String.class);
                        value_pay = ds.child("Status Payment").getValue(String.class);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}