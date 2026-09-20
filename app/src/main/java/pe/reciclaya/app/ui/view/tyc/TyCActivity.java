package pe.reciclaya.app.ui.view.tyc;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import pe.reciclaya.app.R;
import pe.reciclaya.app.ui.viewmodel.TyCViewModel;

public class TyCActivity extends AppCompatActivity {
    private TextView TVTitulo, TVMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tyc_activity);

        inicializarComponentes();
        inicializarVM();
    }

    private void inicializarComponentes() {
        TVTitulo = findViewById(R.id.TVTituloTyC);
        TVMensaje = findViewById(R.id.TVMensajeTyC);
    }

    private void inicializarVM() {
        TyCViewModel viewModel = new ViewModelProvider(this).get(TyCViewModel.class);

        viewModel.getTitulo().observe(this, tituloID -> {
            TVTitulo.setText(tituloID);
        });

        viewModel.getMensaje().observe(this, mensajeID -> {
            TVMensaje.setText(mensajeID);
        });


        int tipo = getIntent().getIntExtra("Tipo", 0);
        viewModel.cargarContenido(tipo);
    }
}