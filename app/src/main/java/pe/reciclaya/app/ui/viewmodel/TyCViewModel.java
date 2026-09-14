package pe.reciclaya.app.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import pe.reciclaya.app.domain.factory_method.tyc.FactoryPoliticaDePrivacidad;
import pe.reciclaya.app.domain.factory_method.tyc.FactoryTerminosDeServicio;
import pe.reciclaya.app.data.repository.TyCRepository;

public class TyCViewModel extends ViewModel {
    private final MutableLiveData<Integer> titulo = new MutableLiveData<>();
    private final MutableLiveData<Integer> mensaje = new MutableLiveData<>();

    public LiveData<Integer> getTitulo() { return titulo; }
    public LiveData<Integer> getMensaje() { return mensaje; }

    public void cargarContenido(int tipo) {
        TyCRepository tyCRepository;

        if (tipo == 0) tyCRepository = new TyCRepository(new FactoryTerminosDeServicio());
        else tyCRepository = new TyCRepository(new FactoryPoliticaDePrivacidad());

        titulo.setValue(tyCRepository.getTituloID());
        mensaje.setValue(tyCRepository.getMensajeID());
    }
}
