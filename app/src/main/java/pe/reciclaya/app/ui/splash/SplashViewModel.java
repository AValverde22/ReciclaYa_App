package pe.reciclaya.app.ui.splash;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import pe.reciclaya.app.data.repository.user.SplashRepositoryImp;
import pe.reciclaya.app.domain.model.user.UserRole;
import pe.reciclaya.app.domain.repository.user.SplashRepository;
import pe.reciclaya.app.ui.common.event.Event;

public class SplashViewModel extends AndroidViewModel {
    private final SplashRepository splashRepository;
    private final MutableLiveData<Event<UserRole>> roleResponse = new MutableLiveData<>();

    public SplashViewModel(@NonNull Application application) {
        super(application);
        this.splashRepository = new SplashRepositoryImp(application.getApplicationContext());

        getSavedRole();
    }

    public LiveData<Event<UserRole>> getRoleResponse() { return roleResponse; }

    private void getSavedRole() {
        UserRole role = splashRepository.getSavedUser();
        roleResponse.setValue(new Event<>(role));
    }
}
