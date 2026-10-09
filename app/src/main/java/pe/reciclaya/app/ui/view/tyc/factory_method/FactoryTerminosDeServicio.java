package pe.reciclaya.app.ui.view.tyc.factory_method;

public class FactoryTerminosDeServicio implements FactoryTyC{
    @Override
    public TyC crear() {
        return new TerminosDeServicio();
    }
}
