package pe.reciclaya.app.data.repository;

import pe.reciclaya.app.data.factory_method.tyc.FactoryTyC;
import pe.reciclaya.app.data.factory_method.tyc.TyC;

public class TyCRepository {
    private final TyC tyc;

    public TyCRepository(FactoryTyC factoryTyC) {
        tyc = factoryTyC.crear();
    }

    public int getTituloID() { return tyc.getTituloID(); }
    public int getMensajeID() { return tyc.getMensajeID(); }
}
