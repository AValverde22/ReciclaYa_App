package pe.reciclaya.app.ui.view.main.factory_method.main;

public class FactoryRecicladorActivity implements FactoryMainActivity {
    @Override
    public Class<?> crear() { return RecicladorMainActivity.class; }
}
