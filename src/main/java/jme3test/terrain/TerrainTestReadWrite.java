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

package jme3test.terrain;

import com.jme3.app.SimpleApplication;
import com.jme3.bounding.BoundingBox;
import com.jme3.export.binary.BinaryExporter;
import com.jme3.export.binary.BinaryImporter;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.HillHeightMap;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Saves and loads terrain.
 *
 * @author Brent Owens
 */
public class TerrainTestReadWrite extends SimpleApplication {

	private TerrainQuad terrain;
    protected BitmapText hintText;

	public static void main(String[] args) {
		TerrainTestReadWrite app = new TerrainTestReadWrite();
		app.start();
	}

    @Override
	public void initialize() {
		super.initialize();

		loadHintText();
	}

	@Override
	public void simpleInitApp() {
		createControls();
		createMap();

	}

	private void createMap() {
		AbstractHeightMap heightmap = null;
		try {
			heightmap = new HillHeightMap(513, 3000, 30, 60, (byte) 4);
		} catch (Exception ex) {
			Logger.getLogger(TerrainTestReadWrite.class.getName()).log(Level.SEVERE, null, ex);
		}
		terrain = new TerrainQuad("terrain", 65, 513, heightmap.getHeightMap());
		getRootNode().attachChild(terrain);

		// TERRAIN TEXTURE material
		Material matRock = new Material(assetManager, "Common/MatDefs/Terrain/Terrain.j3md");

		// ALPHA map (for splat textures)
		matRock.setTexture("m_Alpha", assetManager.loadTexture("Textures/Terrain/splat/alphamap.png"));

		// HEIGHTMAP image (for the terrain heightmap)
		Texture heightMapImage = assetManager.loadTexture("Textures/Terrain/splat/mountains512.png");

		// GRASS texture
		Texture grass = assetManager.loadTexture("Textures/Terrain/splat/grass.jpg");
		grass.setWrap(WrapMode.Repeat);
		matRock.setTexture("m_Tex1", grass);
		matRock.setFloat("m_Tex1Scale", 64f);

		// DIRT texture
		Texture dirt = assetManager.loadTexture("Textures/Terrain/splat/dirt.jpg");
		dirt.setWrap(WrapMode.Repeat);
		matRock.setTexture("m_Tex2", dirt);
		matRock.setFloat("m_Tex2Scale", 32f);

		// ROCK texture
		Texture rock = assetManager.loadTexture("Textures/Terrain/splat/road.jpg");
		rock.setWrap(WrapMode.Repeat);
		matRock.setTexture("m_Tex3", rock);
		matRock.setFloat("m_Tex3Scale", 128f);

        // create the terrain as normal, and give it a control for LOD management
		List<Camera> cameras = new ArrayList<Camera>();
		cameras.add(getCamera());
		TerrainLodControl control = new TerrainLodControl(terrain, cameras);
		terrain.addControl(control);
		terrain.setMaterial(matRock);
		terrain.setModelBound(new BoundingBox());
		terrain.updateModelBound();
		terrain.setLocalScale(1f, 0.25f, 1f);
	}

    /**
     * Create the save and load actions and add them to the input listener
     */
	private void createControls() {
		flyCam.setMoveSpeed(50);
		getCamera().setLocation(new Vector3f(0,100,0));

        inputManager.addMapping("save", new KeyTrigger(KeyInput.KEY_T));
		inputManager.addListener(saveActionListener, "save");

        inputManager.addMapping("load", new KeyTrigger(KeyInput.KEY_Y));
		inputManager.addListener(loadActionListener, "load");
	}

    public void loadHintText() {
		hintText = new BitmapText(guiFont, false);
		hintText.setSize(guiFont.getCharSet().getRenderedSize());
		hintText.setLocalTranslation(0, getCamera().getHeight(), 0);
		hintText.setText("Hit T to save, and Y to load");
		guiNode.attachChild(hintText);
	}

    private ActionListener saveActionListener = new ActionListener() {

		public void onAction(String name, boolean pressed, float tpf) {
			if (name.equals("save") && !pressed) {

                FileOutputStream fos = null;
                try {
                    long start = System.currentTimeMillis();
                    fos = new FileOutputStream(new File("terrainsave.jme"));

                    // we just use the exporter and pass in the terrain
                    BinaryExporter.getInstance().save(terrain, new BufferedOutputStream(fos));

                    fos.flush();
                    float duration = (System.currentTimeMillis()-start)/1000.0f;
                    System.out.println("Save took "+duration+" seconds");
                } catch (IOException ex) {
                    Logger.getLogger(TerrainTestReadWrite.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                        Logger.getLogger(TerrainTestReadWrite.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
			}
		}
	};

    private ActionListener loadActionListener = new ActionListener() {

		public void onAction(String name, boolean pressed, float tpf) {
			if (name.equals("load") && !pressed) {
                FileInputStream fis = null;
                try {
                    long start = System.currentTimeMillis();
                    // remove the existing terrain and detach it from the root node.
                    terrain.removeFromParent();
                    terrain.removeControl(TerrainLodControl.class);
                    terrain = null;

                    // import the saved terrain, and attach it back to the root node
                    fis = new FileInputStream(new File("terrainsave.jme"));
                    BinaryImporter imp = BinaryImporter.getInstance();
                    imp.setAssetManager(assetManager);
                    terrain = (TerrainQuad) imp.load(new BufferedInputStream(fis));
                    rootNode.attachChild(terrain);

                    float duration = (System.currentTimeMillis()-start)/1000.0f;
                    System.out.println("Load took "+duration+" seconds");

                    // now we have to add back the cameras to the LOD control, since we didn't want to duplicate them on save
                    List<Camera> cameras = new ArrayList<Camera>();
                    cameras.add(getCamera());
                    TerrainLodControl lodControl = terrain.getControl(TerrainLodControl.class);
                    if (lodControl != null && !(terrain.getParent() instanceof TerrainQuad))
                        lodControl.setCameras(cameras);

                } catch (IOException ex) {
                    Logger.getLogger(TerrainTestReadWrite.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        if (fis != null)
                            fis.close();
                    } catch (IOException ex) {
                        Logger.getLogger(TerrainTestReadWrite.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
			}
		}
	};
}


