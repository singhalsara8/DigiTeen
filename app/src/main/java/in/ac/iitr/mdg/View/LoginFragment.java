package in.ac.iitr.mdg.View;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import in.ac.iitr.mdg.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginFragment extends Fragment {
    View view;
    private EditText memail, mpassword;
    private FirebaseAuth fAuth;
    private DatabaseReference ref;
    private NavController navController;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        memail = view.findViewById(R.id.email_id);
        mpassword = view.findViewById(R.id.password);
        Button mloginbtn = view.findViewById(R.id.login_btn);
        TextView msignin = view.findViewById(R.id.signUp);
        fAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
        navController = Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment);
        checkUserLoggedIn();
        mloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = memail.getText().toString().trim();
                String password = mpassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    memail.setError("Email is Required");
                    return;
                } else if (TextUtils.isEmpty(password)) {
                    mpassword.setError("Password is Required");
                    return;
                }
                progressDialog = new ProgressDialog(getContext());
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("Logging in please wait....");
                progressDialog.show();
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (fAuth.getCurrentUser().isEmailVerified()) {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "login successful", Toast.LENGTH_SHORT).show();
                                DatabaseReference usr = ref.child("user").child(fAuth.getCurrentUser().getUid()).child("profile");
                                usr.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String ctgry = dataSnapshot.child("category").getValue(String.class);
                                        NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build();
                                        assert ctgry != null;
                                        if (ctgry.equals("student")) {
                                            navController.navigate(R.id.action_loginFragment_to_studentLandingFragment, null, navOptions);
                                        } else {
                                            navController.navigate(R.id.action_loginFragment_to_ownerLandingFragment, null, navOptions);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        fAuth.signOut();
                                        Toast.makeText(getContext(), "Login unsuccessful", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "Please verify your email address.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Login unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        msignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_loginFragment_to_signUpFragment);
            }
        });
        return view;
    }

    public void checkUserLoggedIn() {
        if (fAuth.getCurrentUser() != null && fAuth.getCurrentUser().isEmailVerified()) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Logging in please wait....");
            progressDialog.show();
            DatabaseReference usr = ref.child("user").child(fAuth.getCurrentUser().getUid()).child("profile");
            usr.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String ctgry = dataSnapshot.child("category").getValue(String.class);
                    NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build();
                    progressDialog.dismiss();
                    assert ctgry != null;
                    if (ctgry.equals("student")) {
                        navController.navigate(R.id.action_loginFragment_to_studentLandingFragment, null, navOptions);
                    } else {
                        navController.navigate(R.id.action_loginFragment_to_ownerLandingFragment, null, navOptions);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    progressDialog.dismiss();
                    fAuth.signOut();
                    Toast.makeText(getContext(), "Login unsuccessful", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
