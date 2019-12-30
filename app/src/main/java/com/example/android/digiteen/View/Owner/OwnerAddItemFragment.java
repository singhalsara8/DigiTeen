package com.example.android.digiteen.View.Owner;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.android.digiteen.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class OwnerAddItemFragment extends Fragment {
    private Button button;
    private DatabaseReference databaseReference, reference;
    private FirebaseAuth firebaseAuth;
    private EditText itemname, itemprice;
    private String ownerbhawan;
    private NavController navController;

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
        itemname = view.findViewById(R.id.owner_itemname);
        itemprice = view.findViewById(R.id.owner_itemprice);
        button = view.findViewById(R.id.owner_upload_item_button);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        reference = FirebaseDatabase.getInstance().getReference();
        navController = Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference = databaseReference.child("user").child(firebaseAuth.getCurrentUser().getUid()).child("profile").child("bhawan");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ownerbhawan = dataSnapshot.getValue(String.class);
                        Log.d("bhawan", ownerbhawan);
                        databaseReference.child("bhawan").child(ownerbhawan).child("Menu").child(itemname.getText().toString()).setValue(Integer.parseInt(itemprice.getText().toString()));
                        NavOptions navOptions=new NavOptions.Builder().setPopUpTo(R.id.ownerMenuFragment,true).build();
                        navController.navigate(R.id.action_ownerAddItemFragment_to_ownerMenuFragment,null,navOptions);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
