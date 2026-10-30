package pe.reciclaya.app.ui.main.seguridad;

import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.ViewModelProvider;

import pe.reciclaya.app.R;
import pe.reciclaya.app.ui.common.event.EventObserver;

// HU-3.1: activar o desactivar la autenticación de dos factores
public class SeguridadCuentaActivity extends AppCompatActivity {
    private ImageView IVRegresar;
    private TextView TVDescripcion;
    private SwitchCompat SW2FA;
    private LinearLayout LLLoading;

    private SeguridadCuentaViewModel viewModel;

    // Evita reaccionar a los cambios del interruptor hechos desde el código
    private boolean actualizandoInterruptor = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguridad_cuenta);

        inicializarComponentes();
        inicializarVM();
        inicializarListeners();
    }

    private void inicializarComponentes() {
        IVRegresar = findViewById(R.id.IVRegresarSC);
        TVDescripcion = findViewById(R.id.TVDescripcionSC);
        SW2FA = findViewById(R.id.SW2FASC);
        LLLoading = findViewById(R.id.LLLoadingSC);
    }

    private void inicializarVM() {
        viewModel = new ViewModelProvider(this).get(SeguridadCuentaViewModel.class);

        viewModel.getEmail().observe(this, email ->
                TVDescripcion.setText(getString(R.string.seguridad_2fa_descripcion, email))
        );

        viewModel.getHabilitado().observe(this, habilitado -> {
            actualizandoInterruptor = true;
            SW2FA.setChecked(Boolean.TRUE.equals(habilitado));
            actualizandoInterruptor = false;
            actualizarDisponibilidad();
        });

        viewModel.getLoading().observe(this, isLoading -> {
            LLLoading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            actualizarDisponibilidad();
        });

        viewModel.getMensaje().observe(this, new EventObserver<>(mensaje ->
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
        ));
    }

    private void inicializarListeners() {
        IVRegresar.setOnClickListener(view -> finish());

        SW2FA.setOnCheckedChangeListener((boton, activado) -> {
            if(actualizandoInterruptor) return;

            if(activado) viewModel.activar();
            else confirmarDesactivacion();
        });
    }

    // El interruptor solo se usa cuando se conoce el estado y no hay una operación en curso
    private void actualizarDisponibilidad() {
        boolean cargando = Boolean.TRUE.equals(viewModel.getLoading().getValue());
        SW2FA.setEnabled(!cargando && viewModel.getHabilitado().getValue() != null);
    }

    private void confirmarDesactivacion() {
        EditText ETPassword = new EditText(this);
        ETPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        ETPassword.setHint(R.string.Contrasena);

        int margen = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
        FrameLayout contenedor = new FrameLayout(this);
        contenedor.setPadding(margen, 0, margen, 0);
        contenedor.addView(ETPassword);

        new AlertDialog.Builder(this)
                .setTitle(R.string.seguridad_confirmar_titulo)
                .setMessage(R.string.seguridad_confirmar_mensaje)
                .setView(contenedor)
                .setPositiveButton(R.string.seguridad_desactivar, (dialog, which) ->
                        viewModel.desactivar(ETPassword.getText().toString()))
                .setNegativeButton(R.string.Cancelar, (dialog, which) -> viewModel.restaurarEstado())
                .setOnCancelListener(dialog -> viewModel.restaurarEstado())
                .show();
    }
}
