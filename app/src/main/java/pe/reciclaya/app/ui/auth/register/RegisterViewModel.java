package pe.reciclaya.app.ui.auth.register;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import pe.reciclaya.app.data.repository.RegisterRepository;
import pe.reciclaya.app.ui.common.event.Event;
import pe.reciclaya.app.ui.common.util.Validaciones;

public class RegisterViewModel extends AndroidViewModel {
    private final MutableLiveData<Event<String>> error = new MutableLiveData<>();
    private final MutableLiveData<Event<Integer>> tipo = new MutableLiveData<>();
    private final MutableLiveData<Event<Boolean>> siguiente = new MutableLiveData<>();
    private final MutableLiveData<Event<String>> roleResponse = new MutableLiveData<>();

    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);

    private final MutableLiveData<String> fullNameError = new MutableLiveData<>();
    private final MutableLiveData<String> emailError = new MutableLiveData<>();
    private final MutableLiveData<String> passwordError = new MutableLiveData<>();
    private final MutableLiveData<String> confirmPasswordError = new MutableLiveData<>();
    private final MutableLiveData<String> roleError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> checkError = new MutableLiveData<>(false);

    private String fullName;
    private String email;
    private String password;
    private String role;

    private final RegisterRepository registerRepository;
    public RegisterViewModel(@NonNull Application application) {
        super(application);
        registerRepository = new RegisterRepository(application.getApplicationContext());
    }

    public LiveData<Event<Integer>> getTipo() { return tipo; }
    public LiveData<Event<String>> getError() { return error; }
    public LiveData<Event<Boolean>> irSiguiente() { return siguiente; }
    public LiveData<Event<String>> getRoleResponse() { return roleResponse; }

    public LiveData<Boolean> getLoading() { return loading; }

    public LiveData<String> getFullNameError() { return fullNameError; }
    public LiveData<String> getEmailError() { return emailError; }
    public LiveData<String> getPasswordError() { return passwordError; }
    public LiveData<String> getConfirmPasswordError() { return confirmPasswordError; }

    public void updateTipo(int t) { tipo.setValue(new Event<>(t)); }

    public void updateFullName(String fullName) { fullNameError.setValue(Validaciones.campoGenerico(fullName)); }
    public void updateEmail(String email) { emailError.setValue(Validaciones.email(email)); }
    public void updatePassword(String password) { passwordError.setValue(Validaciones.password(password)); }
    public void updateConfirmPassword(String password, String confirmPassword) {
        confirmPasswordError.setValue(Validaciones.confirmPassword(password, confirmPassword));
    }
    public void updateRole(String role) { this.role = role; }
    public void updateCheckBox(boolean isChecked) { checkError.setValue(!isChecked); }

    public void validarEmail(String fullName, String email, String password, String confirmPassword) {
        updateFullName(fullName);
        updateEmail(email);
        updatePassword(password);
        updateConfirmPassword(password, confirmPassword);

        if(fullNameError.getValue() != null
                || emailError.getValue() != null
                || passwordError.getValue() != null
                || confirmPasswordError.getValue() != null)
            return;


        if(Boolean.TRUE.equals(checkError.getValue())) {
            error.setValue(new Event<>("Para seguir, debe de aceptar los términos y condiciones."));
            return;
        }

        this.fullName = fullName;
        this.email = email;
        this.password = password;

        loading.setValue(true);
        registerRepository.validarEmail(email.trim(), new RegisterRepository.RegisterCallback() {
            @Override
            public void onSuccess(String role) {
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

    public void registerUser() {
        roleError.setValue(Validaciones.role(role));
        if(roleError.getValue() != null) {
            error.setValue(new Event<>(roleError.getValue()));
            return;
        }

        loading.setValue(true);
        registerRepository.registerUser(fullName, email, password, role, new RegisterRepository.RegisterCallback() {
            @Override
            public void onSuccess(String role) {
                loading.postValue(false);
                roleResponse.postValue(new Event<>(role));
            }

            @Override
            public void onError(String errorMessage) {
                loading.postValue(false);
                error.postValue(new Event<>(errorMessage));
            }
        });
    }
}
