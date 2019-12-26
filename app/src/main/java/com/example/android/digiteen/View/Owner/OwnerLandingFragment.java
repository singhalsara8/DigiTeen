package com.example.android.digiteen.View.Owner;

import android.os.Bundle;
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
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.android.digiteen.R;
import com.example.android.digiteen.ViewModel.BhawanDataViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class OwnerLandingFragment extends Fragment {
    private Button button;
    private Button order,menu;
    private FirebaseAuth fauth;
    private NavController navController;
    private DatabaseReference databaseReference,reference;
    private String ownerbhawan;

    public OwnerLandingFragment() {
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
        return inflater.inflate(R.layout.fragment_owner_landing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        order=view.findViewById(R.id.owner_order);
        menu=view.findViewById(R.id.owner_menu);
        button=view.findViewById(R.id.owner_logout);
        fauth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        reference=FirebaseDatabase.getInstance().getReference();
        navController= Navigation.findNavController(getActivity(),R.id.my_nav_host_fragment);
        BhawanDataViewModel bhawanDataViewModel= ViewModelProviders.of(getActivity()).get(BhawanDataViewModel.class);
        final LiveData<DataSnapshot> liveData=bhawanDataViewModel.getdatasnapshotlivedata();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fauth.signOut();
                NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.ownerLandingFragment, true).build();
                navController.navigate(R.id.action_ownerLandingFragment_to_loginFragment2,null,navOptions);
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_ownerLandingFragment_to_ownerMenuFragment);
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               reference=databaseReference.child("user").child(fauth.getCurrentUser().getUid()).child("profile").child("bhawan");
               reference.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       ownerbhawan=dataSnapshot.getValue(String.class);
                       liveData.observe(getViewLifecycleOwner(), new Observer<DataSnapshot>() {
                           @Override
                           public void onChanged(DataSnapshot dataSnapshot) {
                               DataSnapshot dataSnapshot1=dataSnapshot.child(ownerbhawan);
                                   if(dataSnapshot1.hasChild("Menu"))
                                   {
                                       navController.navigate(R.id.action_ownerLandingFragment_to_ownerOrderSummaryFragment);
                                   }
                                   else
                                   {
                                       Toast.makeText(getContext(),"Upload menu first", Toast.LENGTH_SHORT).show();
                                   }
                           }
                       });
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });
            }
        });
    }
}
