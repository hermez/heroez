package net.hermesprime.rpg.heroez.input;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;

/**
 * User: moore
 * Date: 8/21/11
 * Time: 7:00 AM
 */
public class HeroezInputSettings {

    public final static String INPUT_MAPPING_SHOW_MAIN_MENU = "HEROEZ_ShowMainMenu";

    public static void setupInputManager(final InputManager inputManager, final ActionListener actionListener) {
        if (inputManager != null) {
//            inputManager.addMapping("SIMPLEAPP_CameraPos", new KeyTrigger(KeyInput.KEY_C));
//            inputManager.addMapping("SIMPLEAPP_Memory", new KeyTrigger(KeyInput.KEY_M));
//            inputManager.addListener(actionListener, "SIMPLEAPP_Exit",
//                    "SIMPLEAPP_CameraPos", "SIMPLEAPP_Memory");

            inputManager.deleteMapping("SIMPLEAPP_Exit");
            inputManager.addMapping(INPUT_MAPPING_SHOW_MAIN_MENU, new KeyTrigger(KeyInput.KEY_ESCAPE));
            inputManager.addListener(actionListener, INPUT_MAPPING_SHOW_MAIN_MENU);


//            //testing
//            inputManager.addMapping("BUTTON_LEFT", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
//            inputManager.addMapping("BUTTON_MIDDLE", new MouseButtonTrigger(MouseInput.BUTTON_MIDDLE));
//            inputManager.addMapping("BUTTON_RIGHT", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
//            inputManager.addMapping("AXIS_WHEEL", new MouseButtonTrigger(MouseInput.AXIS_WHEEL));
//            inputManager.addMapping("AXIS_X", new MouseButtonTrigger(MouseInput.AXIS_X));
//            inputManager.addMapping("AXIS_Y", new MouseButtonTrigger(MouseInput.AXIS_Y));
//            inputManager.addMapping("AXIS_WHEEL, false", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false));
//            inputManager.addMapping("AXIS_WHEEL, true", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, true));
//            inputManager.addMapping("AXIS_X, false", new MouseAxisTrigger(MouseInput.AXIS_X, false));
//            inputManager.addMapping("AXIS_X, true", new MouseAxisTrigger(MouseInput.AXIS_X, true));
//            inputManager.addMapping("AXIS_Y, false", new MouseAxisTrigger(MouseInput.AXIS_Y, false));
//            inputManager.addMapping("AXIS_Y, true", new MouseAxisTrigger(MouseInput.AXIS_Y, true));
//
//            inputManager.addListener(actionListener,
//                    "BUTTON_LEFT", "BUTTON_MIDDLE", "BUTTON_RIGHT",
//                    "AXIS_WHEEL", "AXIS_X", "AXIS_Y",
//                    "AXIS_WHEEL, false", "AXIS_WHEEL, true", "AXIS_X, false", "AXIS_X, true", "AXIS_Y, false", "AXIS_Y, true");
        }
    }
}
