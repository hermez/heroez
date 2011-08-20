package net.hermesprime.rpg.heroez.util;

import com.jme3.system.AppSettings;

/**
 * User: moore
 * Date: 8/6/11
 * Time: 12:31 AM
 */
public class HeroezSettings {

    public static final int GAME_LOOP_MENU_MODE_DELAY = 100;
    private AppSettings appSettings;

    public final static String INPUT_MAPPING_SHOW_MAIN_MENU = "HEROEZ_ShowMainMenu";

    public AppSettings getAppSettings() {
        return appSettings;
    }

    public void setAppSettings(AppSettings appSettings) {
        this.appSettings = appSettings;
    }
}
