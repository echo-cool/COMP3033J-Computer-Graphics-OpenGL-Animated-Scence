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
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;

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

    private static ArrayList<Vector4f> key_positions = new ArrayList<>();
    private static ArrayList<Vector4f> key_rotations = new ArrayList<>();

    public static void main(String[] args) {
        engine = new Engine(WIDTH, HEIGHT);
        glPointSize(8);
        engine.init();
        engine.setOrtho(Camera.OrthoNumber);
        engine.enterModelView();
        engine.initTimer();


//        camera.setCamera(new Vector4f(
//                0, 2800, 0, 0
//        ));
        Display.setTitle("CG Project 1 Loading....... Scene: Init");
        Scene.initScene(sceneManager, Engine.getTextures());
        Scene.initBackground(backgroundManager, Engine.getTextures());
        Display.setTitle("CG Project 1 Loading....... Scene: Ok.");
        key_positions.add(new Vector4f(0, 5000, 0, 2000));
        key_rotations.add(new Vector4f(0, 0, 0, 2000));

        key_positions.add(new Vector4f(-1000, 2000, 0, 1000));
        key_rotations.add(new Vector4f(-10, -90, 0, 1000));

        key_positions.add(new Vector4f(1500, 2000, 0, 2000));
        key_rotations.add(new Vector4f(-15, -90, 0, 2000));

        key_positions.add(new Vector4f(0, 5000, 0, 1000));
        key_rotations.add(new Vector4f(-20, -180, 0, 1000));

        key_positions.add(new Vector4f(0, 10000, -8000, 2000));
        key_rotations.add(new Vector4f(0, -180, 0, 2000));

        key_positions.add(new Vector4f(0, 5000, 8000, 1000));
        key_rotations.add(new Vector4f(-20, -180, 0, 1000));

        key_positions.add(new Vector4f(0, 1000, 8000, 1000));
        key_rotations.add(new Vector4f(20, -180, 0, 1000));

        key_positions.add(new Vector4f(0, 100, 5000, 2000));
        key_rotations.add(new Vector4f(30, -180, 0, 2000));

        key_positions.add(new Vector4f(-3000, 100, 5000, 1000));
        key_rotations.add(new Vector4f(30, -180, 0, 1000));

        key_positions.add(new Vector4f(-3000, 1000, 3000, 2000));
        key_rotations.add(new Vector4f(40, -180, 0, 2000));

        key_positions.add(new Vector4f(-3000, 100, 5000, 2000));
        key_rotations.add(new Vector4f(0, -180, 0, 2000));


        key_positions.add(new Vector4f(0, 500, -1000, 4000));
        key_rotations.add(new Vector4f(20, 0, 0, 4000));

        key_positions.add(new Vector4f(0, 0, 0, 3000));
        key_rotations.add(new Vector4f(10, 180, 0, 3000));

        Runnable cameraRunnable = new Runnable() {
            @Override
            public void run() {
                Camera.position = new Vector4f(
                        0, 12000, 0, 0
                );
                Camera.rotation = new Vector3f(10,0,0);
                for (int i = 0; i < key_positions.size(); i++) {
                    Vector4f key_position = key_positions.get(i);
                    Vector4f key_rotation = key_rotations.get(i);
                    Vector4f origin_position = new Vector4f(Camera.position.x, Camera.position.y, Camera.position.z, Camera.position.a);
                    Vector3f origin_rotation = new Vector3f(Camera.rotation.x, Camera.rotation.y, Camera.rotation.z);
                    Vector4f Current_position = Camera.position;
                    Vector3f Current_rotation = Camera.rotation;
                    System.out.println("Current_position: " + Current_position);
                    System.out.println("Current_rotation: " + Current_rotation);
                    System.out.println("key_position: " + key_position);
                    System.out.println("key_rotation: " + key_rotation);
                    float position_x_distance = key_position.x - Current_position.x;
                    float position_y_distance = key_position.y - Current_position.y;
                    float position_z_distance = key_position.z - Current_position.z;
                    float rotation_x_distance = key_rotation.x - Current_rotation.x;
                    float rotation_y_distance = key_rotation.y - Current_rotation.y;
                    float rotation_z_distance = key_rotation.z - Current_rotation.z;
                    float position_x_distance_step = position_x_distance / key_position.a;
                    float position_y_distance_step = position_y_distance / key_position.a;
                    float position_z_distance_step = position_z_distance / key_position.a;
                    float rotation_x_distance_step = rotation_x_distance / key_position.a;
                    float rotation_y_distance_step = rotation_y_distance / key_position.a;
                    float rotation_z_distance_step = rotation_z_distance / key_position.a;
                    System.out.println("position_x_distance_step: " + position_x_distance_step);
                    System.out.println("position_y_distance_step: " + position_y_distance_step);
                    System.out.println("position_z_distance_step: " + position_z_distance_step);
                    System.out.println("rotation_x_distance_step: " + rotation_x_distance_step);
                    System.out.println("rotation_y_distance_step: " + rotation_y_distance_step);
                    System.out.println("rotation_z_distance_step: " + rotation_z_distance_step);
                    float count = 0f;
                    while (count < key_position.a) {

                        Current_position.x = (float) (origin_position.x + position_x_distance_step * count * Math.sin(count/key_position.a * Math.PI/2));
                        Current_position.y = (float) (origin_position.y + position_y_distance_step * count * Math.sin(count/key_position.a * Math.PI/2));
                        Current_position.z = (float) (origin_position.z + position_z_distance_step * count * Math.sin(count/key_position.a * Math.PI/2));

                        Current_rotation.x = (float) (origin_rotation.x + rotation_x_distance_step * count * Math.sin(count/key_position.a * Math.PI/2));
                        Current_rotation.y = (float) (origin_rotation.y + rotation_y_distance_step * count * Math.sin(count/key_position.a * Math.PI/2));
                        Current_rotation.z = (float) (origin_rotation.z + rotation_z_distance_step * count * Math.sin(count/key_position.a * Math.PI/2));
//                        Current_rotation.x = (float) (rotation_x_distance_step * count * Math.sin(count/key_position.a * Math.PI/2));
//                        Current_rotation.y = (float) (rotation_y_distance_step * count * Math.sin(count/key_position.a * Math.PI/2));
//                        Current_rotation.z = (float) (rotation_z_distance_step * count * Math.sin(count/key_position.a * Math.PI/2));
                        count += 1;
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(Current_position);
                    System.out.println(Current_rotation);
                    Current_position = key_position;
                    Current_rotation = new Vector3f(key_rotation.x, key_rotation.y, key_rotation.z);
                    System.out.println(Current_position);
                    System.out.println(Current_rotation);
                    System.out.println("-------------------------------------------------");
                }
                Camera.loading_finished = true;
            }
        };

//        Vector4f cam_anmi = new Vector4f(0, 1800, 0, 0);
//        Camera.setPosition(cam_anmi);
//        Camera.rotation.x = 0;
//        Camera.rotation.y = -180;
//        Runnable cameraRunnable = new Runnable() {
//            @Override
//            public void run() {
//                Vector4f cam_anmi = new Vector4f(0, -1800, 5600, 0);
//                int steps = (int) Math.max(Math.abs(cam_anmi.y), Math.abs(cam_anmi.z));
//                for(int i = 0; i < steps; i++){
//                    cam_anmi = new Vector4f(0, -1800, 5600, 0);
//                    float ration = (float)i/steps;
//                    float y = (float) (Math.cos(ration * Math.PI/2) * cam_anmi.y);
//                    float z = (float) (Math.cos(ration * Math.PI/2) * cam_anmi.z);
//                    cam_anmi.y = y;
//                    cam_anmi.z = z;
//
//                    Camera.setPosition(cam_anmi);
//                    Camera.rotation.x = (float) (-Math.sin(ration * Math.PI) * 10);
//                    Camera.rotation.y = (float) ((float) 0 + (-Math.sin(ration * Math.PI/2) * 180));
//                    try {
//                        Thread.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                Camera.loading_finished = true;
//            }
//        };
        Runnable light_anmi = new Runnable() {
            @Override
            public void run() {
                float upper = 10000f;
                float lower = -10000f;
                float current = 10000f;
                while (true) {
                    while (current > lower) {
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
                    while (current < upper) {
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
