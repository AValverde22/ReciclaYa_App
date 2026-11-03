package pe.reciclaya.app.data.model.user.request.login;

public class LoginRequest {
    private String email;
    private String password;
    private String app_version, dispositivo, ciudad, pais;
    private Double latitud, longitud;

    public void setAccessMetadata(String version, String device, Double latitude,
                                  Double longitude, String city, String country) {
        app_version = version;
        dispositivo = device;
        latitud = latitude;
        longitud = longitude;
        ciudad = city;
        pais = country;
    }

    public LoginRequest() {}
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() { return email; }
    public String getPassword() { return password; }

    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
}
