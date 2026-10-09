package pe.reciclaya.app.domain.model.solicitud;

public final class SolicitudNueva {
    private final TipoResiduo tipoResiduo;
    private final Tamano tamano;
    private final String fecha;
    private final String hora;
    private final String direccion;
    private final double latitude;
    private final double longitude;
    private final int userId;

    public SolicitudNueva(
            TipoResiduo tipoResiduo,
            Tamano tamano,
            String fecha,
            String hora,
            String direccion,
            double latitude,
            double longitude,
            int userId
    ) {
        this.tipoResiduo = tipoResiduo;
        this.tamano = tamano;
        this.fecha = fecha;
        this.hora = hora;
        this.direccion = direccion;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userId = userId;
    }

    public TipoResiduo getTipoResiduo() { return tipoResiduo; }
    public Tamano getTamano() { return tamano; }
    public String getFecha() { return fecha; }
    public String getHora() { return hora; }
    public String getDireccion() { return direccion; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public int getUserId() { return userId; }

    public String getTipoResiduoString() { return tipoResiduo.getTipoResiduo(); }
    public String getTamanoString() { return tamano.getTamano(); }
}
