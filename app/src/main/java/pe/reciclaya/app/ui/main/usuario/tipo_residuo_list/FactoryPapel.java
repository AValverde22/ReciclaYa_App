package pe.reciclaya.app.ui.main.usuario.tipo_residuo_list;

public class FactoryPapel implements FactoryTipoResiduo {

    @Override
    public TipoResiduo crear() {
        return new Papel();
    }
}
