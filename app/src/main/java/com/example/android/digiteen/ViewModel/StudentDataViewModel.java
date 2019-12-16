package com.example.android.digiteen.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.digiteen.FireBaseQueryLiveData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentDataViewModel extends ViewModel {
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    private final DatabaseReference databaseref= FirebaseDatabase.getInstance().getReference().child("user").child(firebaseAuth.getCurrentUser().getUid()).child("order");
    private FireBaseQueryLiveData liveData=new FireBaseQueryLiveData(databaseref);

    @NonNull
    public LiveData<DataSnapshot> getdatasnapshotlivedata(){
        return liveData;
    }
}
