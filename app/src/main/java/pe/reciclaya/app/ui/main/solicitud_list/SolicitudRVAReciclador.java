package pe.reciclaya.app.ui.main.solicitud_list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.SystemClock;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.List;
import java.util.Locale;

import pe.reciclaya.app.R;

public class SolicitudRVAReciclador extends RecyclerView.Adapter<SolicitudRVAReciclador.ViewHolder> {
    private boolean blocked = true;
    @SuppressLint("NotifyDataSetChanged")
    public void setBlocked(boolean value) { if (blocked != value) { blocked = value; notifyDataSetChanged(); } }
    private List<SolicitudItem> solicitudes;
    private OnSolicitudActionListener listener;
    private final boolean showDistance;
    private Location currentLocation;

    @SuppressLint("NotifyDataSetChanged")
    public void setCurrentLocation(Location location) {
        Location recent = location != null && SystemClock.elapsedRealtimeNanos() - location.getElapsedRealtimeNanos() < 120000000000L ? location : null;
        if (currentLocation == null && recent == null) return;
        if (currentLocation != null && recent != null && currentLocation.getElapsedRealtimeNanos() == recent.getElapsedRealtimeNanos()) return;
        currentLocation = recent == null ? null : new Location(recent);
        if (showDistance) notifyDataSetChanged();
    }

    public SolicitudRVAReciclador(List<SolicitudItem> solicitudes) {
        this.solicitudes = solicitudes;
        this.showDistance = false;
    }

    public SolicitudRVAReciclador(List<SolicitudItem> solicitudes, OnSolicitudActionListener listener) {
        this.solicitudes = solicitudes;
        this.listener = listener;
        this.showDistance = true;
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
        holder.imprimir(solicitudes.get(position), listener, blocked, showDistance, currentLocation);
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
        private final CardView CVDistancia;
        private final TextView TVDistancia, TVEstado;

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
            CVDistancia = itemView.findViewById(R.id.CVDistanciaISR);
            TVDistancia = itemView.findViewById(R.id.TVDistanciaISR);
            TVEstado = itemView.findViewById(R.id.TVEstadoISR);
        }

        public void imprimir(SolicitudItem solicitud, OnSolicitudActionListener listener, boolean blocked, boolean showDistance, Location currentLocation) {
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

            CVDistancia.setVisibility(showDistance ? View.VISIBLE : View.GONE);
            TVEstado.setVisibility("Disponible".equals(solicitud.getEstado()) ? View.GONE : View.VISIBLE);
            TVEstado.setText(solicitud.getEstado());
            TVEstado.setTextColor(context.getColor(solicitud.getColor()));
            if (showDistance) {
                boolean recent = currentLocation != null && SystemClock.elapsedRealtimeNanos() - currentLocation.getElapsedRealtimeNanos() < 120000000000L;
                if (recent) {
                    float[] meters = new float[1];
                    Location.distanceBetween(currentLocation.getLatitude(), currentLocation.getLongitude(), solicitud.getLatitude(), solicitud.getLongitude(), meters);
                    String distance = String.format(Locale.getDefault(), "%.1f km", meters[0] / 1000f);
                    TVDistancia.setText(distance);
                    TVDistancia.setContentDescription(context.getString(R.string.distancia_solicitud_descripcion, distance));
                } else {
                    TVDistancia.setText(R.string.distancia_sin_ubicacion);
                    TVDistancia.setContentDescription(context.getString(R.string.distancia_ubicacion_pendiente));
                }
            }

            BtnAceptar.setVisibility(showDistance && solicitud.isBtnCancelarEnabled() ? View.VISIBLE : View.GONE);
            BtnAceptar.setEnabled(!blocked);
            BtnAceptar.setText(blocked ? "Finaliza tu reciclaje activo" : "Aceptar");
            BtnAceptar.setOnClickListener(view ->
                listener.onAcceptRequested(solicitud.getSolicitudID())
            );

            TVPuntuacion.setText(String.valueOf(solicitud.getPuntuacion()));
        }
    }
}
