package pe.reciclaya.app.domain.model.solicitud;

public class SolicitudFiltros {
    private final TipoResiduo tipoResiduo;
    private final String fecha;
    private final String hora;
    private final String score;

    public SolicitudFiltros(TipoResiduo tipoResiduo, String fecha, String hora, String score) {
        this.tipoResiduo = tipoResiduo;
        this.fecha = fecha;
        this.hora = hora;
        this.score = score;
    }

    public TipoResiduo getTipoResiduo() { return tipoResiduo; }
    public String getFecha() { return fecha; }
    public String getHora() { return hora; }
    public String getScore() { return score; }

    public String getTipoResiduoString() {
        return tipoResiduo == null ? null : tipoResiduo.getTipoResiduo();
    }
}
