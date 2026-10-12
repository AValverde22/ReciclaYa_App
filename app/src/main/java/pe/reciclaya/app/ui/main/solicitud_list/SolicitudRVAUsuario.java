package pe.reciclaya.app.ui.main.solicitud_list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import pe.reciclaya.app.R;

public class SolicitudRVAUsuario extends RecyclerView.Adapter<SolicitudRVAUsuario.ViewHolder> {
    private List<SolicitudItem> solicitudes;
    private OnSolicitudActionListener listener;

    public SolicitudRVAUsuario(List<SolicitudItem> solicitudes) {
        this.solicitudes = solicitudes;
    }

    public SolicitudRVAUsuario(List<SolicitudItem> solicitudes, OnSolicitudActionListener listener) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_solicitud_usuario, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imprimir(solicitudes.get(position), listener);
    }

    @Override
    public int getItemCount() { return solicitudes.size(); }

    public interface OnSolicitudActionListener {
        void onEditRequested(SolicitudItem solicitudItem);
        void onCancelRequested(int id);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final Context context;

        private final MaterialCardView MCVFondo;
        private final ImageView IVTipoResiduo, IVFotoPerfil, IVPin;
        private final TextView TVTipoResiduo, TVTamano, TVNombreUsuario, TVPuntuacion, TVAntiguedad, TVDireccion;
        private final Button BtnCancelar, BtnEditar;
        private final CardView CVDistancia;
        private final LinearLayout LLPuntuacion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();
            MCVFondo = itemView.findViewById(R.id.MCVFondoISU);

            IVTipoResiduo = itemView.findViewById(R.id.IVTipoResiduoISU);
            IVFotoPerfil = itemView.findViewById(R.id.IVFotoPerfilISU);
            IVPin = itemView.findViewById(R.id.IVPinISU);

            TVTipoResiduo = itemView.findViewById(R.id.TVTipoResiduoISU);
            TVTamano = itemView.findViewById(R.id.TVTamanoISU);
            TVNombreUsuario = itemView.findViewById(R.id.TVNombreUsuarioISU);
            TVPuntuacion = itemView.findViewById(R.id.TVPuntuacionISU);
            TVAntiguedad = itemView.findViewById(R.id.TVAntiguedadISU);
            TVDireccion = itemView.findViewById(R.id.TVDireccionISU);

            BtnCancelar = itemView.findViewById(R.id.BtnCancelarISU);
            BtnEditar = itemView.findViewById(R.id.BtnEditarISU);

            CVDistancia = itemView.findViewById(R.id.CVDistanciaISU);
            LLPuntuacion = itemView.findViewById(R.id.LLPuntuacionISU);
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

            BtnCancelar.setVisibility(solicitud.isBtnCancelarEnabled() ? View.VISIBLE : View.GONE);
            BtnEditar.setVisibility(solicitud.isBtnEditarEnabled() ? View.VISIBLE : View.GONE);

            CVDistancia.setVisibility(solicitud.isBtnCancelarEnabled() ? View.GONE : View.VISIBLE);
            LLPuntuacion.setVisibility(solicitud.isBtnEditarEnabled() ? View.GONE : View.VISIBLE);

            TVPuntuacion.setText(String.valueOf(solicitud.getPuntuacion()));

            BtnEditar.setOnClickListener(view ->
                    listener.onEditRequested(solicitud)
            );

            BtnCancelar.setOnClickListener(view ->
                    listener.onCancelRequested(solicitud.getSolicitudID())
            );
        }
    }
}
