package pe.reciclaya.app.ui.tyc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import pe.reciclaya.app.ui.tyc.factory_method.FactoryPoliticaDePrivacidad;
import pe.reciclaya.app.ui.tyc.factory_method.FactoryTerminosDeServicio;
import pe.reciclaya.app.data.repository.TyCRepository;
import pe.reciclaya.app.ui.tyc.factory_method.FactoryTyC;

public class TyCViewModel extends ViewModel {
    private final MutableLiveData<Integer> titulo = new MutableLiveData<>();
    private final MutableLiveData<Integer> mensaje = new MutableLiveData<>();

    public LiveData<Integer> getTitulo() { return titulo; }
    public LiveData<Integer> getMensaje() { return mensaje; }

    public void cargarContenido(int tipo) {
        FactoryTyC factoryTyC;
        if (tipo == 0) factoryTyC = new FactoryTerminosDeServicio();
        else factoryTyC = new FactoryPoliticaDePrivacidad();

        TyCRepository tyCRepository = new TyCRepository(factoryTyC);
        titulo.setValue(tyCRepository.getTituloID());
        mensaje.setValue(tyCRepository.getMensajeID());
    }
}
