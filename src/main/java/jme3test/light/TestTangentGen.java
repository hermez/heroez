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

package jme3test.light;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import com.jme3.scene.shape.Sphere;
import com.jme3.util.TangentBinormalGenerator;


public class TestTangentGen extends SimpleApplication {

    float angle;
    PointLight pl;
    Geometry lightMdl;

    public static void main(String[] args){
        TestTangentGen app = new TestTangentGen();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        Sphere sphereMesh = new Sphere(32, 32, 1);
        sphereMesh.setTextureMode(Sphere.TextureMode.Projected);
        sphereMesh.updateGeometry(32, 32, 1, false, false);
        addMesh("Sphere", sphereMesh, new Vector3f(-1, 0, 0));

        Quad quadMesh = new Quad(1, 1);
        quadMesh.updateGeometry(1, 1);
        addMesh("Quad", quadMesh, new Vector3f(1, 0, 0));
        
        DirectionalLight dl = new DirectionalLight();
        dl.setDirection(new Vector3f(1, -1, -1).normalizeLocal());
        dl.setColor(ColorRGBA.White);
        rootNode.addLight(dl);
    }

    private void addMesh(String name, Mesh mesh, Vector3f translation) {
        TangentBinormalGenerator.generate(mesh);

        Geometry testGeom = new Geometry(name, mesh);
        Material mat = assetManager.loadMaterial("Textures/BumpMapTest/Tangent.j3m");
        testGeom.setMaterial(mat);
        testGeom.getLocalTranslation().set(translation);
        rootNode.attachChild(testGeom);

        Geometry debug = new Geometry(
                "Debug " + name,
                TangentBinormalGenerator.genTbnLines(mesh, 0.08f)
        );
        Material debugMat = assetManager.loadMaterial("Common/Materials/VertexColor.j3m");
        debug.setMaterial(debugMat);
        debug.setCullHint(Spatial.CullHint.Never);
        debug.getLocalTranslation().set(translation);
        rootNode.attachChild(debug);
    }

    @Override
    public void simpleUpdate(float tpf){
    }

}
