package pe.reciclaya.app.domain.model.solicitud;

public class SolicitudActualizada {
    private final int id;
    private final TipoResiduo tipoResiduo;
    private final Tamano tamano;
    private final String fecha;
    private final String hora;
    private final String direccion;
    private final double latitude;
    private final double longitude;

    public SolicitudActualizada(
            int id,
            TipoResiduo tipoResiduo,
            Tamano tamano,
            String fecha,
            String hora,
            String direccion,
            double latitude,
            double longitude
    ) {
        this.id = id;
        this.tipoResiduo = tipoResiduo;
        this.tamano = tamano;
        this.fecha = fecha;
        this.hora = hora;
        this.direccion = direccion;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getID() { return id; }
    public TipoResiduo getTipoResiduo() { return tipoResiduo; }
    public Tamano getTamano() { return tamano; }
    public String getFecha() { return fecha; }
    public String getHora() { return hora; }
    public String getDireccion() { return direccion; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }

    public String getTipoResiduoString() { return tipoResiduo.getTipoResiduo(); }
    public String getTamanoString() { return tamano.getTamano(); }
}

