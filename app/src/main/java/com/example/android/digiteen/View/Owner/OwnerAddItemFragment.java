package com.example.android.digiteen.View.Owner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static android.app.Activity.RESULT_OK;


public class OwnerAddItemFragment extends Fragment {
    private Button button;
    private DatabaseReference databaseReference, reference;
    private FirebaseAuth firebaseAuth;
    private EditText itemname, itemprice;
    private String ownerbhawan;
    private NavController navController;
    private ImageView imageView;
    private StorageReference storageReference, storageReference1;
    Uri selectimage;
    private ProgressDialog progressDialog;

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
        imageView = view.findViewById(R.id.owner_choose_item_button);
        itemname = view.findViewById(R.id.owner_itemname);
        itemprice = view.findViewById(R.id.owner_itemprice);
        button = view.findViewById(R.id.owner_upload_item_button);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        storageReference1 = FirebaseStorage.getInstance().getReference();
        reference = FirebaseDatabase.getInstance().getReference();
        navController = Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemname.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Field Name cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (itemprice.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Field price cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setMessage("Uploading...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    reference = databaseReference.child("user").child(firebaseAuth.getCurrentUser().getUid()).child("profile").child("bhawan");
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ownerbhawan = dataSnapshot.getValue(String.class);
                            Log.d("bhawan", ownerbhawan);
                            databaseReference.child("bhawan").child(ownerbhawan).child("Menu").child(itemname.getText().toString()).setValue(Integer.parseInt(itemprice.getText().toString()));
                            UploadImage();
                            progressDialog.dismiss();
                            NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.ownerMenuFragment, true).build();
                            navController.navigate(R.id.action_ownerAddItemFragment_to_ownerMenuFragment, null, navOptions);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });
    }

    private void SelectImage() {
        Intent pickphoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickphoto, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                selectimage = data.getData();
                imageView.setImageURI(selectimage);
            }
        }
    }

    private void UploadImage() {
        if (selectimage != null) {
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String bhawan = dataSnapshot.getValue().toString();
                    String value = itemname.getText().toString();
                    StorageReference storageReference1 = storageReference.child(bhawan).child(value);
                    storageReference1.putFile(selectimage);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
