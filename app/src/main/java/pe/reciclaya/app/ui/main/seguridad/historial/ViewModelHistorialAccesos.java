package pe.reciclaya.app.ui.main.seguridad.historial;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import pe.reciclaya.app.data.repository.user.AccesosRepositoryImp;
import pe.reciclaya.app.data.model.user.response.LoginAccessResponse;
import pe.reciclaya.app.domain.repository.RepositoryCallback;
import pe.reciclaya.app.domain.repository.user.AccesosRepository;
import pe.reciclaya.app.ui.common.event.Event;

public class ViewModelHistorialAccesos extends AndroidViewModel {

    private final MutableLiveData<List<AccesoItem>> accesos =
            new MutableLiveData<>(new ArrayList<>());

    private final MutableLiveData<Boolean> loading =
            new MutableLiveData<>(false);

    private final MutableLiveData<Event<String>> error =
            new MutableLiveData<>();

    private final AccesosRepository accesosRepository;

    public ViewModelHistorialAccesos(@NonNull Application application) {
        super(application);

        accesosRepository = new AccesosRepositoryImp(
                application.getApplicationContext()
        );
    }

    public LiveData<List<AccesoItem>> getAccesos() {
        return accesos;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public LiveData<Event<String>> getError() {
        return error;
    }

    public void updateHistorial() {

        loading.setValue(true);

        accesosRepository.obtenerHistorial(new RepositoryCallback<>() {

            @Override
            public void onSuccess(List<LoginAccessResponse> data) {

                List<AccesoItem> lista = AccesoItemMapper.allToUI(data);

                accesos.postValue(lista);
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