package net.hermesprime.rpg.heroez.gui.controller;

import net.hermesprime.rpg.heroez.Heroez;

/**
 * User: moore
 * Date: 8/20/11
 * Time: 3:09 AM
 */
public class MultiplayerScreen extends SimpleController {

    public MultiplayerScreen(final Heroez application) {
        super(application);
    }

    public void back() {
        nifty.fromXml("gui/mainMenu.xml", "start");
    }

    public void newCampaign() {
        //nifty.fromXml("gui/options.xml", "start");
    }

    public void newScenario() {
        //nifty.fromXml("gui/options.xml", "start");
    }

    public void loadGame() {
        //nifty.fromXml("gui/options.xml", "start");
    }
}
