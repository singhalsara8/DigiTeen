package com.example.android.digiteen;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OwnerSignUpFragment extends Fragment {
    private EditText ownerName, ownerCanteen, ownerNumber, ownerEmail, ownerPw;
    private Button ownerRegistration;
    FirebaseAuth fauth;
    DatabaseReference ref;

    public OwnerSignUpFragment() {
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
        return inflater.inflate(R.layout.fragment_owner_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        fauth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
        ownerName = view.findViewById(R.id.Owner_name);
        ownerCanteen = view.findViewById(R.id.canteen);
        ownerNumber = view.findViewById(R.id.owner_number);
        ownerEmail = view.findViewById(R.id.owner_email);
        ownerPw = view.findViewById(R.id.owner_password);
        ownerRegistration = view.findViewById(R.id.owner_register);

        ownerRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = ownerName.getText().toString().trim();
                final String canteen = ownerCanteen.getText().toString().trim();
                final String number = ownerNumber.getText().toString().trim();
                final String email = ownerEmail.getText().toString().trim();
                final String password = ownerPw.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    ownerName.setError("This field cannot be empty");
                } else if (TextUtils.isEmpty(canteen)) {
                    ownerCanteen.setError("This field cannot be empty");
                } else if (TextUtils.isEmpty(number)) {
                    ownerNumber.setError("This field cannot be empty");
                } else if (TextUtils.isEmpty(email)) {
                    ownerEmail.setError("Email is required");
                } else if (TextUtils.isEmpty(password)) {
                    ownerPw.setError("Password is required");
                }
                else {
                    fauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                DatabaseReference ownrref=ref.child("user").child(fauth.getCurrentUser().getUid());
                                ownrref.child("Profile").child("name").setValue(name);
                                ownrref.child("Profile").child("canteen").setValue(canteen);
                                ownrref.child("Profile").child("number").setValue(number);
                                ownrref.child("Profile").child("email").setValue(email);
                                ownrref.child("Profile").child("category").setValue("owner");
                            }
                        }
                    });
                    Toast.makeText(getContext(),"Registration successful",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
