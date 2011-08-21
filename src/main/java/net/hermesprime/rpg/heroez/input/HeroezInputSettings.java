package net.hermesprime.rpg.heroez.input;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import net.hermesprime.rpg.heroez.util.HeroezSettings;

/**
 * User: moore
 * Date: 8/21/11
 * Time: 7:00 AM
 */
public class HeroezInputSettings {

    public static void setupInputManager(final InputManager inputManager, final ActionListener actionListener) {
        if (inputManager != null) {
            inputManager.deleteMapping("SIMPLEAPP_Exit");
            inputManager.addMapping(HeroezSettings.INPUT_MAPPING_SHOW_MAIN_MENU, new KeyTrigger(KeyInput.KEY_ESCAPE));
            inputManager.addListener(actionListener, HeroezSettings.INPUT_MAPPING_SHOW_MAIN_MENU);

//          inputManager.addMapping("SIMPLEAPP_Exit", new KeyTrigger(KeyInput.KEY_ESCAPE));
//          inputManager.addListener(actionListener, "SIMPLEAPP_Exit","SIMPLEAPP_CameraPos", "SIMPLEAPP_Memory");
        }
    }
}
