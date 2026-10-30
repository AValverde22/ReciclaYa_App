package pe.reciclaya.app.ui.auth.verificacion;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import pe.reciclaya.app.data.repository.user.LoginRepositoryImp;
import pe.reciclaya.app.domain.model.user.UserRole;
import pe.reciclaya.app.domain.repository.RepositoryCallback;
import pe.reciclaya.app.domain.repository.user.LoginRepository;
import pe.reciclaya.app.ui.common.event.Event;
import pe.reciclaya.app.ui.common.task.MainThreadScheduler;
import pe.reciclaya.app.ui.common.task.TaskScheduler;

public class VerificacionViewModel extends AndroidViewModel {
    // Igual a la espera entre reenvíos del servidor
    private static final int ESPERA_REENVIO_SEGUNDOS = 60;

    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private final MutableLiveData<Event<String>> mensaje = new MutableLiveData<>();
    private final MutableLiveData<Event<UserRole>> roleResponse = new MutableLiveData<>();
    private final MutableLiveData<Integer> segundosReenvio = new MutableLiveData<>(0);

    private final LoginRepository loginRepository;
    private final TaskScheduler scheduler = new MainThreadScheduler();

    private String email;
    private String challenge;

    private final Runnable cuentaRegresiva = new Runnable() {
        @Override
        public void run() {
            Integer restantes = segundosReenvio.getValue();
            int siguiente = (restantes == null) ? 0 : restantes - 1;
            segundosReenvio.setValue(Math.max(siguiente, 0));
            if(siguiente > 0) scheduler.post(this, 1000);
        }
    };

    public VerificacionViewModel(@NonNull Application application) {
        super(application);
        loginRepository = new LoginRepositoryImp(application.getApplicationContext());
    }

    public LiveData<Boolean> getLoading() { return loading; }
    public LiveData<Event<String>> getMensaje() { return mensaje; }
    public LiveData<Event<UserRole>> getRoleResponse() { return roleResponse; }
    public LiveData<Integer> getSegundosReenvio() { return segundosReenvio; }
    public String getEmail() { return email; }

    // El código se envió al iniciar sesión, por eso el reenvío empieza bloqueado
    public void inicializar(String email, String challenge) {
        if(this.challenge != null) return;

        this.email = email;
        this.challenge = challenge;
        iniciarCuentaRegresiva();
    }

    public void verificar(String codigo) {
        if(!codigo.matches("\\d{6}")) {
            mensaje.setValue(new Event<>("Ingrese los 6 dígitos del código."));
            return;
        }

        loading.setValue(true);
        loginRepository.verifyCode(email, challenge, codigo, new RepositoryCallback<>() {
            @Override
            public void onSuccess(UserRole role) {
                loading.postValue(false);
                roleResponse.postValue(new Event<>(role));
            }

            @Override
            public void onError(String errorMessage) {
                loading.postValue(false);
                mensaje.postValue(new Event<>(errorMessage));
            }
        });
    }

    public void reenviarCodigo() {
        Integer restantes = segundosReenvio.getValue();
        if(restantes != null && restantes > 0) return;

        loading.setValue(true);
        loginRepository.resendCode(email, challenge, new RepositoryCallback<>() {
            @Override
            public void onSuccess(Void data) {
                loading.postValue(false);
                mensaje.postValue(new Event<>("Se envió un nuevo código a su correo."));
                iniciarCuentaRegresiva();
            }

            @Override
            public void onError(String errorMessage) {
                loading.postValue(false);
                mensaje.postValue(new Event<>(errorMessage));
            }
        });
    }

    private void iniciarCuentaRegresiva() {
        scheduler.remove(cuentaRegresiva);
        segundosReenvio.setValue(ESPERA_REENVIO_SEGUNDOS);
        scheduler.post(cuentaRegresiva, 1000);
    }

    @Override
    protected void onCleared() {
        scheduler.remove(cuentaRegresiva);
        super.onCleared();
    }
}
