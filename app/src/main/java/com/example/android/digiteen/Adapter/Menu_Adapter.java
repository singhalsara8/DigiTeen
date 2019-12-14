package com.example.android.digiteen.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.digiteen.Model.MenuItem;
import com.example.android.digiteen.R;

import java.util.List;

public class Menu_Adapter extends RecyclerView.Adapter<Menu_Adapter.viewholder>{

    private Context context;
    private List<MenuItem> list;

    public Menu_Adapter(Context context, List<MenuItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.select_item,parent,false);
        Menu_Adapter.viewholder viewHolder=new Menu_Adapter.viewholder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        MenuItem menulist=list.get(position);
        holder.item.setText(menulist.getMitem());
        holder.amount.setText(menulist.getMamount());
        holder.subtotal.setText(menulist.getMsubtotal());
        holder.number.setText(menulist.getMnumber());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        private TextView item,amount,subtotal;
        private EditText number;
        private Button increment,decrement;
        private viewholder(View view){
            super(view);
            item=view.findViewById(R.id.item);
            amount=view.findViewById(R.id.amount);
            subtotal=view.findViewById(R.id.subtotal);
            number=view.findViewById(R.id.number);
            increment=view.findViewById(R.id.increment);
            decrement=view.findViewById(R.id.decrement);
            increment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MenuItem menu=list.get(getAdapterPosition());
                    int i=menu.getMnumber();
                    int total;
                    int cost=menu.getMamount();
                    i+=1;
                    total=i*cost;
                    menu.setMnumber(i);
                    menu.setMsubtotal(total);
                    list.set(getAdapterPosition(),menu);
                }
            });
            decrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MenuItem menuItem=list.get(getAdapterPosition());
                    int i=menuItem.getMnumber();
                    int cost=menuItem.getMamount();
                    int total;
                    i--;
                    total=i*cost;
                    menuItem.setMnumber(i);
                    menuItem.setMsubtotal(total);
                    list.set(getAdapterPosition(),menuItem);
                }
            });
        }

    }

}

