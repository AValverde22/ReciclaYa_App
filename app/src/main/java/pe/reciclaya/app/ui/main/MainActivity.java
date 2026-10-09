package pe.reciclaya.app.ui.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
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

    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if(!isGranted) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        comprobarPermisoDeUbicacion();
        inicializarComponentes();
    }

    private void comprobarPermisoDeUbicacion() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&  ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
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