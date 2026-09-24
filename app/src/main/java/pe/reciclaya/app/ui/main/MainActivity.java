package pe.reciclaya.app.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import pe.reciclaya.app.R;
import pe.reciclaya.app.ui.main.tabs.MainTabsFactory;
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

    public void mostrarLLoading(boolean mostrar) {
        if(mostrar) LLLoading.setVisibility(View.VISIBLE);
        else LLLoading.setVisibility(View.GONE);
    }
}