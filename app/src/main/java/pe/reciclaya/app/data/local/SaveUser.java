package pe.reciclaya.app.data.local;

import pe.reciclaya.app.data.model.user.response.UserResponse;
import pe.reciclaya.app.domain.model.user.User;
import pe.reciclaya.app.domain.model.user.UserRole;

public class SaveUser {
    public static void saveData(AppPreferencesManager appPreferencesManager, User user, UserResponse response) {
        user.setID(response.getID());
        user.setFullName(response.getFullName());
        user.setEmail(response.getEmail());
        user.setRole(UserRole.fromString(response.getRole()));
        user.setProfilePhotoURL(response.getProfilePhotoURL());
        user.setScore(response.getScore());

        appPreferencesManager.putInt("id", response.getID());
        appPreferencesManager.putString("fullName", response.getFullName());
        appPreferencesManager.putString("email", user.getEmail());
        appPreferencesManager.putString("role", user.getRole().getRole());
        appPreferencesManager.putString("profilePhotoURL", user.getProfilePhotoURL());
        appPreferencesManager.putFloat("score", user.getScore());
    }

    public static void loadData(AppPreferencesManager appPreferencesManager, User user) {
        int id = appPreferencesManager.getInt("id");
        if(id == -1) return;

        String fullName = appPreferencesManager.getString("fullName");
        String email = appPreferencesManager.getString("email");
        String role = appPreferencesManager.getString("role");
        String profilePhotoURL = appPreferencesManager.getString("profilePhotoURL");
        float score = appPreferencesManager.getFloat("score");

        user.setID(id);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setRole(UserRole.fromString(role));
        user.setProfilePhotoURL(profilePhotoURL);
        user.setScore(score);
    }
}
