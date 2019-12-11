package com.example.android.digiteen.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.digiteen.R;
import com.example.android.digiteen.Adapter.TabAdapter;
import com.example.android.digiteen.View.Owner.OwnerSignUpFragment;
import com.example.android.digiteen.View.Student.StudentSignUpFragment;

import java.util.ArrayList;
import java.util.List;

public class SignUpFragment extends Fragment {
    TabAdapter adapter;
     ViewPager viewPager;
    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final List<String> title=new ArrayList<>();
        title.add("Student SignUp");
        title.add("Owner SignUp");
        final List<Fragment> fragment=new ArrayList<>();
        fragment.add(new StudentSignUpFragment());
        fragment.add(new OwnerSignUpFragment());
        adapter=new TabAdapter(getChildFragmentManager(), fragment,title);
        viewPager= view.findViewById(R.id.pager);
        viewPager.setAdapter(adapter);
    }

}
