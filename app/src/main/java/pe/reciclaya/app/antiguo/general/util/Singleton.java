package pe.reciclaya.app.antiguo.general.util;

import androidx.lifecycle.MutableLiveData;

import pe.reciclaya.app.data.model.login.LoginResponse;

public class Singleton {
    private static final MutableLiveData<LoginResponse> userLogged = new MutableLiveData<>();
    private static final MutableLiveData<Boolean> mostrarLLLoadingRegister = new MutableLiveData<>(false);

    public static MutableLiveData<LoginResponse> getUserLogged() { return userLogged; }
    public static MutableLiveData<Boolean> getMostrarLLLoadingRegister() { return mostrarLLLoadingRegister; }
}
