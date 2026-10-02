package pe.reciclaya.app.ui.main.usuario.solicitud_list;

import pe.reciclaya.app.ui.main.usuario.tipo_residuo_list.TipoResiduo;

public abstract class Solicitud {
    private final TipoResiduo tipoResiduo;
    private final String urlFotoPerfil;
    private final String tamano;
    private final String direccion;
    private final double latitude;
    private final double longitude;
    private final float puntuacion;
    private final int solicitudID;
    private final int userID;

    protected String nombreUsuario;
    protected String dia;
    protected String hora;

    private String horaString;

    public Solicitud(int id,
                     TipoResiduo tipoResiduo,
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
        this.solicitudID = id;
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

    public abstract String getNombreUsuario();
    public abstract String getAntiguedad();
    public abstract boolean isBtnCancelarEnabled();
    public abstract boolean isBtnEditarEnabled();
    public abstract int getColorFondo();

    public String getNombreTipoResiduo() { return tipoResiduo.getNombre(); }
    public String getUrlFotoPerfil() { return urlFotoPerfil; }
    public String getTamano() { return tamano; }
    public String getDireccion() { return direccion; }
    public String getHoraString() { return horaString; }
    public String getHora() { return hora; }
    public String getDia() { return dia; }
    public float getPuntuacion() { return puntuacion; }
    public int getIconoTipoResiduo() { return tipoResiduo.getIcono(); }
    public int getSolicitudID() { return solicitudID; }
    public int getUserID() { return userID; }

    private void setHoraString() {
        String[] splitHora = hora.split(":");
        int h = Integer.parseInt(splitHora[0]);

        // xx:yy am/pm
        horaString = ((h > 12) ? (h - 12) : h) + ":" + splitHora[1] + ((h >= 12) ? " pm" : " am");
    }
}
