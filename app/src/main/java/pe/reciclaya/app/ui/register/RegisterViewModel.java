package pe.reciclaya.app.ui.register;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import pe.reciclaya.app.data.repository.RegisterRepository;
import pe.reciclaya.app.ui.util.Validaciones;

public class RegisterViewModel extends AndroidViewModel {
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> siguiente = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Integer> tipo = new MutableLiveData<>(0);

    private final MutableLiveData<Boolean> finalizarActivity = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> eliminarFragment = new MutableLiveData<>(false);

    private final MutableLiveData<String> fullNameError = new MutableLiveData<>();
    private final MutableLiveData<String> emailError = new MutableLiveData<>();
    private final MutableLiveData<String> passwordError = new MutableLiveData<>();
    private final MutableLiveData<String> confirmPasswordError = new MutableLiveData<>();
    private final MutableLiveData<String> roleError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> checkError = new MutableLiveData<>(true);

    private String fullName;
    private String email;
    private String password;
    private String role;

    private final RegisterRepository registerRepository;
    public RegisterViewModel(@NonNull Application application) {
        super(application);
        registerRepository = new RegisterRepository(application.getApplicationContext());
    }

    public LiveData<Boolean> getLoading() { return loading; }
    public LiveData<Boolean> irSiguiente() { return siguiente; }
    public LiveData<Integer> getTipo() { return tipo; }
    public LiveData<String> getError() { return error; }

    public LiveData<Boolean> getFinalizarActivity() { return finalizarActivity; }
    public LiveData<Boolean> getEliminarFragment() { return eliminarFragment; }

    public LiveData<String> getFullNameError() { return fullNameError; }
    public LiveData<String> getEmailError() { return emailError; }
    public LiveData<String> getPasswordError() { return passwordError; }
    public LiveData<String> getConfirmPasswordError() { return confirmPasswordError; }
    public LiveData<String> getRoleError() { return roleError; }

    public void updateTipo(int t) { tipo.setValue(t); }
    public void updateFinalizarActivity(boolean finalizar) { finalizarActivity.setValue(finalizar); }
    public void updateEliminarFragment(boolean eliminar) { eliminarFragment.setValue(eliminar); }

    public void updateFullName(String fullName) { fullNameError.setValue(Validaciones.campoGenerico(fullName)); }
    public void updateEmail(String email) { emailError.setValue(Validaciones.email(email)); }
    public void updatePassword(String password) { passwordError.setValue(Validaciones.password(password)); }

    public void updateConfirmPassword(String password, String confirmPassword) {
        confirmPasswordError.setValue(Validaciones.confirmPassword(password, confirmPassword));
    }

    public void updateRole(String role) { roleError.setValue(role); }
    public void updateCheckBox(boolean isChecked) { checkError.setValue(!isChecked); }

    public void validarEmail(String fullName, String email, String password, String confirmPassword) {
        updateFullName(fullName);
        updateEmail(email);
        updatePassword(password);
        updateConfirmPassword(password, confirmPassword);

        if(fullNameError.getValue() != null
                || emailError.getValue() != null
                || passwordError.getValue() != null
                || confirmPasswordError.getValue() != null
        )
            return;

        if(Boolean.TRUE.equals(checkError.getValue())) {
            error.setValue("Para seguir, debe de aceptar los términos y condiciones.");
            return;
        }

        this.fullName = fullName;
        this.email = email;
        this.password = password;

        loading.setValue(true);
        registerRepository.validarEmail(email.trim(), new RegisterRepository.RegisterCallback() {
            @Override
            public void onSuccess() {
                loading.postValue(false);
                siguiente.postValue(true);
            }

            @Override
            public void onError(String errorMessage) {
                loading.postValue(false);
                error.postValue(errorMessage);
            }
        });
    }

    public void registerUser() {
        updateRole(role);
        if(roleError.getValue() != null) return;

        loading.setValue(true);
        registerRepository.registerUser(fullName, email, password, role, new RegisterRepository.RegisterCallback() {
            @Override
            public void onSuccess() {
                loading.postValue(false);
            }

            @Override
            public void onError(String errorMessage) {
                loading.postValue(false);
                error.postValue(errorMessage);
            }
        });
    }
}
