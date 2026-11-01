package pe.reciclaya.app.ui.main.seguridad.historial;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDateTime;

import pe.reciclaya.app.R;

public class AccesoRVA extends RecyclerView.Adapter<AccesoRVA.ViewHolder> {

    private final OnAccesoClickListener listener;
    private java.util.List<AccesoItem> accesos;

    public AccesoRVA(
            java.util.List<AccesoItem> accesos,
            OnAccesoClickListener listener
    ) {
        this.accesos = accesos;
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setAccesos(java.util.List<AccesoItem> accesos) {
        this.accesos = accesos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_acceso, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position
    ) {
        holder.imprimir(accesos.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return accesos == null ? 0 : accesos.size();
    }

    public interface OnAccesoClickListener {
        void onAccesoSelected(AccesoItem acceso);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final Context context;

        private final MaterialCardView MCVFondo;
        private final TextView TVIdentificador;
        private final TextView TVEstado;
        private final TextView TVTipoActividad;
        private final TextView TVFecha;
        private final TextView TVUbicacion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();

            MCVFondo = itemView.findViewById(R.id.MCVFondoIA);
            TVIdentificador = itemView.findViewById(R.id.TVIdentificadorIA);
            TVEstado = itemView.findViewById(R.id.TVEstadoIA);
            TVTipoActividad = itemView.findViewById(R.id.TVTipoActividadIA);
            TVFecha = itemView.findViewById(R.id.TVFechaIA);
            TVUbicacion = itemView.findViewById(R.id.TVUbicacionIA);
        }

        public void imprimir(
                AccesoItem acceso,
                OnAccesoClickListener listener
        ) {
            TVIdentificador.setText("ACCESO #" + acceso.getId());

            TVTipoActividad.setText(
                    acceso.getTipoActividad() == null
                            ? "Actividad desconocida"
                            : acceso.getTipoActividad()
            );

            TVFecha.setText(formatearFecha(acceso.getFechaLogin()));
            TVUbicacion.setText(acceso.getUbicacion());

            if (acceso.isAutorizado()) {
                TVEstado.setText("AUTORIZADO");
                TVEstado.setTextColor(context.getColor(R.color.verde_claro));
            } else if (acceso.isRechazado()) {
                TVEstado.setText("RECHAZADO");
                TVEstado.setTextColor(context.getColor(R.color.rojo));
            } else {
                TVEstado.setText("DESCONOCIDO");
                TVEstado.setTextColor(context.getColor(R.color.black));
            }

            MCVFondo.setOnClickListener(view -> {
                if (listener != null) {
                    listener.onAccesoSelected(acceso);
                }
            });
        }

        private String formatearFecha(String fecha) {
            if (fecha == null || fecha.isBlank()) {
                return "Fecha no disponible";
            }

            try {
                return OffsetDateTime.parse(fecha)
                        .atZoneSameInstant(ZoneId.of("America/Lima"))
                        .format(DateTimeFormatter.ofPattern("dd/MM/uuuu · hh:mm a"));

            } catch (DateTimeParseException ignored) {
                try {
                    return LocalDateTime.parse(fecha)
                            .format(DateTimeFormatter.ofPattern("dd/MM/uuuu · hh:mm a"));
                } catch (DateTimeParseException ignoredAgain) {
                    return fecha;
                }
            }
        }
    }
}