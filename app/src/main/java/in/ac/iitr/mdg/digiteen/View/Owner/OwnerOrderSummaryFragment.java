package in.ac.iitr.mdg.digiteen.View.Owner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.ac.iitr.mdg.digiteen.Adapter.OwnerTabAdapter;
import in.ac.iitr.mdg.digiteen.R;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class OwnerOrderSummaryFragment extends Fragment {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private OwnerTabAdapter ownerTabAdapter;

    public OwnerOrderSummaryFragment() {
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
        return inflater.inflate(R.layout.fragment_owner_order_summary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewPager=view.findViewById(R.id.owner_pager);
        tabLayout=view.findViewById(R.id.owner_tab_layout);
        List<Fragment> fragment=new ArrayList<>();
        List<String> names=new ArrayList<>();
        fragment.add(new PaymentPendingFragment());
        fragment.add(new AcceptedOrderFragment());
        fragment.add(new CompletedOrderFragment());
        names.add("PAYMENT PENDING");
        names.add("APPROVED");
        names.add("COMPLETED");
        ownerTabAdapter=new OwnerTabAdapter(getChildFragmentManager(),fragment,names);
        viewPager.setAdapter(ownerTabAdapter);
    }
}
