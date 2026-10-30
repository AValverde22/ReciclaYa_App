package pe.reciclaya.app.data.model.user.response;

import com.google.gson.annotations.SerializedName;

public class LoginResendResponse {
    public static final String SENT = "SENT";
    public static final String COOLDOWN = "COOLDOWN";
    public static final String EMAIL_ERROR = "EMAIL_ERROR";

    private String result;
    @SerializedName("retry_after") private int retryAfter;

    public LoginResendResponse() {}

    public String getResult() { return result; }
    public int getRetryAfter() { return retryAfter; }

    public void setResult(String result) { this.result = result; }
    public void setRetryAfter(int retryAfter) { this.retryAfter = retryAfter; }
}
