package pe.reciclaya.app.ui.auth.verificacion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import pe.reciclaya.app.R;
import pe.reciclaya.app.ui.common.event.EventObserver;
import pe.reciclaya.app.ui.common.util.CodigoVerificacion;
import pe.reciclaya.app.ui.main.navigation.MainManager;

// HU-3.2: ingreso del código de verificación enviado al correo
public class VerificacionActivity extends AppCompatActivity {
    public static final String EXTRA_EMAIL = "pe.reciclaya.app.extra.EMAIL";
    public static final String EXTRA_CHALLENGE = "pe.reciclaya.app.extra.CHALLENGE";

    private ImageView IVRegresar;
    private TextView TVCorreo, TVReenviarCodigo;
    private EditText[] casillas;
    private Button BtnVerificar;
    private LinearLayout LLLoading;

    private VerificacionViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificacion);

        String email = getIntent().getStringExtra(EXTRA_EMAIL);
        String challenge = getIntent().getStringExtra(EXTRA_CHALLENGE);
        if(email == null || challenge == null) {
            finish();
            return;
        }

        inicializarComponentes();
        inicializarVM(email, challenge);
        inicializarListeners();
    }

    private void inicializarComponentes() {
        IVRegresar = findViewById(R.id.IVRegresarVC);
        TVCorreo = findViewById(R.id.TVCorreoVC);
        TVReenviarCodigo = findViewById(R.id.TVReenviarCodigoVC);
        BtnVerificar = findViewById(R.id.BtnVerificarVC);
        LLLoading = findViewById(R.id.LLLoadingVC);

        casillas = new EditText[] {
                findViewById(R.id.ETCod1VC),
                findViewById(R.id.ETCod2VC),
                findViewById(R.id.ETCod3VC),
                findViewById(R.id.ETCod4VC),
                findViewById(R.id.ETCod5VC),
                findViewById(R.id.ETCod6VC)
        };
        CodigoVerificacion.configurar(casillas);
    }

    private void inicializarVM(String email, String challenge) {
        viewModel = new ViewModelProvider(this).get(VerificacionViewModel.class);
        viewModel.inicializar(email, challenge);

        TVCorreo.setText(getString(R.string.verificacion_enviado_a, viewModel.getEmail()));

        viewModel.getLoading().observe(this, isLoading -> {
            LLLoading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            BtnVerificar.setEnabled(!isLoading);
        });

        viewModel.getMensaje().observe(this, new EventObserver<>(mensaje ->
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
        ));

        viewModel.getSegundosReenvio().observe(this, segundos -> {
            boolean disponible = segundos == null || segundos <= 0;
            TVReenviarCodigo.setEnabled(disponible);
            TVReenviarCodigo.setTextColor(getColor(disponible ? R.color.verde : R.color.gris_intermedio));
            TVReenviarCodigo.setText(disponible
                    ? getString(R.string.verificacion_reenviar)
                    : getString(R.string.verificacion_reenviar_en, segundos));
        });

        viewModel.getRoleResponse().observe(this, new EventObserver<>(roleResponse -> {
            startActivity(new Intent(this, MainManager.crearMainFactory(roleResponse)));
            finishAffinity();
        }));
    }

    private void inicializarListeners() {
        IVRegresar.setOnClickListener(view -> finish());
        TVReenviarCodigo.setOnClickListener(view -> viewModel.reenviarCodigo());
        BtnVerificar.setOnClickListener(view -> viewModel.verificar(CodigoVerificacion.leer(casillas)));
    }
}
