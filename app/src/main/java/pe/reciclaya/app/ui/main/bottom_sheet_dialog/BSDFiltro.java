package pe.reciclaya.app.ui.main.bottom_sheet_dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;
import java.util.Locale;

import pe.reciclaya.app.R;
import pe.reciclaya.app.ui.common.event.EventObserver;
import pe.reciclaya.app.ui.main.tipo_residuo_list.Papel;
import pe.reciclaya.app.ui.main.tipo_residuo_list.Plastico;
import pe.reciclaya.app.ui.main.tipo_residuo_list.TipoResiduoItem;
import pe.reciclaya.app.ui.main.tipo_residuo_list.TipoResiduoRVA;
import pe.reciclaya.app.ui.main.tipo_residuo_list.Vidrio;

public class BSDFiltro extends BottomSheetDialogFragment {
    private RecyclerView RVTipoResiduo;
    private ViewModelFiltro viewModel;
    private TipoResiduoRVA tipoResiduoRVA;
    private TipoResiduoItem[] tipoResiduos;

    private TextView TVFecha, TVHora;
    private Button BtnAplicar, BtnReset;
    private RatingBar RB;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bs_filtro, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        inicializarComponentes(view);
        inicializarVM();
        inicializarTipoResiduoRA();
        inicializarListeners();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(dialogInterface -> {
            if (getView() != null) {
                View parent = (View) getView().getParent();
                parent.setBackground(new ColorDrawable(Color.TRANSPARENT));
            }
        });

        return dialog;
    }

    private void inicializarComponentes(View view) {
        RVTipoResiduo = view.findViewById(R.id.RVTipoResiduoBSDF);
        TVFecha = view.findViewById(R.id.TVFechaBSDF);
        TVHora = view.findViewById(R.id.TVHoraBSDF);
        RB = view.findViewById(R.id.RBBSDF);
        BtnAplicar = view.findViewById(R.id.BtnAplicarFiltrosBSDF);
        BtnReset = view.findViewById(R.id.BtnResetFiltrosBSDF);

        tipoResiduos = new TipoResiduoItem[]{
                new Plastico(),
                new Vidrio(),
                new Papel()
        };
    }

    private void inicializarVM() {
        LifecycleOwner lifecycleOwner = getViewLifecycleOwner();
        viewModel = new ViewModelProvider(requireParentFragment()).get(ViewModelFiltro.class);

        viewModel.getError().observe(lifecycleOwner, new EventObserver<>(error ->
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        ));

        viewModel.getErrorFecha().observe(lifecycleOwner,error -> {
            TVFecha.setError(error ? "" : null);
            TVHora.setEnabled(!error);
        });

        viewModel.getTipoResiduo().observe(getViewLifecycleOwner(), tipo -> {
            if (tipo == null) {
                tipoResiduoRVA.limpiarSeleccionVisual();
                return;
            }

            for (int i = 0; i < tipoResiduos.length; i++) {
                if (tipoResiduos[i].getNombre().equalsIgnoreCase(tipo)) {
                    tipoResiduoRVA.setPosSeleccionada(i);
                    return;
                }
            }

            tipoResiduoRVA.limpiarSeleccionVisual();
        });

        viewModel.getFecha().observe(lifecycleOwner, fecha ->
                TVFecha.setText(fecha == null ? "" : fecha)
        );

        viewModel.getHora().observe(lifecycleOwner, hora ->
                TVHora.setText(hora == null ? "" : hora)
        );

        viewModel.getScore().observe(lifecycleOwner, score ->
                RB.setRating(score == null ? 0f : Float.parseFloat(score))
        );
    }

    private void inicializarTipoResiduoRA() {


        tipoResiduoRVA = new TipoResiduoRVA(tipoResiduos, tipoResiduo -> viewModel.updateTipoResiduo(tipoResiduo));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollHorizontally() { return false; }
        };

        RVTipoResiduo.setLayoutManager(linearLayoutManager);
        RVTipoResiduo.setAdapter(tipoResiduoRVA);
    }

    private void inicializarListeners() {
        TVFecha.setOnClickListener(view -> mostrarSelectorFecha());

        TVHora.setOnClickListener(view -> {
            viewModel.updateFecha(TVFecha.getText().toString());
            if(TVFecha.getError() == null) mostrarSelectorHora();
        });

        RB.setOnRatingBarChangeListener((ratingBar, v, b) -> {
            if(b) viewModel.updateScore(String.valueOf(v));
        });

        BtnAplicar.setOnClickListener(view -> {
            viewModel.aplicarFiltros();
            dismiss();
        });

        BtnReset.setOnClickListener(view -> {
            viewModel.resetFiltros();
            dismiss();
        });
    }

    private void mostrarSelectorFecha() {
        final Calendar now = Calendar.getInstance();
        new DatePickerDialog(
                requireContext(),
                (datePicker, year, month, dayOfMonth) -> {
                    String fecha = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);
                    viewModel.updateFecha(fecha);
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void mostrarSelectorHora() {
        final Calendar now = Calendar.getInstance();
        new TimePickerDialog(
                requireContext(),
                (timePicker, hourOfDay, minute) -> {
                    String hora = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                    viewModel.updateHora(hora);
                },
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        ).show();
    }
}
