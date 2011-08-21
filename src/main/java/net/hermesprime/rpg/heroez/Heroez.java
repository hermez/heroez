package net.hermesprime.rpg.heroez;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeContext;
import de.lessvoid.nifty.Nifty;
import net.hermesprime.rpg.heroez.gui.camera.ExtendedFlyByCamera;
import net.hermesprime.rpg.heroez.gui.controller.*;
import net.hermesprime.rpg.heroez.gui.listener.GuiListener;
import net.hermesprime.rpg.heroez.input.HeroezInputSettings;
import net.hermesprime.rpg.heroez.logic.GameLogic;
import net.hermesprime.rpg.heroez.model.map.Map;
import net.hermesprime.rpg.heroez.util.HeroezSettings;
import net.hermesprime.rpg.heroez.view.GameView;
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
    private GameLogic gameLogic;
    private boolean devel = true;

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

//    @Override
//    public void initialize() {
//        super.initialize();
//
//        guiNode.setQueueBucket(RenderQueue.Bucket.Gui);
//        guiNode.setCullHint(Spatial.CullHint.Never);
//        loadFPSText();
//        loadStatsView();
//        viewPort.attachScene(rootNode);
//        guiViewPort.attachScene(guiNode);
//
//        setupInput();
//
//        // call user code
//        simpleInitApp();
//    }

    @Override
    public void simpleInitApp() {
        createMenu();

        setupInput();

        createGameView();
        Mouse.setGrabbed(false);//lwjgl
        if (devel) {
            newScenario();
        } else {
            setMenuMode(true);
        }
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
        final GuiListener guiListener = new GuiListener(this);

        //old one
        flyCam.setEnabled(false);
        //new one
        flyCam = new ExtendedFlyByCamera(cam);
        flyCam.setMoveSpeed(3f);
        flyCam.registerWithInput(inputManager);
        flyCam.setDragToRotate(true);

        if (context.getType() == JmeContext.Type.Display) {
            HeroezInputSettings.setupInputManager(inputManager, guiListener);
        }

        //cam.setFrustumPerspective(45f, (float)cam.getWidth() / cam.getHeight(), 1f, 1000f);
        cam.setLocation(new Vector3f(1f, 5f, 9f));
        cam.lookAt(new Vector3f(0f, 0f, 0f), Vector3f.UNIT_Y);
    }

    /**
     * Init menu.
     *
     * @return
     */
    private void createMenu() {
        niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        nifty = niftyDisplay.getNifty();
        nifty.registerScreenController(
                new MainMenuScreen(this),
                new CreditsController(this),
                new SinglePlayerScreen(this),
                new MultiplayerScreen(this),
                new OptionsScreen(this));
    }

    private void showMainMenu() {
        nifty.fromXml("gui/mainMenu.xml", "start");
    }

    private void createGameLogic() {
        gameLogic = new GameLogic();
        gameLogic.setMap(new Map(5, 5));
    }

    /**
     * Starts new scenario.
     */
    public void newScenario() {
        createGameLogic();
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
            nifty.fromXml("gui/mainMenu.xml", "start");
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
