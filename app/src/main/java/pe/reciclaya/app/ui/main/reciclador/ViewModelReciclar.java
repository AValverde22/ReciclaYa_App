package pe.reciclaya.app.ui.main.reciclador;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.ArrayList;
import pe.reciclaya.app.domain.model.recojo.LocationFix;
import pe.reciclaya.app.ui.main.solicitud_list.SolicitudDistanceMapper;

import pe.reciclaya.app.data.repository.solicitud.SolicitudRepositoryImp;
import pe.reciclaya.app.data.repository.user.UserRepositoryImp;
import pe.reciclaya.app.domain.model.solicitud.Solicitud;
import pe.reciclaya.app.domain.model.solicitud.SolicitudFiltros;
import pe.reciclaya.app.domain.repository.RepositoryCallback;
import pe.reciclaya.app.domain.repository.solicitud.SolicitudRepository;
import pe.reciclaya.app.domain.repository.user.UserRepository;
import pe.reciclaya.app.ui.common.event.Event;
import pe.reciclaya.app.ui.main.solicitud_list.SolicitudItem;
import pe.reciclaya.app.ui.main.solicitud_list.SolicitudItemMapper;

public class ViewModelReciclar extends AndroidViewModel {
    private final MutableLiveData<SolicitudFiltros> solicitudFiltro = new MutableLiveData<>(new SolicitudFiltros(null, null, null, null));
    private final MutableLiveData<List<SolicitudItem>> solicitudesItem = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private final MutableLiveData<Event<String>> error = new MutableLiveData<>();
    private final MutableLiveData<Event<Boolean>> accepted = new MutableLiveData<>();

    private final SolicitudRepository solicitudRepository;
    private final UserRepository userRepository;
    private List<Solicitud> distanceSource;
    private LocationFix pickupLocation;
    private long locationNow;

    public void updatePickupLocation(LocationFix location,long now) {
        boolean same=pickupLocation==null?location==null:location!=null && pickupLocation.elapsedMillis==location.elapsedMillis;
        pickupLocation=location; locationNow=now;
        if (!same && distanceSource!=null) solicitudesItem.setValue(SolicitudDistanceMapper.toUI(distanceSource,pickupLocation,locationNow));
    }

    public ViewModelReciclar(@NonNull Application application) {
        super(application);

        solicitudRepository = new SolicitudRepositoryImp();
        userRepository = new UserRepositoryImp(application.getApplicationContext());
    }

    public LiveData<List<SolicitudItem>> getSolicitudesItem() { return solicitudesItem; }
    public LiveData<Boolean> getLoading() { return loading; }

    public LiveData<Event<String>> getError() { return error; }
    public LiveData<Event<Boolean>> getAccepted() { return accepted; }

    public void updateSolicitudFiltro(SolicitudFiltros filtro) { solicitudFiltro.setValue(filtro); }

    public void getSolicitudesDisponibles() {
        getSolicitudesDisponibles(solicitudFiltro.getValue());
    }

    public void getSolicitudesDisponibles(SolicitudFiltros solicitudFiltros) {
        updateSolicitudFiltro(solicitudFiltros);
        loading.setValue(true);

        solicitudRepository.getSolicitudesDisponibles(solicitudFiltros, new RepositoryCallback<>() {
            @Override
            public void onSuccess(List<Solicitud> solicitudes) {
                loading.postValue(false);
                distanceSource=new ArrayList<>(solicitudes);
                solicitudesItem.postValue(SolicitudDistanceMapper.toUI(distanceSource,pickupLocation,locationNow));
            }

            @Override
            public void onError(String errorMessage) {
                error.postValue(new Event<>(errorMessage));
                loading.postValue(false);
            }
        });
    }

    public void acceptSolicitud(int solicitudID) {
        loading.setValue(true);

        int userID = userRepository.getID();
        solicitudRepository.acceptSolicitud(solicitudID, userID, new RepositoryCallback<>() {
            @Override
            public void onSuccess(Void data) {
                accepted.postValue(new Event<>(true));
                loading.postValue(false);
            }

            @Override
            public void onError(String errorMessage) {
                accepted.postValue(new Event<>(false));
                error.postValue(new Event<>(errorMessage));
                loading.postValue(false);
            }
        });
    }
}
