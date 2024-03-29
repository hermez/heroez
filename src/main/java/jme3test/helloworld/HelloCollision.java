/*
 * Copyright (c) 2009-2010 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package jme3test.helloworld;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import com.jme3.bullet.nodes.PhysicsCharacterNode;
import com.jme3.bullet.nodes.PhysicsNode;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * Example 9 - How to make walls and floors solid.
 * This version uses Physics and a custom Action Listener.
 * @author normen, with edits by Zathras
 */
public class HelloCollision extends SimpleApplication
  implements ActionListener {

  private Spatial sceneModel;
  private BulletAppState bulletAppState;
  private PhysicsNode landscape;
  private PhysicsCharacterNode player;
  private Vector3f walkDirection = new Vector3f();
  private boolean left = false, right = false, up = false, down = false;

  public static void main(String[] args) {
    HelloCollision app = new HelloCollision();
    app.start();
  }

  public void simpleInitApp() {
    /** Set up Physics */
    bulletAppState = new BulletAppState();
    stateManager.attach(bulletAppState);

    // We re-use the flyby camera for rotation, while positioning is handled by physics
    viewPort.setBackgroundColor(new ColorRGBA(0.7f,0.8f,1f,1f));
    flyCam.setMoveSpeed(100);
    setupKeys();

    // We add a light so we see the scene
    DirectionalLight dl = new DirectionalLight();
    dl.setColor(ColorRGBA.White.clone().multLocal(2)); // bright white light
    dl.setDirection(new Vector3f(2.8f, -2.8f, -2.8f).normalize());
    rootNode.addLight(dl);

    // We load the scene from the zip file and adjust its size.
    assetManager.registerLocator("town.zip", ZipLocator.class.getName());
    sceneModel = assetManager.loadModel("main.scene");
    sceneModel.setLocalScale(2f);

//    // We set up collision detection for the scene by creating a
//    // compound collision shape and a physics node.
//    CompoundCollisionShape sceneShape =
//      CollisionShapeFactory.createMeshCompoundShape((Node) sceneModel);
//    landscape = new PhysicsNode(sceneModel, sceneShape, 0);
    
    // We set up collision detection for the player by creating
    // a capsule collision shape and a physics character node.
    // The physics character node offers extra settings for
    // size, stepheight, jumping, falling, and gravity.
    // We also put the player in its starting position.
    player = new PhysicsCharacterNode(new CapsuleCollisionShape(1.5f, 6f, 1), .05f);
    player.setJumpSpeed(20);
    player.setFallSpeed(30);
    player.setGravity(30);
    player.setLocalTranslation(new Vector3f(0, 10, 0));

    // We attach the scene and the player to the rootnode and the physics space,
    // to make them appear in the game world.
    rootNode.attachChild(landscape);
    rootNode.attachChild(player);
    bulletAppState.getPhysicsSpace().add(landscape);
    bulletAppState.getPhysicsSpace().add(player);
  }

  /** We over-write some navigational key mappings here, so we can
   * add physics-controlled walking and jumping: */
  private void setupKeys() {
    inputManager.addMapping("Lefts",  new KeyTrigger(KeyInput.KEY_A));
    inputManager.addMapping("Rights", new KeyTrigger(KeyInput.KEY_D));
    inputManager.addMapping("Ups",    new KeyTrigger(KeyInput.KEY_W));
    inputManager.addMapping("Downs",  new KeyTrigger(KeyInput.KEY_S));
    inputManager.addMapping("Jumps",  new KeyTrigger(KeyInput.KEY_SPACE));
    inputManager.addListener(this, "Lefts");
    inputManager.addListener(this, "Rights");
    inputManager.addListener(this, "Ups");
    inputManager.addListener(this, "Downs");
    inputManager.addListener(this, "Jumps");
  }

  /** These are our custom actions triggered by key presses.
   * We do not walk yet, we just keep track of the direction the user pressed. */
  public void onAction(String binding, boolean value, float tpf) {
    if (binding.equals("Lefts")) {
      if (value) { left = true; }  else { left = false; }
    } else if (binding.equals("Rights")) {
      if (value) { right = true; } else { right = false; }
    } else if (binding.equals("Ups")) {
      if (value) { up = true; }    else { up = false; }
    } else if (binding.equals("Downs")) {
      if (value) { down = true; }  else { down = false; }
    } else if (binding.equals("Jumps")) {
      player.jump();
    }
  }

  /**
   * This is the main event loop--walking happens here.
   * We check in which direction the player is walking by interpreting
   * the camera direction forward (camDir) and to the side (camLeft).
   * The setWalkDirection() command is what lets a physics-controlled player walk.
   * We also make sure here that the camera moves with player.
   */
  @Override
  public void simpleUpdate(float tpf) {
    Vector3f camDir = cam.getDirection().clone().multLocal(0.6f);
    Vector3f camLeft = cam.getLeft().clone().multLocal(0.4f);
    walkDirection.set(0, 0, 0);
    if (left)  { walkDirection.addLocal(camLeft); }
    if (right) { walkDirection.addLocal(camLeft.negate()); }
    if (up)    { walkDirection.addLocal(camDir); }
    if (down)  { walkDirection.addLocal(camDir.negate()); }
    player.setWalkDirection(walkDirection);
    cam.setLocation(player.getLocalTranslation());
  }
}
