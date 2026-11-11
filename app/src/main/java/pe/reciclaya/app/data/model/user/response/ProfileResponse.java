package pe.reciclaya.app.data.model.user.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public final class ProfileResponse {
    public int id;
    @SerializedName("full_name") public String fullName;
    public String email;
    public String role;
    @SerializedName("profile_photo_url") public String profilePhotoUrl;
    public float score;
    @SerializedName("validation_status") public String validationStatus;
    @SerializedName("sensitive_fields") public List<String> sensitiveFields;
    public String message;
}
