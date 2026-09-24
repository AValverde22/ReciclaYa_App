package pe.reciclaya.app.ui.tyc.factory_method;

public class FactoryPoliticaDePrivacidad implements FactoryTyC{
    @Override
    public TyC crear() {
        return new PoliticaDePrivacidad();
    }
}
