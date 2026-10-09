package pe.reciclaya.app.ui.view.main.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import pe.reciclaya.app.ui.view.main.abstract_factory.MainTabsFactory;

public class MainPagerAdapter extends FragmentStateAdapter {
    private final Fragment primerFragment, segundoFragment, tercerFragment, cuartoFragment;

    public MainPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, MainTabsFactory mainTabsFactory) {
        super(fragmentManager, lifecycle);

        primerFragment = mainTabsFactory.crearPrimerFragment();
        segundoFragment = mainTabsFactory.crearSegundoFragment();
        tercerFragment = mainTabsFactory.crearTercerFragment();
        cuartoFragment = mainTabsFactory.crearCuartoFragment();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position) {
            case 0: return primerFragment;
            case 1: return segundoFragment;
            case 2: return tercerFragment;
            default: return cuartoFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
