package pe.reciclaya.app.ui.view.main.factory_method.main;

public class FactoryUsuarioActivity implements FactoryMainActivity {
    @Override
    public Class<?> crear() {
        return UsuarioMainActivity.class;
    }
}
