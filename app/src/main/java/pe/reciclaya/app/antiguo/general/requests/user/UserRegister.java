package pe.reciclaya.app.antiguo.general.requests.user;

import com.google.gson.annotations.SerializedName;

public class UserRegister {

    @SerializedName("full_name")
    private String fullName;
    private String email;
    private String password;
    private String role;

    public UserRegister() {}
    public UserRegister(String fullName, String email, String password, String role) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getFullName() {return fullName;}
    public String getEmail() {return email;}
    public String getPassword() {return password;}
    public String getRole() {return role;}

    public void setFullName(String fullName) {this.fullName = fullName;}
    public void setEmail(String email) {this.email = email;}
    public void setPassword(String password) {this.password = password;}
    public void setRole(String role) {this.role = role;}
}
