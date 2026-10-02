package pe.reciclaya.app.ui.main.usuario;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;

import pe.reciclaya.app.data.repository.main.SolicitudRepository;
import pe.reciclaya.app.ui.common.event.Event;


public class ViewModelSolicitar extends ViewModel {
    private final MutableLiveData<Event<String>> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> errorFecha = new MutableLiveData<>();
    private final MutableLiveData<Boolean> errorHora = new MutableLiveData<>();
    private final MutableLiveData<Boolean> errorDireccion = new MutableLiveData<>();

    private final MutableLiveData<String> direccionSeleccionada = new MutableLiveData<>();
    private final MutableLiveData<LatLng> ubicacionSeleccionada = new MutableLiveData<>();

    private final MutableLiveData<Event<Boolean>> success = new MutableLiveData<>();

    private String tipoResiduo;
    private String fecha;
    private String hora;
    private String direccion;
    private LatLng latLng;

    private final SolicitudRepository solicitudRepository;

    public ViewModelSolicitar() {
        solicitudRepository = new SolicitudRepository();
    }


    public LiveData<Event<String>> getError() { return error; }
    public LiveData<Boolean> getErrorFecha() { return errorFecha; }
    public LiveData<Boolean> getErrorHora() { return errorHora; }
    public LiveData<Boolean> getErrorDireccion() { return errorDireccion; }

    public LiveData<String> getDireccion() { return direccionSeleccionada; }
    public LiveData<LatLng> getUbicacionSeleccionada() { return ubicacionSeleccionada; }

    public LiveData<Event<Boolean>> getSuccess() { return success; }

    public void updateError(String errorMessage) { error.setValue(new Event<>(errorMessage)); }
    public void updateTipoResiduo(String tipoResiduo) { this.tipoResiduo = tipoResiduo; }
    public void updateFecha(String fecha) {
        if(fecha.isBlank()) {
            errorFecha.setValue(true);
            error.setValue(new Event<>("Debe de seleccionar una fecha primero."));
        } else {
            errorFecha.setValue(false);
            this.fecha = fecha;
        }
    }
    public void updateHora(String hora) { this.hora = hora; errorHora.setValue(false); }
    public void updateDireccion(String direccion) {
        direccionSeleccionada.setValue(direccion);
        errorDireccion.setValue(false);
        this.direccion = direccion;
    }
    public void updateUbicacionSeleccionada(LatLng latLng) {
        ubicacionSeleccionada.setValue(latLng);
        this.latLng = latLng;
    }

    public void publicarSolicitud(String tamano) {
        if(!conforme()) return;

        solicitudRepository.crearSolicitud(tipoResiduo, tamano, fecha, hora, direccion, latLng,
                new SolicitudRepository.SolicitudCallback() {
                    @Override
                    public void onSuccess() {
                        success.postValue(new Event<>(true));
                    }

                    @Override
                    public void onError(String errorMessage) {
                        error.postValue(new Event<>(errorMessage));
                    }
                }
        );
    }

    private boolean conforme() {
        boolean conforme = true;
        if(tipoResiduo == null) {
            error.setValue(new Event<>("Debe de seleccionar un tipo de residuo."));
            conforme = false;
        }

        if(fecha == null) {
            error.setValue(new Event<>("Debe de seleccionar una fecha."));
            errorFecha.setValue(true);
            conforme = false;
        }

        if(hora == null) {
            error.setValue(new Event<>("Debe de seleccionar una hora."));
            errorHora.setValue(true);
            conforme = false;
        }

        if(direccion == null) {
            error.setValue(new Event<>("Debe de seleccionar una dirección."));
            errorDireccion.setValue(true);
            conforme = false;
        }

        return conforme;
    }

    public void limpiarCampos() {
        tipoResiduo = null;
        fecha = null;
        hora = null;
        direccion = null;
        latLng = null;
    }
}
