package engineering.software.advanced.cantoolapp.webinterfaces;

import android.content.SharedPreferences;

/**
 * Created by ningge on 2017/10/25.
 */

public class CanSettings {
    private String version = "";
    private String speed = "";
    private boolean is_open = false;

    private SharedPreferences sharedPreferences = null;

    public CanSettings(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        this.version = sharedPreferences.getString("version", "");
        this.speed = sharedPreferences.getString("speed", "");
        this.is_open = sharedPreferences.getBoolean("is_open", false);
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public boolean is_open() {
        return is_open;
    }

    public void setIs_open(boolean is_open) {
        this.is_open = is_open;
    }

    public boolean save() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("version", this.version);
        editor.putString("speed", this.speed);
        editor.putBoolean("is_open", this.is_open);
        editor.commit();

        return true;
    }
}
