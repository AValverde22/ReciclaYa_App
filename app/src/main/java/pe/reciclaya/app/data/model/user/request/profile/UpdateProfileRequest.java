package pe.reciclaya.app.data.model.user.request.profile;

import com.google.gson.annotations.SerializedName;

public final class UpdateProfileRequest {
    @SerializedName("full_name") private final String fullName;
    private final String email;
    @SerializedName("profile_photo_url") private final String profilePhotoUrl;

    public UpdateProfileRequest(String fullName, String email, String profilePhotoUrl) {
        this.fullName = fullName;
        this.email = email;
        this.profilePhotoUrl = profilePhotoUrl;
    }
}
