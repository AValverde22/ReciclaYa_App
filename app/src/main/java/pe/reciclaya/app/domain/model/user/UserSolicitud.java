package pe.reciclaya.app.domain.model.user;

public class UserSolicitud {
    private final int id;
    private final String fullName;
    private final String profilePhotoURL;
    private final float score;

    public UserSolicitud(int id, String fullName, String profilePhotoURL, float score) {
        this.id = id;
        this.fullName = fullName;
        this.profilePhotoURL = profilePhotoURL;
        this.score = score;
    }

    public int getID() { return id; }
    public String getFullName() { return fullName; }
    public String getProfilePhotoURL() { return profilePhotoURL; }
    public float getScore() { return score; }
}
