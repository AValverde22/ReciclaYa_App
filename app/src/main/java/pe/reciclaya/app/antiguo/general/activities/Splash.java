package pe.reciclaya.app.antiguo.general.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.splashscreen.SplashScreen;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import pe.reciclaya.app.antiguo.reciclador.activities.MainActivityReciclador;
import pe.reciclaya.app.antiguo.usuario.activities.MainActivityUsuario;
import pe.reciclaya.app.data.model.login.LoginResponse;
import pe.reciclaya.app.ui.login.LoginActivity;
import pe.reciclaya.app.ui.splash.SplashViewModel;
import pe.reciclaya.app.ui.util.Common;
import pe.reciclaya.app.antiguo.general.util.Singleton;

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