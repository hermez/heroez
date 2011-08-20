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

package jme3test.texture;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.ModelKey;
import com.jme3.asset.TextureKey;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.post.HDRRenderer;
import com.jme3.renderer.Caps;
import com.jme3.scene.Geometry;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;

public class TestCubeMap extends SimpleApplication {

    private Texture envMap;
    private HDRRenderer hdrRender;

    public static void main(String[] args){
        TestCubeMap app = new TestCubeMap();
        app.start();
    }

    public void loadEnvMap(){
        TextureKey key;
        if (renderer.getCaps().contains(Caps.FloatTexture)){
            key = new TextureKey("Textures/Sky/St Peters/StPeters.hdr", true);
        }else{
            key = new TextureKey("Textures/Sky/St Peters/StPeters.jpg", true);
        }
        key.setGenerateMips(true);
        key.setAsCube(false);
        envMap = assetManager.loadTexture(key);

//        Image negx = assetManager.loadTexture("Textures/Sky/CubeSky/negx.hdr").getImage();
//        Image posx = assetManager.loadTexture("Textures/Sky/CubeSky/posx.hdr").getImage();
//        Image negy = assetManager.loadTexture("Textures/Sky/CubeSky/negy.hdr").getImage();
//        Image posy = assetManager.loadTexture("Textures/Sky/CubeSky/posy.hdr").getImage();
//        Image negz = assetManager.loadTexture("Textures/Sky/CubeSky/negz.hdr").getImage();
//        Image posz = assetManager.loadTexture("Textures/Sky/CubeSky/posz.hdr").getImage();
//
//        Image cube = new Image(negx.getFormat(), negx.getWidth(), negy.getHeight(), null);
//        cube.addData(negx.getData(0));
//        cube.addData(posx.getData(0));
//        cube.addData(negy.getData(0));
//        cube.addData(posy.getData(0));
//        cube.addData(negz.getData(0));
//        cube.addData(posz.getData(0));
//
//        envMap = new TextureCubeMap(cube);
    }

    public Geometry createReflectiveTeapot(){
        Geometry g = (Geometry) assetManager.loadAsset(new ModelKey("Models/Teapot/Teapot.obj"));
        g.setLocalScale(5);

        Material mat = new Material(assetManager, "Common/MatDefs/Light/Reflection.j3md");
        mat.setTexture("m_Texture", envMap);
        mat.setBoolean("m_SphereMap", true);
        g.setMaterial(mat);

        return g;
    }

    public void initHDR(){
        hdrRender = new HDRRenderer(assetManager, renderer);

        hdrRender.setSamples(settings.getSamples());
        hdrRender.setExposure(0.80f);
        hdrRender.setWhiteLevel(10);
        hdrRender.setThrottle(0.25f);
        hdrRender.setMaxIterations(30);
        hdrRender.setUseFastFilter(false);

        viewPort.addProcessor(hdrRender);
    }

    public void setupSkyBox(){
        rootNode.attachChild(SkyFactory.createSky(assetManager, envMap, new Vector3f(-1,-1,-1), true));
    }

    @Override
    public void simpleInitApp() {
        initHDR();
        loadEnvMap();
        setupSkyBox();

        rootNode.attachChild(createReflectiveTeapot());
    }

}
