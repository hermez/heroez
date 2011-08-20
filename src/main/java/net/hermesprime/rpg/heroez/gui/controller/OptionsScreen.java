package net.hermesprime.rpg.heroez.gui.controller;

import net.hermesprime.rpg.heroez.Heroez;

/**
 * User: moore
 * Date: 8/20/11
 * Time: 3:09 AM
 */
public class OptionsScreen extends SimpleController {

    public OptionsScreen(final Heroez application) {
        super(application);
    }

    public void back() {
        nifty.fromXml("gui/mainMenu.xml", "start");
    }

    public void audio() {
        //nifty.fromXml("gui/options.xml", "start");
    }

    public void video() {
        //nifty.fromXml("gui/options.xml", "start");
    }

    public void ai() {
        //nifty.fromXml("gui/options.xml", "start");
    }

    public void gameplay() {
        //nifty.fromXml("gui/options.xml", "start");
    }
}
