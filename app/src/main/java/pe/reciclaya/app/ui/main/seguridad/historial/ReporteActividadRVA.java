package pe.reciclaya.app.ui.main.seguridad.historial;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.OffsetDateTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import pe.reciclaya.app.R;
import pe.reciclaya.app.data.model.user.response.LoginAccessResponse;
import pe.reciclaya.app.data.model.user.response.reportes.ReporteActividadResponse;

public class ReporteActividadRVA
        extends RecyclerView.Adapter<ReporteActividadRVA.ViewHolder> {

    private List<ReporteActividadResponse> reportes;

    public ReporteActividadRVA(List<ReporteActividadResponse> reportes) {
        this.reportes = reportes == null
                ? new ArrayList<>()
                : reportes;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setReportes(List<ReporteActividadResponse> reportes) {
        this.reportes = reportes == null
                ? new ArrayList<>()
                : reportes;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reporte_actividad, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position
    ) {
        holder.imprimir(reportes.get(position));
    }

    @Override
    public int getItemCount() {
        return reportes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView TVId;
        private final TextView TVEstado;
        private final TextView TVAcceso;
        private final TextView TVActividad;
        private final TextView TVFecha;
        private final TextView TVDescripcion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            TVId = itemView.findViewById(R.id.TVIdReporteIRA);
            TVEstado = itemView.findViewById(R.id.TVEstadoReporteIRA);
            TVAcceso = itemView.findViewById(R.id.TVAccesoReporteIRA);
            TVActividad = itemView.findViewById(R.id.TVActividadReporteIRA);
            TVFecha = itemView.findViewById(R.id.TVFechaReporteIRA);
            TVDescripcion = itemView.findViewById(R.id.TVDescripcionReporteIRA);
        }

        public void imprimir(ReporteActividadResponse reporte) {

            TVId.setText("REPORTE #" + reporte.getId());

            String estado = reporte.getEstado() == null
                    ? "Desconocido"
                    : reporte.getEstado();

            TVEstado.setText(estado.toUpperCase());

            TVAcceso.setText(
                    "Acceso reportado #" + reporte.getLogAccesosId()
            );

            TVFecha.setText(
                    "Fecha: " + formatearFecha(reporte.getFechaReporte())
            );

            String descripcion = reporte.getDescripcion();

            TVDescripcion.setText(
                    descripcion == null || descripcion.isBlank()
                            ? "Sin descripción adicional."
                            : descripcion
            );

            LoginAccessResponse acceso = reporte.getLogAcceso();

            if (acceso != null) {
                String actividad = acceso.getTipoActividad();

                TVActividad.setText(
                        actividad == null || actividad.isBlank()
                                ? "Actividad no disponible"
                                : actividad
                );
            } else {
                TVActividad.setText("Actividad no disponible");
            }
        }

        private String formatearFecha(String fecha) {

            if (fecha == null || fecha.isBlank()) {
                return "Fecha no disponible";
            }

            try {
                return OffsetDateTime.parse(fecha)
                        .atZoneSameInstant(
                                java.time.ZoneId.of("America/Lima")
                        )
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
    }
}