package com.example.android.digiteen.View.Owner;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.digiteen.Adapter.AddItemAdapter;
import com.example.android.digiteen.Model.AddMenuItem;
import com.example.android.digiteen.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class OwnerAddItemFragment extends Fragment {
    private Button button;
    private DatabaseReference databaseReference;
    private List<AddMenuItem> menuItemList;
    private FirebaseAuth firebaseAuth;
    private AddItemAdapter addItemAdapter;
    private RecyclerView recyclerView;

    public OwnerAddItemFragment() {
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
        return inflater.inflate(R.layout.fragment_owner_add_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        button=view.findViewById(R.id.owner_add_item_button);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        recyclerView=view.findViewById(R.id.owner_add_item_recyclerview);
        menuItemList=new ArrayList<>();
        addItemAdapter=new AddItemAdapter(getContext(),menuItemList);
        menuItemList.add(new AddMenuItem("maggi", 20));
        addItemAdapter.notifyDataSetChanged();
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(addItemAdapter);
    }
}
