package in.ac.iitr.mdg.View.Owner;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import in.ac.iitr.mdg.Adapter.OrderSummaryAdapter;
import in.ac.iitr.mdg.Model.OrderSummary;
import in.ac.iitr.mdg.R;
import in.ac.iitr.mdg.ViewModel.BhawanDataViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OwnerOrderDetailFragment extends Fragment {
    private OrderSummaryAdapter adapter;
    private RecyclerView recyclerView;
    private String token_number;
    private TextView token, summary, total;
    private ProgressDialog progressDialog;
    private int grandTotal = 0;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private String bhawan;
    private DatabaseReference reference;

    public OwnerOrderDetailFragment() {
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
        return inflater.inflate(R.layout.fragment_owner_order_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        reference = FirebaseDatabase.getInstance().getReference();
        total = view.findViewById(R.id.owner_Grand_total_value);
        token = view.findViewById(R.id.owner_token_number_value);
        summary = view.findViewById(R.id.owner_summary);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        token_number = getArguments().getString("tokenNumber");
        token.setText(token_number);
        recyclerView = view.findViewById(R.id.owner_order_details_recyclerview);
        reference = databaseReference.child("user").child(firebaseAuth.getCurrentUser().getUid()).child("profile");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bhawan = snapshot.child("bhawan").getValue().toString();
                Log.d("check", bhawan);
                BhawanDataViewModel viewModel = ViewModelProviders.of(getParentFragment()).get(BhawanDataViewModel.class);
                LiveData<DataSnapshot> liveData = viewModel.getdatasnapshotlivedata();
                liveData.observe(getViewLifecycleOwner(), new Observer<DataSnapshot>() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onChanged(DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {
                            Log.d("tag", token_number);
                            Log.d("status", dataSnapshot.child(bhawan).child("order").child(token_number).child("status").getValue(String.class));
                            summary.setText(dataSnapshot.child(bhawan).child("order").child(token_number).child("status").getValue().toString());
                            if (dataSnapshot.child(bhawan).child("order").child(token_number).child("status").getValue().toString().equals("Payment Pending") || dataSnapshot.child(bhawan).child("order").child(token_number).child("status").getValue().toString().equals("Declined"))
                                summary.setTextColor(getActivity().getColor(R.color.colorPrimary));
                            else summary.setTextColor(getActivity().getColor(R.color.colorAccent));
                            List<OrderSummary> list = new ArrayList<>();
                            adapter = new OrderSummaryAdapter(getContext(), list);
                            for (DataSnapshot readData : dataSnapshot.child(bhawan).child("order").child(token_number).child("item").getChildren()) {
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

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}