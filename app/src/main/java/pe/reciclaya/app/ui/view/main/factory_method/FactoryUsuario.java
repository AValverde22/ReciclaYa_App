package pe.reciclaya.app.ui.view.main.factory_method;

public class FactoryUsuario implements FactoryMain {
    @Override
    public Class<?> crear() {
        return UsuarioMainActivity.class;
    }
}
