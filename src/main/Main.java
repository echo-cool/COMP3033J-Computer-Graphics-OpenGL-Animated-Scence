package main;

import Scene.Scene;
import Scene.base.SceneManager;
import base.GraphicsObjects.Vector4f;
import base.RenderProgramStatement;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glPointSize;

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
    public static Engine engine;
    public static SceneManager sceneManager = new SceneManager();
    public static SceneManager backgroundManager = new SceneManager();
    public static Camera camera = new Camera();
    private static boolean isBigScreen = false;
    private static Boolean setUpFinish = true;
    private static Boolean FPS_MODE = false;
    private static Boolean GOD_MODE = false;

    public static void main(String[] args) {
        engine = new Engine(WIDTH, HEIGHT);
        glPointSize(8);
        engine.init();
        engine.setOrtho(Camera.OrthoNumber);
        engine.enterModelView();
        engine.initTimer();


//        camera.setCamera(new Vector4f(
//                0, -300, -600, 0
//        ));

        Scene.initScene(sceneManager, Engine.getTextures());
        Scene.initBackground(backgroundManager, Engine.getTextures());
        Runnable cameraRunnable = new Runnable() {
            @Override
            public void run() {
                Vector4f cam_anmi = new Vector4f(0, -1800, 5600, 0);
                int steps = (int) Math.max(Math.abs(cam_anmi.y), Math.abs(cam_anmi.z));
                for(int i = 0; i < steps; i++){
                    cam_anmi = new Vector4f(0, -1800, 3600, 0);
                    float ration = (float)i/steps;
                    float y = (float) (Math.cos(ration * Math.PI/2) * cam_anmi.y);
                    float z = (float) (Math.cos(ration * Math.PI/2) * cam_anmi.z);
                    cam_anmi.y = y;
                    cam_anmi.z = z;

                    Camera.setPosition(cam_anmi);
                    Camera.rotation.x = (float) (-Math.sin(ration * Math.PI) * 10);
                    Camera.rotation.y = (float) ((float) 0 + (-Math.sin(ration * Math.PI/2) * 180));
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Runnable light_anmi = new Runnable() {
            @Override
            public void run() {
                float upper = 10000f;
                float lower = -10000f;
                float current = 10000f;
                while (true){
                    while(current > lower){
                        current -= 2;
                        Engine.lightPosition = BufferUtils.createFloatBuffer(4);
                        Engine.lightPosition.put(current).put(10000f).put(5000).put(0).flip();
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(current);
                    }
                    while (current < upper){
                        current += 2;
                        Engine.lightPosition = BufferUtils.createFloatBuffer(4);
                        Engine.lightPosition.put(current).put(10000f).put(5000).put(0).flip();
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(current);
                    }

                }

            }
        };
        new Thread(cameraRunnable).start();
//        new Thread(light_anmi).start();

        while (!Display.isCloseRequested()) {
            glLoadIdentity();
            camera.updatePosition();
            engine.setLight();
            engine.render(new RenderProgramStatement() {
                @Override
                public void renderScene(int delta) {
                    Scene.drawScene(sceneManager, delta);
                }

                @Override
                public void renderBackground(int delta) {
                    Scene.drawBackground(backgroundManager, delta);
                }
            });

            if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                break;
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
                FPS_MODE = FPS_MODE ? false : true;
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_G)) {
                GOD_MODE = GOD_MODE ? false : true;
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_EQUALS)) {
                isBigScreen = isBigScreen ? false : true;
                setUpFinish = false;

            }
            if (FPS_MODE) {
                camera.setCamera(new Vector4f(
                        0, -300, 0, 0
                ));
            } else {
                camera.setCamera(new Vector4f(
                        0, -300, -600, 0
                ));
            }
            if (GOD_MODE) {
                camera.setCamera(new Vector4f(
                        0, -2300, -600, 0
                ));
                Camera.maxLookDown = -360;
                Camera.maxLookUp = 360;

            }
            if(Keyboard.isKeyDown(Keyboard.KEY_0)){
                new Thread(cameraRunnable).start();
            }
            if (isBigScreen) {
                if (!setUpFinish) {
                    try {
                        Display.setDisplayMode(new DisplayMode(2400, 1200));
                        setUpFinish = true;
                    } catch (LWJGLException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                if (!setUpFinish) {
                    try {
                        Display.setDisplayMode(new DisplayMode(1600, 800));
                        setUpFinish = true;
                    } catch (LWJGLException e) {
                        e.printStackTrace();
                    }
                }
            }


        }

        engine.close();

    }
}
