package com.example.android.digiteen.View.Student;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.digiteen.Adapter.Menu_Adapter;
import com.example.android.digiteen.Model.MenuItem;
import com.example.android.digiteen.Model.SelectMenu;
import com.example.android.digiteen.Model.Total;
import com.example.android.digiteen.R;
import com.example.android.digiteen.ViewModel.BhawanDataViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class StudentSelectItemFragment extends Fragment {
    private String bhawan;
    private RecyclerView recyclerView;
    private Menu_Adapter menuAdapter;
    private NavController navController;
    private Button bttn;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference reference;
    private List<SelectMenu> selectMenus;
    private List<MenuItem> menuItems;
    private String token;
    private Bundle bundle;
//    private ConnectivityManager cm;


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
//        cm= (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        bundle=new Bundle();
        selectMenus=new ArrayList<>();
        menuItems=new ArrayList<>();
        firebaseAuth=FirebaseAuth.getInstance();
        reference= FirebaseDatabase.getInstance().getReference();
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
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setMessage("Do you want to confirm order");
                builder.setTitle("CONFIRMATION");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyMMddHHmmss");
                        token=simpleDateFormat.format(new Date());
                        Log.d("timestamp",token);
                        selectMenus=menuAdapter.getList();
                        for (int j=0; j<selectMenus.size()-1; j++)
                        {
                            if(selectMenus.get(j).getMenuItem().getMnumber()!=0)
                            {
                                menuItems.add(selectMenus.get(j).getMenuItem());
                            }
                        }
                        for(int k=0; k<menuItems.size();k++)
                        {
                            reference.child("user").child(firebaseAuth.getCurrentUser().getUid()).child("order").child(token).child("item").child(menuItems.get(k).getMitem()).child("price").setValue(menuItems.get(k).getMamount());
                            reference.child("user").child(firebaseAuth.getCurrentUser().getUid()).child("order").child(token).child("item").child(menuItems.get(k).getMitem()).child("quantity").setValue(menuItems.get(k).getMnumber());
                            reference.child("bhawan").child(bhawan).child("order").child(token).child("item").child(menuItems.get(k).getMitem()).child("price").setValue(menuItems.get(k).getMamount());
                            reference.child("bhawan").child(bhawan).child("order").child(token).child("item").child(menuItems.get(k).getMitem()).child("quantity").setValue(menuItems.get(k).getMnumber());

                        }
                        bundle.putString("token",token);
                        navController.navigate(R.id.action_studentSelectItemFragment_to_orderDetailFragment,bundle);
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });
    }
}