package pe.reciclaya.app.domain.model.solicitud;

import pe.reciclaya.app.domain.model.user.UserSolicitud;

public class Solicitud {
    private final int id;
    private final TipoResiduo tipoResiduo;
    private final Tamano tamano;
    private final Estado estado;
    private final String fecha;
    private final String hora;
    private final String direccion;
    private final double latitude;
    private final double longitude;
    private final UserSolicitud user;

    public Solicitud(
            int id,
            TipoResiduo tipoResiduo,
            Tamano tamano,
            Estado estado,
            String fecha,
            String hora,
            String direccion,
            double latitude,
            double longitude,
            UserSolicitud user
    ) {
        this.id = id;
        this.tipoResiduo = tipoResiduo;
        this.tamano = tamano;
        this.estado = estado;
        this.fecha = fecha;
        this.hora = hora;
        this.direccion = direccion;
        this.latitude = latitude;
        this.longitude = longitude;
        this.user = user;
    }

    public int getID() { return id; }
    public TipoResiduo getTipoResiduo() { return tipoResiduo; }
    public Tamano getTamano() { return tamano; }
    public Estado getEstado() { return estado; }
    public String getFecha() { return fecha; }
    public String getHora() { return hora; }
    public String getDireccion() { return direccion; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }

    public UserSolicitud getUser() { return user;}
    public int getUserID() { return user.getID(); }
    public String getUserName() { return user.getFullName(); }
    public String getUserProfilePhotoURL() { return user.getProfilePhotoURL(); }
    public float getUserScore() { return user.getScore(); }

    public String getTipoResiduoString() { return tipoResiduo.getTipoResiduo(); }
    public String getTamanoString() { return tamano.getTamano(); }
    public String getEstadoString() { return estado.getEstado(); }

}