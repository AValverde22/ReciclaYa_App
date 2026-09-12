package pe.reciclaya.app.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;

import pe.reciclaya.app.R;
import pe.reciclaya.app.ui.tyc.TyCActivity;

public class RegisterActivity extends AppCompatActivity {

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

        viewModel.getError().observe(this, errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        );

        viewModel.irSiguiente().observe(this, irSiguiente -> {
            if(irSiguiente) {
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.FLRegister, new FragmentRegisterSeleccion())
                        .addToBackStack(null)
                        .commit();
            }
        });

        viewModel.getTipo().observe(this, tipo ->
                startActivity(new Intent(this, TyCActivity.class)
                        .putExtra("Tipo", tipo))
        );

        viewModel.getFinalizarActivity().observe(this, finalizar -> {
            if(finalizar) finish();
        });

        viewModel.getEliminarFragment().observe(this, eliminar -> {
            if(eliminar) getSupportFragmentManager().popBackStack();
        });
    }
}