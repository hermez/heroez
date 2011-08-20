package net.hermesprime.rpg.heroez.gui.controller;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import net.hermesprime.rpg.heroez.Heroez;

import java.util.List;

/**
 * User: moore
 * Date: 8/20/11
 * Time: 1:24 AM
 */
public class SimpleController implements ScreenController {

    protected Nifty nifty;
    protected Screen screen;
    protected Heroez application;

    public SimpleController(final Heroez application) {
        this.application = application;
    }

    @Override
    public void bind(final Nifty nifty, final Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
    }

    @Override
    public void onStartScreen() {
    }

    @Override
    public void onEndScreen() {
    }
}