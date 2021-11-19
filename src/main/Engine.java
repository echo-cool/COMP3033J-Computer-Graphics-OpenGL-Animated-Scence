package main;

import Scene.Objects.Player;
import Scene.Objects.TheMostImportantImage;
import Scene.Scene;
import Scene.base.SceneObject;
import base.GraphicsObjects.Utils;
import base.GraphicsObjects.Vector4f;
import base.RenderProgramStatement;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static main.Main.camera;
import static main.ShaderLoader.loadShaders;
import static org.lwjgl.opengl.ARBShadowAmbient.GL_TEXTURE_COMPARE_FAIL_VALUE_ARB;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL14.GL_DEPTH_TEXTURE_MODE;
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
    private static final FloatBuffer textureBuffer = BufferUtils.createFloatBuffer(16);
    private static final Matrix4f depthModelViewProjection = new Matrix4f();
    /**
     * The color of the fog in rgba.
     */
    private static final Color fogColor = new Color(0f, 0f, 0f, 1f);
    public static HashMap<String, Texture> textures = new LinkedHashMap();
    public static FloatBuffer lightPosition;
    public static FloatBuffer lightPos2;
    public static FloatBuffer lightPos3;
    public static FloatBuffer lightPos4;
    public static int delta;
    public static int shadowTexture;
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
    /**
     * The width of the depth texture that is known as the shadow map. The higher the width, the more detailed the
     * shadows.
     */
    private static int shadowMapWidth;
    /**
     * The height of the depth texture that is known as the shadow map. The higher the height, the more detailed the
     * shadows.
     */
    private static int shadowMapHeight;
    private static int frameBuffer;
    private static int renderBuffer;
    private static long lastFrameTime;
    private static long startTime;
    private static long timePassed;
    private static float zNear = 100f;
    /**
     * The distance where fog starts appearing.
     */
    private static final float fogNear = zNear;
    private static float zFar = 50000f;
    /**
     * The distance where the fog stops appearing (fully black here)
     */
    private static final float fogFar = zFar;
    FloatBuffer noAmbient = BufferUtils.createFloatBuffer(4);
    FloatBuffer diffuse = BufferUtils.createFloatBuffer(4);
    FloatBuffer spec = BufferUtils.createFloatBuffer(4);
    FloatBuffer direction = BufferUtils.createFloatBuffer(4);
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
            Display.create(
                    new PixelFormat()
//                    new ContextAttribs(3, 3).withProfileCore(true)
            );
            System.out.println("Your OpenGL version is " + GL11.glGetString(GL11.GL_VERSION));
            Display.setTitle("CG Project 1 Loading.......");
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

    private static long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    public static int getDelta() {
        long currentTime = getTime();
        int delta = (int) (currentTime - lastFrameTime);
        lastFrameTime = getTime();
        return delta;
    }

    public void init() {
        Display.setVSyncEnabled(true);
        glEnable(GL13.GL_MULTISAMPLE);
//        Display.setResizable(true);
        glClear(GL_COLOR_BUFFER_BIT);
        shadowTexture = glGenTextures();

        GL11.glMatrixMode(GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, 1600, 800, 0, 1, -1);
        GL11.glMatrixMode(GL_MODELVIEW);
        Texture loading = null;
        try {
            loading = TextureLoader.getTexture("2021.png", "PNG");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Color.white.bind();
        loading.bind();
        glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glTexParameteri(
                GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                GL11.GL_CLAMP);
        GL11.glTexParameteri(
                GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S,
                GL11.GL_CLAMP);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glEnable(GL_TEXTURE_2D);
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2i(0, 0);
        glTexCoord2f(1, 0);
        glVertex2i(1600, 0);
        glTexCoord2f(1, 1);
        glVertex2i(1600, 800);
        glTexCoord2f(0, 1);
        glVertex2i(0, 800);
        glEnd();

        Display.update();


//        glEnable(GL_FOG);
//        {
//            FloatBuffer fogColours = BufferUtils.createFloatBuffer(4);
//            fogColours.put(new float[]{fogColor.r, fogColor.g, fogColor.b, fogColor.a});
//            glClearColor(fogColor.r, fogColor.g, fogColor.b, fogColor.a);
//            fogColours.flip();
//            glFog(GL_FOG_COLOR, fogColours);
//            glFogi(GL_FOG_MODE, GL_LINEAR);
//            glHint(GL_FOG_HINT, GL_NICEST);
//            glFogf(GL_FOG_START, fogNear);
//            glFogf(GL_FOG_END, fogFar);
//            glFogf(GL_FOG_DENSITY, 0.005f);
//        }
        loadShaders();
        loadTexture();
//        setUpFrameBufferObject();

        glEnable(GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LIGHTING); // switch lighting on
        GL11.glEnable(GL11.GL_DEPTH_TEST); // make sure depth buffer is switched
        GL11.glEnable(GL11.GL_NORMALIZE); // normalize normal vectors for safety
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
//        GL11.glEnable(GL_CULL_FACE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        glPolygonOffset(2.5F, 0.0F);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE); // [x,y,z,w] -> [s,t,r,q]
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_DEPTH_TEXTURE_MODE, GL_INTENSITY);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_COMPARE_FAIL_VALUE_ARB, 0.5F);
        glTexGeni(GL_S, GL_TEXTURE_GEN_MODE, GL_EYE_LINEAR);
        glTexGeni(GL_T, GL_TEXTURE_GEN_MODE, GL_EYE_LINEAR);
        glTexGeni(GL_R, GL_TEXTURE_GEN_MODE, GL_EYE_LINEAR);
        glTexGeni(GL_Q, GL_TEXTURE_GEN_MODE, GL_EYE_LINEAR);

        lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(10000f).put(10000f).put(5000).put(0).flip();

        lightPos2 = BufferUtils.createFloatBuffer(4);
        lightPos2.put(0f).put(10000f).put(0).put(0).flip();

        lightPos3 = BufferUtils.createFloatBuffer(4);
        lightPos3.put(0).put(3000f).put(0).put(1).flip();

        lightPos4 = BufferUtils.createFloatBuffer(4);
        lightPos4.put(-10000f).put(10000f).put(-5000).put(0).flip();

        noAmbient.put(new float[]{0.2f, 0.2f, 0.2f, 1.0f});
        noAmbient.rewind();

        diffuse.put(new float[]{1.0f, 1.0f, 1.0f, 1.0f});
        diffuse.rewind();
        spec.put(new float[]{1.0f, 1.0f, 1.0f, 1.0f});
        spec.rewind();
        direction.put(new float[]{0f, 0f, -1f, 0});
        direction.rewind();
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


        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPosition); // specify the

        GL11.glLightf(GL11.GL_LIGHT0, GL11.GL_QUADRATIC_ATTENUATION, 0.5f); // specify the
        if (!TheMostImportantImage.isPlayVideo)
            GL11.glEnable(GL11.GL_LIGHT0); // SUN_light
        else
            GL11.glDisable(GL11.GL_LIGHT0); // SUN_light
//        GL11.glDisable(GL11.GL_LIGHT0); // SUN_light

        GL11.glLight(GL11.GL_LIGHT1, GL_POSITION, lightPos2);
        float amb[] = {0.2f, 0.2f, 0.2f, 1.0f};
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, Utils.ConvertForGL(spot));
        GL11.glLight(GL11.GL_LIGHT1, GL_AMBIENT, Utils.ConvertForGL(amb));
//        GL11.glEnable(GL11.GL_LIGHT1);
//        float light3_DIFFUSE[] = {1f, 1f, 1f, 1.0f};
//        float light3_SPECULAR[] = {1f, 1, 1f, 1.0f};
        GL11.glLight(GL11.GL_LIGHT2, GL_POSITION, lightPos3);
        GL11.glLight(GL11.GL_LIGHT2, GL_DIFFUSE, diffuse);
        GL11.glLight(GL11.GL_LIGHT2, GL_SPECULAR, spec);
        GL11.glLight(GL11.GL_LIGHT2, GL_SPOT_DIRECTION, direction);
        GL11.glLightf(GL11.GL_LIGHT2, GL_SPOT_CUTOFF, 40);

        if (TheMostImportantImage.isPlayVideo)
            GL11.glEnable(GL11.GL_LIGHT2); //Spotlight
        else
            GL11.glDisable(GL11.GL_LIGHT2); // Spotlight

//
//
//
        float[] fLightSpecular = {1f, 1f, 1f, 1f};
        GL11.glLight(GL11.GL_LIGHT3, GL_POSITION, lightPos4);
        GL11.glLight(GL11.GL_LIGHT3, GL_SPECULAR, Utils.ConvertForGL(fLightSpecular));
        GL11.glEnable(GL11.GL_LIGHT3);

    }

    public void close() {
        Display.destroy();
        System.exit(0);
    }

    public void render(RenderProgramStatement renderProgram) {
        glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glColor3f(0.5f, 0.5f, 1.0f);

        delta = getDelta();
        timePassed = getTime() - startTime;

        updateFPS();
        checkInput();

        renderProgram.renderScene(delta);
        renderProgram.renderBackground(delta);
//        glPushAttrib(GL_ALL_ATTRIB_BITS);
//        {
//            generateTextureCoordinates();
//
//            drawShadowMap(renderProgram);
//
//
//
//        }
//        glPopAttrib();


        Display.update();
        Display.sync(FPS);
    }


    private void checkInput() {
        Vector4f v = Scene.player.checkInput();
        Boolean ishit = false;
        for (SceneObject o : Main.sceneManager.getSceneObjects()) {
            if (!(o instanceof Player)) {
                if (o.isHit(Scene.player)) {
                    ishit = true;
                    Scene.player.move(v.NegateVector());
                }
            }
        }
        camera.update_mouse();
        if (!ishit)
            camera.update();

    }

    public void setOrtho(int OrthoNumber) {
//        if (600 + OrthoNumber > 0 && 700 + OrthoNumber * 0.66 > 0) {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        //Placing 0,0 at the center of the screen
//        GL11.glOrtho(1200 / 2 - OrthoNumber, OrthoNumber / 2, (800 / 2 - (OrthoNumber * 0.66f)), (OrthoNumber / 2 * 0.66f), 100000, -100000);
//        GL11.glOrtho(1200 - OrthoNumber, OrthoNumber, (800 - (OrthoNumber * 0.66f)), (OrthoNumber * 0.66f), 100000, -100000);F
        gluPerspective((float) 60, Display.getWidth() / Display.getHeight(), zNear, zFar);

//            GL11.glOrtho(-600 - OrthoNumber, 600 + OrthoNumber, -100 - OrthoNumber * 0.66, 700 + OrthoNumber * 0.66, 100000, -100000);
        enterModelView();
//        }

    }

    private void loadTexture() {
        TextureLoader.loadTexture(textures);
        Display.setTitle("CG Project 1 Loading....... Texture loaded okay");
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
