package Scene;

import Scene.Objects.*;
import Scene.base.IDrawListener;
import Scene.base.SceneManager;
import Scene.base.SceneObject;
import Scene.skybox.Skybox;
import base.GraphicsObjects.Point4f;
import base.GraphicsObjects.Vector4f;
import main.Camera;
import main.Util;
import org.lwjgl.input.Keyboard;
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


    public static void initScene(SceneManager sceneManager, HashMap textures) {

        Human human = new Human(
                new Point4f(0, 130, 0, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(90, 90, 90, 0),
                textures
        );
        sceneManager.addSceneObject(human);
        sceneManager.addSceneObject(new Ground(
                new Point4f(0, 0, 0, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(2000f, 1f, 2000f, 0),
                textures
        ));
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

        sceneManager.addSceneObject(new Skybox(
                new Point4f(0, 0, 0, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(10000f, 10000f, 10000f, 0),
                textures
        ));

    }

    public static void drawAll(SceneManager sceneManager, Integer delta) {



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
