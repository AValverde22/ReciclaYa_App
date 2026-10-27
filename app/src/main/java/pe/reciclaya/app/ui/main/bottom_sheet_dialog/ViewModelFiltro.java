package pe.reciclaya.app.ui.main.bottom_sheet_dialog;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import pe.reciclaya.app.domain.model.solicitud.SolicitudFiltros;
import pe.reciclaya.app.domain.model.solicitud.TipoResiduo;

import pe.reciclaya.app.ui.common.event.Event;

public class ViewModelFiltro extends ViewModel {
    private final MutableLiveData<Event<String>> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> errorFecha = new MutableLiveData<>();
    private final MutableLiveData<String> tipoResiduo = new MutableLiveData<>();
    private final MutableLiveData<String> fecha = new MutableLiveData<>();
    private final MutableLiveData<String> hora = new MutableLiveData<>();
    private final MutableLiveData<String> score = new MutableLiveData<>();

    private final MutableLiveData<SolicitudFiltros> solicitudFiltro = new MutableLiveData<>();

    public LiveData<Event<String>> getError() { return error; }
    public LiveData<Boolean> getErrorFecha() { return errorFecha; }
    public LiveData<String> getTipoResiduo() { return tipoResiduo; }
    public LiveData<String> getFecha() { return fecha; }
    public LiveData<String> getHora() { return hora; }
    public LiveData<String> getScore() { return score; }

    public LiveData<SolicitudFiltros> getSolicitudFiltro() { return solicitudFiltro; }

    public void updateTipoResiduo(String _tipoResiduo) { tipoResiduo.setValue(_tipoResiduo); }
    public void updateHora(String _hora) { hora.setValue(_hora); }
    public void updateScore(String _score) { score.setValue(_score); }

    public void updateFecha(String _fecha) {
        if(_fecha.isBlank()) {
            errorFecha.setValue(true);
            error.setValue(new Event<>("Debe de seleccionar una fecha primero."));
        } else {
            errorFecha.setValue(false);
            fecha.setValue(_fecha);
        }
    }

    public void aplicarFiltros() {
        solicitudFiltro.setValue(
                new SolicitudFiltros(
                        TipoResiduo.fromString(tipoResiduo.getValue()),
                        fecha.getValue(),
                        hora.getValue(),
                        score.getValue()
                ));
    }

    public void resetFiltros() {
        tipoResiduo.setValue(null);
        fecha.setValue(null);
        hora.setValue(null);
        score.setValue(null);

        aplicarFiltros();
    }
}

