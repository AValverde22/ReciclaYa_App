package pe.reciclaya.app.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import pe.reciclaya.app.R;
import pe.reciclaya.app.ui.main.tabs.MainTabsAbstractFactory;
import pe.reciclaya.app.ui.main.tabs.MainPagerAdapter;

public abstract class MainActivity extends AppCompatActivity {
    private LinearLayout LLLoading;
    protected TabLayout tabLayout;
    private ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        LLLoading = findViewById(R.id.LLLoadingMain);
        viewPager2 = findViewById(R.id.VPMain);
        tabLayout = findViewById(R.id.TLMain);

        MainTabsAbstractFactory mainTabsAbstractFactory = getMainTabFactory();

        FragmentManager fragmentManager = getSupportFragmentManager();
        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(fragmentManager, getLifecycle(), mainTabsAbstractFactory);

        viewPager2.setAdapter(mainPagerAdapter);
        viewPager2.setUserInputEnabled(false);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, pos) -> {
            tab.setText(mainTabsAbstractFactory.getTabName(pos));
            tab.setIcon(mainTabsAbstractFactory.getTabIcon(pos));
        }).attach();
    }

    protected abstract MainTabsAbstractFactory getMainTabFactory();

    public void mostrarLLoading(boolean mostrar) {
        if(mostrar) LLLoading.setVisibility(View.VISIBLE);
        else LLLoading.setVisibility(View.GONE);
    }
}