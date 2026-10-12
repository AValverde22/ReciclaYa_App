package pe.reciclaya.app.ui.main.solicitud_list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import pe.reciclaya.app.R;

public class SolicitudRVAReciclador extends RecyclerView.Adapter<SolicitudRVAReciclador.ViewHolder> {
    private List<SolicitudItem> solicitudes;
    private OnSolicitudActionListener listener;

    public SolicitudRVAReciclador(List<SolicitudItem> solicitudes) {
        this.solicitudes = solicitudes;
    }

    public SolicitudRVAReciclador(List<SolicitudItem> solicitudes, OnSolicitudActionListener listener) {
        this.solicitudes = solicitudes;
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSolicitudes(List<SolicitudItem> solicitudes) {
        this.solicitudes = solicitudes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_solicitud_reciclador, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imprimir(solicitudes.get(position), listener);
    }

    @Override
    public int getItemCount() { return solicitudes.size(); }

    public interface OnSolicitudActionListener {
        void onAcceptRequested(int id);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final Context context;

        private final MaterialCardView MCVFondo;
        private final ImageView IVTipoResiduo, IVFotoPerfil, IVPin;
        private final TextView TVTipoResiduo, TVTamano, TVNombreUsuario, TVPuntuacion, TVAntiguedad, TVDireccion;
        private final Button BtnAceptar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();
            MCVFondo = itemView.findViewById(R.id.MCVFondoISR);

            IVTipoResiduo = itemView.findViewById(R.id.IVTipoResiduoISR);
            IVFotoPerfil = itemView.findViewById(R.id.IVFotoPerfilISR);
            IVPin = itemView.findViewById(R.id.IVPinISR);

            TVTipoResiduo = itemView.findViewById(R.id.TVTipoResiduoISR);
            TVTamano = itemView.findViewById(R.id.TVTamanoISR);
            TVNombreUsuario = itemView.findViewById(R.id.TVNombreUsuarioISR);
            TVPuntuacion = itemView.findViewById(R.id.TVPuntuacionISR);
            TVAntiguedad = itemView.findViewById(R.id.TVAntiguedadISR);
            TVDireccion = itemView.findViewById(R.id.TVDireccionISR);

            BtnAceptar = itemView.findViewById(R.id.BtnAceptarISR);
        }

        public void imprimir(SolicitudItem solicitud, OnSolicitudActionListener listener) {
            MCVFondo.setCardBackgroundColor(context.getColor(solicitud.getColorFondo()));

            IVTipoResiduo.setBackgroundResource(solicitud.getIconoTipoResiduo());
            IVTipoResiduo.setBackgroundTintList(context.getColorStateList(solicitud.getColor()));
            IVPin.setBackgroundTintList(context.getColorStateList(solicitud.getColor()));

            String urlFotoPerfil = solicitud.getUrlFotoPerfil();
            if(urlFotoPerfil != null && !urlFotoPerfil.isBlank()) Glide.with(context).load(Uri.parse(urlFotoPerfil)).into(IVFotoPerfil);

            TVTipoResiduo.setText(solicitud.getNombreTipoResiduo());
            TVTipoResiduo.setTextColor(context.getColor(solicitud.getColor()));

            TVTamano.setText(solicitud.getTamano());
            TVNombreUsuario.setText(solicitud.getNombreUsuario());
            TVAntiguedad.setText(solicitud.getAntiguedad());

            TVDireccion.setText(solicitud.getDireccion());
            TVDireccion.setTextColor(context.getColor(solicitud.getColor()));

            BtnAceptar.setVisibility(solicitud.isBtnCancelarEnabled() ? View.VISIBLE : View.GONE);
            BtnAceptar.setOnClickListener(view ->
                listener.onAcceptRequested(solicitud.getSolicitudID())
            );

            TVPuntuacion.setText(String.valueOf(solicitud.getPuntuacion()));
        }
    }
}
