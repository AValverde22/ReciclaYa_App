package pe.reciclaya.app.data.remote;

import pe.reciclaya.app.BuildConfig;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BackendClient {

    private static final String BASE_URL = BuildConfig.BASE_URL;
    private static UserService userService;
    private static SolicitudService solicitudService;

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static UserService getUserService() {
        if(userService == null) userService = retrofit.create(UserService.class);
        return userService;
    }

    public static SolicitudService getSolicitudService() {
        if(solicitudService == null) solicitudService = retrofit.create(SolicitudService.class);
        return solicitudService;
    }

    public static <T> T buildService(Class<T> service) { return retrofit.create(service); }
}
