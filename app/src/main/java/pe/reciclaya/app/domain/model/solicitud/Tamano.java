package pe.reciclaya.app.domain.model.solicitud;

public enum Tamano {
    EXTRAPEQUENA("Extrapequeña"),
    PEQUENA("Pequeña"),
    MEDIANA("Mediana"),
    GRANDE("Grande"),
    EXTRAGRANDE("Extragrande");

    private final String tamano;

    Tamano(String tamano) {
        this.tamano = tamano;
    }

    public String getTamano() {
        return tamano;
    }

    public static Tamano fromString(String size) {
        for (Tamano tamano : values()) {
            if (tamano.getTamano().equalsIgnoreCase(size)) {
                return tamano;
            }
        }
        return null;
    }
}
