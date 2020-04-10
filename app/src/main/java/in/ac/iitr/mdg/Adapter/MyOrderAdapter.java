package in.ac.iitr.mdg.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import in.ac.iitr.mdg.Model.MyOrder;
import in.ac.iitr.mdg.R;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyOrderViewholder> {
    private Context context;
    private List<MyOrder> list;
    private Activity activity;
    private NavController navController;
    private Bundle bundle;

    public MyOrderAdapter(Context context, List<MyOrder> list, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyOrderViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_my_orders, parent, false);
        MyOrderViewholder myOrderViewholder = new MyOrderViewholder(view);
        return myOrderViewholder;
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull MyOrderViewholder holder, int position) {
        final MyOrder myOrder = list.get(position);
        holder.token.setText(myOrder.getMtoken());
        holder.status.setText(myOrder.getMstatus());
        if (myOrder.getMstatus().equals("Payment Pending") || myOrder.getMstatus().equals("Declined"))
            holder.status.setTextColor(context.getColor(R.color.colorPrimary));
        else holder.status.setTextColor(context.getColor(R.color.colorAccent));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle = new Bundle();
                bundle.putString("token", myOrder.getMtoken());
                navController = Navigation.findNavController(activity, R.id.my_nav_host_fragment);
                navController.navigate(R.id.action_studentOrderListFragment_to_orderDetailFragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyOrderViewholder extends RecyclerView.ViewHolder {
        private TextView token, status;
        private RelativeLayout layout;

        private MyOrderViewholder(View view) {
            super(view);
            token = view.findViewById(R.id.token);
            status = view.findViewById(R.id.status);
            layout = view.findViewById(R.id.view_id);
        }
    }
}