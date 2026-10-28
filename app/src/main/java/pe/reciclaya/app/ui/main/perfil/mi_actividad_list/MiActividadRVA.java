package pe.reciclaya.app.ui.main.perfil.mi_actividad_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import pe.reciclaya.app.R;
import java.util.function.Consumer;

public class MiActividadRVA extends RecyclerView.Adapter<MiActividadRVA.ViewHolder> {
    private final MiActividad[] actividades;
    private final Consumer<MiActividad> onClick;

    public MiActividadRVA(MiActividad[] actividades, Consumer<MiActividad> onClick) {
        this.actividades = actividades;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mi_actividad, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imprimir(actividades[position]);
        MiActividad actividad = actividades[position];
        holder.itemView.setOnClickListener(actividad instanceof HistorialSolicitudes
                ? view -> onClick.accept(actividad) : null);
    }

    @Override
    public int getItemCount() { return actividades.length; }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView IVLogo;
        private final TextView TVActividad, TVMensajeActividad;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            IVLogo = itemView.findViewById(R.id.IVLogoActividadIMA);
            TVActividad = itemView.findViewById(R.id.TVActividadIMA);
            TVMensajeActividad = itemView.findViewById(R.id.TVMensajeActividadIMA);
        }

        public void imprimir(MiActividad actividad) {
            IVLogo.setBackgroundResource(actividad.getIconoActividad());
            TVActividad.setText(actividad.getActividad());
            TVMensajeActividad.setText(actividad.getMensajeActividad());
        }
    }
}
