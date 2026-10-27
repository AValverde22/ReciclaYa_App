package pe.reciclaya.app.domain.model.user;

public enum UserRole {
    USUARIO("Usuario"),
    RECICLADOR("Reciclador"),
    DEFAULT("Default");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public static UserRole fromString(String rol) {
        for (UserRole role : values()) {
            if (role.getRole().equalsIgnoreCase(rol)) {
                return role;
            }
        }
        return null;
    }
}