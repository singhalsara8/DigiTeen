package com.example.android.digiteen.View.Owner;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.android.digiteen.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_spinner_item;

public class OwnerSignUpFragment extends Fragment {
    private EditText ownerName, ownerNumber, ownerEmail, ownerPw;
    private Button ownerRegistration;
    private Spinner spinner;
    FirebaseAuth fauth;
    DatabaseReference ref;
    NavController navController;
    ProgressDialog progressDialog;
    public static String bhawan;

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
        ownerNumber = view.findViewById(R.id.owner_number);
        ownerEmail = view.findViewById(R.id.owner_email);
        progressDialog=new ProgressDialog(getContext());
        ownerPw = view.findViewById(R.id.owner_password);
        ownerRegistration = view.findViewById(R.id.owner_register);
        spinner=view.findViewById(R.id.canteen_spinner);
        navController= Navigation.findNavController(getActivity(),R.id.my_nav_host_fragment);
        List<String> canteen=new ArrayList<>();
        canteen.add("Azad Bhawan");
        canteen.add("Cautley Bhawan");
        canteen.add("Ganga Bhawan");
        canteen.add("Govind Bhawan");
        canteen.add("Jawahar Bhawan");
        canteen.add("Radhakrishnan Bhawan");
        canteen.add("Rajendra Bhawan");
        canteen.add("Rajiv Bhawan");
        canteen.add("Ravindra Bhawan");
        canteen.add("Sarojini Bhawan");
        canteen.add("Kasturba Bhawan");
        ArrayAdapter<String> adapter= new ArrayAdapter<>(getContext(), simple_spinner_item, canteen);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              bhawan=spinner.getSelectedItem().toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        ownerRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = ownerName.getText().toString().trim();
                final String number = ownerNumber.getText().toString().trim();
                final String email = ownerEmail.getText().toString().trim();
                final String password = ownerPw.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    ownerName.setError("This field cannot be empty");
                }  else if (TextUtils.isEmpty(number)) {
                    ownerNumber.setError("This field cannot be empty");
                } else if (TextUtils.isEmpty(email)) {
                    ownerEmail.setError("Email is required");
                } else if (TextUtils.isEmpty(password)) {
                    ownerPw.setError("Password is required");
                }
                else {
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setMessage("Registering in please wait....");
                    progressDialog.show();
                    fauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                progressDialog.dismiss();
                                DatabaseReference ownrref=ref.child("user").child(fauth.getCurrentUser().getUid());
                                ownrref.child("profile").child("name").setValue(name);
                                ownrref.child("profile").child("number").setValue(number);
                                ownrref.child("profile").child("email").setValue(email);
                                ownrref.child("profile").child("bhawan").setValue(bhawan);
                                ownrref.child("profile").child("category").setValue("owner");
                                Toast.makeText(getContext(),"Registration successful",Toast.LENGTH_SHORT).show();
                                NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build();
                                navController.navigate(R.id.action_signUpFragment_to_ownerLandingFragment, null, navOptions);
                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(),"Registration unsuccessful",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
