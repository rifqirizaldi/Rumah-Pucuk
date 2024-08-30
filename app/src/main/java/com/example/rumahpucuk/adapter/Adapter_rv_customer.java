package com.example.rumahpucuk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rumahpucuk.R;
import com.example.rumahpucuk.model_class.Model_customer;

import java.util.ArrayList;

public class Adapter_rv_customer extends RecyclerView.Adapter<Adapter_rv_customer.MyViewHolder> {

    Context context;
    ArrayList<Model_customer> list;

    public Adapter_rv_customer(Context context, ArrayList<Model_customer> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_customer, parent, false);
        return new Adapter_rv_customer.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model_customer customer = list.get(position);
        holder.name.setText(customer.getCustomerName());
        holder.address.setText(customer.getCustomerAddress());
        holder.phone.setText(customer.getCustomerPhoneNumber());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,address,phone;
        ImageButton btn_edit,btn_detail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txt_list_nama_orang_from_customer);
            address = itemView.findViewById(R.id.txt_list_address);
            phone = itemView.findViewById(R.id.txt_list_number_phone);
            btn_detail = itemView.findViewById(R.id.btn_list_detail_from_customer);
            btn_edit = itemView.findViewById(R.id.btn_list_edit_from_customer);

        }
    }

}
