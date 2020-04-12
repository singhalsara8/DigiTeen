package in.ac.iitr.mdg.digiteen.Adapter;

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

import in.ac.iitr.mdg.digiteen.Model.PaymentPending;
import in.ac.iitr.mdg.digiteen.R;

import java.util.List;

public class CompletedOrderAdapter extends RecyclerView.Adapter<CompletedOrderAdapter.CompletedOrderViewHolder> {
    private Context context;
    private List<PaymentPending> list;
    private Bundle bundle;
    private NavController navController;
    private Activity activity;

    public CompletedOrderAdapter(Context context, List<PaymentPending> list,Activity activity) {
        this.context = context;
        this.list = list;
        this.activity=activity;
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
        final PaymentPending completed=list.get(position);
        holder.completed_token.setText(completed.getOwner_token_number());
        holder.completed_total.setText(completed.getOwner_grand_total()+"₹");
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle=new Bundle();
                bundle.putString("tokenNumber",completed.getOwner_token_number());
                navController= Navigation.findNavController(activity,R.id.my_nav_host_fragment);
                navController.navigate(R.id.action_ownerOrderSummaryFragment_to_ownerOrderDetailFragment,bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CompletedOrderViewHolder extends RecyclerView.ViewHolder{
        TextView completed_token, completed_total;
        RelativeLayout relativeLayout;
        private CompletedOrderViewHolder(View view)
        {
            super(view);
            relativeLayout=view.findViewById(R.id.completed_order_layout);
            completed_token=view.findViewById(R.id.owner_completed_order_token_number);
            completed_total=view.findViewById(R.id.owner_completed_order_grand_total);
        }
    }
}
