package net.hermesprime.rpg.heroez.gui.listener;

import com.jme3.input.controls.ActionListener;
import net.hermesprime.rpg.heroez.Heroez;
import net.hermesprime.rpg.heroez.input.HeroezInputSettings;

import java.util.logging.Logger;

/**
 * User: moore
 * Date: 8/21/11
 * Time: 7:05 AM
 */
public class GuiListener implements ActionListener {

    private static Logger logger = Logger.getLogger(GuiListener.class.getName());
    private final Heroez application;

    public GuiListener(final Heroez application) {
        this.application = application;
    }

    @Override
    public void onAction(final String name, final boolean isPressed, final float tpf) {
        if (HeroezInputSettings.INPUT_MAPPING_SHOW_MAIN_MENU.equals(name)) {
            application.setMenuMode(true);
        } else {
            logger.info("action=" + name + " isPressed=" + isPressed + " tpf=" + tpf);
        }
    }
}
