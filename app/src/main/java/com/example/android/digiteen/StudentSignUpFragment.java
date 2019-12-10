package com.example.android.digiteen;

import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class StudentSignUpFragment extends Fragment {

    private EditText mStudentName, mStudentEnroll, mStudentHostel, mStudentNumber, mStudentemail, mStudentPassword;
    private Button mStudentRegister;
    private FirebaseAuth fauth;
    private NavController navController;
    private DatabaseReference ref;


    public StudentSignUpFragment() {
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
        return inflater.inflate(R.layout.fragment_student_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mStudentName = view.findViewById(R.id.student_name);
        mStudentEnroll = view.findViewById(R.id.student_enroll);
        mStudentHostel = view.findViewById(R.id.student_hostel);
        mStudentNumber = view.findViewById(R.id.student_number);
        mStudentemail = view.findViewById(R.id.student_email);
        mStudentPassword = view.findViewById(R.id.student_password);
        mStudentRegister = view.findViewById(R.id.student_register);
        fauth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
        navController = Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment);

        mStudentRegister.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                final String name = mStudentName.getText().toString().trim();
                final String enroll = mStudentEnroll.getText().toString().trim();
                final String hostel = mStudentHostel.getText().toString().trim();
                final String number = mStudentNumber.getText().toString().trim();
                final String emailid = mStudentemail.getText().toString().trim();
                final String password = mStudentPassword.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    mStudentName.setError("This field cannot be empty");
                } else if (TextUtils.isEmpty(enroll)) {
                    mStudentEnroll.setError("This field cannot be empty");
                } else if (TextUtils.isEmpty(hostel)) {
                    mStudentHostel.setError("This field cannot be empty");
                } else if (TextUtils.isEmpty(number)) {
                    mStudentNumber.setError("This field cannot be empty");
                } else if (TextUtils.isEmpty(emailid)) {
                    mStudentemail.setError("Email is required");
                } else if (TextUtils.isEmpty(password)) {
                    mStudentPassword.setError("Password is required");
                } else {
                    fauth.createUserWithEmailAndPassword(emailid, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                DatabaseReference usref = ref.child("user").child(fauth.getCurrentUser().getUid());
                                usref.child("profile").child("name").setValue(name);
                                usref.child("profile").child("enrollment").setValue(enroll);
                                usref.child("profile").child("hostel").setValue(hostel);
                                usref.child("profile").child("number").setValue(number);
                                usref.child("profile").child("emailid").setValue(emailid);
                                usref.child("profile").child("category").setValue("student");
                                Toast.makeText(getContext(),"Registration successful",Toast.LENGTH_SHORT).show();
                                NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build();
                                navController.navigate(R.id.action_signUpFragment_to_studentLandingFragment, null, navOptions);

                            }
                            else
                            {
                                Toast.makeText(getContext(),"Registration unsuccessful",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
    });

}
}
