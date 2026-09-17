package pe.reciclaya.app.ui.view.tyc.factory_method;

public class FactoryPoliticaDePrivacidad implements FactoryTyC{
    @Override
    public TyC crear() {
        return new PoliticaDePrivacidad();
    }
}
