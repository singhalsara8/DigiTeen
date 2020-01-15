package com.example.android.digiteen.View.Owner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.digiteen.Adapter.CompletedOrderAdapter;
import com.example.android.digiteen.Model.PaymentPending;
import com.example.android.digiteen.R;
import com.example.android.digiteen.ViewModel.BhawanDataViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class CompletedOrderFragment extends Fragment {
    private RecyclerView recyclerView;
    private CompletedOrderAdapter completedOrderAdapter;
    private List<PaymentPending> list;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String ownerbhawan;

    public CompletedOrderFragment() {
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
        return inflater.inflate(R.layout.fragment_completed_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.owner_Completed_order_list_recyclerview);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference reference = databaseReference.child("user").child(firebaseAuth.getCurrentUser().getUid()).child("profile").child("bhawan");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ownerbhawan = dataSnapshot.getValue(String.class);
                BhawanDataViewModel bhawanDataViewModel = ViewModelProviders.of(getParentFragment()).get(BhawanDataViewModel.class);
                LiveData<DataSnapshot> liveData = bhawanDataViewModel.getdatasnapshotlivedata();
                liveData.observe(getViewLifecycleOwner(), new Observer<DataSnapshot>() {
                    @Override
                    public void onChanged(DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {
                            list = new ArrayList<>();
                            completedOrderAdapter = new CompletedOrderAdapter(getContext(), list,getActivity());
                            DataSnapshot dataSnapshot1 = dataSnapshot.child(ownerbhawan).child("status").child("Completed");
                            for (DataSnapshot readdata : dataSnapshot1.getChildren()) {
                                list.add(new PaymentPending(readdata.getKey(), Integer.parseInt(readdata.getValue().toString())));
                                completedOrderAdapter.notifyDataSetChanged();
                            }
                            RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setAdapter(completedOrderAdapter);
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
