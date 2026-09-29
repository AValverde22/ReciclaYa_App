package pe.reciclaya.app.data.model.auth.register.request;

public class RegisterRequestEmail {
    private String email;

    public RegisterRequestEmail() {}
    public RegisterRequestEmail(String email) {this.email = email;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
}
