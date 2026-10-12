package pe.reciclaya.app.ui.main.usuario;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import pe.reciclaya.app.data.repository.solicitud.SolicitudRepositoryImp;
import pe.reciclaya.app.data.repository.user.UserRepositoryImp;
import pe.reciclaya.app.domain.model.solicitud.Solicitud;
import pe.reciclaya.app.domain.repository.RepositoryCallback;
import pe.reciclaya.app.domain.repository.solicitud.SolicitudRepository;
import pe.reciclaya.app.domain.repository.user.UserRepository;
import pe.reciclaya.app.ui.common.event.Event;
import pe.reciclaya.app.ui.main.solicitud_list.SolicitudItem;
import pe.reciclaya.app.ui.main.solicitud_list.SolicitudItemMapper;

public class ViewModelHistorial extends AndroidViewModel {
    private final MutableLiveData<List<SolicitudItem>> solicitudes = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final MutableLiveData<Event<String>> error = new MutableLiveData<>();

    private final MutableLiveData<Event<Boolean>> updated = new MutableLiveData<>();
    private final MutableLiveData<Event<Boolean>> canceled = new MutableLiveData<>();

    private final SolicitudRepository solicitudRepository;
    private final UserRepository userRepository;

    public ViewModelHistorial(@NonNull Application application) {
        super(application);

        solicitudRepository = new SolicitudRepositoryImp();
        userRepository = new UserRepositoryImp(application.getApplicationContext());
    }

    public LiveData<List<SolicitudItem>> getSolicitudes() { return solicitudes; }
    public LiveData<Boolean> getLoading() { return loading; }
    public LiveData<Event<String>> getError() { return error; }

    public LiveData<Event<Boolean>> getUpdated() { return updated; }
    public LiveData<Event<Boolean>> getCanceled() { return canceled; }

    public void updateSolicitudes() { getSolicitudesFromRepository(); }

    public void cancelSolicitud(int solicitudID) {
        loading.setValue(true);

        solicitudRepository.cancelSolicitud(
                solicitudID,
                new RepositoryCallback<>() {
                    @Override
                    public void onSuccess(Void data) {
                        canceled.postValue(new Event<>(true));
                        loading.postValue(false);
                    }

                    @Override
                    public void onError(String errorMessage) {
                        error.postValue(new Event<>(errorMessage));
                        loading.postValue(false);
                    }
        });
    }

    private void getSolicitudesFromRepository() {
        loading.setValue(true);

        int id = userRepository.getID();
        solicitudRepository.getSolicitudesCreadas(id, new RepositoryCallback<>() {
            @Override
            public void onSuccess(List<Solicitud> solicitudesDomain) {
                solicitudes.postValue(SolicitudItemMapper.allToUI(solicitudesDomain));
                loading.postValue(false);
            }

            @Override
            public void onError(String errorMessage) {
                error.postValue(new Event<>(errorMessage));
                loading.postValue(false);
            }
        });
    }
}
