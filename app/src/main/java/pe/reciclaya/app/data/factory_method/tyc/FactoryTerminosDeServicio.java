package pe.reciclaya.app.data.factory_method.tyc;

public class FactoryTerminosDeServicio implements FactoryTyC{
    @Override
    public TyC crear() {
        return new TerminosDeServicio();
    }
}
