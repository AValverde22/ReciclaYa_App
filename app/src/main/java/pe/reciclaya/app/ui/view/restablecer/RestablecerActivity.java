package pe.reciclaya.app.ui.view.restablecer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import pe.reciclaya.app.R;
import pe.reciclaya.app.antiguo.reciclador.activities.RecicladorMainActivity;
import pe.reciclaya.app.antiguo.usuario.activities.UsuarioMainActivity;
import pe.reciclaya.app.ui.viewmodel.RestablecerViewModel;

public class RestablecerActivity extends AppCompatActivity {
    private LinearLayout LLLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restablecer);

        LLLoading = findViewById(R.id.LLLoadingRestablecer);
        inicializarVM();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.FLRestablecer, new FragmentRestablecerEnviarCodigo())
                .commit();
    }

    private void inicializarVM() {
        RestablecerViewModel viewModel = new ViewModelProvider(this).get(RestablecerViewModel.class);

        viewModel.getLoading().observe(this, isLoading -> {
            if(isLoading) LLLoading.setVisibility(View.VISIBLE);
            else LLLoading.setVisibility(View.GONE);
        });

        viewModel.getError().observe(this, errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        );

        viewModel.getFinalizarActivity().observe(this, finalizar -> {
            if(finalizar) finish();
        });

        viewModel.getEliminarFragment().observe(this, eliminar -> {
            if(eliminar) getSupportFragmentManager().popBackStack();
        });

        viewModel.irSiguiente().observe(this, irSiguiente -> {
            if(irSiguiente) {
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.FLRegister, new FragmentRestablecerCompararCodigo())
                        .addToBackStack(null)
                        .commit();
            }
        });

        viewModel.irSubSiguiente().observe(this, irSubSiguiente -> {
            if(irSubSiguiente)
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.FLRegister, new FragmentRestablecerCambiarPassword())
                        .addToBackStack(null)
                        .commit();
        });

        viewModel.getRoleResponse().observe(this, role -> {
            if(role.equals("Usuario"))
                startActivity(new Intent(this, UsuarioMainActivity.class));
            else
                startActivity(new Intent(this, RecicladorMainActivity.class));

            finishAffinity();
        });
    }
}