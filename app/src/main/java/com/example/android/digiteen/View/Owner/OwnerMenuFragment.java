package com.example.android.digiteen.View.Owner;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.digiteen.Adapter.OwnerMenuAdapter;
import com.example.android.digiteen.Model.OwnerMenu;
import com.example.android.digiteen.R;
import com.example.android.digiteen.SwipeToDelete;
import com.example.android.digiteen.ViewModel.BhawanDataViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class OwnerMenuFragment extends Fragment {
    private RecyclerView recyclerView;
    private Button button;
    private List<OwnerMenu> ownerMenus;
    private OwnerMenuAdapter adapter;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference, reference;
    private String ownerbhawan;
    private ProgressDialog progressDialog;
    private NavController navController;

    public OwnerMenuFragment() {
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
        return inflater.inflate(R.layout.fragment_owner_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController= Navigation.findNavController(getActivity(),R.id.my_nav_host_fragment);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        reference = FirebaseDatabase.getInstance().getReference();
        button = view.findViewById(R.id.owner_add_item_button);
        recyclerView = view.findViewById(R.id.owner_menu_recyclerView);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        BhawanDataViewModel bhawanDataViewModel = ViewModelProviders.of(getActivity()).get(BhawanDataViewModel.class);
        final LiveData<DataSnapshot> liveData = bhawanDataViewModel.getdatasnapshotlivedata();
        reference = databaseReference.child("user").child(firebaseAuth.getCurrentUser().getUid()).child("profile").child("bhawan");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ownerbhawan = dataSnapshot.getValue(String.class);
                liveData.observe(getViewLifecycleOwner(), new Observer<DataSnapshot>() {
                    @Override
                    public void onChanged(DataSnapshot datasnapshot) {
                        ownerMenus = new ArrayList<>();
                        adapter = new OwnerMenuAdapter(ownerMenus, getContext());
                        DataSnapshot dataSnapshot1 = datasnapshot.child(ownerbhawan).child("Menu");
                        for (DataSnapshot readData : dataSnapshot1.getChildren()) {
                            ownerMenus.add(new OwnerMenu(readData.getKey(), Integer.parseInt(readData.getValue().toString())));
                            adapter.notifyDataSetChanged();
                            Log.d("check", ownerMenus.toString());
                        }
                        progressDialog.dismiss();
                        enableSwipeToDelete();
                        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(adapter);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_ownerMenuFragment_to_ownerAddItemFragment);
            }
        });

    }

    private void enableSwipeToDelete(){
        SwipeToDelete swipeToDelete=new SwipeToDelete(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
               final int position=viewHolder.getAdapterPosition();
               adapter.removeItem(position);
            }
        };
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(swipeToDelete);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
