package pe.reciclaya.app.data.repository;

import pe.reciclaya.app.ui.view.tyc.factory_method.FactoryTyC;
import pe.reciclaya.app.ui.view.tyc.factory_method.TyC;

public class TyCRepository {
    private final TyC tyc;

    public TyCRepository(FactoryTyC factoryTyC) {
        tyc = factoryTyC.crear();
    }

    public int getTituloID() { return tyc.getTituloID(); }
    public int getMensajeID() { return tyc.getMensajeID(); }
}
