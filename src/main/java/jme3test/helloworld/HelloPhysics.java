/**
 * Copyright (c) 2009-2010 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *  notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *  may be used to endorse or promote products derived from this software
 *  without specific prior written permission.
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
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.nodes.PhysicsNode;
import com.jme3.font.BitmapText;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.scene.shape.Sphere.TextureMode;
import com.jme3.shadow.BasicShadowRenderer;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;

/**
 * Example 12 - how to give objects physical properties so they bounce and fall.
 * @author double1984, reformatted and javadocced by zathras
 */
public class HelloPhysics extends SimpleApplication {

  public static void main(String args[]) {
    HelloPhysics app = new HelloPhysics();
    app.start();
  }

  /** Activate custom rendering of shadows */
  BasicShadowRenderer bsr;

  /** geometries and collisions shapes for bricks and cannon balls. */
  private BulletAppState bulletAppState;
  private static final Box  brick;
  private static final BoxCollisionShape boxCollisionShape;
  private static final Sphere cannonball;
  private static final SphereCollisionShape cannonballCollisionShape;

  /** brick dimensions */
  private static final float brickLength = 0.48f;
  private static final float brickWidth = 0.24f;
  private static final float brickHeight = 0.12f;

  /** Materials */
  Material wall_mat;
  Material stone_mat;
  Material floor_mat;

  static {
    /** initializing the cannon ball geometry that is reused later */
    cannonball = new Sphere(32, 32, 0.4f, true, false);
    cannonball.setTextureMode(TextureMode.Projected);
    cannonballCollisionShape=new SphereCollisionShape(0.4f);
    /** initializing the brick geometry that is reused later */
    brick = new Box(Vector3f.ZERO, brickLength, brickHeight, brickWidth);
    brick.scaleTextureCoordinates(new Vector2f(1f, .5f));
    boxCollisionShape =
     new BoxCollisionShape(new Vector3f(brickLength, brickHeight, brickWidth));
  }

  @Override
  public void simpleInitApp() {
    /** Set up Physics */
    bulletAppState = new BulletAppState();
    stateManager.attach(bulletAppState);
    /** Set up camera */
    this.cam.setLocation(new Vector3f(0, 6f, 6f));
    cam.lookAt(Vector3f.ZERO, new Vector3f(0, 1, 0));
    cam.setFrustumFar(15);
    /** Add shooting action */
    inputManager.addMapping("shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
    inputManager.addListener(actionListener, "shoot");
    /** Initialize the scene and physics space */
    initMaterials();
    initWall();
    initFloor();
    initCrossHairs();
    bulletAppState.getPhysicsSpace().setAccuracy(0.005f);
    /** Activate custom shadows */
    rootNode.setShadowMode(ShadowMode.Off);
    bsr = new BasicShadowRenderer(assetManager, 256);
    bsr.setDirection(new Vector3f(-1, -1, -1).normalizeLocal());
    viewPort.addProcessor(bsr);
  }

  /**
   * Every time the shoot action is triggered, a new cannon ball is produced.
   * The ball is set up to fly from the camera position in the camera direction.
   */
  private ActionListener actionListener = new ActionListener() {
    public void onAction(String name, boolean keyPressed, float tpf) {
      if (name.equals("shoot") && !keyPressed) {
        makeCannonBall();
      }
    }
  };

  /** Initialize the materials used in this scene. */
  public void initMaterials() {
    wall_mat = new Material(assetManager, "Common/MatDefs/Misc/SimpleTextured.j3md");
    TextureKey key = new TextureKey("Textures/Terrain/BrickWall/BrickWall.jpg");
    key.setGenerateMips(true);
    Texture tex = assetManager.loadTexture(key);
    wall_mat.setTexture("m_ColorMap", tex);

    stone_mat = new Material(assetManager, "Common/MatDefs/Misc/SimpleTextured.j3md");
    TextureKey key2 = new TextureKey("Textures/Terrain/Rock/Rock.PNG");
    key2.setGenerateMips(true);
    Texture tex2 = assetManager.loadTexture(key2);
    stone_mat.setTexture("m_ColorMap", tex2);

    floor_mat = new Material(assetManager, "Common/MatDefs/Misc/SimpleTextured.j3md");
    TextureKey key3 = new TextureKey("Textures/Terrain/Pond/Pond.png");
    key3.setGenerateMips(true);
    Texture tex3 = assetManager.loadTexture(key3);
    tex3.setWrap(WrapMode.Repeat);
    floor_mat.setTexture("m_ColorMap", tex3);
  }

  /** Make a solid floor and add it to the scene. */
  public void initFloor() {
    Box floorBox = new Box(Vector3f.ZERO, 10f, 0.1f, 5f);
    floorBox.scaleTextureCoordinates(new Vector2f(3, 6));
    Geometry floor = new Geometry("floor", floorBox);
    floor.setMaterial(floor_mat);
    floor.setShadowMode(ShadowMode.Receive);
    PhysicsNode floorNode = new PhysicsNode(
     floor,
     new BoxCollisionShape(new Vector3f(10f, 0.1f, 5f)),
     0);
    floorNode.setLocalTranslation(0, -0.1f, 0);
    this.rootNode.attachChild(floorNode);
    bulletAppState.getPhysicsSpace().add(floorNode);
  }

  /** A loop that builds a wall out of individual bricks. */
  public void initWall() {
    float startpt = brickLength / 4;
    float height = 0;
    for (int j = 0; j < 15; j++) {
      for (int i = 0; i < 4; i++) {
        Vector3f vt =
         new Vector3f(i * brickLength * 2 + startpt, brickHeight + height, 0);
        makeBrick(vt);
      }
      startpt = -startpt;
      height += 2 * brickHeight;
    }
  }

  /** This method creates one individual physical brick. */
  public void makeBrick(Vector3f ori) {
    /** create a new brick */
    Geometry box_geo = new Geometry("brick", brick);
    box_geo.setMaterial(wall_mat);
    PhysicsNode brickNode = new PhysicsNode(
     box_geo,      // geometry
     boxCollisionShape, // collision shape
     1.5f);       // mass
    /** position the brick and activate shadows */
    brickNode.setLocalTranslation(ori);
    brickNode.setShadowMode(ShadowMode.CastAndReceive);
    rootNode.attachChild(brickNode);
    bulletAppState.getPhysicsSpace().add(brickNode);
  }

  /** This method creates one individual physical cannon ball.
   * By defaul, the ball is accelerated and flies
   * from the camera position in the camera direction.*/
   public void makeCannonBall() {
    /** create a new cannon ball. */
    Geometry ball_geo = new Geometry("cannon ball", cannonball);
    ball_geo.setMaterial(stone_mat);
    PhysicsNode cannonballNode = new PhysicsNode(
       ball_geo,         // geometry
       cannonballCollisionShape, // collision shape
       1.0f);          // mass
    /** position the cannon ball and activate shadows */
    cannonballNode.setLocalTranslation(cam.getLocation());
    cannonballNode.setShadowMode(ShadowMode.CastAndReceive);
    /** Attach the cannon call to the scene and accelerate it. */
    rootNode.attachChild(cannonballNode);
    bulletAppState.getPhysicsSpace().add(cannonballNode);
    cannonballNode.setLinearVelocity(cam.getDirection().mult(25));
  }

  /** A plus sign used as crosshairs to help the player with aiming.*/
  protected void initCrossHairs() {
    guiNode.detachAllChildren();
    guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
    BitmapText ch = new BitmapText(guiFont, false);
    ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
    ch.setText("+"); // crosshairs
    ch.setLocalTranslation( // center
      settings.getWidth() / 2 - guiFont.getCharSet().getRenderedSize() / 3 * 2,
      settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
    guiNode.attachChild(ch);
  }
}
