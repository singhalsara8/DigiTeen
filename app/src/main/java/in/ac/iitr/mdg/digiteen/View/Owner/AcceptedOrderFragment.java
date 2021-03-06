package in.ac.iitr.mdg.digiteen.View.Owner;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.ac.iitr.mdg.digiteen.Adapter.ApprovedOrderAdapter;
import in.ac.iitr.mdg.digiteen.Model.PaymentPending;
import in.ac.iitr.mdg.digiteen.R;
import in.ac.iitr.mdg.digiteen.ViewModel.BhawanDataViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class AcceptedOrderFragment extends Fragment {
    private RecyclerView recyclerView;
    private ApprovedOrderAdapter approvedOrderAdapter;
    private List<PaymentPending> list;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    private String ownerbhawan;
    private ProgressDialog progressDialog;

    public AcceptedOrderFragment() {
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
        return inflater.inflate(R.layout.fragment_accepted_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView=view.findViewById(R.id.owner_approved_ordered_list_recyclerview);
        databaseReference= FirebaseDatabase.getInstance().getReference();
//        progressDialog=new ProgressDialog(getContext());
//        progressDialog.setMessage("Loading...");
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.show();
        DatabaseReference reference=databaseReference.child("user").child(firebaseAuth.getCurrentUser().getUid()).child("profile").child("bhawan");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ownerbhawan=dataSnapshot.getValue(String.class);
                BhawanDataViewModel bhawanDataViewModel= ViewModelProviders.of(getParentFragment()).get(BhawanDataViewModel.class);
                LiveData<DataSnapshot> liveData=bhawanDataViewModel.getdatasnapshotlivedata();
                liveData.observe(getViewLifecycleOwner(), new Observer<DataSnapshot>() {
                    @Override
                    public void onChanged(DataSnapshot dataSnapshot) {
                        if(dataSnapshot!=null)
                        {
                            list = new ArrayList<>();
                            approvedOrderAdapter = new ApprovedOrderAdapter(getContext(),list,getActivity());
                            DataSnapshot dataSnapshot1=dataSnapshot.child(ownerbhawan).child("status").child("Approved");
                            for (DataSnapshot readdata : dataSnapshot1.getChildren())
                            {
                                list.add(new PaymentPending(readdata.getKey(),Integer.parseInt(readdata.getValue().toString())));
                                approvedOrderAdapter.notifyDataSetChanged();
                            }
 //                           progressDialog.dismiss();
                            RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setAdapter(approvedOrderAdapter);
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
