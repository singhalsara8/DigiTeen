package com.example.android.digiteen.View.Student;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.digiteen.Adapter.Menu_Adapter;
import com.example.android.digiteen.Model.MenuItem;
import com.example.android.digiteen.Model.SelectMenu;
import com.example.android.digiteen.Model.Total;
import com.example.android.digiteen.R;
import com.example.android.digiteen.ViewModel.BhawanDataViewModel;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;


public class StudentSelectItemFragment extends Fragment {
    String bhawan;
    RecyclerView recyclerView;
    Menu_Adapter menuAdapter;
    NavController navController;
    Button bttn;
    ProgressDialog progressDialog;

    public StudentSelectItemFragment() {
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
        return inflater.inflate(R.layout.fragment_student_select_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        bttn = view.findViewById(R.id.place_order);
        navController = Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment);
        bhawan = getArguments().getString("list");
        recyclerView = view.findViewById(R.id.select_item_recyclerview);
        Log.d("bhawan", bhawan);
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        BhawanDataViewModel viewModel = ViewModelProviders.of(this).get(BhawanDataViewModel.class);
        LiveData<DataSnapshot> liveData = viewModel.getdatasnapshotlivedata();
        liveData.observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    final List<SelectMenu> list = new ArrayList<>();
                    menuAdapter = new Menu_Adapter(getContext(), list);
                    for (DataSnapshot readData : dataSnapshot.child(bhawan).child("Menu").getChildren()) {
                        Log.d("debug", readData.getValue().toString());
                        int price = Integer.parseInt(readData.getValue().toString());
                        MenuItem menuItem = new MenuItem(readData.getKey(), 0, 0, price);
                        SelectMenu selectMenu = new SelectMenu(SelectMenu.MENU_TYPE, menuItem);
                        list.add(selectMenu);
                        menuAdapter.notifyDataSetChanged();
                    }
                    progressDialog.dismiss();
                    Total total = new Total(0);
                    SelectMenu selectMenu1 = new SelectMenu(SelectMenu.TOTAL_TYPE, total);
                    list.add(selectMenu1);
                    menuAdapter.notifyDataSetChanged();
                    RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(menuAdapter);
                }
            }
        });
        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_studentSelectItemFragment_to_orderDetailFragment);
            }
        });
    }
}