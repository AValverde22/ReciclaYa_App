package pe.reciclaya.app.ui.main.tabs;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MainPagerAdapter extends FragmentStateAdapter {
    private final MainTabsFactory mainTabsFactory;

    public MainPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, MainTabsFactory mainTabsFactory) {
        super(fragmentManager, lifecycle);
        this.mainTabsFactory = mainTabsFactory;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position) {
            case 0: return mainTabsFactory.crearPrimerFragment();
            case 1: return mainTabsFactory.crearSegundoFragment();
            case 2: return mainTabsFactory.crearTercerFragment();
            default: return mainTabsFactory.crearCuartoFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
