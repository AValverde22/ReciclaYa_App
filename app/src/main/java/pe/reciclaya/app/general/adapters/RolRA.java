package pe.reciclaya.app.general.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import pe.reciclaya.app.R;
import pe.reciclaya.app.general.items.Rol;

public class RolRA extends RecyclerView.Adapter<RolRA.RolRAHolder> {
    private final Rol[] roles;
    private int posSeleccionada;

    public RolRA(Rol[] roles){
        this.roles = roles;
        posSeleccionada = -1;
    }

    @NonNull
    @Override
    public RolRAHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rol, parent, false);
        return new RolRAHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RolRAHolder holder, int position) {
        if(posSeleccionada == position) holder.imprimirVerde(roles[position]);
        else holder.imprimirNormal(roles[position]);

        holder.itemView.setOnClickListener(view -> {
            int posAnterior = posSeleccionada;

            posSeleccionada = holder.getAbsoluteAdapterPosition();
            if(posAnterior == posSeleccionada) posSeleccionada  = -1;
            notifyItemChanged(posSeleccionada);

            if(posAnterior != -1) notifyItemChanged(posAnterior);
        });
    }

    @Override
    public int getItemCount() { return roles.length; }

    public String getRol() {
        if(posSeleccionada == -1) return null;
        return roles[posSeleccionada].getRol();
    }

    public static class RolRAHolder extends RecyclerView.ViewHolder {
        private final Context context;
        private final LinearLayout LLFondo;
        private final TextView TVRol, TVDescripcion;
        private final ImageView IVRol, IVSeleccion;

        public RolRAHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();

            LLFondo = itemView.findViewById(R.id.LLFondoIR);
            TVRol = itemView.findViewById(R.id.TVRolRS);
            TVDescripcion = itemView.findViewById(R.id.TVDescripcionIR);
            IVRol = itemView.findViewById(R.id.IVRolIR);
            IVSeleccion = itemView.findViewById(R.id.IVSeleccionIR);
        }

        public void imprimirNormal(Rol rol) {
            LLFondo.setBackgroundResource(R.drawable.linearlayout_transparente);

            TVRol.setText(rol.getTitulo());
            TVRol.setTextColor(context.getResources().getColor(R.color.black, null));


            TVDescripcion.setText(rol.getDescripcion());

            IVRol.setBackgroundResource(rol.getIVNoSeleccionado());
            IVSeleccion.setBackgroundResource(R.drawable.no_seleccionado);
        }

        public void imprimirVerde(Rol rol) {
            LLFondo.setBackgroundResource(R.drawable.linearlayout_verde_seleccion);

            TVRol.setText(rol.getTitulo());
            TVRol.setTextColor(context.getResources().getColor(R.color.verde, null));

            TVDescripcion.setText(rol.getDescripcion());

            IVRol.setBackgroundResource(rol.getIVSeleccionado());
            IVSeleccion.setBackgroundResource(R.drawable.seleccionado);
        }
    }
}
