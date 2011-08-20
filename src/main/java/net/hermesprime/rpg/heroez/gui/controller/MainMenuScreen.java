package net.hermesprime.rpg.heroez.gui.controller;

import de.lessvoid.nifty.elements.Element;
import net.hermesprime.rpg.heroez.Heroez;

import java.util.logging.Logger;

/**
 * User: moore
 * Date: 8/18/11
 * Time: 11:08 PM
 */
public class MainMenuScreen extends SimpleController {

    private static Logger logger = Logger.getLogger(MainMenuScreen.class.getName());

    public MainMenuScreen(final Heroez application) {
        super(application);
    }

    @Override
    public void onStartScreen() {
        final Element notInGameMainMenuLayer = screen.findElementByName("notInGameMainMenuLayer");
        final Element inGameMainMenuLayer = screen.findElementByName("inGameMainMenuLayer");
        if (application.isInGame()) {
            notInGameMainMenuLayer.setVisible(false);
            inGameMainMenuLayer.setVisible(true);
        } else {
            notInGameMainMenuLayer.setVisible(true);
            inGameMainMenuLayer.setVisible(false);
        }
        super.onStartScreen();
    }

    public void about() {
        nifty.fromXml("gui/about.xml", "start");
    }

    public void credits() {
        nifty.fromXml("gui/credits.xml", "start");
    }

    public void options() {
        nifty.fromXml("gui/options.xml", "start");
    }

    public void multiplayer() {
        nifty.fromXml("gui/multiplayer.xml", "start");
    }

    public void singlePlayer() {
        nifty.fromXml("gui/singlePlayer.xml", "start");
    }

    public void exit() {
        nifty.setAlternateKey("fade");
        nifty.exit();
        application.stop();
    }

    public void returnToGame() {
        nifty.exit();
        application.setMenuMode(false);
    }
}
