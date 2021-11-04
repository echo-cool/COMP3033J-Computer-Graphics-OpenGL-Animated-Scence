package main;

import base.GraphicsObjects.Utils;
import base.RenderProgramStatement;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

/**
 * @Author: WangYuyang
 * @Date: 2021/11/4-15:58
 * @Project: Assignment3
 * @Package: PACKAGE_NAME
 * @Description:
 **/
public class Engine {
    private static final int FPS = 60;
    public static HashMap<String, Texture> textures = new LinkedHashMap();
    // basic colours
    static float black[] = {0.0f, 0.0f, 0.0f, 1.0f};
    static float white[] = {1.0f, 1.0f, 1.0f, 1.0f};
    static float grey[] = {0.5f, 0.5f, 0.5f, 1.0f};
    static float spot[] = {0.1f, 0.1f, 0.1f, 0.5f};
    // primary colours
    static float red[] = {1.0f, 0.0f, 0.0f, 1.0f};
    static float green[] = {0.0f, 1.0f, 0.0f, 1.0f};
    static float blue[] = {0.0f, 0.0f, 1.0f, 1.0f};
    // secondary colours
    static float yellow[] = {1.0f, 1.0f, 0.0f, 1.0f};
    static float magenta[] = {1.0f, 0.0f, 1.0f, 1.0f};
    static float cyan[] = {0.0f, 1.0f, 1.0f, 1.0f};
    // other colours
    static float orange[] = {1.0f, 0.5f, 0.0f, 1.0f, 1.0f};
    static float brown[] = {0.5f, 0.25f, 0.0f, 1.0f, 1.0f};
    static float dkgreen[] = {0.0f, 0.5f, 0.0f, 1.0f, 1.0f};
    static float pink[] = {1.0f, 0.6f, 0.6f, 1.0f, 1.0f};
    private static long lastFrameTime;
    private static long startTime;
    private static long timePassed;
    /**
     * frames per second
     */
    int fps;
    /**
     * last fps time
     */
    long lastFPS;


    public Engine(int WIDTH, int HEIGHT) {
        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create();
            Display.setTitle("CG Project 1");
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, Texture> getTextures() {
        return textures;
    }

    public static long getStartTime() {
        return startTime;
    }

    public static long getTimePassed() {
        return timePassed;
    }

    public void init() {
        Display.setVSyncEnabled(true);
        loadTexture();

    }

    public void enterModelView() {
        GL11.glMatrixMode(GL_MODELVIEW);
    }

    public void initTimer() {
        lastFrameTime = getTime();
        startTime = getTime();
        lastFPS = getTime(); // call before loop to initialise fps timer
    }

    public void setLight() {
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        FloatBuffer lightPos = BufferUtils.createFloatBuffer(4);
        lightPos.put(10000f).put(1000f).put(1000).put(0).flip();

        FloatBuffer lightPos2 = BufferUtils.createFloatBuffer(4);
        lightPos2.put(0f).put(1000f).put(0).put(-1000f).flip();

        FloatBuffer lightPos3 = BufferUtils.createFloatBuffer(4);
        lightPos3.put(-10000f).put(1000f).put(1000).put(0).flip();

        FloatBuffer lightPos4 = BufferUtils.createFloatBuffer(4);
        lightPos4.put(1000f).put(1000f).put(1000f).put(0).flip();

        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPos); // specify the
        // position
        // of the
        // light
//         GL11.glEnable(GL11.GL_LIGHT0); // switch light #0 on // I've setup specific materials so in real light it will look abit strange

        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION, lightPos); // specify the
        // position
        // of the
        // light
        GL11.glEnable(GL11.GL_LIGHT1); // switch light #0 on
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, Utils.ConvertForGL(spot));

        GL11.glLight(GL11.GL_LIGHT2, GL11.GL_POSITION, lightPos3); // specify
        // the
        // position
        // of the
        // light
        GL11.glEnable(GL11.GL_LIGHT2); // switch light #0 on
        GL11.glLight(GL11.GL_LIGHT2, GL11.GL_DIFFUSE, Utils.ConvertForGL(grey));

        GL11.glLight(GL11.GL_LIGHT3, GL11.GL_POSITION, lightPos4); // specify
        // the
        // position
        // of the
        // light
        GL11.glEnable(GL11.GL_LIGHT3); // switch light #0 on
        GL11.glLight(GL11.GL_LIGHT3, GL11.GL_DIFFUSE, Utils.ConvertForGL(grey));

        GL11.glEnable(GL11.GL_LIGHTING); // switch lighting on
        GL11.glEnable(GL11.GL_DEPTH_TEST); // make sure depth buffer is switched
        // on
        GL11.glEnable(GL11.GL_NORMALIZE); // normalize normal vectors for safety
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    public void close() {
        Display.destroy();
        System.exit(0);
    }

    public void render(RenderProgramStatement renderProgram) {

        glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glColor3f(0.5f, 0.5f, 1.0f);

        int delta = getDelta();
        timePassed = getTime() - startTime;

        updateFPS();
        checkInput();
        glPushMatrix();
        renderProgram.render(delta);
        glPopMatrix();

        Display.update();
        Display.sync(FPS);
    }

    private void checkInput() {

    }

    public void setOrtho(int OrthoNumber) {
        if (600 + OrthoNumber > 0 && 700 + OrthoNumber * 0.66 > 0) {
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();
            //Placing 0,0 at the center of the screen
//        GL11.glOrtho(1200 / 2 - OrthoNumber, OrthoNumber / 2, (800 / 2 - (OrthoNumber * 0.66f)), (OrthoNumber / 2 * 0.66f), 100000, -100000);
//        GL11.glOrtho(1200 - OrthoNumber, OrthoNumber, (800 - (OrthoNumber * 0.66f)), (OrthoNumber * 0.66f), 100000, -100000);F
            gluPerspective((float) 60, Display.getWidth() / Display.getHeight(), 100f, 10000);

//            GL11.glOrtho(-600 - OrthoNumber, 600 + OrthoNumber, -100 - OrthoNumber * 0.66, 700 + OrthoNumber * 0.66, 100000, -100000);
            enterModelView();
        }

    }

    private long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    private int getDelta() {
        long currentTime = getTime();
        int delta = (int) (currentTime - lastFrameTime);
        lastFrameTime = getTime();
        return delta;
    }

    private void loadTexture() {
        //LOAD textures
        try {
            textures.put("awesomeface", TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/awesomeface.png")));

            textures.put("default_dirt", TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/default_dirt.png")));
            textures.put("earthspace", TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/earthspace.png")));
            textures.put("tnt_side", TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/tnt_side.png")));
            textures.put("farming_wheat_8", TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/farming_wheat_8.png")));
            textures.put("wool_pink", TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/wool_pink.png")));
            textures.put("default_lava", TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/default_lava.png")));
            textures.put("default_stone", TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/default_stone.png")));
            textures.put("default_cobble", TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/default_cobble.png")));
//        textures.put("awesomeface",TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/awesomeface.png")));
//        textures.put("awesomeface",TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/awesomeface.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Texture loaded okay ");
    }

    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("FPS: " + fps);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }
}
