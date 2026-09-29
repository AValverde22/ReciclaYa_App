package pe.reciclaya.app.data.remote;

import pe.reciclaya.app.data.model.auth.login.response.LoginResponse;
import pe.reciclaya.app.data.model.auth.register.request.RegisterRequestEmail;
import pe.reciclaya.app.data.model.auth.register.request.RegisterRequestUser;
import pe.reciclaya.app.data.model.auth.login.request.LoginRequest;
import pe.reciclaya.app.data.model.auth.restablecer.request.RestablecerRequestCompare;
import pe.reciclaya.app.data.model.auth.restablecer.request.RestablecerRequestRecover;
import pe.reciclaya.app.data.model.auth.restablecer.request.RestablecerRequestReset;
import pe.reciclaya.app.data.model.auth.restablecer.response.RestablecerResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface UserService {

    @POST("user/validate")
    Call<Boolean> validateEmail(@Body RegisterRequestEmail requestModel);

    @POST("user/register")
    Call<Integer> registerUser(@Body RegisterRequestUser requestModel);

    @POST("user/login")
    Call<LoginResponse> loginUser(@Body LoginRequest requestModel);

    @POST("user/recover")
    Call<Void> recoverUser(@Body RestablecerRequestRecover requestModel);

    @POST("user/compare")
    Call<Boolean> compareCode(@Body RestablecerRequestCompare requestModel);

    @PATCH("user/reset")
    Call<RestablecerResponse> resetUser(@Body RestablecerRequestReset requestModel);

}
