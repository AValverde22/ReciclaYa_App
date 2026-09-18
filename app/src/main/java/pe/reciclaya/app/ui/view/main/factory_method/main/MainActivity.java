package pe.reciclaya.app.ui.view.main.factory_method.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import pe.reciclaya.app.R;
import pe.reciclaya.app.ui.view.main.abstract_factory.MainTabsFactory;
import pe.reciclaya.app.ui.view.main.adapter.MainPagerAdapter;

public abstract class MainActivity extends AppCompatActivity {
    protected TabLayout tabLayout;
    private ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        viewPager2 = findViewById(R.id.VPMain);
        tabLayout = findViewById(R.id.TLMain);

        FragmentManager fragmentManager = getSupportFragmentManager();
        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(fragmentManager, getLifecycle(), getMainTabFactory());

        viewPager2.setAdapter(mainPagerAdapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) { tabLayout.selectTab(tabLayout.getTabAt(position)); }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) { viewPager2.setCurrentItem(tab.getPosition()); }

            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });

        modificarNombresTabLayout();
    }

    protected abstract MainTabsFactory getMainTabFactory();
    protected abstract void modificarNombresTabLayout();
}