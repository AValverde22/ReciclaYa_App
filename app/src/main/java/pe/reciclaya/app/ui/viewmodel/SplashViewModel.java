package pe.reciclaya.app.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import pe.reciclaya.app.data.repository.SplashRepository;
import pe.reciclaya.app.ui.util.Event;

public class SplashViewModel extends AndroidViewModel {
    private final SplashRepository splashRepository;
    private final MutableLiveData<Event<Boolean>> destino = new MutableLiveData<>();

    public SplashViewModel(@NonNull Application application) {
        super(application);
        this.splashRepository = new SplashRepository(application.getApplicationContext());

        getSavedRole();
    }

    public LiveData<Event<Boolean>> getDestinoConfirmado() { return destino; }

    private void getSavedRole() {
        splashRepository.getSavedUser();
        destino.setValue(new Event<>(true));
    }
}
