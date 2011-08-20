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

package jme3test.niftygui;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapFont;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import de.lessvoid.nifty.Nifty;

public class TestNiftyGui extends SimpleApplication {

    private Nifty nifty;

    public static void main(String[] args){
        TestNiftyGui app = new TestNiftyGui();
        app.setPauseOnLostFocus(false);
        app.start();
    }

    public void simpleInitApp() {

//        AssetInfo assetInfo = assetManager.locateAsset(new AssetKey<>("aurulent-sans-17.fnt"));
//        System.out.println(assetInfo.toString());

//        BitmapFont bitmapFont = assetManager.loadFont("aurulent-sans-17.fnt");



//        Box b = new Box(Vector3f.ZERO, 1, 1, 1);
//        Geometry geom = new Geometry("Box", b);
//        Material mat = new Material(assetManager, "Common/MatDefs/Misc/SimpleTextured.j3md");
//        mat.setTexture("m_ColorMap", assetManager.loadTexture("Interface/Logo/Monkey.jpg"));
//        geom.setMaterial(mat);
//        rootNode.attachChild(geom);

        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager,
                                                          inputManager,
                                                          audioRenderer,
                                                          guiViewPort);

        nifty = niftyDisplay.getNifty();

        nifty.fromXml("jme3test/niftygui/hellojme.xml", "start");


//        nifty.fromXml(/*"tutorial/tutorial.xml"*/ "jme3test/niftygui/hellojme.xml", "start");
//        nifty.fromXmlWithoutStartScreen("jme3test/niftygui/hellojme.xml");



        // attach the nifty display to the gui view port as a processor
        guiViewPort.addProcessor(niftyDisplay);

        // disable the fly cam
        flyCam.setEnabled(false);
//        flyCam.setDragToRotate(true);
    }

}
