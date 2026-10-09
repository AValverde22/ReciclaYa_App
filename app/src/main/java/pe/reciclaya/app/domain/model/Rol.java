package pe.reciclaya.app.domain.model;

public class Rol {
    private final String rol;
    private final String titulo;
    private final String descripcion;
    private final int IVSeleccionado;
    private final int IVNoSeleccionado;

    public Rol(String rol, String titulo, String descripcion, int IVSeleccionado, int IVNoSeleccionado) {
        this.rol = rol;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.IVSeleccionado = IVSeleccionado;
        this.IVNoSeleccionado = IVNoSeleccionado;
    }

    public String getRol() { return rol; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public int getIVSeleccionado() { return IVSeleccionado; }
    public int getIVNoSeleccionado() { return IVNoSeleccionado; }
}
