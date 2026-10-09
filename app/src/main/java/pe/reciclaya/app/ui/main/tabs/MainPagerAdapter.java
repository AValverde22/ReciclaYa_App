package pe.reciclaya.app.ui.main.tabs;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MainPagerAdapter extends FragmentStateAdapter {
    private final MainTabsAbstractFactory mainTabsAbstractFactory;

    public MainPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, MainTabsAbstractFactory mainTabsAbstractFactory) {
        super(fragmentManager, lifecycle);
        this.mainTabsAbstractFactory = mainTabsAbstractFactory;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mainTabsAbstractFactory.crearFragment(position);
    }

    @Override
    public int getItemCount() {
        return mainTabsAbstractFactory.getTabCount();
    }
}
