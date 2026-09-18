package pe.reciclaya.app.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import pe.reciclaya.app.data.repository.RestablecerRepository;
import pe.reciclaya.app.ui.util.Event;
import pe.reciclaya.app.ui.util.Validaciones;

public class RestablecerViewModel extends AndroidViewModel {
    private final MutableLiveData<Event<String>> error = new MutableLiveData<>();
    private final MutableLiveData<Event<Boolean>> siguiente = new MutableLiveData<>();
    private final MutableLiveData<Event<Boolean>> subSiguiente = new MutableLiveData<>();
    private final MutableLiveData<Event<Boolean>> roleResponse = new MutableLiveData<>();

    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);

    private final MutableLiveData<String> emailError = new MutableLiveData<>();
    private final MutableLiveData<String> passwordError = new MutableLiveData<>();
    private final MutableLiveData<String> confirmPasswordError = new MutableLiveData<>();

    private String email;

    private final RestablecerRepository restablecerRepository;
    public RestablecerViewModel(@NonNull Application application) {
        super(application);
        restablecerRepository = new RestablecerRepository(application.getApplicationContext());
    }

    public LiveData<Event<String>> getError() { return error; }
    public LiveData<Event<Boolean>> irSiguiente() { return siguiente; }
    public LiveData<Event<Boolean>> irSubSiguiente() { return subSiguiente; }
    public LiveData<Event<Boolean>> getRoleResponse() { return roleResponse; }

    public LiveData<Boolean> getLoading() { return loading; }

    public LiveData<String> getEmailError() { return emailError; }
    public LiveData<String> getPasswordError() { return passwordError; }
    public LiveData<String> getConfirmPasswordError() { return confirmPasswordError; }

    public void updateEmail(String email){ emailError.setValue(Validaciones.campoGenerico(email)); }
    public void updatePassword(String password) { passwordError.setValue(Validaciones.password(password)); }
    public void updateConfirmPassword(String password, String confirmPassword) {
        confirmPasswordError.setValue(Validaciones.confirmPassword(password, confirmPassword));
    }

    public void reenviarCodigo() { enviarCodigo(email); }

    public void enviarCodigo(String email) {
        updateEmail(email);

        if(emailError.getValue() != null) return;
        this.email = email.trim();

        loading.setValue(true);
        restablecerRepository.enviarCodigo(email.trim(), new RestablecerRepository.ResetCallback() {
            @Override
            public void onSuccess() {
                loading.postValue(false);
                siguiente.postValue(new Event<>(true));
            }

            @Override
            public void onError(String errorMessage) {
                loading.postValue(false);
                error.postValue(new Event<>(errorMessage));
            }
        });
    }

    public void compararCodigo(String cod1, String cod2, String cod3, String cod4, String cod5, String cod6) {
        if(Validaciones.codigo(cod1, cod2, cod3, cod4, cod5, cod6) != null) {
            error.setValue(new Event<>("Rellene los campos correctamente."));
            return;
        }

        int codigo = Integer.parseInt(cod1 + cod2 + cod3 + cod4 + cod5 + cod6);

        loading.setValue(true);
        restablecerRepository.compararCodigo(email, codigo, new RestablecerRepository.ResetCallback() {
            @Override
            public void onSuccess() {
                loading.postValue(false);
                subSiguiente.postValue(new Event<>(true));
            }

            @Override
            public void onError(String errorMessage) {
                loading.postValue(false);
                error.postValue(new Event<>(errorMessage));
            }
        });
    }

    public void resetUser(String password, String confirmPassword) {
        updatePassword(password);
        updateConfirmPassword(password, confirmPassword);

        if(passwordError.getValue() != null || confirmPasswordError.getValue() != null) return;

        loading.setValue(true);
        restablecerRepository.resetUser(email, password.trim(), new RestablecerRepository.ResetCallback() {
            @Override
            public void onSuccess() {
                loading.postValue(false);
                roleResponse.postValue(new Event<>(true));
            }

            @Override
            public void onError(String errorMessage) {
                loading.postValue(false);
                error.postValue(new Event<>(errorMessage));
            }
        });
    }
}
