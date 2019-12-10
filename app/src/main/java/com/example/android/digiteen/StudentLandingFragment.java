package com.example.android.digiteen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;


public class StudentLandingFragment extends Fragment {
    FirebaseAuth fauth;
    private Button logout;
    private NavController navController;

    public StudentLandingFragment() {
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
        return inflater.inflate(R.layout.fragment_student_landing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        logout=view.findViewById(R.id.logout);
        fauth=FirebaseAuth.getInstance();
        navController= Navigation.findNavController(getActivity(),R.id.my_nav_host_fragment);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fauth.signOut();
                NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.studentLandingFragment, true).build();
                navController.navigate(R.id.action_studentLandingFragment_to_loginFragment,null,navOptions);
            }
        });

    }
}
