package pe.reciclaya.app.ui.main.usuario.tipo_residuo_list;

import pe.reciclaya.app.domain.model.solicitud.TipoResiduo;

public class TipoResiduoMapper {
    private TipoResiduoMapper() {}

    public static FactoryTipoResiduo mapTipoResiduo(TipoResiduo tipoResiduo) {
        switch (tipoResiduo) {
            case PLASTICO: return new FactoryPlastico();
            case VIDRIO: return new FactoryVidrio();
            default: return new FactoryPapel();
        }
    }
}
