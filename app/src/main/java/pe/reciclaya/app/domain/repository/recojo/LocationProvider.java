package pe.reciclaya.app.domain.repository.recojo;

import pe.reciclaya.app.domain.model.recojo.LocationFix;

public interface LocationProvider {
    interface Listener { void onLocation(LocationFix location); void onError(String message); }
    void start(Listener listener);
    void stop();
}
