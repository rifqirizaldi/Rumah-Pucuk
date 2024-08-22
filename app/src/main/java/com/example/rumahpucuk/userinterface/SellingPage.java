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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SellingPage extends AppCompatActivity {

    public ImageView img_back;
    public Button btn_send,btn_refresh;
    public EditText ed_total_payment,ed_amount_item,ed_recevied_money;
    public TextView txt_recevied_money;
    public Spinner spinner_payment_status, spinner_customer_name, spinner_item_name;
    public DatabaseReference dbCustomer, dbStockItem, dbSelling ;
    public ValueEventListener listener_customer, listener_stockItems;
    public ArrayList<String> list_payment_status;
    public ArrayList<String> list_customer = new ArrayList<>();
    public ArrayList<String> list_stockItems = new ArrayList<>();
    public ArrayList<String> list_amount = new ArrayList<>();
    public String customer_name, item_name, payment_status,amount, total_payment,total_debt,total_recevied;
    public Integer tem_amount, tem_price;
    public ArrayAdapter adapter_customer, adapter_stockItems, adapter_payment;
    public String dateTime, tanggal, tanggal2;
    public Calendar calendar;
    public SimpleDateFormat simpleDateFormat;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.selling_page);
        img_back = findViewById(R.id.img_back_from_selling);
        btn_send = findViewById(R.id.btn_kirim_from_selling);
        btn_refresh = findViewById(R.id.btn_refresh);
        ed_amount_item = findViewById(R.id.ed_amount_order_from_selling);
        ed_recevied_money = findViewById(R.id.ed_recevied_payment_from_selling);
        ed_total_payment = findViewById(R.id.ed_total_payment_from_selling);
        txt_recevied_money = findViewById(R.id.txt_recevied_payment_from_selling);
        spinner_payment_status = findViewById(R.id.spiinner_payment_status);
        spinner_customer_name = findViewById(R.id.spinner_customer_name_from_selling);
        spinner_item_name = findViewById(R.id.spinner_item_name_from_selling);
        dbCustomer = FirebaseDatabase.getInstance().getReference("customer");
        dbStockItem = FirebaseDatabase.getInstance().getReference("stockItems");

        //Read data from database
        readData();

        //Spinner customer name
        adapter_customer =
                new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list_customer);
        adapter_customer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_customer_name.setAdapter(adapter_customer);
        spinner_customer_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                customer_name = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Spinner stock items
        adapter_stockItems =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list_stockItems);
        adapter_stockItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_item_name.setAdapter(adapter_stockItems);
        spinner_item_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                item_name = adapterView.getSelectedItem().toString();
//                countPrice(item_name);
                btn_refresh.performClick();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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

        //Spinner payment status
        list_payment_status = new ArrayList<>();
        list_payment_status.add("-BELUM MEMILIH-");
        list_payment_status.add("HUTANG");
        list_payment_status.add("LUNAS");
        adapter_payment =
                new ArrayAdapter(this,android.R.layout.simple_spinner_item,list_payment_status);
        adapter_payment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_payment_status.setAdapter(adapter_payment);
        spinner_payment_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                payment_status = adapterView.getSelectedItem().toString();
                checkStatus(payment_status);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        img_back.setOnClickListener(view ->
                startActivity(new Intent(SellingPage.this, AddOrderPage.class)));


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total_payment = ed_total_payment.getText().toString();
                amount = ed_amount_item.getText().toString();
                total_recevied = ed_recevied_money.getText().toString();
                processingData(customer_name,item_name,amount,total_payment,payment_status,total_recevied);
            }
        });

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countPrice(item_name);
            }
        });

    }

    private void processingData(String customer_name, String item_name,
                                String amount, String total_payment,
                                String payment_status, String total_recevied) {

       if (customer_name.equals("AAA")){
           Toast.makeText(this, "Data Customer tidak valid", Toast.LENGTH_SHORT).show();
       }else if (item_name.equals("AAA")){
           Toast.makeText(this, "Data Barang tidak valid", Toast.LENGTH_SHORT).show();
       } else if (amount.isEmpty()) {
           ed_amount_item.setError("Tidak boleh kosong");
       } else if (payment_status.equals("-BELUM MEMILIH-")) {
           Toast.makeText(this, "Data pembayaran tidak valid", Toast.LENGTH_SHORT).show();
       } else if (total_recevied.isEmpty()) {
           ed_recevied_money.setError("Tidak boleh kosong");
       }else {
           tanggal = setDate(); tanggal2 = setDate2();
           total_debt = String.valueOf(Integer.valueOf(total_payment) - Integer.valueOf(ed_recevied_money.getText().toString()));
           dbSelling = FirebaseDatabase.getInstance().getReference("sellingActivity");
           dbSelling.child(tanggal2 + "_"+ customer_name).child("Customer Name").setValue(customer_name.toUpperCase());
           dbSelling.child(tanggal2 + "_"+ customer_name).child("Order Item Name").setValue(item_name.toUpperCase());
           dbSelling.child(tanggal2 + "_"+ customer_name).child("Date").setValue(tanggal);
           dbSelling.child(tanggal2 + "_"+ customer_name).child("Amount Item").setValue(amount);
           dbSelling.child(tanggal2 + "_"+ customer_name).child("Total Payment").setValue(total_payment);
           dbSelling.child(tanggal2 + "_"+ customer_name).child("Status Payment").setValue(payment_status.toUpperCase());
           if (payment_status.equals("LUNAS")){
               dbSelling.child(tanggal2 + "_"+ customer_name).child("Total Recevied Payment").setValue(total_payment);
               dbSelling.child(tanggal2 + "_"+ customer_name).child("Total Debt").setValue("0");
           }else {
               dbSelling.child(tanggal2 + "_"+ customer_name).child("Total Recevied Payment").setValue(ed_recevied_money.getText().toString());
               dbSelling.child(tanggal2 + "_"+ customer_name).child("Total Debt").setValue(total_debt);
           }
           updateDataStock(item_name, amount);
           startActivity(new Intent(getApplicationContext(), AddOrderPage.class));
       }


    }

    private void updateDataStock(String item_name, String amount) {
        for (int a = 0 ; a<list_stockItems.size();a++){
            if (list_stockItems.get(a).equals(item_name)){
                tem_amount = Integer.valueOf(list_amount.get(a));
                tem_amount -= Integer.valueOf(amount);
                dbStockItem = FirebaseDatabase.getInstance().getReference("stockItems");
                dbStockItem.child(item_name).child("Amount").setValue(String.valueOf(tem_amount));
            }
        }
    }

    private String setDate2() {
//        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("ddLLLLyyyy");
        dateTime = simpleDateFormat.format(new Date());
        return  dateTime;
    }

    private String setDate() {
//        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("ddLLLLyyyy HHmmss aaa z");
        dateTime = simpleDateFormat.format(new Date());
        return  dateTime;
    }

    private void countPrice(String item_name) {
        listener_stockItems = dbStockItem.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    if (ds.child("Name Item").getValue().toString().equals(item_name)){
                        tem_price = Integer.valueOf(ds.child("Price").getValue().toString());
                        if (ed_amount_item.getText().toString().isEmpty() ||
                                ed_amount_item.getText().toString().equals("0")){
                            tem_price *= 0;
                        }else {
                            tem_price *= Integer.valueOf(ed_amount_item.getText().toString());
                        }
                        ed_total_payment.setText(String.valueOf(tem_price));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readData() {
        Query query = dbCustomer.orderByChild("Nama");
        listener_customer = query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    list_customer.add(data.child("Nama").getValue().toString());
                }
                adapter_customer.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query query2 = dbStockItem.orderByChild("Name Item");
        listener_stockItems = query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    list_stockItems.add(ds.child("Name Item").getValue().toString());
                    list_amount.add(ds.child("Amount").getValue().toString());
                }
                adapter_stockItems.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void checkStatus(String paymentStatus) {
        if (paymentStatus.equals("HUTANG")){
            txt_recevied_money.setVisibility(View.VISIBLE);
            ed_recevied_money.setVisibility(View.VISIBLE);
        }else {
            txt_recevied_money.setVisibility(View.GONE);
            ed_recevied_money.setVisibility(View.GONE);
        }
    }

}