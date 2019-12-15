package com.example.android.digiteen.View.Student;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.digiteen.Adapter.OrderSummaryAdapter;
import com.example.android.digiteen.Model.OrderSummary;
import com.example.android.digiteen.R;
import com.example.android.digiteen.ViewModel.BhawanDataViewModel;
import com.example.android.digiteen.ViewModel.StudentDataViewModel;
import com.google.firebase.database.DataSnapshot;

import java.math.BigInteger;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class OrderDetailFragment extends Fragment {
    Calendar calendar;
    OrderSummaryAdapter adapter;
    RecyclerView recyclerView;
    long token;

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
//        //calendar=Calendar.getInstance(Locale.ENGLISH);
//        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyMMddHHmmss");
//        String s=simpleDateFormat.format(new Date());
//      // Long sys=System.currentTimeMillis()/1000;
//       //String s=sys.toString();
//        Log.d("timestamp",s);
        token=123456789;
        recyclerView=view.findViewById(R.id.order_details_recyclerview);
        StudentDataViewModel viewModel= ViewModelProviders.of(this).get(StudentDataViewModel.class);
        LiveData<DataSnapshot> liveData= viewModel.getdatasnapshotlivedata();
        liveData.observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if (dataSnapshot!=null) {
                    List<OrderSummary> list = new ArrayList<>();
                    adapter = new OrderSummaryAdapter(getContext(), list);
                    for (DataSnapshot readData:dataSnapshot.child("order").child(String.valueOf(token)).getChildren())
                    {
                        Log.d("key",readData.getKey());
                    }
                }
            }
        });

    }
}
