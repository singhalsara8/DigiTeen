package in.ac.iitr.mdg.digiteen.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class OwnerTabAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<String> name;

    public OwnerTabAdapter(@NonNull FragmentManager fm, List<Fragment> fragments, List<String> name) {
        super(fm);
        this.fragments = fragments;
        this.name = name;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return name.get(position);
    }
}
