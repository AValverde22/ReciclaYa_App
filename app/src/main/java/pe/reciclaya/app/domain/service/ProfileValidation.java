package pe.reciclaya.app.domain.service;

import java.net.URI;
import java.util.regex.Pattern;

public final class ProfileValidation {
    private static final Pattern EMAIL = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    private ProfileValidation() { }

    public static String fullName(String value) {
        String clean = value == null ? "" : value.trim().replaceAll("\\s+", " ");
        if (clean.length() < 2 || clean.length() > 100)
            return "El nombre debe tener entre 2 y 100 caracteres.";
        return null;
    }

    public static String email(String value) {
        String clean = value == null ? "" : value.trim();
        if (clean.length() > 254 || !EMAIL.matcher(clean).matches())
            return "Ingrese un correo válido.";
        return null;
    }

    public static String photoUrl(String value) {
        String clean = value == null ? "" : value.trim();
        if (clean.isEmpty()) return null;
        if (clean.length() > 2048) return "Ingrese una URL de foto válida.";
        try {
            URI uri = URI.create(clean);
            if (!("http".equalsIgnoreCase(uri.getScheme()) || "https".equalsIgnoreCase(uri.getScheme()))
                    || uri.getHost() == null)
                return "Ingrese una URL de foto válida.";
        } catch (IllegalArgumentException error) {
            return "Ingrese una URL de foto válida.";
        }
        return null;
    }
}
