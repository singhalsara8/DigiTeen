package com.example.android.digiteen.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.digiteen.Model.OwnerMenu;
import com.example.android.digiteen.R;

import java.util.List;

public class OwnerMenuAdapter extends RecyclerView.Adapter<OwnerMenuAdapter.OwnerMenuViewHolder> {
    private List<OwnerMenu> ownerMenus;
    private Context context;

    public OwnerMenuAdapter(List<OwnerMenu> ownerMenus, Context context) {
        this.ownerMenus = ownerMenus;
        this.context = context;
    }

    @NonNull
    @Override
    public OwnerMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_owner_menu,parent,false);
        OwnerMenuViewHolder ownerMenuViewHolder=new OwnerMenuViewHolder(view);
        return ownerMenuViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OwnerMenuViewHolder holder, int position) {
        final OwnerMenu ownerMenu=ownerMenus.get(position);
        holder.itemname.setText(ownerMenu.getItemname());
        holder.itemprice.setText(String.valueOf(ownerMenu.getItemprice()));
    }

    @Override
    public int getItemCount() {
        return ownerMenus.size();
    }

    public class OwnerMenuViewHolder extends RecyclerView.ViewHolder{
        private TextView itemname,itemprice;
        private OwnerMenuViewHolder(View view){
            super(view);
            itemname=view.findViewById(R.id.owner_menu_itemname);
            itemprice=view.findViewById(R.id.owner_menu_itemprice);
        }

    }
}
