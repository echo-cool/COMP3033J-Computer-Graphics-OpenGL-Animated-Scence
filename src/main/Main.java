package main;

import Scene.Scene;
import Scene.base.SceneManager;
import base.GraphicsObjects.Vector4f;
import base.RenderProgramStatement;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

/**
 * @Author: WangYuyang
 * @Date: 2021/11/4-15:58
 * @Project: Assignment3
 * @Package: PACKAGE_NAME
 * @Description:
 **/
public class Main {
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    private static final boolean DEBUG = false;
    public static Engine engine;
    private static SceneManager sceneManager = new SceneManager();
    private static Camera camera = new Camera();
    private static Boolean FPS_MODE = false;

    public static void main(String[] args) {
        engine = new Engine(WIDTH, HEIGHT);
        engine.init();
        engine.setOrtho(Camera.OrthoNumber);
        engine.setLight();
        engine.enterModelView();
        engine.initTimer();


        camera.setCamera(new Vector4f(
                0, -300, -600, 0
        ));


        Scene.initScene(sceneManager, Engine.getTextures());

        while (!Display.isCloseRequested()) {
            glPushMatrix();
            engine.render(new RenderProgramStatement() {
                @Override
                public void render(int delta) {
                    camera.update();
                    Scene.drawAll(sceneManager, delta);

                }
            });
            glPopMatrix();

            if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                break;
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
                FPS_MODE = FPS_MODE ? false : true;
            }
            if(FPS_MODE){
                camera.setCamera(new Vector4f(
                        0, -500, 0, 0
                ));
            }
            else{
                camera.setCamera(new Vector4f(
                        0, -300, -600, 0
                ));
            }
        }

        engine.close();

    }
}
