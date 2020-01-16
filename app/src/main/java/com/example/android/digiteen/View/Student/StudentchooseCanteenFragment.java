package com.example.android.digiteen.View.Student;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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

import com.example.android.digiteen.Adapter.RadioButtonAdapter;
import com.example.android.digiteen.R;
import com.example.android.digiteen.ViewModel.BhawanDataViewModel;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;


public class StudentchooseCanteenFragment extends Fragment {
    RadioButtonAdapter adapter;
    RecyclerView radiobttn;
    Bundle bundle;
    String s;
    NavController navController;
    Button bttn;
    ProgressDialog progressDialog;

    public StudentchooseCanteenFragment() {
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
        return inflater.inflate(R.layout.fragment_studentchoose_canteen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        bttn = view.findViewById(R.id.submit);
        bundle = new Bundle();
        radiobttn = view.findViewById(R.id.recyclerView);
        navController = Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        BhawanDataViewModel viewModel = ViewModelProviders.of(this).get(BhawanDataViewModel.class);
        LiveData<DataSnapshot> liveData = viewModel.getdatasnapshotlivedata();
        liveData.observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    final List<String> list = new ArrayList<>();
                    adapter = new RadioButtonAdapter(list, getContext());
                    for (DataSnapshot readData : dataSnapshot.getChildren()) {
                        list.add(readData.getKey());
                        adapter.notifyDataSetChanged();
                        Log.d("list", list.toString());
                    }
                    progressDialog.dismiss();
                    RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    radiobttn.setLayoutManager(linearLayoutManager);
                    radiobttn.setAdapter(adapter);
                }
            }
        });
        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getItemCount() != 0) {
                    s = adapter.getUserId();
                    bundle.putString("list", s);
                    navController.navigate(R.id.action_studentchooseCanteenFragment_to_studentSelectItemFragment, bundle);
                }
                else
                {
                    Toast.makeText(getContext(),"Nothing selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
