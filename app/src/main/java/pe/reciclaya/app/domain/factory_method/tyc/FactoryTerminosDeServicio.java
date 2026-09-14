package pe.reciclaya.app.domain.factory_method.tyc;

public class FactoryTerminosDeServicio implements FactoryTyC{
    @Override
    public TyC crear() {
        return new TerminosDeServicio();
    }
}
