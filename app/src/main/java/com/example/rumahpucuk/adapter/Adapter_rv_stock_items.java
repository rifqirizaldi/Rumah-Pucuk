package com.example.rumahpucuk.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rumahpucuk.R;
import com.example.rumahpucuk.model_class.Model_history;
import com.example.rumahpucuk.model_class.Model_stock_items;

import java.util.ArrayList;

public class Adapter_rv_stock_items extends RecyclerView.Adapter<Adapter_rv_stock_items.MyViewHolder> {

    Context context;
    ArrayList<Model_stock_items> list;

    public Adapter_rv_stock_items(Context context, ArrayList<Model_stock_items> list) {
        this.context = context;
        this.list = list;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filterListStockItems(ArrayList<Model_stock_items> filteredList){
        list = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_stock_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Model_stock_items stockItems = list.get(position);
        holder.name.setText(stockItems.getNameItems());
        holder.amount.setText(stockItems.getAmount());
        holder.price.setText(stockItems.getPrice());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
       TextView name,price,amount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.txt_list_nama_item_from_stock_item);
            price = itemView.findViewById(R.id.txt_list_amount_money);
            amount = itemView.findViewById(R.id.txt_list_item_count);
        }
    }
}
