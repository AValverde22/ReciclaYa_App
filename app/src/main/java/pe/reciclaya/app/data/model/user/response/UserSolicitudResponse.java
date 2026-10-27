package pe.reciclaya.app.data.model.user.response;

import com.google.gson.annotations.SerializedName;

public class UserSolicitudResponse {
    private int id;
    @SerializedName ("full_name") private String fullName;
    @SerializedName("profile_photo_url") private String profilePhotoURL;
    private float score;

    public UserSolicitudResponse(int id, String fullName, String profilePhotoURL, float score) {
        this.id = id;
        this.fullName = fullName;
        this.profilePhotoURL = profilePhotoURL;
        this.score = score;
    }

    public int getID() { return id; }
    public String getFullName() { return fullName; }
    public String getProfilePhotoURL() { return profilePhotoURL; }
    public float getScore() { return score; }

    public void setID(int id) { this.id = id; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setProfilePhotoURL(String profilePhotoURL) { this.profilePhotoURL = profilePhotoURL; }
    public void setScore(float score) { this.score = score; }
}
