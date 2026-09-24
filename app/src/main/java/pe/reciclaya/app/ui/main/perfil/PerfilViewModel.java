package pe.reciclaya.app.ui.main.perfil;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import pe.reciclaya.app.data.repository.main.PerfilRepository;
import pe.reciclaya.app.ui.common.event.Event;

public class PerfilViewModel extends AndroidViewModel {
    private final MutableLiveData<Event<Boolean>> cerrarSesion = new MutableLiveData<>();

    private final MutableLiveData<String> fullName = new MutableLiveData<>();
    private final MutableLiveData<String> email = new MutableLiveData<>();
    private final MutableLiveData<String> role = new MutableLiveData<>();

    private final PerfilRepository perfilRepository;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        perfilRepository = new PerfilRepository(application.getApplicationContext());

        obtenerInformacionPerfil();
    }

    public LiveData<Event<Boolean>> getCerrarSesion() { return cerrarSesion; }
    public LiveData<String> getFullName() { return fullName; }
    public LiveData<String> getEmail() { return email; }
    public LiveData<String> getRole() { return role; }

    private void obtenerInformacionPerfil() {
        fullName.setValue(perfilRepository.getFullName());
        email.setValue(perfilRepository.getEmail());
        role.setValue(perfilRepository.getRole());
    }

    public void cerrarSesion() {
        perfilRepository.cerrarSesion();
        cerrarSesion.setValue(new Event<>(true));
    }
}
