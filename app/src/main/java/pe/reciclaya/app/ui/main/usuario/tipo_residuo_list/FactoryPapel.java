package pe.reciclaya.app.ui.main.usuario.tipo_residuo_list;

public class FactoryPapel implements FactoryTipoResiduo {

    @Override
    public TipoResiduoItem crear() {
        return new Papel();
    }
}
