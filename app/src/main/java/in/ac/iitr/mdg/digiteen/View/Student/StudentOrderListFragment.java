package in.ac.iitr.mdg.digiteen.View.Student;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.ac.iitr.mdg.digiteen.Adapter.MyOrderAdapter;
import in.ac.iitr.mdg.digiteen.Model.MyOrder;
import in.ac.iitr.mdg.digiteen.R;
import in.ac.iitr.mdg.digiteen.ViewModel.StudentDataViewModel;
import com.google.firebase.database.DataSnapshot;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StudentOrderListFragment extends Fragment {
    private RecyclerView recyclerView;
    private MyOrderAdapter myOrderAdapter;
    private ProgressDialog progressDialog;
    public StudentOrderListFragment() {
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
        return inflater.inflate(R.layout.fragment_student_order_list, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.order_details_list_recyclerview);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Fetching orders...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StudentDataViewModel viewModel = ViewModelProviders.of(this).get(StudentDataViewModel.class);
        LiveData<DataSnapshot> liveData = viewModel.getdatasnapshotlivedata();
        liveData.observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    List<MyOrder> list = new ArrayList<>();
                    myOrderAdapter = new MyOrderAdapter(getContext(), list, getActivity());
                    for (DataSnapshot readdata : dataSnapshot.getChildren()) {
                        list.add(new MyOrder(readdata.getKey(), readdata.child("status").getValue().toString()));
                        myOrderAdapter.notifyDataSetChanged();
                    }
                    progressDialog.dismiss();
                    RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(myOrderAdapter);
                }
            }
        });
    }
}