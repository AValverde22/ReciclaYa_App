package pe.reciclaya.app.ui.main.seguridad;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import pe.reciclaya.app.data.model.user.response.LoginAccessResponse;
import pe.reciclaya.app.data.model.user.response.reportes.ReporteActividadResponse;
import pe.reciclaya.app.data.repository.user.AccesosRepositoryImp;
import pe.reciclaya.app.domain.repository.RepositoryCallback;
import pe.reciclaya.app.domain.repository.user.AccesosRepository;
import pe.reciclaya.app.ui.common.event.Event;

public class AccesosViewModel extends AndroidViewModel {

    private final AccesosRepository repository;

    private final MutableLiveData<List<LoginAccessResponse>> historial =
            new MutableLiveData<>();

    private final MutableLiveData<LoginAccessResponse> detalleAcceso =
            new MutableLiveData<>();

    private final MutableLiveData<List<ReporteActividadResponse>> misReportes =
            new MutableLiveData<>();

    private final MutableLiveData<ReporteActividadResponse> detalleReporte =
            new MutableLiveData<>();

    private final MutableLiveData<Boolean> loading =
            new MutableLiveData<>(false);

    private final MutableLiveData<Event<String>> mensaje =
            new MutableLiveData<>();

    public AccesosViewModel(@NonNull Application application) {
        super(application);
        repository = new AccesosRepositoryImp(
                application.getApplicationContext()
        );
    }

    public LiveData<List<LoginAccessResponse>> getHistorial() {
        return historial;
    }

    public LiveData<LoginAccessResponse> getDetalleAcceso() {
        return detalleAcceso;
    }

    public LiveData<List<ReporteActividadResponse>> getMisReportes() {
        return misReportes;
    }

    public LiveData<ReporteActividadResponse> getDetalleReporte() {
        return detalleReporte;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public LiveData<Event<String>> getMensaje() {
        return mensaje;
    }

    public void cargarHistorial() {
        loading.setValue(true);

        repository.obtenerHistorial(new RepositoryCallback<>() {
            @Override
            public void onSuccess(List<LoginAccessResponse> resultado) {
                historial.postValue(resultado);
                loading.postValue(false);
            }

            @Override
            public void onError(String errorMessage) {
                loading.postValue(false);
                mensaje.postValue(new Event<>(errorMessage));
            }
        });
    }

    public void cargarDetalleAcceso(int accesoId) {
        loading.setValue(true);

        repository.obtenerDetalle(accesoId, new RepositoryCallback<>() {
            @Override
            public void onSuccess(LoginAccessResponse resultado) {
                detalleAcceso.postValue(resultado);
                loading.postValue(false);
            }

            @Override
            public void onError(String errorMessage) {
                loading.postValue(false);
                mensaje.postValue(new Event<>(errorMessage));
            }
        });
    }

    public void reportarAcceso(int accesoId, String descripcion) {
        loading.setValue(true);

        repository.registrarReporte(
                accesoId,
                descripcion,
                new RepositoryCallback<>() {
                    @Override
                    public void onSuccess(Void resultado) {
                        loading.postValue(false);
                        mensaje.postValue(
                                new Event<>("Reporte registrado correctamente.")
                        );
                    }

                    @Override
                    public void onError(String errorMessage) {
                        loading.postValue(false);
                        mensaje.postValue(new Event<>(errorMessage));
                    }
                }
        );
    }

    public void cargarMisReportes() {
        loading.setValue(true);

        repository.obtenerMisReportes(new RepositoryCallback<>() {
            @Override
            public void onSuccess(
                    List<ReporteActividadResponse> resultado
            ) {
                misReportes.postValue(resultado);
                loading.postValue(false);
            }

            @Override
            public void onError(String errorMessage) {
                loading.postValue(false);
                mensaje.postValue(new Event<>(errorMessage));
            }
        });
    }

    public void cargarDetalleReporte(int reporteId) {
        loading.setValue(true);

        repository.obtenerDetalleReporte(
                reporteId,
                new RepositoryCallback<>() {
                    @Override
                    public void onSuccess(ReporteActividadResponse resultado) {
                        detalleReporte.postValue(resultado);
                        loading.postValue(false);
                    }

                    @Override
                    public void onError(String errorMessage) {
                        loading.postValue(false);
                        mensaje.postValue(new Event<>(errorMessage));
                    }
                }
        );
    }
}