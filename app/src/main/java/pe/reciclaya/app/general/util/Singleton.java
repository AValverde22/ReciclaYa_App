package pe.reciclaya.app.general.util;

import androidx.lifecycle.MutableLiveData;

import pe.reciclaya.app.general.models.User;

public class Singleton {
    private static final MutableLiveData<User> userLogged = new MutableLiveData<>();
    private static final MutableLiveData<Boolean> mostrarLLLoadingRegister = new MutableLiveData<>(false);

    public static MutableLiveData<User> getUserLogged() { return userLogged; }
    public static MutableLiveData<Boolean> getMostrarLLLoadingRegister() { return mostrarLLLoadingRegister; }
}
