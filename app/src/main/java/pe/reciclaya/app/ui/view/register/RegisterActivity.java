package pe.reciclaya.app.ui.view.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;

import pe.reciclaya.app.R;
import pe.reciclaya.app.ui.util.EventObserver;
import pe.reciclaya.app.ui.util.FragmentNavigation;
import pe.reciclaya.app.ui.view.main.MainManager;
import pe.reciclaya.app.ui.view.register.fragments.FragmentRegisterDatos;
import pe.reciclaya.app.ui.view.register.fragments.FragmentRegisterSeleccion;
import pe.reciclaya.app.ui.view.tyc.TyCActivity;
import pe.reciclaya.app.ui.viewmodel.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity implements FragmentNavigation {

    private LinearLayout LLLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        LLLoading = findViewById(R.id.LLLoadingRegister);
        inicializarVM();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.FLRegister, new FragmentRegisterDatos())
                .commit();
    }

    private void inicializarVM() {
        RegisterViewModel viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        viewModel.getLoading().observe(this, isLoading -> {
            if(isLoading) LLLoading.setVisibility(View.VISIBLE);
            else LLLoading.setVisibility(View.GONE);
        });

        viewModel.getError().observe(this, new EventObserver<>(errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        ));

        viewModel.getTipo().observe(this, new EventObserver<>(tipo ->
            startActivity(new Intent(this, TyCActivity.class)
                    .putExtra("Tipo", tipo))
        ));

        viewModel.irSiguiente().observe(this, new EventObserver<>(irSiguiente -> {
            if(irSiguiente) {
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.FLRegister, new FragmentRegisterSeleccion())
                        .addToBackStack(null)
                        .commit();
            }
        }));

        viewModel.getRoleResponse().observe(this, new EventObserver<>(roleResponse -> {
            if(roleResponse) {
                startActivity(new Intent(this, MainManager.crearMainFactory()));
                finishAffinity();
            }
        }));
    }

    @Override
    public void navigateBack() {
        if(getSupportFragmentManager().getBackStackEntryCount() == 0) finish();
        else getSupportFragmentManager().popBackStack();
    }
}