package com.example.rumahpucuk.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rumahpucuk.R;
import com.example.rumahpucuk.model_class.Model_history;
import com.example.rumahpucuk.userinterface.DetailPurchasingPage;
import com.example.rumahpucuk.userinterface.DetailSellingPage;


import java.util.ArrayList;

public class Adapter_rv_history extends RecyclerView.Adapter<Adapter_rv_history.MyViewHolder> {
    public String id, transaction_type;
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
        holder.transaction_type.setText(model.getTransaction_type());
        holder.btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = model.getName_order();
                transaction_type = model.getTransaction_type();
                passData(view ,id, transaction_type);
            }
        });
    }

    private void passData(View view, String id, String transaction_type) {
        if (transaction_type.equals("PURCHASING")){
            Intent intent = new Intent(view.getContext(), DetailPurchasingPage.class);
            intent.putExtra("Id", id);
            view.getContext().startActivity(intent);
        }else {
            Intent intent = new Intent(view.getContext(), DetailSellingPage.class);
            intent.putExtra("Id", id);
            view.getContext().startActivity(intent);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name_order,payment_amount,payment_status, delivery_status, transaction_type;
        ImageButton btn_detail;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_order = itemView.findViewById(R.id.txt_list_nama_item_from_history);
            payment_amount = itemView.findViewById(R.id.txt_list_payment_amount_from_history);
            payment_status = itemView.findViewById(R.id.txt_list_payment_status_from_history);
            delivery_status = itemView.findViewById(R.id.txt_list_delivery_status_from_history);
            transaction_type = itemView.findViewById(R.id.txt_list_transaction_status_from_history);
            btn_detail = itemView.findViewById(R.id.btn_list_detail_from_history);
        }
    }
}
