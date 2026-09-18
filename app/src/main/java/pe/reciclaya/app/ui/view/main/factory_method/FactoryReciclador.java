package pe.reciclaya.app.ui.view.main.factory_method;

public class FactoryReciclador implements FactoryMain {
    @Override
    public Class<?> crear() { return RecicladorMainActivity.class; }
}
