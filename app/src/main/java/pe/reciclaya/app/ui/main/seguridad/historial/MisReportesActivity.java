package pe.reciclaya.app.ui.main.seguridad.historial;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pe.reciclaya.app.R;
import pe.reciclaya.app.ui.common.event.EventObserver;
import pe.reciclaya.app.ui.main.seguridad.AccesosViewModel;

public class MisReportesActivity extends AppCompatActivity {

    private RecyclerView RV;
    private TextView TVVacio;
    private ProgressBar PBLista;

    private ReporteActividadRVA adapter;
    private AccesosViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_reportes);

        inicializarComponentes();
        inicializarAdapter();
        inicializarViewModel();
        observarDatos();

        viewModel.cargarMisReportes();
    }

    private void inicializarComponentes() {
        RV = findViewById(R.id.RVMisReportes);
        TVVacio = findViewById(R.id.TVVacioReportes);
        PBLista = findViewById(R.id.PBListaReportes);
    }

    private void inicializarAdapter() {
        adapter = new ReporteActividadRVA(new ArrayList<>());

        RV.setLayoutManager(new LinearLayoutManager(this));
        RV.setAdapter(adapter);
    }

    private void inicializarViewModel() {
        viewModel = new ViewModelProvider(this)
                .get(AccesosViewModel.class);
    }

    private void observarDatos() {

        viewModel.getMisReportes().observe(this, reportes -> {

            adapter.setReportes(reportes);

            boolean vacio = reportes == null || reportes.isEmpty();

            TVVacio.setVisibility(
                    vacio ? View.VISIBLE : View.GONE
            );

            RV.setVisibility(
                    vacio ? View.GONE : View.VISIBLE
            );
        });

        viewModel.getLoading().observe(this, loading -> {
            PBLista.setVisibility(
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
}