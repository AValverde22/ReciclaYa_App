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

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Path;
import pe.reciclaya.app.data.model.user.response.UserScoreResponse;
import pe.reciclaya.app.data.model.user.response.LoginAccessResponse;
import pe.reciclaya.app.data.model.user.request.login.LoginAccessRequest;
import pe.reciclaya.app.data.model.user.request.reportes.ReporteActividadRequest;
import pe.reciclaya.app.data.model.user.response.reportes.ReporteActividadResponse;
import pe.reciclaya.app.data.model.user.response.reportes.ReporteActividadResponseWrapper;
import pe.reciclaya.app.data.model.user.response.reportes.ReporteActividadDetalleWrapper;
import pe.reciclaya.app.data.model.user.request.profile.UpdateProfileRequest;
import pe.reciclaya.app.data.model.user.response.ProfileResponse;
import pe.reciclaya.app.data.model.user.response.PointsAccountResponse;
import pe.reciclaya.app.data.model.user.response.BenefitsResponse;
import pe.reciclaya.app.data.model.user.response.RedemptionResponse;
import pe.reciclaya.app.data.model.user.response.ReputationResponse;

public interface UserService {
    @GET("user/{id}/profile")
    Call<ProfileResponse> getProfile(@Path("id") int userId);

    @PATCH("user/{id}/profile")
    Call<ProfileResponse> updateProfile(@Path("id") int userId, @Body UpdateProfileRequest requestModel);

    @GET("user/{id}/points")
    Call<PointsAccountResponse> getPoints(@Path("id") int userId);

    @GET("user/{id}/benefits")
    Call<BenefitsResponse> getBenefits(@Path("id") int userId);

    @POST("user/{id}/benefits/{benefitId}/redeem")
    Call<RedemptionResponse> redeemBenefit(@Path("id") int userId, @Path("benefitId") int benefitId);

    @GET("user/{id}/reputation")
    Call<ReputationResponse> getReputation(@Path("id") int userId);

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

    @GET("accesos/{userId}")
    Call<List<LoginAccessResponse>> obtenerHistorialAccesos(@Path("userId") int userId);

    @GET("accesos/{userId}/{id}")
    Call<LoginAccessResponse> obtenerDetalleAcceso(@Path("userId") int userId, @Path("id") int id);

    @POST("accesos/{userId}")
    Call<Void> registrarAcceso(@Path("userId") int userId, @Body LoginAccessRequest request);

    @POST("accesos/{userId}/{id}/reportar")
    Call<ReporteActividadDetalleWrapper> registrarReporte(@Path("userId") int userId, @Path("id") int id, @Body ReporteActividadRequest request);

    @GET("accesos/{userId}/mis-reportes")
    Call<ReporteActividadResponseWrapper> obtenerMisReportes(@Path("userId") int userId);

    @GET("accesos/{userId}/mis-reportes/{idReporte}")
    Call<ReporteActividadDetalleWrapper> obtenerDetalleReporte(@Path("userId") int userId, @Path("idReporte") int idReporte);
}
