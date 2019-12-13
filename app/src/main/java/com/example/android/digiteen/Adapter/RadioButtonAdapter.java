package com.example.android.digiteen.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.digiteen.R;

import java.util.List;

public class RadioButtonAdapter extends RecyclerView.Adapter<RadioButtonAdapter.ViewHolder> {
    private Context context;
    private List<String> list;
    private int lastSelectedPosition=-1;

    public RadioButtonAdapter(List<String> canteen, Context contxt)
    {
        list=canteen;
        context=contxt;
    }

    @NonNull
    @Override
    public RadioButtonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
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
        public TextView canteen_name;
        public RadioButton selectionState;
        public ViewHolder(View view)
        {
            super(view);
            canteen_name=view.findViewById(R.id.bhawan);
            selectionState=view.findViewById(R.id.bhawanchoice);
            selectionState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lastSelectedPosition=getAdapterPosition();
                    notifyDataSetChanged();
                    Toast.makeText(RadioButtonAdapter.this.context,"Selected canteen is "+canteen_name,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

