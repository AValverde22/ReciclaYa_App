package pe.reciclaya.app.ui.main.perfil;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import pe.reciclaya.app.data.repository.user.UserRepositoryImp;
import pe.reciclaya.app.domain.model.user.UserRole;
import pe.reciclaya.app.domain.repository.user.UserRepository;
import pe.reciclaya.app.ui.common.event.Event;
import pe.reciclaya.app.domain.repository.RepositoryCallback;

public class PerfilViewModel extends AndroidViewModel {
    private final MutableLiveData<Event<Boolean>> cerrarSesion = new MutableLiveData<>();

    private final MutableLiveData<String> fullName = new MutableLiveData<>();
    private final MutableLiveData<String> email = new MutableLiveData<>();
    private final MutableLiveData<UserRole> role = new MutableLiveData<>();
    private final MutableLiveData<String> urlFotoPerfil = new MutableLiveData<>();
    private final MutableLiveData<Float> score = new MutableLiveData<>();

    private final UserRepository userRepository;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepositoryImp(application.getApplicationContext());
        obtenerInformacionPerfil();
    }

    public LiveData<Event<Boolean>> getCerrarSesion() { return cerrarSesion; }
    public LiveData<String> getFullName() { return fullName; }
    public LiveData<String> getEmail() { return email; }
    public LiveData<UserRole> getRole() { return role; }
    public LiveData<String> getURLFotoPerfil() { return urlFotoPerfil; }
    public LiveData<Float> getScore() { return score; }

    private void obtenerInformacionPerfil() {
        fullName.setValue(userRepository.getFullName());
        email.setValue(userRepository.getEmail());
        role.setValue(userRepository.getRole());
        urlFotoPerfil.setValue(userRepository.getProfilePhotoURL());
        score.setValue(userRepository.getScore());
    }

    public void refreshScore() {
        userRepository.refreshScore(new RepositoryCallback<Float>() {
            @Override public void onSuccess(Float value) { score.setValue(value); }
            @Override public void onError(String message) { /* Keep the last known score when offline. */ }
        });
    }

    @Override protected void onCleared() {
        userRepository.cancelScoreRefresh();
        super.onCleared();
    }

    public void cerrarSesion() {
        userRepository.logout();
        cerrarSesion.setValue(new Event<>(true));
    }
}
