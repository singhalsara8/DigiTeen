package com.example.android.digiteen;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;


public class StudentSignUpFragment extends Fragment {

    private EditText mStudentName, mStudentEnroll,mStudentHostel,mStudentNumber,mStudentemail,mStudentPassword;
    private Button mStudentRegister;
    FirebaseAuth fauth;
    NavController navController;

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
        mStudentName=view.findViewById(R.id.student_name);
        mStudentEnroll=view.findViewById(R.id.student_enroll);
        mStudentHostel=view.findViewById(R.id.student_hostel);
        mStudentNumber=view.findViewById(R.id.student_number);
        mStudentemail=view.findViewById(R.id.student_email);
        mStudentPassword=view.findViewById(R.id.password);
        mStudentRegister=view.findViewById(R.id.student_register);
        fauth=FirebaseAuth.getInstance();
        navController= Navigation.findNavController(getActivity(),R.id.my_nav_host_fragment);
        mStudentRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=mStudentName.getText().toString().trim();
                if(TextUtils.isEmpty(name))
                {
                    mStudentName.setError("This field cannot be empty");
                    return;
                }
                String enroll=mStudentEnroll.getText().toString().trim();
                if(TextUtils.isEmpty(enroll))
                {
                    mStudentEnroll.setError("This field cannot be empty");
                    return;
                }
                String hostel=mStudentHostel.getText().toString().trim();
                if(TextUtils.isEmpty(hostel))
                {
                    mStudentHostel.setError("This field cannot be empty");
                    return;
                }
                String number=mStudentNumber.getText().toString().trim();
                if(TextUtils.isEmpty(number))
                {
                    mStudentNumber.setError("This field cannot be empty");
                    return;
                }
                String email = mStudentemail.getText().toString().trim();
                String password = mStudentPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    mStudentemail.setError("Email is Required");
                    return;
                }

                if(TextUtils.isEmpty(password))
                {
                    mStudentPassword.setError("Password is Required");
                    return;
                }
            }
        });

    }
}
