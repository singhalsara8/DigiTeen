package com.example.android.digiteen.View;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.android.digiteen.Adapter.OrderSummaryAdapter;
import com.example.android.digiteen.Model.OrderSummary;
import com.example.android.digiteen.R;
import com.example.android.digiteen.ViewModel.StudentDataViewModel;
import com.google.firebase.database.DataSnapshot;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
public class OrderDetailFragment extends Fragment {
    OrderSummaryAdapter adapter;
    RecyclerView recyclerView;
    String token_number;
    TextView token, summary, total;
    ProgressDialog progressDialog;
    int grandTotal = 0;
    public OrderDetailFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_detail, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        total = view.findViewById(R.id.Grand_total_value);
        token = view.findViewById(R.id.token_number_value);
        summary = view.findViewById(R.id.summary);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        token_number = getArguments().getString("token");
        token.setText(token_number);
        recyclerView = view.findViewById(R.id.order_details_recyclerview);
        StudentDataViewModel viewModel = ViewModelProviders.of(this).get(StudentDataViewModel.class);
        LiveData<DataSnapshot> liveData = viewModel.getdatasnapshotlivedata();
        liveData.observe(this, new Observer<DataSnapshot>() {
            @SuppressLint("NewApi")
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    Log.d("debug",token_number);
                    Log.d("status", dataSnapshot.child(token_number).child("status").getValue().toString());
                    summary.setText(dataSnapshot.child(token_number).child("status").getValue().toString());
                    if (dataSnapshot.child(token_number).child("status").getValue().toString().equals("Payment Pending")||dataSnapshot.child(token_number).child("status").getValue().toString().equals("Declined"))
                        summary.setTextColor(getActivity().getColor(R.color.colorPrimary));
                    else summary.setTextColor(getActivity().getColor(R.color.colorAccent));
                    List<OrderSummary> list = new ArrayList<>();
                    adapter = new OrderSummaryAdapter(getContext(), list);
                    for (DataSnapshot readData : dataSnapshot.child(token_number).child("item").getChildren()) {
                        Log.d("item", readData.getKey());
                        int price = Integer.parseInt(readData.child("price").getValue().toString());
                        int quantity = Integer.parseInt(readData.child("quantity").getValue().toString());
                        list.add(new OrderSummary(readData.getKey(), price, quantity, price * quantity));
                        adapter.notifyDataSetChanged();
                    }
                    grandTotal = adapter.getTotal();
                    Log.d("total", String.valueOf(grandTotal));
                    total.setText(grandTotal + "₹");
                    progressDialog.dismiss();
                    RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }
}