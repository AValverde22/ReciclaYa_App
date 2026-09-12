package pe.reciclaya.app.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import pe.reciclaya.app.ui.register.RegisterActivity;
import pe.reciclaya.app.antiguo.reciclador.activities.MainActivityReciclador;
import pe.reciclaya.app.antiguo.usuario.activities.MainActivityUsuario;
import pe.reciclaya.app.ui.util.Inicializaciones;

import pe.reciclaya.app.R;

public class LoginActivity extends AppCompatActivity {

    private EditText ETEmail, ETPassword;
    private Button BtnIniciarSesion;
    private LinearLayout LLLoading;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inicializarComponentes();
        inicializarVM();
        inicializarListeners();
    }

    private void inicializarComponentes() {
        ETEmail = findViewById(R.id.ETEmailLogin);
        ETPassword = findViewById(R.id.ETPasswordLogin);
        BtnIniciarSesion = findViewById(R.id.BtnIniciarSesionLogin);
        LLLoading = findViewById(R.id.LLLoadingLogin);

        ImageView IVOjo = findViewById(R.id.IVOjoLogin);
        Inicializaciones.mostrarPassword(ETPassword, IVOjo);
    }

    private void inicializarVM() {
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        viewModel.getLoading().observe(this, isLoading -> {
            if(isLoading) {
                LLLoading.setVisibility(View.VISIBLE);
                BtnIniciarSesion.setEnabled(false);
            } else {
                LLLoading.setVisibility(View.GONE);
                BtnIniciarSesion.setEnabled(true);
            }
        });

        viewModel.getRoleResponse().observe(this, role -> {
            if(role.equals("Usuario"))
                startActivity(new Intent(this, MainActivityUsuario.class));
            else
                startActivity(new Intent(this, MainActivityReciclador.class));

            finishAffinity();
        });

        viewModel.getError().observe(this, errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        );

        viewModel.getEmailError().observe(this, error ->
                ETEmail.setError(error)
        );

        viewModel.getPasswordError().observe(this, error ->
                ETPassword.setError(error)
        );
    }

    private void inicializarListeners() {
        ETEmail.setOnFocusChangeListener((view, hasFocus) -> {
            if(!hasFocus) viewModel.updateEmail(ETEmail.getText().toString());
        });

        ETPassword.setOnFocusChangeListener((view, hasFocus) -> {
            if(!hasFocus) viewModel.updatePassword(ETPassword.getText().toString());
        });
    }

    public void iniciarSesion(View view) {
        String email = ETEmail.getText().toString();
        String password = ETPassword.getText().toString();

        viewModel.iniciarSesion(email, password);
    }

    public void irARegistro(View view) { startActivity(new Intent(this, RegisterActivity.class)); }
}