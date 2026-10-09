package pe.reciclaya.app.ui.main.usuario.solicitud_list;

import pe.reciclaya.app.ui.main.usuario.tipo_residuo_list.TipoResiduoItem;

public abstract class SolicitudItem {
    private final TipoResiduoItem tipoResiduo;
    private final String nombreUsuario;
    private final String urlFotoPerfil;
    private final String tamano;
    private final String direccion;
    private final double latitude;
    private final double longitude;
    private final float puntuacion;
    private final int userID;
    private final int id;

    protected String dia;
    protected String hora;
    protected String horaString;

    public SolicitudItem(int id,
                     TipoResiduoItem tipoResiduo,
                     String tamano,
                     String dia,
                     String hora,
                     String direccion,
                     double latitude,
                     double longitude,

                     int userID,
                     String nombreCompleto,
                     String urlFotoPerfil,
                     float puntuacion
    ) {
        this.id = id;
        this.tipoResiduo = tipoResiduo;
        this.tamano = tamano;
        this.dia = dia;
        this.hora = hora;
        this.direccion = direccion;
        this.latitude = latitude;
        this.longitude = longitude;

        this.userID = userID;
        this.nombreUsuario = nombreCompleto;
        this.urlFotoPerfil = urlFotoPerfil;
        this.puntuacion = puntuacion;

        setHoraString();
    }

    public abstract String getAntiguedad();
    public abstract boolean isBtnCancelarEnabled();
    public abstract boolean isBtnEditarEnabled();
    public abstract int getColorFondo();
    public abstract int getColor();

    public TipoResiduoItem getTipoResiduo() { return tipoResiduo; }
    public String getNombreUsuario() { return nombreUsuario; }
    public String getNombreTipoResiduo() { return tipoResiduo.getNombre(); }
    public String getUrlFotoPerfil() { return urlFotoPerfil; }
    public String getTamano() { return tamano; }
    public String getDireccion() { return direccion; }
    public String getHoraString() { return horaString; }
    public String getHora() { return hora; }
    public String getDia() { return dia; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public float getPuntuacion() { return puntuacion; }
    public int getIconoTipoResiduo() { return tipoResiduo.getIcono(); }
    public int getSolicitudID() { return id; }
    public int getUserID() { return userID; }

    private void setHoraString() {
        String[] splitHora = hora.split(":");
        int h = Integer.parseInt(splitHora[0]);

        // xx:yy am/pm
        horaString = ((h > 12) ? (h - 12) : h) + ":" + splitHora[1] + ((h >= 12) ? " pm" : " am");
    }
}