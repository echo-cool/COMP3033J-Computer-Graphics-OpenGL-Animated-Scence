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
    public static float bookRotate_Speed = 0.1f;
    private static ParticleEmitter frontParticleEmitter = new ParticleEmitterBuilder()
            .setLocation(new Vector3f(0, 12900, 2.5f))
            .setEnable3D(true)
            .setInitialVelocity(new Vector3f(0, 0, 0))
            .setGravity(new Vector3f(0, -0.4f, 0))
            .setSpawningRate(20)
            .setParticleLifeTime(270)
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
//    private static ParticleEmitter backParticleEmitter = new ParticleEmitterBuilder()
//            .setLocation(new Vector3f(0,12900,-2.5f))
//            .setEnable3D(true)
//            .setInitialVelocity(new Vector3f(0, 0, 0))
//            .setGravity(new Vector3f(0, -0.4f, 0))
//            .setSpawningRate(10)
//            .setParticleLifeTime(200)
//            .createParticleEmitter();

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


        sceneManager.addSceneObject(new BookTable(
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

        for (int i = -9; i < 10; i++) {
            sceneManager.addSceneObject(new BookTable(
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

            sceneManager.addSceneObject(new BookTable(
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

            sceneManager.addSceneObject(new BookTable(
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

            sceneManager.addSceneObject(new BookTable(
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

//        sceneManager.addSceneObject(new CarportObject(
//                new Point4f(0, 0, 200, 0),
//                new Point4f(0, 0, 0, 0),
//                new Vector4f(500, 500, 500, 0),
//                new Vector4f(1, 0, 0, -90),
//                textures
//        ));

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

        sceneManager.addSceneObject(new TheMostImportantImage(
                new Point4f(0, 5500, -10000, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(10, 4000, 6000, 0),
                new Vector4f(0, 1, 0, 90),
                textures
        ));
        sceneManager.addSceneObject(new VideoTriggerBox(
                new Point4f(0, 100, -2000, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(100, 100, 100, 0),
//                new Vector4f(0, 1, 0, 90),
                textures
        ));

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    Book.yRotation += bookRotate_Speed;
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    BunnyTestObject2.angle += 0.1f;
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (Math.abs(NPC.angle - NPC.angle_target) > 200) {
                        NPC.angle = NPC.angle_target;
                    }

                    if (NPC.angle > NPC.angle_target) {
                        NPC.angle -= 1f;
                    } else if (NPC.angle < NPC.angle_target) {
                        NPC.angle += 1f;
                    }
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

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
                if (Book.hasSomeJumpping)
                    bookParticleEmitter.draw();
//                rightParticleEmitter.draw();
//                leftParticleEmitter.draw();
//                backParticleEmitter.draw();
                GL11.glPopMatrix();
            }
        }, delta);

        frontParticleEmitter.update();
        if (Book.hasSomeJumpping)
            bookParticleEmitter.update();

        bookParticleEmitter.colorVec = new Vector3f(random.nextFloat() + 0.5f, random.nextFloat() + 0.5f, random.nextFloat() + 0.5f);
//        rightParticleEmitter.update();
//        leftParticleEmitter.update();
//        backParticleEmitter.update();
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
