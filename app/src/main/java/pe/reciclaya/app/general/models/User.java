package pe.reciclaya.app.general.models;

import com.google.gson.annotations.SerializedName;

public class User {
    private int id;
    @SerializedName ("full_name") private String fullName;
    private String email;
    private String role;

    public User(int id, String fullName, String email, String role) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }

    public int getID() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getRole() { return role; }

    public void setID(int id) { this.id = id; }
    public void setFullName(String full_name) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setRole(String role) { this.role = role; }
}
