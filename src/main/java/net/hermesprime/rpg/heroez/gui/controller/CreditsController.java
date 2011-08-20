package net.hermesprime.rpg.heroez.gui.controller;

import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.KeyInputHandler;
import net.hermesprime.rpg.heroez.Heroez;

/**
 * User: moore
 * Date: 8/20/11
 * Time: 1:03 AM
 */
public class CreditsController extends SimpleController /*implements KeyInputHandler*/ {

    public CreditsController(final Heroez application) {
        super(application);
    }

    public void back() {
        nifty.fromXml("gui/mainMenu.xml", "start");


    }

//    public boolean keyEvent(final NiftyInputEvent inputEvent) {
//        if (inputEvent == NiftyInputEvent.Escape) {
//            //escape = true;
//            nifty.setAlternateKey("exit");
//            nifty.setAlternateKeyForNextLoadXml("fade");
//            nifty.fromXml("gui/mainMenu.xml", "start");
//            return true;
//        }
//        return false;
//    }
}
