package pe.reciclaya.app.data.factory_method.tyc;

public class FactoryPoliticaDePrivacidad implements FactoryTyC{
    @Override
    public TyC crear() {
        return new PoliticaDePrivacidad();
    }
}
