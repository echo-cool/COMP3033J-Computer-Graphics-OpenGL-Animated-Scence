package Scene;

import Scene.Objects.*;
import Scene.base.IDrawListener;
import Scene.base.SceneManager;
import Scene.base.SceneObject;
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
        sceneManager.addSceneObject(new Ground(
                new Point4f(0, 2000, 0, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(2000f, 1f, 2000f, 0),
                textures
        ));
        sceneManager.addSceneObject(new Wall(
                new Point4f(0, 1000f, 2000, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(2000f, 1f, 1000f, 0),
                new Vector4f(1, 0, 0, 90),
                textures
        ));

        sceneManager.addSceneObject(new Wall(
                new Point4f(0, 1000f, -2000, 0),
                new Point4f(0, 0, 0, 0),
                new Vector4f(2000f, 1f, 1000f, 0),
                new Vector4f(1, 0, 0, 90),
                textures
        ));

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

    }

    public static void drawAll(SceneManager sceneManager, Integer delta) {

        Boolean KEY_W = Keyboard.isKeyDown(Keyboard.KEY_W);
        Boolean KEY_A = Keyboard.isKeyDown(Keyboard.KEY_A);
        Boolean KEY_S = Keyboard.isKeyDown(Keyboard.KEY_S);
        Boolean KEY_D = Keyboard.isKeyDown(Keyboard.KEY_D);

        sceneManager.drawAll(new IDrawListener() {
            @Override
            public void beforeEachDraw(SceneObject object) {
                GL11.glPushMatrix();
                if (object instanceof Human) {
                    Human human = (Human) object;
                    Integer speed = 10;
                    float speedX = speed / human.getScale().x;
                    float speedY = speed / human.getScale().y;
                    float speedZ = speed / human.getScale().z;
                    Boolean hasKeyDown = false;
                    Vector4f move = new Vector4f();
                    if (KEY_W) {
                        Vector4f tmp = new Vector4f();
                        tmp = tmp.PlusVector(new Vector4f(0, 0, speedZ, 0));
                        hasKeyDown = true;
                        float angle = Camera.rotation.y - 180;
                        float new_x = (float) (tmp.length() * Math.sin(Math.toRadians(angle)));
                        float new_z = (float) (tmp.length() * Math.cos(Math.toRadians(angle)));
                        tmp.x = -new_x;
                        tmp.z = new_z;
//                        System.out.println(tmp);
                        move = tmp.PlusVector(move);
                        human.setAngle(-angle);
                    }
                    if (KEY_D) {
                        Vector4f tmp = new Vector4f();
                        tmp = tmp.PlusVector(new Vector4f(-speedX, 0, 0, 0));
//                        System.out.println(tmp.length());
                        hasKeyDown = true;
                        float angle = 180 - Camera.rotation.y - 90;
                        float new_x = (float) (tmp.length() * Math.sin(Math.toRadians(angle)));
                        float new_z = (float) (tmp.length() * Math.cos(Math.toRadians(angle)));
                        tmp.x = new_x;
                        tmp.z = new_z;
//                        System.out.println(tmp);
                        move = tmp.PlusVector(move);
                        human.setAngle(angle);
                    }
                    if (KEY_S) {
                        Vector4f tmp = new Vector4f();
                        tmp = tmp.PlusVector(new Vector4f(0, 0, -speedZ, 0));
                        hasKeyDown = true;
                        float angle = Camera.rotation.y - 180;
                        float new_x = (float) (tmp.length() * Math.sin(Math.toRadians(angle)));
                        float new_z = (float) (tmp.length() * Math.cos(Math.toRadians(angle)));
                        tmp.x = new_x;
                        tmp.z = -new_z;
//                        System.out.println(tmp);
                        move = tmp.PlusVector(move);
                        human.setAngle(-angle - 180);
                    }
                    if (KEY_A) {
                        Vector4f tmp = new Vector4f();
                        tmp = tmp.PlusVector(new Vector4f(speedX, 0, 0, 0));
                        hasKeyDown = true;
                        float angle = 180 - Camera.rotation.y + 90;
                        float new_x = (float) (tmp.length() * Math.sin(Math.toRadians(angle)));
                        float new_z = (float) (tmp.length() * Math.cos(Math.toRadians(angle)));
                        tmp.x = new_x;
                        tmp.z = new_z;
                        move = tmp.PlusVector(move);
                        human.setAngle(angle);
                    }
                    if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
                        move = move.PlusVector(new Vector4f(0, speedY, 0, 0));
                    }
                    if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                        human.jump(500);
                    }
                    if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                        move = move.PlusVector(new Vector4f(0, -speedY, 0, 0));
                    }
                    human.move(move);
//                    human.setPosition(new Point4f(posn_x * 3.0f, 0.0f, posn_y * 3.0f, 0));
                    if (hasKeyDown) {
                        human.walk();

                    }
                    {
                        human.stop();
                    }
                }
            }

            @Override
            public void afterEachDraw(SceneObject object) {
                GL11.glPopMatrix();
            }
        });
    }

}
