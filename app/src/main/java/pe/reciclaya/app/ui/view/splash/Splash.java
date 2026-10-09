package pe.reciclaya.app.ui.view.splash;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.splashscreen.SplashScreen;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import pe.reciclaya.app.ui.util.EventObserver;
import pe.reciclaya.app.ui.view.main.manager.MainManager;
import pe.reciclaya.app.ui.viewmodel.SplashViewModel;

public class Splash extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);

        inicializarVM();
    }

    private void inicializarVM() {
        SplashViewModel viewModel = new ViewModelProvider(this).get(SplashViewModel.class);

        viewModel.getRoleResponse().observe(this, new EventObserver<>(roleResponse -> {
            startActivity(new Intent(this, MainManager.crearMainFactory(roleResponse)));
            finish();
        }));
    }
}