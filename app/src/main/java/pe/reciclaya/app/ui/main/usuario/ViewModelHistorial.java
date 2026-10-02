package pe.reciclaya.app.ui.main.usuario;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import pe.reciclaya.app.data.repository.main.HistorialRepository;
import pe.reciclaya.app.ui.common.event.Event;
import pe.reciclaya.app.ui.main.usuario.solicitud_list.Solicitud;

public class ViewModelHistorial extends ViewModel {
    private final MutableLiveData<List<Solicitud>> solicitudes = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final MutableLiveData<Event<String>> error = new MutableLiveData<>();

    private final HistorialRepository historialRepository;

    public ViewModelHistorial() {
        historialRepository = new HistorialRepository();
        getSolicitudesFromRepository();
    }

    public LiveData<List<Solicitud>> getSolicitudes() { return solicitudes; }
    public LiveData<Boolean> getLoading() { return loading; }
    public LiveData<Event<String>> getError() { return error; }

    public void updateSolicitudes() { getSolicitudesFromRepository(); }

    private void getSolicitudesFromRepository() {
        loading.setValue(true);

        historialRepository.getSolicitudes(new HistorialRepository.HistorialCallback() {
            @Override
            public void onSuccess(List<Solicitud> solicitudesList) {
                solicitudes.postValue(solicitudesList);
                loading.postValue(false);
            }

            @Override
            public void onError(String errorMessage) {
                error.postValue(new Event<>(errorMessage));
                loading.postValue(false);
            }
        });
    };
}
