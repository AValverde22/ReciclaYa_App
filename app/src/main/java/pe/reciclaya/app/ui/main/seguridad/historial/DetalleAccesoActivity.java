package pe.reciclaya.app.ui.main.seguridad.historial;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import pe.reciclaya.app.R;
import pe.reciclaya.app.data.model.user.response.LoginAccessResponse;
import pe.reciclaya.app.ui.common.event.EventObserver;
import pe.reciclaya.app.ui.main.seguridad.AccesosViewModel;

public class DetalleAccesoActivity extends AppCompatActivity {

    private TextView TVIdentificador;
    private TextView TVEstado;
    private TextView TVActividad;
    private TextView TVFecha;
    private TextView TVIp;
    private TextView TVDispositivo;
    private TextView TVVersion;
    private TextView TVUbicacion;
    private TextView TVCoordenadas;

    private ProgressBar PBDetalle;
    private MaterialButton BTReportar;

    private AccesosViewModel viewModel;

    private int accesoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_acceso);

        accesoId = getIntent().getIntExtra("ACCESO_ID", -1);

        if (accesoId <= 0) {
            Toast.makeText(
                    this,
                    "No se recibió un identificador de acceso válido.",
                    Toast.LENGTH_LONG
            ).show();
            finish();
            return;
        }

        inicializarComponentes();
        inicializarViewModel();
        observarDatos();

        BTReportar.setOnClickListener(v -> abrirFormularioReporte());

        viewModel.cargarDetalleAcceso(accesoId);
    }

    private void inicializarComponentes() {
        TVIdentificador = findViewById(R.id.TVIdentificadorDA);
        TVEstado = findViewById(R.id.TVEstadoDA);
        TVActividad = findViewById(R.id.TVActividadDA);
        TVFecha = findViewById(R.id.TVFechaDA);
        TVIp = findViewById(R.id.TVIpDA);
        TVDispositivo = findViewById(R.id.TVDispositivoDA);
        TVVersion = findViewById(R.id.TVVersionDA);
        TVUbicacion = findViewById(R.id.TVUbicacionDA);
        TVCoordenadas = findViewById(R.id.TVCordenadasDA);

        PBDetalle = findViewById(R.id.PBDetalleDA);
        BTReportar = findViewById(R.id.BTReportarDA);
    }

    private void inicializarViewModel() {
        viewModel = new ViewModelProvider(this)
                .get(AccesosViewModel.class);
    }

    private void observarDatos() {

        viewModel.getDetalleAcceso().observe(this, acceso -> {
            if (acceso != null && acceso.getId() == accesoId) {
                imprimirDetalle(acceso);
            }
        });

        viewModel.getLoading().observe(this, loading -> {
            PBDetalle.setVisibility(
                    Boolean.TRUE.equals(loading)
                            ? View.VISIBLE
                            : View.GONE
            );
        });

        viewModel.getMensaje().observe(
                this,
                new EventObserver<>(mensaje ->
                        Toast.makeText(
                                this,
                                mensaje,
                                Toast.LENGTH_SHORT
                        ).show()
                )
        );
    }

    private void imprimirDetalle(LoginAccessResponse acceso) {

        TVIdentificador.setText("ACCESO #" + acceso.getId());

        String estado = valor(acceso.getEstado());
        TVEstado.setText(estado.toUpperCase());

        if ("Autorizado".equalsIgnoreCase(estado)) {
            TVEstado.setTextColor(
                    getColor(R.color.verde_claro)
            );
        } else if ("Rechazado".equalsIgnoreCase(estado)) {
            TVEstado.setTextColor(
                    getColor(R.color.rojo)
            );
        } else {
            TVEstado.setTextColor(
                    getColor(R.color.black)
            );
        }

        TVActividad.setText(valor(acceso.getTipoActividad()));
        TVFecha.setText(formatearFecha(acceso.getFechaLogin()));
        TVIp.setText(valor(acceso.getDireccionIp()));
        TVDispositivo.setText(valor(acceso.getDispositivo()));
        TVVersion.setText(valor(acceso.getAppVersion()));

        String ubicacion = valor(acceso.getCiudad());

        if (!"No disponible".equals(ubicacion)
                && acceso.getPais() != null
                && !acceso.getPais().isBlank()) {
            ubicacion += ", " + acceso.getPais();
        } else if ("No disponible".equals(ubicacion)) {
            ubicacion = valor(acceso.getPais());
        }

        TVUbicacion.setText(ubicacion);

        if (acceso.getLatitud() != null
                && acceso.getLongitud() != null) {
            TVCoordenadas.setText(
                    acceso.getLatitud() + ", " + acceso.getLongitud()
            );
        } else {
            TVCoordenadas.setText("No disponibles");
        }
    }

    private String valor(String dato) {
        return dato == null || dato.isBlank()
                ? "No disponible"
                : dato;
    }

    private String formatearFecha(String fecha) {

        if (fecha == null || fecha.isBlank()) {
            return "Fecha no disponible";
        }

        try {
            return OffsetDateTime.parse(fecha)
                    .atZoneSameInstant(ZoneId.of("America/Lima"))
                    .format(DateTimeFormatter.ofPattern(
                            "dd/MM/uuuu · hh:mm a"
                    ));

        } catch (DateTimeParseException ignored) {
            try {
                return LocalDateTime.parse(fecha)
                        .format(DateTimeFormatter.ofPattern(
                                "dd/MM/uuuu · hh:mm a"
                        ));
            } catch (DateTimeParseException ignoredAgain) {
                return fecha;
            }
        }
    }

    private void abrirFormularioReporte() {

        android.content.Intent intent = new android.content.Intent(
                this,
                ReportarAccesoActivity.class
        );

        intent.putExtra("ACCESO_ID", accesoId);

        startActivity(intent);
    }
}