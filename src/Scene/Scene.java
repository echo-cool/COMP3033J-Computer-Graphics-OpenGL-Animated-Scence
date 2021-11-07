package Scene;

import Scene.Objects.*;
import Scene.base.*;
import Scene.skybox.Skybox;
import base.GraphicsObjects.Point4f;
import base.GraphicsObjects.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import java.util.HashMap;
import java.util.Random;

/**
 * @Author: WangYuyang
 * @Date: 2021/11/4-16:28
 * @Project: Assignment3
 * @Package: main
 * @Description:
 **/
public class Scene {
    public static Player player;
    public static BunnyTestObject bunnyTestObject;
    public static NPC center;
    private static ParticleEmitter frontParticleEmitter = new ParticleEmitterBuilder()
            .setLocation(new Vector3f(0, 12900, 2.5f))
            .setEnable3D(true)
            .setInitialVelocity(new Vector3f(0, 0, 0))
            .setGravity(new Vector3f(0, -0.4f, 0))
            .setSpawningRate(20)
            .setParticleLifeTime(300)
            .createParticleEmitter();

    private static ParticleEmitter bookParticleEmitter = new ParticleEmitterBuilder()
            .setLocation(new Vector3f(0, 250, 0.1f))
            .setEnable3D(true)
            .setInitialVelocity(new Vector3f(0, 0f, 0))
            .setGravity(new Vector3f(0, -0.8f, 0))
            .setSpawningRate(10)
            .setParticleLifeTime(20)
            .createParticleEmitter();

    private static Random random = new Random();


//    private static ParticleEmitter rightParticleEmitter = new ParticleEmitterBuilder()
//            .setLocation(new Vector3f(2.5f,12900,0))
//            .setEnable3D(true)
//            .setInitialVelocity(new Vector3f(0, 0, 0))
//            .setGravity(new Vector3f(0, -0.4f, 0))
//            .setSpawningRate(20)
//            .setParticleLifeTime(300)
//            .createParticleEmitter();
//
//    private static ParticleEmitter leftParticleEmitter = new ParticleEmitterBuilder()
//            .setLocation(new Vector3f(-2.5f,12900,0))
//            .setEnable3D(true)
//            .setInitialVelocity(new Vector3f(0, 0, 0))
//            .setGravity(new Vector3f(0, -0.4f, 0))
//            .setSpawningRate(20)
//            .setParticleLifeTime(300)
//            .createParticleEmitter();
//
    private static ParticleEmitter backParticleEmitter = new ParticleEmitterBuilder()
            .setLocation(new Vector3f(0,12900,-2.5f))
            .setEnable3D(true)
            .setInitialVelocity(new Vector3f(0, 0, 0))
            .setGravity(new Vector3f(0, -0.4f, 0))
            .setSpawningRate(10)
            .setParticleLifeTime(200)
            .createParticleEmitter();


    public static void initScene(SceneManager sceneManager, HashMap textures) {

        player = new Player(
                new Point4f(0, 130, 0, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(90, 90, 90, 0),
                textures
        );
        sceneManager.addSceneObject(player);
//        sceneManager.addSceneObject(new Ground(
//                new Point4f(0, 2000, 0, 0),
//                new Point4f(0, 0, 0, 0),
//                new Vector4f(2000f, 1f, 2000f, 0),
//                textures
//        ));
//        sceneManager.addSceneObject(new Wall(
//                new Point4f(0, 1000f, 2000, 0),
//                new Point4f(0, 0, 0, 0),
//                new Vector4f(2000f, 1f, 1000f, 0),
//                new Vector4f(1, 0, 0, 90),
//                textures
//        ));
//
//        sceneManager.addSceneObject(new Wall(
//                new Point4f(0, 1000f, -2000, 0),
//                new Point4f(0, 0, 0, 0),
//                new Vector4f(2000f, 1f, 1000f, 0),
//                new Vector4f(1, 0, 0, 90),
//                textures
//        ));


        sceneManager.addSceneObject(new TNT_Door(
                new Point4f(0, 50f, 2000, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(50f, 50f, 50f, 0),
                textures
        ));
        sceneManager.addSceneObject(new LAVA_Door(
                new Point4f(0, 50f, 2000, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(50f, 50f, 50f, 0),
                textures
        ));


        bunnyTestObject = new BunnyTestObject(
                new Point4f(-2500, 530, 2500, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(90, 90, 90, 0),
                textures
        );
        sceneManager.addSceneObject(bunnyTestObject);

        sceneManager.addSceneObject(new NPC(
                new Point4f(-2500, 130, 2500, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(90, 90, 90, 0),
                textures
        ));
        sceneManager.addSceneObject(new NPC(
                new Point4f(-2700, 130, 2500, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(90, 90, 90, 0),
                textures
        ));
        sceneManager.addSceneObject(new NPC(
                new Point4f(-2300, 130, 2500, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(90, 90, 90, 0),
                textures
        ));
        center = new NPC(
                new Point4f(-2500, 130, 2300, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(90, 90, 90, 0),
                textures
        );
        sceneManager.addSceneObject(center);
        sceneManager.addSceneObject(new NPC(
                new Point4f(-2700, 130, 2300, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(90, 90, 90, 0),
                textures
        ));
        sceneManager.addSceneObject(new NPC(
                new Point4f(-2300, 130, 2300, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(90, 90, 90, 0),
                textures
        ));

        sceneManager.addSceneObject(new NPC(
                new Point4f(-2500, 130, 2700, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(90, 90, 90, 0),
                textures
        ));
        sceneManager.addSceneObject(new NPC(
                new Point4f(-2700, 130, 2700, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(90, 90, 90, 0),
                textures
        ));
        sceneManager.addSceneObject(new NPC(
                new Point4f(-2300, 130, 2700, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(90, 90, 90, 0),
                textures
        ));

        sceneManager.addSceneObject(new BunnyTestObject2(
                new Point4f(5000f, 8500f, 5000f, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(190, 190, 190, 0),
                textures
        ));
        sceneManager.addSceneObject(new BunnyTestObject2(
                new Point4f(5000f, 6500f, 5000f, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(190, 190, 190, 0),
                textures
        ));
        sceneManager.addSceneObject(new BunnyTestObject2(
                new Point4f(5000f, 4500f, 5000f, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(190, 190, 190, 0),
                textures
        ));
        sceneManager.addSceneObject(new BunnyTestObject2(
                new Point4f(5000f, 2500f, 5000f, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(190, 190, 190, 0),
                textures
        ));

        sceneManager.addSceneObject(new BunnyTestObject2(
                new Point4f(5000f, 500f, 5000f, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(190, 190, 190, 0),
                textures
        ));


        sceneManager.addSceneObject(new BunnyTestObject2(
                new Point4f(-5000f, 8500f, 5000f, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(190, 190, 190, 0),
                textures
        ));
        sceneManager.addSceneObject(new BunnyTestObject2(
                new Point4f(-5000f, 6500f, 5000f, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(190, 190, 190, 0),
                textures
        ));
        sceneManager.addSceneObject(new BunnyTestObject2(
                new Point4f(-5000f, 4500f, 5000f, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(190, 190, 190, 0),
                textures
        ));
        sceneManager.addSceneObject(new BunnyTestObject2(
                new Point4f(-5000f, 2500f, 5000f, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(190, 190, 190, 0),
                textures
        ));
        sceneManager.addSceneObject(new BunnyTestObject2(
                new Point4f(-5000f, 500f, 5000f, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(190, 190, 190, 0),
                textures
        ));


        sceneManager.addSceneObject(new BunnyTestObject2(
                new Point4f(5000f, 8500f, -5000f, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(190, 190, 190, 0),
                textures
        ));
        sceneManager.addSceneObject(new BunnyTestObject2(
                new Point4f(5000f, 6500f, -5000f, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(190, 190, 190, 0),
                textures
        ));
        sceneManager.addSceneObject(new BunnyTestObject2(
                new Point4f(5000f, 4500f, -5000f, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(190, 190, 190, 0),
                textures
        ));
        sceneManager.addSceneObject(new BunnyTestObject2(
                new Point4f(5000f, 2500f, -5000f, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(190, 190, 190, 0),
                textures
        ));
        sceneManager.addSceneObject(new BunnyTestObject2(
                new Point4f(5000f, 500f, -5000f, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(190, 190, 190, 0),
                textures
        ));


        sceneManager.addSceneObject(new BunnyTestObject2(
                new Point4f(-5000f, 8500f, -5000f, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(190, 190, 190, 0),
                textures
        ));
        sceneManager.addSceneObject(new BunnyTestObject2(
                new Point4f(-5000f, 6500f, -5000f, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(190, 190, 190, 0),
                textures
        ));
        sceneManager.addSceneObject(new BunnyTestObject2(
                new Point4f(-5000f, 4500f, -5000f, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(190, 190, 190, 0),
                textures
        ));
        sceneManager.addSceneObject(new BunnyTestObject2(
                new Point4f(-5000f, 2500f, -5000f, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(190, 190, 190, 0),
                textures
        ));
        sceneManager.addSceneObject(new BunnyTestObject2(
                new Point4f(-5000f, 500f, -5000f, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(190, 190, 190, 0),
                textures
        ));


        sceneManager.addSceneObject(new Table(
                new Point4f(0, 90f, 500, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(90f, 90f, 90f, 0),
                textures
        ));
        sceneManager.addSceneObject(new Book(
                new Point4f(0, 250f, 500, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(90f, 90f, 90f, 0),
                textures
        ));

        sceneManager.addSceneObject(new BookShelf(
                new Point4f(0, 90f, 500, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(90f, 90f, 90f, 0),
                textures
        ));

        for (int i = -10; i < 10; i++) {
            sceneManager.addSceneObject(new Table(
                    new Point4f(500 * i, 90f, 4500, 0),
                    new Point4f(0, 0, 0, 0),
                    new Vector4f(90f, 90f, 90f, 0),
                    textures
            ));
            sceneManager.addSceneObject(new Book(
                    new Point4f(500 * i, 250f, 4500, 0),
                    new Point4f(0, 0, 0, 0),
                    new Vector4f(90f, 90f, 90f, 0),
                    textures
            ));

            sceneManager.addSceneObject(new Table(
                    new Point4f(500 * i, 90f, -4500, 0),
                    new Point4f(0, 0, 0, 0),
                    new Vector4f(90f, 90f, 90f, 0),
                    textures
            ));
            sceneManager.addSceneObject(new Book(
                    new Point4f(500 * i, 250f, -4500, 0),
                    new Point4f(0, 0, 0, 0),
                    new Vector4f(90f, 90f, 90f, 0),
                    textures
            ));

            sceneManager.addSceneObject(new Table(
                    new Point4f(4500, 90f, 500 * i, 0),
                    new Point4f(0, 0, 0, 0),
                    new Vector4f(90f, 90f, 90f, 0),
                    textures
            ));
            sceneManager.addSceneObject(new Book(
                    new Point4f(4500, 250f, 500 * i, 0),
                    new Point4f(0, 0, 0, 0),
                    new Vector4f(90f, 90f, 90f, 0),
                    textures
            ));

            sceneManager.addSceneObject(new Table(
                    new Point4f(-4500, 90f, 500 * i, 0),
                    new Point4f(0, 0, 0, 0),
                    new Vector4f(90f, 90f, 90f, 0),
                    textures
            ));
            sceneManager.addSceneObject(new Book(
                    new Point4f(-4500, 250f, 500 * i, 0),
                    new Point4f(0, 0, 0, 0),
                    new Vector4f(90f, 90f, 90f, 0),
                    textures
            ));
        }

        sceneManager.addSceneObject(new CarportObject(
                new Point4f(0, 0, 200, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(500, 500, 500, 0),
                new Vector4f(1, 0, 0, -90),
                textures
        ));

        sceneManager.addSceneObject(new Copyleft(
                new Point4f(-5000, 3500, 0, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(100, 2000, 3000, 0),
                new Vector4f(1, 0, 0, 180),
                textures
        ));
        sceneManager.addSceneObject(new Copyleft(
                new Point4f(5000, 3500, 0, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(100, 2000, 3000, 0),
                new Vector4f(1, 0, 0, 180),
                textures
        ));

    }

    public static void initBackground(SceneManager backgroundManager, HashMap textures) {
        backgroundManager.addSceneObject(new Ground(
                new Point4f(0, 0, 0, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(5000f, 1f, 5000f, 0),
                textures
        ));
        backgroundManager.addSceneObject(new Skybox(
                new Point4f(0, 0, 0, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(30000f, 30000f, 30000f, 0),
                textures
        ));

    }

    public static void drawBackground(SceneManager backgroundManager, Integer delta) {
        backgroundManager.drawAll(new IDrawListener() {
            @Override
            public void beforeEachDraw(SceneObject object) {
                GL11.glPushMatrix();
            }

            @Override
            public void afterEachDraw(SceneObject object) {
                frontParticleEmitter.draw();
                bookParticleEmitter.draw();
//                rightParticleEmitter.draw();
//                leftParticleEmitter.draw();
                backParticleEmitter.draw();
                GL11.glPopMatrix();
            }
        }, delta);

        frontParticleEmitter.update();
        bookParticleEmitter.update();

        bookParticleEmitter.colorVec = new Vector3f(random.nextFloat() + 0.5f, random.nextFloat() + 0.5f, random.nextFloat() + 0.5f);
//        rightParticleEmitter.update();
//        leftParticleEmitter.update();
        backParticleEmitter.update();
    }

    public static void drawScene(SceneManager sceneManager, Integer delta) {
        sceneManager.drawAll(new IDrawListener() {
            @Override
            public void beforeEachDraw(SceneObject object) {
                GL11.glPushMatrix();
            }

            @Override
            public void afterEachDraw(SceneObject object) {
                GL11.glPopMatrix();
            }
        }, delta);
    }

}
