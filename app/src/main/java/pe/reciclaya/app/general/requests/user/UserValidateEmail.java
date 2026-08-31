package pe.reciclaya.app.general.requests.user;

public class UserValidateEmail {
    private String email;

    public UserValidateEmail() {}
    public UserValidateEmail(String email) {this.email = email;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
}
