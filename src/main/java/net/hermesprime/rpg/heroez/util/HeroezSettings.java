package net.hermesprime.rpg.heroez.util;

import com.jme3.system.AppSettings;

import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

/**
 * User: moore
 * Date: 8/6/11
 * Time: 12:31 AM
 */
public class HeroezSettings {

    private static Logger logger = Logger.getLogger(HeroezSettings.class.getName());
    public static final int GAME_LOOP_MENU_MODE_DELAY = 100;
    private AppSettings appSettings;
    private final Set<Properties> properties;

    public HeroezSettings(final Set<Properties> properties) {
        this.properties = properties;
    }

    public AppSettings getAppSettings() {
        return appSettings;
    }

    public void setAppSettings(AppSettings appSettings) {
        this.appSettings = appSettings;
    }

    public float getPropertyFloat(final String key) {
        try {
            return Float.parseFloat(getProperty(key));
        } catch (Exception e) {
            throw new PropertyException("Failed to load property '" + key + "'", e);
        }
    }

    public int getPropertyInt(final String key) {
        try {
            return Integer.parseInt(getProperty(key));
        } catch (Exception e) {
            throw new PropertyException("Failed to load property '" + key + "'", e);
        }
    }

    public Boolean getPropertyBoolean(final String key) {
        return Boolean.parseBoolean(getProperty(key));
    }

    public String getProperty(final String key) {
        for (final Properties p : properties) {
            if (p.containsKey(key)) {
                return p.getProperty(key);
            }
        }
        throw new PropertyException("Property '" + key + "' not found" + key);
    }
}
