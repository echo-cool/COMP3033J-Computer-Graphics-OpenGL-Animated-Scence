package main;

import Scene.Scene;
import Scene.base.SceneManager;
import base.GraphicsObjects.Vector4f;
import base.RenderProgramStatement;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.util.glu.GLU.gluPerspective;

/**
 * @Author: WangYuyang
 * @Date: 2021/11/4-15:58
 * @Project: Assignment3
 * @Package: PACKAGE_NAME
 * @Description:
 **/
public class Main {
    public static final int WIDTH = 1600;
    public static final int HEIGHT = 800;
    private static final boolean DEBUG = false;
    private static final boolean resizable = true;
    public static Engine engine;
    private static SceneManager sceneManager = new SceneManager();
    private static Camera camera = new Camera();
    private static Boolean FPS_MODE = false;

    public static void main(String[] args) {
        engine = new Engine(WIDTH, HEIGHT);
        engine.init();
        engine.setOrtho(Camera.OrthoNumber);



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
                    engine.setLight();
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
                        0, -300, 0, 0
                ));
            }
            else{
                camera.setCamera(new Vector4f(
                        0, -300, -600, 0
                ));
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_EQUALS)) {
                System.out.println("KEY_EQUALS");
                try {
                    Display.setFullscreen(!Display.isFullscreen());
                    if (!Display.isFullscreen()) {
                        Display.setResizable(resizable);
                        Display.setDisplayMode(new DisplayMode(800, 600));
                        glViewport(0, 0, Display.getWidth(), Display.getHeight());
                        glMatrixMode(GL_PROJECTION);
                        glLoadIdentity();
                        gluPerspective((float) 60, Display.getWidth() / Display.getHeight(), 100f, 17000);
                        glMatrixMode(GL_MODELVIEW);
                        glLoadIdentity();
                    } else {
                        glViewport(0, 0, Display.getWidth(), Display.getHeight());
                        glMatrixMode(GL_PROJECTION);
                        glLoadIdentity();
                        gluPerspective((float) 60, Display.getWidth() / Display.getHeight(), 100f, 17000);
                        glMatrixMode(GL_MODELVIEW);
                        glLoadIdentity();
                    }
                } catch (LWJGLException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("ERROR");
                    engine.close();
                }
            }

        }

        engine.close();

    }
}
