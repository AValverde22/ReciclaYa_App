package pe.reciclaya.app.ui.main.tipo_residuo_list;

public class FactoryPlastico implements FactoryTipoResiduo {
    @Override
    public TipoResiduoItem crear() {
        return new Plastico();
    }
}
