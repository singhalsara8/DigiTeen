package com.example.android.digiteen.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.digiteen.Model.MenuItem;
import com.example.android.digiteen.Model.SelectMenu;
import com.example.android.digiteen.Model.Total;
import com.example.android.digiteen.R;

import java.util.List;

public class Menu_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<SelectMenu> list;

    public Menu_Adapter(Context context, List<SelectMenu> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == SelectMenu.MENU_TYPE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_item, parent, false);
            RecyclerView.ViewHolder holder = new MenuItemViewHolder(view);
            return holder;
        } else if(viewType == SelectMenu.TOTAL_TYPE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grand_total, parent, false);
            RecyclerView.ViewHolder holder = new GrandTotalViewHolder(view);
            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SelectMenu menu = list.get(position);
        if (menu != null) {
            if (menu.getType() == SelectMenu.MENU_TYPE) {
                ((MenuItemViewHolder) holder).item.setText(menu.getMenuItem().getMitem());
                ((MenuItemViewHolder) holder).amount.setText(menu.getMenuItem().getMamount());
                ((MenuItemViewHolder) holder).subtotal.setText(menu.getMenuItem().getMsubtotal());
                ((MenuItemViewHolder) holder).number.setText(menu.getMenuItem().getMnumber());
            }
            else if (menu.getType()==SelectMenu.TOTAL_TYPE) {
                ((GrandTotalViewHolder) holder).totalValue.setText(menu.getTotal().getMtotalvalue());
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (list.get(position).getType()) {
            case 0:
                return SelectMenu.TOTAL_TYPE;
            case 1:
                return SelectMenu.MENU_TYPE;
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MenuItemViewHolder extends RecyclerView.ViewHolder {
        private TextView item, amount, subtotal;
        private EditText number;
        private Button increment, decrement;

        private MenuItemViewHolder(View view) {
            super(view);
            item = view.findViewById(R.id.item);
            amount = view.findViewById(R.id.amount);
            subtotal = view.findViewById(R.id.subtotal);
            number = view.findViewById(R.id.number);
            increment = view.findViewById(R.id.increment);
            decrement = view.findViewById(R.id.decrement);
            increment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SelectMenu menu = list.get(getAdapterPosition());
                    int i = menu.getMenuItem().getMnumber();
                    int total;
                    int cost = menu.getMenuItem().getMamount();
                    i += 1;
                    total = i * cost;
                    menu.getMenuItem().setMnumber(i);
                    menu.getMenuItem().setMsubtotal(total);
                    list.set(getAdapterPosition(), menu);
                }
            });
            decrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SelectMenu menuItem = list.get(getAdapterPosition());
                    int i = menuItem.getMenuItem().getMnumber();
                    int cost = menuItem.getMenuItem().getMamount();
                    int total;
                    i--;
                    total = i * cost;
                    menuItem.getMenuItem().setMnumber(i);
                    menuItem.getMenuItem().setMsubtotal(total);
                    list.set(getAdapterPosition(), menuItem);
                }
            });
        }

    }

    public class GrandTotalViewHolder extends RecyclerView.ViewHolder {
        private TextView totalValue;

        private GrandTotalViewHolder(View view) {
            super(view);
            totalValue = view.findViewById(R.id.total_value);
        }
    }
}


