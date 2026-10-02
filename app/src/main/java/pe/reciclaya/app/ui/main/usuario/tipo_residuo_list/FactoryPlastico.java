package pe.reciclaya.app.ui.main.usuario.tipo_residuo_list;

public class FactoryPlastico implements FactoryTipoResiduo {
    @Override
    public TipoResiduo crear() {
        return new Plastico();
    }
}
