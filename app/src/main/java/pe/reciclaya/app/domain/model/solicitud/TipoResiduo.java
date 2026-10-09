package pe.reciclaya.app.domain.model.solicitud;

public enum TipoResiduo {
    PLASTICO("Plástico"),
    VIDRIO("Vidrio"),
    PAPEL("Papel");

    private final String tipoResiduo;

    TipoResiduo(String tipoResiduo) {
        this.tipoResiduo = tipoResiduo;
    }

    public String getTipoResiduo() {
        return tipoResiduo;
    }

    public static TipoResiduo fromString(String tipo) {
        for (TipoResiduo tipoResiduo : values()) {
            if (tipoResiduo.getTipoResiduo().equalsIgnoreCase(tipo)) {
                return tipoResiduo;
            }
        }
        return null;
    }
}
