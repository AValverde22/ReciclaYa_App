package pe.reciclaya.app.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import pe.reciclaya.app.data.repository.SplashRepository;

public class SplashViewModel extends AndroidViewModel {
    private final SplashRepository splashRepository;
    private final MutableLiveData<String> savedRole = new MutableLiveData<>();

    public SplashViewModel(@NonNull Application application) {
        super(application);
        this.splashRepository = new SplashRepository(application.getApplicationContext());

        getPreviousRole();
    }

    public LiveData<String> getSavedRole() { return savedRole; }

    private void getPreviousRole() {
        String role = splashRepository.getSavedRole();
        savedRole.setValue(role);
    }
}
