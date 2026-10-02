package pe.reciclaya.app.ui.main.usuario.tipo_residuo_list;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import pe.reciclaya.app.R;

public class TipoResiduoRVA extends RecyclerView.Adapter<TipoResiduoRVA.ViewHolder> {
    private final onTipoResiduoSelectedListener listener;
    private final TipoResiduo[] tipoResiduos;
    private int posSeleccionada;

    public TipoResiduoRVA(TipoResiduo[] tipoResiduos, onTipoResiduoSelectedListener listener) {
        this.tipoResiduos = tipoResiduos;
        this.listener = listener;

        posSeleccionada = -1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tipo_residuo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(posSeleccionada == position) holder.imprimirVerde(tipoResiduos[position]);
        else holder.imprimirNormal(tipoResiduos[position]);

        holder.itemView.setOnClickListener(view -> {
            int posAnterior = posSeleccionada;

            posSeleccionada = holder.getAbsoluteAdapterPosition();
            if(posAnterior == posSeleccionada) posSeleccionada = -1;
            notifyItemChanged(posSeleccionada);

            if(posAnterior != -1) notifyItemChanged(posAnterior);

            if(posSeleccionada == -1) listener.onTipoResiduoSelected(null);
            else listener.onTipoResiduoSelected(tipoResiduos[posSeleccionada].getNombre());
        });
    }

    @Override
    public int getItemCount() { return tipoResiduos.length; }

    public void eliminarSeleccion() {
        int posAnterior = posSeleccionada;
        posSeleccionada = -1;

        if (posAnterior != -1) notifyItemChanged(posAnterior);
        listener.onTipoResiduoSelected(null);
    }

    public interface onTipoResiduoSelectedListener {
        void onTipoResiduoSelected(String tipoResiduo);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        private final LinearLayout LLFondo;
        private final CardView CVFondoIcono;
        private final ImageView IVIcono;
        private final TextView TVTipo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();

            LLFondo = itemView.findViewById(R.id.LLFondoITR);
            CVFondoIcono = itemView.findViewById(R.id.CVFondoIconoITR);
            IVIcono = itemView.findViewById(R.id.IVIconoITR);
            TVTipo = itemView.findViewById(R.id.TVTipoITR);
        }

        public void imprimirNormal(TipoResiduo tipoResiduo) {
            LLFondo.setBackgroundResource(R.drawable.linearlayout_transparente);
            CVFondoIcono.setCardBackgroundColor(context.getResources().getColor(R.color.gris_claro, null));

            IVIcono.setBackgroundResource(tipoResiduo.getIcono());
            IVIcono.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.black, null)));

            TVTipo.setText(tipoResiduo.getNombre());
            TVTipo.setTextColor(context.getResources().getColor(R.color.black, null));
        }

        public void imprimirVerde(TipoResiduo tipoResiduo) {
            LLFondo.setBackgroundResource(R.drawable.linearlayout_verde_seleccion);
            CVFondoIcono.setCardBackgroundColor(context.getResources().getColor(R.color.verde, null));

            IVIcono.setBackgroundResource(tipoResiduo.getIcono());
            IVIcono.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white, null)));

            TVTipo.setText(tipoResiduo.getNombre());
            TVTipo.setTextColor(context.getResources().getColor(R.color.verde, null));
        }
    }
}
