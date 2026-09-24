package pe.reciclaya.app.ui.main.perfil.mi_actividad_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import pe.reciclaya.app.R;

public class MiActividadRecyclerViewAdapter extends RecyclerView.Adapter<MiActividadRecyclerViewAdapter.ViewHolder> {
    private final MiActividad[] actividades;

    public MiActividadRecyclerViewAdapter(MiActividad[] actividades) {
        this.actividades = actividades;
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
            IVLogo.setBackgroundResource(actividad.getLogoActividad());
            TVActividad.setText(actividad.getActividad());
            TVMensajeActividad.setText(actividad.getMensajeActividad());
        }
    }
}
