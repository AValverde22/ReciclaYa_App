package pe.reciclaya.app.domain.singleton;

public class User {
    private static User user;

    private int id;
    private String fullName;
    private String email;
    private String role;

    private User(){}

    public static User getInstance() {
        if(user == null) user = new User();
        return user;
    }

    public int getID() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getRole() { return role; }

    public void setID(int id) { this.id = id; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setRole(String role) { this.role = role; }
}
