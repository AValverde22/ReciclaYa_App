package pe.reciclaya.app.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import pe.reciclaya.app.data.model.login.LoginResponse;
import pe.reciclaya.app.data.repository.LoginRepository;
import pe.reciclaya.app.ui.util.Validaciones;

public class LoginViewModel extends AndroidViewModel {
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private final MutableLiveData<String> roleResponse = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    private final MutableLiveData<String> emailError = new MutableLiveData<>();
    private final MutableLiveData<String> passwordError = new MutableLiveData<>();

    private final LoginRepository loginRepository;
    public LoginViewModel(@NonNull Application application) {
        super(application);
        loginRepository = new LoginRepository(application.getApplicationContext());
    }

    public LiveData<Boolean> getLoading() { return loading; }
    public LiveData<String> getRoleResponse() { return roleResponse; }
    public LiveData<String> getError() { return error; }
    public LiveData<String> getEmailError() { return emailError; }
    public LiveData<String> getPasswordError() { return passwordError; }

    public void updateEmail(String email) { emailError.setValue(Validaciones.campoGenerico(email)); }
    public void updatePassword(String password) { passwordError.setValue(Validaciones.campoGenerico(password)); }

    public void iniciarSesion(String email, String password) {
        updateEmail(email);
        updatePassword(password);

        if(emailError.getValue() != null || passwordError.getValue() != null) return;

        loading.setValue(true);
        loginRepository.iniciarSesion(email.trim(), password.trim(), new LoginRepository.LoginCallback() {
            @Override
            public void onSuccess(LoginResponse response) {
                loading.postValue(false);
                roleResponse.postValue(response.getRole());
            }

            @Override
            public void onError(String errorMessage) {
                loading.postValue(false);
                error.postValue(errorMessage);
            }
        });
    }
}
