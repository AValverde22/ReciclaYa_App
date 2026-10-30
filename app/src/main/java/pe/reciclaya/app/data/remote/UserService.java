package pe.reciclaya.app.data.remote;

import pe.reciclaya.app.data.model.user.response.UserResponse;
import pe.reciclaya.app.data.model.user.request.register.RegisterRequestEmail;
import pe.reciclaya.app.data.model.user.request.register.RegisterRequestUser;
import pe.reciclaya.app.data.model.user.request.login.LoginRequest;
import pe.reciclaya.app.data.model.user.request.login.LoginResendRequest;
import pe.reciclaya.app.data.model.user.request.login.LoginVerifyRequest;
import pe.reciclaya.app.data.model.user.request.seguridad.TwoFactorRequest;
import pe.reciclaya.app.data.model.user.response.LoginResendResponse;
import pe.reciclaya.app.data.model.user.response.LoginVerifyResponse;
import pe.reciclaya.app.data.model.user.response.TwoFactorResponse;
import pe.reciclaya.app.data.model.user.request.restablecer.RestablecerRequestCompare;
import pe.reciclaya.app.data.model.user.request.restablecer.RestablecerRequestRecover;
import pe.reciclaya.app.data.model.user.request.restablecer.RestablecerRequestReset;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Path;
import pe.reciclaya.app.data.model.user.response.UserScoreResponse;

public interface UserService {
    @GET("user/{id}/score")
    Call<UserScoreResponse> getScore(@Path("id") int userId);

    @POST("user/validate")
    Call<Boolean> validateEmail(@Body RegisterRequestEmail requestModel);

    @POST("user/register")
    Call<Integer> registerUser(@Body RegisterRequestUser requestModel);

    @POST("user/login")
    Call<UserResponse> loginUser(@Body LoginRequest requestModel);

    @POST("user/login/verify")
    Call<LoginVerifyResponse> verifyLogin(@Body LoginVerifyRequest requestModel);

    @POST("user/login/resend")
    Call<LoginResendResponse> resendLoginCode(@Body LoginResendRequest requestModel);

    @GET("user/{id}/two-factor")
    Call<TwoFactorResponse> getTwoFactor(@Path("id") int userId);

    @PATCH("user/{id}/two-factor")
    Call<TwoFactorResponse> updateTwoFactor(@Path("id") int userId, @Body TwoFactorRequest requestModel);

    @POST("user/recover")
    Call<Void> recoverUser(@Body RestablecerRequestRecover requestModel);

    @POST("user/compare")
    Call<Boolean> compareCode(@Body RestablecerRequestCompare requestModel);

    @PATCH("user/reset")
    Call<UserResponse> resetUser(@Body RestablecerRequestReset requestModel);
}
