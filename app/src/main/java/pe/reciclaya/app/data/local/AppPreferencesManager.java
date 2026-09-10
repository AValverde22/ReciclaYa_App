package pe.reciclaya.app.data.local;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferencesManager {
    private static SharedPreferences sharedPreferences = null;
    private static SharedPreferences getSharedPreferences(Context context) {
        if(context != null) {
            if(sharedPreferences == null) {
                sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
            }
        }
        return sharedPreferences;
    }

    public static int getInt(String key, Context context) { return getSharedPreferences(context).getInt(key, -1); }
    public static String getString(String key, Context context) { return getSharedPreferences(context).getString(key, null); }

    public static void putInt(String key, int value, Context context) { getSharedPreferences(context).edit().putInt(key, value).apply(); }
    public static void putString(String key, String value, Context context) { getSharedPreferences(context).edit().putString(key, value).apply(); }
}
