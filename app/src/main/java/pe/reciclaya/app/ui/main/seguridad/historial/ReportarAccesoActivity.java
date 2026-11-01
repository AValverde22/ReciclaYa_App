package pe.reciclaya.app.ui.main.seguridad.historial;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import pe.reciclaya.app.R;
import pe.reciclaya.app.ui.common.event.EventObserver;
import pe.reciclaya.app.ui.main.seguridad.AccesosViewModel;

public class ReportarAccesoActivity extends AppCompatActivity {

    private TextView TVAcceso;
    private TextInputEditText ETDescripcion;
    private MaterialButton BTEnviar;
    private ProgressBar PBReporte;

    private AccesosViewModel viewModel;
    private int accesoId;

    private static final String MENSAJE_EXITO =
            "Reporte registrado correctamente.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportar_acceso);

        accesoId = getIntent().getIntExtra("ACCESO_ID", -1);

        if (accesoId <= 0) {
            Toast.makeText(
                    this,
                    "Identificador de acceso inválido.",
                    Toast.LENGTH_LONG
            ).show();
            finish();
            return;
        }

        inicializarComponentes();
        inicializarViewModel();
        observarDatos();

        TVAcceso.setText("Estás reportando el acceso #" + accesoId);

        BTEnviar.setOnClickListener(v -> enviarReporte());
    }

    private void inicializarComponentes() {
        TVAcceso = findViewById(R.id.TVAccesoReporteRA);
        ETDescripcion = findViewById(R.id.ETDescripcionRA);
        BTEnviar = findViewById(R.id.BTEnviarReporteRA);
        PBReporte = findViewById(R.id.PBReporteRA);
    }

    private void inicializarViewModel() {
        viewModel = new ViewModelProvider(this)
                .get(AccesosViewModel.class);
    }

    private void observarDatos() {

        viewModel.getLoading().observe(this, loading -> {
            boolean cargando = Boolean.TRUE.equals(loading);

            PBReporte.setVisibility(
                    cargando ? View.VISIBLE : View.GONE
            );

            BTEnviar.setEnabled(!cargando);
        });

        viewModel.getMensaje().observe(
                this,
                new EventObserver<>(mensaje -> {

                    Toast.makeText(
                            this,
                            mensaje,
                            Toast.LENGTH_LONG
                    ).show();

                    if (MENSAJE_EXITO.equals(mensaje)) {
                        setResult(RESULT_OK);
                        finish();
                    }
                })
        );
    }

    private void enviarReporte() {

        String descripcion = ETDescripcion.getText() == null
                ? ""
                : ETDescripcion.getText().toString().trim();

        if (descripcion.length() > 2000) {
            ETDescripcion.setError(
                    "La descripción no puede superar los 2000 caracteres."
            );
            return;
        }

        viewModel.reportarAcceso(accesoId, descripcion);
    }
}