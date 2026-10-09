package pe.reciclaya.app.domain.model.solicitud;

public enum Estado {
    DISPONIBLE("Disponible"),
    FINALIZADA("Finalizada"),
    CANCELADA("Cancelada"),
    PENDIENTE("Pendiente");

    private final String estado;

    Estado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public static Estado fromString(String status) {
        for (Estado estado : values()) {
            if (estado.getEstado().equalsIgnoreCase(status)) {
                return estado;
            }
        }
        return null;
    }
}
