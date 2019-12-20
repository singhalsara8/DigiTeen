package com.example.android.digiteen.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.digiteen.Model.AddMenuItem;
import com.example.android.digiteen.R;

import java.util.List;

public class AddItemAdapter extends RecyclerView.Adapter<AddItemAdapter.AddItemViewHolder>{
    private Context context;
    private List<AddMenuItem> list;

    public AddItemAdapter(Context context, List<AddMenuItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AddItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_add_item,parent,false);
        AddItemAdapter.AddItemViewHolder viewHolder=new AddItemAdapter.AddItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddItemViewHolder holder, int position) {
        AddMenuItem item=list.get(position);
        holder.itemname.setText(item.getMowneritemname());
        holder.itemprice.setText(String.valueOf(item.getMowneritemprice()));
//        holder.itemnametext.setText(item.getMowneritemnametext());
//        holder.itempricetext.setText(item.getMowneritempricetext());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AddItemViewHolder extends RecyclerView.ViewHolder{
        private EditText itemname,itemprice;
        private TextView itemnametext,itempricetext;

        private AddItemViewHolder(View view){
            super(view);
            itemname=view.findViewById(R.id.owner_itemname);
            itemprice=view.findViewById(R.id.owner_itemprice);
            itemnametext=view.findViewById(R.id.item_name_text);
            itempricetext=view.findViewById(R.id.item_price_text);
//            itemname.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                    list.get(getAdapterPosition()).setMowneritemname(itemname.getText().toString());
//                    notifyDataSetChanged();
//                }
//
//                @Override
//                public void afterTextChanged(Editable editable) {
//
//                }
//            });
//            itemprice.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                    list.get(getAdapterPosition()).setMowneritemprice(itemprice.getText().);
//                }
//
//                @Override
//                public void afterTextChanged(Editable editable) {
//
//                }
//            });
        }
    }
}
