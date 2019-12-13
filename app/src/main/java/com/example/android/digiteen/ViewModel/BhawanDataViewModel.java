package com.example.android.digiteen.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.android.digiteen.FireBaseQueryLiveData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BhawanDataViewModel extends ViewModel {
    private static final DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("bhawan");
    private final FireBaseQueryLiveData fireBaseQueryLiveData=new FireBaseQueryLiveData(ref);

    @NonNull
    public LiveData<DataSnapshot> getdatasnapshotlivedata(){
        return fireBaseQueryLiveData;
    }
}
