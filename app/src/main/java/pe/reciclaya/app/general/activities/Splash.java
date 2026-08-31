package pe.reciclaya.app.general.activities;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.splashscreen.SplashScreen;
import androidx.appcompat.app.AppCompatActivity;

import pe.reciclaya.app.general.models.User;
import pe.reciclaya.app.general.util.Common;
import pe.reciclaya.app.general.util.Singleton;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("INICIAL", MODE_PRIVATE);
        int id = prefs.getInt("id", -1);

        if(id == -1) Common.irAMain(this, null);
        else {
            String fullName = prefs.getString("fullName", null);
            String email = prefs.getString("email", null);
            String role = prefs.getString("role", null);

            Singleton.getUserLogged().postValue(new User(id, fullName, email, role));
            Common.irAMain(this, role);
        }

        finish();
    }
}