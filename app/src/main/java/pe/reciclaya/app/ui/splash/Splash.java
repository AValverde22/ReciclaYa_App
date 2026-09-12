package pe.reciclaya.app.ui.splash;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.splashscreen.SplashScreen;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import pe.reciclaya.app.antiguo.reciclador.activities.MainActivityReciclador;
import pe.reciclaya.app.antiguo.usuario.activities.MainActivityUsuario;
import pe.reciclaya.app.ui.login.LoginActivity;

public class Splash extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);

        inicializarVM();
    }

    private void inicializarVM() {
        SplashViewModel viewModel = new ViewModelProvider(this).get(SplashViewModel.class);
        viewModel.getSavedRole().observe(this, role -> {
            if(role == null)
                startActivity(new Intent(this, LoginActivity.class));

            else if(role.equals("Usuario"))
                startActivity(new Intent(this, MainActivityUsuario.class));

            else
                startActivity(new Intent(this, MainActivityReciclador.class));

            finish();
        });
    }
}