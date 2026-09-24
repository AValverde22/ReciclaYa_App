package pe.reciclaya.app.ui.splash;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import pe.reciclaya.app.data.repository.SplashRepository;
import pe.reciclaya.app.ui.common.event.Event;

public class SplashViewModel extends AndroidViewModel {
    private final SplashRepository splashRepository;
    private final MutableLiveData<Event<String>> roleResponse = new MutableLiveData<>();

    public SplashViewModel(@NonNull Application application) {
        super(application);
        this.splashRepository = new SplashRepository(application.getApplicationContext());

        getSavedRole();
    }

    public LiveData<Event<String>> getRoleResponse() { return roleResponse; }

    private void getSavedRole() {
        String role = splashRepository.getSavedUser();
        roleResponse.setValue(new Event<>(role));
    }
}
