package pe.reciclaya.app.ui.view.splash;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.splashscreen.SplashScreen;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import pe.reciclaya.app.antiguo.reciclador.activities.RecicladorMainActivity;
import pe.reciclaya.app.antiguo.usuario.activities.UsuarioMainActivity;
import pe.reciclaya.app.ui.viewmodel.SplashViewModel;
import pe.reciclaya.app.ui.view.login.LoginActivity;

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
                startActivity(new Intent(this, UsuarioMainActivity.class));

            else
                startActivity(new Intent(this, RecicladorMainActivity.class));

            finish();
        });
    }
}