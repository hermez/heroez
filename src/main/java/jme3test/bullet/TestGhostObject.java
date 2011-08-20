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

package jme3test.bullet;

import com.jme3.app.Application;
import com.jme3.bullet.BulletAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.nodes.PhysicsGhostNode;
import com.jme3.bullet.nodes.PhysicsNode;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.shape.Box;

/**
 *
 * @author tim8dev [at] gmail [dot com]
 */
public class TestGhostObject extends SimpleApplication {

    private BulletAppState bulletAppState;
    private PhysicsGhostNode ghostNode;

    public static void main(String[] args) {
        Application app = new TestGhostObject();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);

        // Mesh to be shared across several boxes.
        Box boxGeom = new Box(Vector3f.ZERO, 1f, 1f, 1f);
        // CollisionShape to be shared across several boxes.
        CollisionShape shape = new BoxCollisionShape(new Vector3f(1, 1, 1));

        // Add some phyisics boxes higher up, to fall down and be tracked.
        PhysicsNode physicsBox = new PhysicsNode(new BoxCollisionShape(new Vector3f(1, 1, 1)), 1);
        physicsBox.setName("box0");
        physicsBox.setFriction(0.1f);
        physicsBox.setLocalTranslation(new Vector3f(.6f, 4, .5f));
        physicsBox.attachDebugShape(assetManager);
        rootNode.attachChild(physicsBox);
        getPhysicsSpace().add(physicsBox);

        PhysicsNode physicsBox1 = new PhysicsNode(shape, 1);
        physicsBox1.setName("box1");
        physicsBox1.setFriction(0.1f);
        physicsBox1.setLocalTranslation(new Vector3f(0, 40, 0));
        physicsBox1.attachDebugShape(assetManager);
        rootNode.attachChild(physicsBox1);
        getPhysicsSpace().add(physicsBox1);

        PhysicsNode physicsBox2 = new PhysicsNode(new BoxCollisionShape(new Vector3f(1, 1, 1)), 1);
        physicsBox2.setName("box0");
        physicsBox2.setFriction(0.1f);
        physicsBox2.setLocalTranslation(new Vector3f(.5f, 80, -.8f));
        physicsBox2.attachDebugShape(assetManager);
        rootNode.attachChild(physicsBox2);
        getPhysicsSpace().add(physicsBox2);

        // the floor, does not move (mass=0)
        PhysicsNode node = new PhysicsNode(new BoxCollisionShape(new Vector3f(100, 1, 100)), 0);
        node.setName("floor");
        node.setLocalTranslation(new Vector3f(0f, -6, 0f));
        node.attachDebugShape(assetManager);
        rootNode.attachChild(node);
        getPhysicsSpace().add(node);

        initGhostObject();
    }

    private PhysicsSpace getPhysicsSpace(){
        return bulletAppState.getPhysicsSpace();
    }

    private void initGhostObject() {
        Vector3f halfExtents = new Vector3f(3, 4.2f, 1);
        Material mat = new Material(getAssetManager(), "Common/MatDefs/Misc/WireColor.j3md");
        mat.setColor("m_Color", ColorRGBA.Red);
        ghostNode = new PhysicsGhostNode(new BoxCollisionShape(halfExtents));
        ghostNode.attachDebugShape(mat);
        rootNode.attachChild(ghostNode);
        getPhysicsSpace().add(ghostNode);
    }

    @Override
    public void simpleUpdate(float tpf) {
        fpsText.setText("Overlapping objects: " + ghostNode.getOverlappingObjects().toString());
    }
}
