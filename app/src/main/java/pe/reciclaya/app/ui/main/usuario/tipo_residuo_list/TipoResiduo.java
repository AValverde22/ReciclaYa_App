package pe.reciclaya.app.ui.main.usuario.tipo_residuo_list;

public class TipoResiduo {
    private final String tipo;
    private final int icono;

    public TipoResiduo(String tipo, int icono) {
        this.tipo = tipo;
        this.icono = icono;
    }

    public String getTipo() { return tipo; }
    public int getIcono() { return icono; }
}
