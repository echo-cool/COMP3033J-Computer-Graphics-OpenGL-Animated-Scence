package Scene;

import Scene.Objects.*;
import Scene.base.IDrawListener;
import Scene.base.SceneManager;
import Scene.base.SceneObject;
import Scene.skybox.Skybox;
import base.GraphicsObjects.Point4f;
import base.GraphicsObjects.Vector4f;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;

/**
 * @Author: WangYuyang
 * @Date: 2021/11/4-16:28
 * @Project: Assignment3
 * @Package: main
 * @Description:
 **/
public class Scene {
    public static Human human;
    public static void initScene(SceneManager sceneManager, HashMap textures) {
        human = new Human(
                new Point4f(0, 130, 0, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(90, 90, 90, 0),
                textures
        );
        sceneManager.addSceneObject(human);
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
                new Point4f(0, 50f, 200, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(50f, 50f, 50f, 0),
                textures
        ));

        sceneManager.addSceneObject(new LAVA_Door(
                new Point4f(0, 50f, 200, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(50f, 50f, 50f, 0),
                textures
        ));


//        sceneManager.addSceneObject(new Cube(
//                new Point4f(300, 200, 600, 0),
//                new Point4f(0, 0, 0, 0),
//                new Vector4f(50, 50, 50, 0),
//                textures
//        ));
        sceneManager.addSceneObject(new BunnyTestObject(
                new Point4f(500, 100, 600, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(50, 50, 50, 0),
                textures
        ));

    }

    public static void initBackground(SceneManager backgroundManager, HashMap textures) {

        backgroundManager.addSceneObject(new Ground(
                new Point4f(0, 0, 0, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(3000f, 1f, 3000f, 0),
                textures
        ));
        backgroundManager.addSceneObject(new Skybox(
                new Point4f(0, 0, 0, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(30000f, 30000f, 30000f, 0),
                textures
        ));

    }

    public static void drawBackground(SceneManager backgroundManager, Integer delta){
        backgroundManager.drawAll(new IDrawListener() {
            @Override
            public void beforeEachDraw(SceneObject object) {
                GL11.glPushMatrix();
            }

            @Override
            public void afterEachDraw(SceneObject object) {
                GL11.glPopMatrix();
            }
        });
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
        });
    }

}
