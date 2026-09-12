package pe.reciclaya.app.domain.factory_method.tyc;

public class FactoryPoliticaDePrivacidad implements FactoryTyC{
    @Override
    public TyC crear() {
        return new PoliticaDePrivacidad();
    }
}
