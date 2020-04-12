package in.ac.iitr.mdg.digiteen.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import in.ac.iitr.mdg.digiteen.Model.OrderSummary;
import in.ac.iitr.mdg.digiteen.R;
import java.util.List;

public class OrderSummaryAdapter extends RecyclerView.Adapter<OrderSummaryAdapter.OrderSummaryViewHolder> {
    private Context context;
    private List<OrderSummary> list;
    public OrderSummaryAdapter(Context context, List<OrderSummary> list) {
        this.context = context;
        this.list = list;
    }
    public int getTotal(){
        int total=0;
        for(int i=0; i<list.size();i++)
        {
            total+=list.get(i).getMitemprice()*list.get(i).getMitemQuantity();
        }
        return total;
    }
    @NonNull
    @Override
    public OrderSummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_order_details,parent,false);
        OrderSummaryViewHolder viewHolder=new OrderSummaryViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull OrderSummaryViewHolder holder, int position) {
        OrderSummary orderSummary=list.get(position);
        holder.itemname.setText(orderSummary.getMitemname());
        holder.itemprice.setText(orderSummary.getMitemprice() +"₹");
        holder.quantityordered.setText(String.valueOf(orderSummary.getMitemQuantity()));
        holder.subtotal.setText(orderSummary.getMsubtotal() +"₹");
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class OrderSummaryViewHolder extends RecyclerView.ViewHolder{
        private TextView itemname, itemprice, quantityordered, subtotal;
        private OrderSummaryViewHolder(View view){
            super(view);
            itemname=view.findViewById(R.id.item_name);
            itemprice=view.findViewById(R.id.item_price);
            quantityordered=view.findViewById(R.id.quantity_ordered);
            subtotal=view.findViewById(R.id.subtotalsummary);
        }
    }
}