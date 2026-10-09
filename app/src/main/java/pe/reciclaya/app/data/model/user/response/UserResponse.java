package pe.reciclaya.app.data.model.user.response;

import com.google.gson.annotations.SerializedName;

public class UserResponse {
    private int id;
    @SerializedName ("full_name") private String fullName;
    private String email;
    private String role;
    @SerializedName("profile_photo_url") private String profilePhotoURL;
    private float score;

    public UserResponse(int id, String fullName, String email, String role, String profilePhotoURL, float score) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.profilePhotoURL = profilePhotoURL;
        this.score = score;
    }

    public int getID() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public String getProfilePhotoURL() { return profilePhotoURL; }
    public float getScore() { return score; }

    public void setID(int id) { this.id = id; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setRole(String role) { this.role = role; }
    public void setProfilePhotoURL(String profilePhotoURL) { this.profilePhotoURL = profilePhotoURL; }
    public void setScore(float score) { this.score = score; }
}
