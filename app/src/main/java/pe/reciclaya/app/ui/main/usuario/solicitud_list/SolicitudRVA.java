package pe.reciclaya.app.ui.main.usuario.solicitud_list;

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

public class SolicitudRVA extends RecyclerView.Adapter<SolicitudRVA.ViewHolder> {
    private List<SolicitudItem> solicitudes;
    private final OnSolicitudActionListener listener;

    public SolicitudRVA(List<SolicitudItem> solicitudes, OnSolicitudActionListener listener) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_solicitud, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imprimir(solicitudes.get(position), listener);
    }

    @Override
    public int getItemCount() { return solicitudes.size(); }

    public void actualizarPos(SolicitudItem solicitudItem, int pos) {
        solicitudes.set(pos, solicitudItem);
        notifyItemChanged(pos);
    }

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
            MCVFondo = itemView.findViewById(R.id.MCVFondoIS);

            IVTipoResiduo = itemView.findViewById(R.id.IVTipoResiduoIS);
            IVFotoPerfil = itemView.findViewById(R.id.IVFotoPerfilIS);
            IVPin = itemView.findViewById(R.id.IVPinIS);

            TVTipoResiduo = itemView.findViewById(R.id.TVTipoResiduoIS);
            TVTamano = itemView.findViewById(R.id.TVTamanoIS);
            TVNombreUsuario = itemView.findViewById(R.id.TVNombreUsuarioIS);
            TVPuntuacion = itemView.findViewById(R.id.TVPuntuacionIS);
            TVAntiguedad = itemView.findViewById(R.id.TVAntiguedadIS);
            TVDireccion = itemView.findViewById(R.id.TVDireccionIS);

            BtnCancelar = itemView.findViewById(R.id.BtnCancelarIS);
            BtnEditar = itemView.findViewById(R.id.BtnEditarIS);

            CVDistancia = itemView.findViewById(R.id.CVDistanciaIS);
            LLPuntuacion = itemView.findViewById(R.id.LLPuntuacionIS);
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
