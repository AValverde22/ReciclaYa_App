package pe.reciclaya.app.data.remote;

import pe.reciclaya.app.data.model.login.response.LoginResponse;
import pe.reciclaya.app.data.model.register.request.RegisterRequestEmail;
import pe.reciclaya.app.data.model.register.request.RegisterRequestUser;
import pe.reciclaya.app.data.model.login.request.LoginRequest;
import pe.reciclaya.app.data.model.restablecer.request.RestablecerRequestCompare;
import pe.reciclaya.app.data.model.restablecer.request.RestablecerRequestRecover;
import pe.reciclaya.app.data.model.restablecer.request.RestablecerRequestReset;
import pe.reciclaya.app.data.model.restablecer.response.RestablecerResponse;
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
