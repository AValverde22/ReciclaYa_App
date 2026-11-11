package pe.reciclaya.app.domain.model.user;

import java.util.Collections;
import java.util.List;

public final class Profile {
    public final int id;
    public final String fullName;
    public final String email;
    public final UserRole role;
    public final String profilePhotoUrl;
    public final float score;
    public final String validationStatus;
    public final List<String> sensitiveFields;

    public Profile(int id, String fullName, String email, UserRole role, String profilePhotoUrl,
                   float score, String validationStatus, List<String> sensitiveFields) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.profilePhotoUrl = profilePhotoUrl;
        this.score = score;
        this.validationStatus = validationStatus == null ? "Validado" : validationStatus;
        this.sensitiveFields = sensitiveFields == null ? Collections.emptyList() : sensitiveFields;
    }
}
