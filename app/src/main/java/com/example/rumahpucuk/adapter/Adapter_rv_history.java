package com.example.rumahpucuk.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rumahpucuk.R;
import com.example.rumahpucuk.model_class.Model_history;


import java.util.ArrayList;

public class Adapter_rv_history extends RecyclerView.Adapter<Adapter_rv_history.MyViewHolder> {
    Context context;
    ArrayList<Model_history> list;

    public Adapter_rv_history(Context context, ArrayList<Model_history> list) {
        this.context = context;
        this.list = list;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filterListHistory(ArrayList<Model_history> filteredList){
        list = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_riwayat_pesanan, parent, false);
        return new Adapter_rv_history.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model_history model = list.get(position);
        holder.name_order.setText(model.getName_order());
        holder.delivery_status.setText(model.getDelivery_status());
        holder.payment_status.setText(model.getPayment_status());
        holder.payment_amount.setText(model.getPayment_amount());
        holder.btn_detail.setOnClickListener(view -> Toast.makeText(context, "Button yang ditekan", Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name_order,payment_amount,payment_status, delivery_status;
        Button btn_detail;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_order = itemView.findViewById(R.id.txt_list_nama_item_from_history);
            payment_amount = itemView.findViewById(R.id.txt_list_payment_amount);
            payment_status = itemView.findViewById(R.id.txt_list_payment_status);
            delivery_status = itemView.findViewById(R.id.txt_list_delivery_status);
            btn_detail = itemView.findViewById(R.id.btn_list_detail_from_history);
        }
    }
}
