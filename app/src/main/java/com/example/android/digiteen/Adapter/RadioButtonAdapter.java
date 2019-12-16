package com.example.android.digiteen.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.digiteen.R;

import java.util.List;

public class RadioButtonAdapter extends RecyclerView.Adapter<RadioButtonAdapter.ViewHolder> {
    private Context context;
    private List<String> list;
    private int lastSelectedPosition=0;

    public RadioButtonAdapter(List<String> canteen, Context contxt)
    {
        list=canteen;
        context=contxt;
    }
    public String getUserId(){

        if(lastSelectedPosition == -1)
        {
            return null;
        } else {
            return list.get(lastSelectedPosition);
        }
    }

    @NonNull
    @Override
    public RadioButtonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_canteen,parent,false);
        RadioButtonAdapter.ViewHolder viewHolder=new RadioButtonAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RadioButtonAdapter.ViewHolder holder, int position) {
        String string=list.get(position);
        holder.canteen_name.setText(string);
        holder.selectionState.setChecked(lastSelectedPosition == position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView canteen_name;
        private RadioButton selectionState;
        private ViewHolder(View view)
        {
            super(view);
            canteen_name=view.findViewById(R.id.bhawan);
            selectionState=view.findViewById(R.id.bhawanchoice);
            selectionState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lastSelectedPosition=getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }
    }
}

