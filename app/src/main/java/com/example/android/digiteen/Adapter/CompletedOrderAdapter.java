package com.example.android.digiteen.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.digiteen.Model.PaymentPending;
import com.example.android.digiteen.R;

import java.util.List;

public class CompletedOrderAdapter extends RecyclerView.Adapter<CompletedOrderAdapter.CompletedOrderViewHolder> {
    private Context context;
    private List<PaymentPending> list;

    public CompletedOrderAdapter(Context context, List<PaymentPending> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CompletedOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_completed_order,parent,false);
        CompletedOrderViewHolder completedOrderViewHolder=new CompletedOrderViewHolder(view);
        return completedOrderViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedOrderViewHolder holder, int position) {
        PaymentPending completed=list.get(position);
        holder.completed_token.setText(completed.getOwner_token_number());
        holder.completed_total.setText(String.valueOf(completed.getOwner_grand_total()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CompletedOrderViewHolder extends RecyclerView.ViewHolder{
        TextView completed_token, completed_total;
        private CompletedOrderViewHolder(View view)
        {
            super(view);
            completed_token=view.findViewById(R.id.owner_completed_order_token_number);
            completed_total=view.findViewById(R.id.owner_completed_order_grand_total);
        }
    }
}
