package pe.reciclaya.app.domain.model.user;

public class User {
    private static volatile User user;

    private int id;
    private String fullName;
    private String email;
    private UserRole role;
    private String profilePhotoURL;
    private float score;

    private User() {}
    public static synchronized User getInstance() {
        if(user == null) user = new User();
        return user;
    }

    public static synchronized void clearInstance() { user = null; }

    public int getID() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public UserRole getRole() { return role; }
    public String getProfilePhotoURL() { return profilePhotoURL; }
    public float getScore() { return score; }

    public void setID(int id) { this.id = id; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setRole(UserRole role) { this.role = role; }
    public void setProfilePhotoURL(String profilePhotoURL) { this.profilePhotoURL = profilePhotoURL; }
    public void setScore(float score) { this.score = score; }
}
