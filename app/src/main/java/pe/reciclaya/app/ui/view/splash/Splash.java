package pe.reciclaya.app.ui.view.splash;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.splashscreen.SplashScreen;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import pe.reciclaya.app.ui.util.EventObserver;
import pe.reciclaya.app.ui.view.main.MainManager;
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

        viewModel.getDestinoConfirmado().observe(this, new EventObserver<>(destinoConfirmado -> {
            if(destinoConfirmado) {
                startActivity(new Intent(this, MainManager.crearMainFactory()));
                finish();
            }
        }));
    }
}