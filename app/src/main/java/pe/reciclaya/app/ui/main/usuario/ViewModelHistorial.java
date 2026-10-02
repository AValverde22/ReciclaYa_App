package pe.reciclaya.app.ui.main.usuario;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import pe.reciclaya.app.data.repository.main.HistorialRepository;
import pe.reciclaya.app.ui.common.event.Event;
import pe.reciclaya.app.ui.main.usuario.solicitud_list.Solicitud;

public class ViewModelHistorial extends ViewModel {
    private final MutableLiveData<Event<Solicitud[]>> solicitudes = new MutableLiveData<>();
    private final MutableLiveData<Event<String>> error = new MutableLiveData<>();

    private final HistorialRepository historialRepository;

    public ViewModelHistorial() {
        historialRepository = new HistorialRepository();
        getSolicitudesFromRepository();
    }

    public LiveData<Event<Solicitud[]>> getSolicitudes() { return solicitudes; }
    public LiveData<Event<String>> getError() { return error; }

    private void getSolicitudesFromRepository() {
        historialRepository.getSolicitudes(new HistorialRepository.HistorialCallback() {
            @Override
            public void onSuccess(Solicitud[] solicitudesList) {
                solicitudes.setValue(new Event<>(solicitudesList));
            }

            @Override
            public void onError(String errorMessage) {
                error.setValue(new Event<>(errorMessage));
            }
        });
    };
}
