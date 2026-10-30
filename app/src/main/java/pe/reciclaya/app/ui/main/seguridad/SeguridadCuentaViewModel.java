package pe.reciclaya.app.ui.main.seguridad;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import pe.reciclaya.app.data.repository.user.SeguridadRepositoryImp;
import pe.reciclaya.app.domain.repository.RepositoryCallback;
import pe.reciclaya.app.domain.repository.user.SeguridadRepository;
import pe.reciclaya.app.ui.common.event.Event;

public class SeguridadCuentaViewModel extends AndroidViewModel {
    // null mientras no se conoce el estado guardado en el servidor
    private final MutableLiveData<Boolean> habilitado = new MutableLiveData<>(null);
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private final MutableLiveData<Event<String>> mensaje = new MutableLiveData<>();
    private final MutableLiveData<String> email = new MutableLiveData<>();

    private final SeguridadRepository seguridadRepository;

    public SeguridadCuentaViewModel(@NonNull Application application) {
        super(application);
        seguridadRepository = new SeguridadRepositoryImp(application.getApplicationContext());
        email.setValue(seguridadRepository.getEmail());
        cargar();
    }

    public LiveData<Boolean> getHabilitado() { return habilitado; }
    public LiveData<Boolean> getLoading() { return loading; }
    public LiveData<Event<String>> getMensaje() { return mensaje; }
    public LiveData<String> getEmail() { return email; }

    public void cargar() {
        loading.setValue(true);
        seguridadRepository.getTwoFactor(new RepositoryCallback<>() {
            @Override
            public void onSuccess(Boolean enabled) {
                loading.postValue(false);
                habilitado.postValue(enabled);
            }

            @Override
            public void onError(String errorMessage) {
                loading.postValue(false);
                mensaje.postValue(new Event<>(errorMessage));
            }
        });
    }

    public void activar() {
        actualizar(true, null, "Autenticación de dos factores activada");
    }

    public void desactivar(String password) {
        if(password == null || password.isBlank()) {
            mensaje.setValue(new Event<>("Ingrese su contraseña."));
            restaurarEstado();
            return;
        }
        actualizar(false, password, "Autenticación de dos factores desactivada");
    }

    // Devuelve el interruptor al último estado confirmado por el servidor
    public void restaurarEstado() { habilitado.setValue(habilitado.getValue()); }

    private void actualizar(boolean enabled, String password, String mensajeExito) {
        loading.setValue(true);
        seguridadRepository.updateTwoFactor(enabled, password, new RepositoryCallback<>() {
            @Override
            public void onSuccess(Boolean nuevoEstado) {
                loading.postValue(false);
                habilitado.postValue(nuevoEstado);
                mensaje.postValue(new Event<>(mensajeExito));
            }

            @Override
            public void onError(String errorMessage) {
                loading.postValue(false);
                habilitado.postValue(habilitado.getValue());
                mensaje.postValue(new Event<>(errorMessage));
            }
        });
    }
}
