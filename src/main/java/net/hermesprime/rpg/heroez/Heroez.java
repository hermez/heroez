package net.hermesprime.rpg.heroez;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.InputListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeContext;
import de.lessvoid.nifty.Nifty;
import net.hermesprime.rpg.heroez.gameView.GameView;
import net.hermesprime.rpg.heroez.gui.controller.*;
import net.hermesprime.rpg.heroez.util.HeroezSettings;
import org.lwjgl.input.Mouse;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: moore
 * Date: 8/5/11
 * Time: 10:23 PM
 */
public class Heroez extends SimpleApplication {

    private static Logger logger = Logger.getLogger(Heroez.class.getName());
    private final HeroezSettings heroezSettings;
    private Nifty nifty;
    private boolean menuMode = true;
    private boolean inGame = false;
    private NiftyJmeDisplay niftyDisplay;
    private GameView gameView;

    public Heroez() {
        heroezSettings = new HeroezSettings();
        setShowSettings(false);
        heroezSettings.setAppSettings(new AppSettings(true));
        setSettings(heroezSettings.getAppSettings());
        settings.setResolution(1280, 768);
    }

    public static void main(final String... args) {
        final Heroez heroez = new Heroez();
        heroez.setPauseOnLostFocus(false);
        heroez.start();
    }

    @Override
    public void simpleUpdate(float tpf) {
        super.simpleUpdate(tpf);
        if (menuMode) {
            try {
                Thread.sleep(HeroezSettings.GAME_LOOP_MENU_MODE_DELAY);
            } catch (InterruptedException e) {
                logger.log(Level.WARNING, "tpf=" + tpf + " paused=" + paused, e);
            }
        }
    }

    @Override
    public void simpleInitApp() {
        setupInput();
        createMenu();
        createGameView();
        Mouse.setGrabbed(false);//lwjgl
        setMenuMode(true);
    }

    private void createGameView() {
        gameView = new GameView(rootNode, assetManager);
        //todo start loading terrain and stuff in background
    }

    /**
     * Setup of input subsystem.
     * Esc - go back from game to menu, no exit from game.
     */
    private void setupInput() {
        if (inputManager != null) {
            if (context.getType() == JmeContext.Type.Display) {
                inputManager.deleteMapping("SIMPLEAPP_Exit");
            }
            inputManager.addMapping(HeroezSettings.INPUT_MAPPING_SHOW_MAIN_MENU, new KeyTrigger(KeyInput.KEY_ESCAPE));
            final InputListener inputListener = new ActionListener() {
                @Override
                public void onAction(final String name, final boolean isPressed, final float tpf) {
                    logger.info("name=" + name + " isPressed=" + isPressed + " tpf=" + tpf);//rem
                    setMenuMode(true);
                    nifty.fromXml("gui/mainMenu.xml", "start");
                }
            };
            inputManager.addListener(inputListener, HeroezSettings.INPUT_MAPPING_SHOW_MAIN_MENU);

//          inputManager.addMapping("SIMPLEAPP_Exit", new KeyTrigger(KeyInput.KEY_ESCAPE));
//          inputManager.addListener(actionListener, "SIMPLEAPP_Exit","SIMPLEAPP_CameraPos", "SIMPLEAPP_Memory");
        }
    }

    /**
     * Init menu.
     *
     * @return
     */
    private void createMenu() {
        niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        nifty = niftyDisplay.getNifty();
        nifty.fromXml("gui/mainMenu.xml", "start",
                new MainMenuScreen(this),
                new CreditsController(this),
                new SinglePlayerScreen(this),
                new MultiplayerScreen(this),
                new OptionsScreen(this));
    }

    /**
     * Starts new scenario.
     */
    public void newScenario() {
        gameView.initTerrain();
        inGame = true;
    }

    /**
     * Is menu visible or not.
     *
     * @param menuMode
     */
    public void setMenuMode(final boolean menuMode) {
        this.menuMode = menuMode;
        if (menuMode) {
            guiViewPort.addProcessor(niftyDisplay);// attach the nifty display to the gui view port as a processor
            flyCam.setEnabled(false);
        } else {
            guiViewPort.removeProcessor(niftyDisplay);
            flyCam.setEnabled(true);
        }
    }

    /**
     * Is game running.
     *
     * @return
     */
    public boolean isInGame() {
        return inGame;
    }
}
