package main;

import Scene.Objects.Player;
import Scene.Scene;
import Scene.base.SceneObject;
import base.GraphicsObjects.Vector4f;
import base.RenderProgramStatement;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.PNGDecoder;
import org.newdawn.slick.opengl.Texture;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static main.Main.camera;
import static org.lwjgl.opengl.ARBFramebufferObject.*;
import static org.lwjgl.opengl.ARBShadowAmbient.GL_TEXTURE_COMPARE_FAIL_VALUE_ARB;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.util.glu.GLU.*;
import static org.newdawn.slick.opengl.PNGDecoder.RGBA;

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
    public static FloatBuffer lightPosition;
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
    public static int delta;
    private static float zNear = 100f;
    /**
     * The distance where fog starts appearing.
     */
    private static final float fogNear = zNear;
    private static float zFar = 30000f;
    /**
     * The distance where the fog stops appearing (fully black here)
     */
    private static final float fogFar = zFar;
    /**
     * frames per second
     */
    int fps;
    /**
     * last fps time
     */
    long lastFPS;

    public static int shadowTexture;


    public Engine(int WIDTH, int HEIGHT) {

        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create(
                    new PixelFormat()
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

    /**
     * Sets up the OpenGL states.
     */
    private static void setUpFrameBufferObject() {
        final int MAX_RENDERBUFFER_SIZE = glGetInteger(GL_MAX_RENDERBUFFER_SIZE);
        final int MAX_TEXTURE_SIZE = glGetInteger(GL_MAX_TEXTURE_SIZE);
        /**
         * Cap the maximum shadow map size at 1024x1024 pixels or at the maximum render buffer size. If you have a good
         * graphics card, feel free to increase this value. The program will lag
         * if I record and run the program at the same time with higher values.
         */
        if (MAX_TEXTURE_SIZE > 1024) {
            if (MAX_RENDERBUFFER_SIZE < MAX_TEXTURE_SIZE) {
                shadowMapWidth = shadowMapHeight = MAX_RENDERBUFFER_SIZE;
            } else {
                shadowMapWidth = shadowMapHeight = 1024;
            }
        } else {
            shadowMapWidth = shadowMapHeight = MAX_TEXTURE_SIZE;
        }
        // Generate and bind a frame buffer.
        frameBuffer = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer);
        // Generate and bind a render buffer.
        renderBuffer = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, renderBuffer);
        // Set the internal storage format of the render buffer to a depth component of 32 bits (4 bytes).
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT32, shadowMapWidth, shadowMapHeight);
        // Attach the render buffer to the frame buffer as a depth attachment. This means that, if the frame buffer is
        // bound, any depth texture values will be copied to the render buffer object.
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, renderBuffer);
        // OpenGL shall make no amendment to the colour or multisample buffer.
        glDrawBuffer(GL_NONE);
        // Disable the colour buffer for pixel read operations (such as glReadPixels or glCopyTexImage2D).
        glReadBuffer(GL_NONE);
        // Check for frame buffer errors.
        int FBOStatus = glCheckFramebufferStatus(GL_FRAMEBUFFER);
        if (FBOStatus != GL_FRAMEBUFFER_COMPLETE) {
            System.err.println("Framebuffer error: " + gluErrorString(glGetError()));
        }
        // Bind the default frame buffer, which is used for ordinary drawing.
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    private static void generateTextureCoordinates() {
        glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
        // Compare the texture coordinate 'r' (the distance from the light to the surface of the object) to the
        // value in the depth buffer.
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_COMPARE_MODE, GL_COMPARE_R_TO_TEXTURE);
        // Enable 's' texture coordinate generation.
        glEnable(GL_TEXTURE_GEN_S);
//        // Enable 't' texture coordinate generation.
        glEnable(GL_TEXTURE_GEN_T);
//        // Enable 'r' texture coordinate generation.
        glEnable(GL_TEXTURE_GEN_R);
//        // Enable 'q' texture coordinate generation.
        glEnable(GL_TEXTURE_GEN_Q);
        textureBuffer.clear();
        textureBuffer.put(0, depthModelViewProjection.m00);
        textureBuffer.put(1, depthModelViewProjection.m01);
        textureBuffer.put(2, depthModelViewProjection.m02);
        textureBuffer.put(3, depthModelViewProjection.m03);

        glTexGen(GL_S, GL_EYE_PLANE, textureBuffer);

        textureBuffer.put(0, depthModelViewProjection.m10);
        textureBuffer.put(1, depthModelViewProjection.m11);
        textureBuffer.put(2, depthModelViewProjection.m12);
        textureBuffer.put(3, depthModelViewProjection.m13);

        glTexGen(GL_T, GL_EYE_PLANE, textureBuffer);

        textureBuffer.put(0, depthModelViewProjection.m20);
        textureBuffer.put(1, depthModelViewProjection.m21);
        textureBuffer.put(2, depthModelViewProjection.m22);
        textureBuffer.put(3, depthModelViewProjection.m23);

        glTexGen(GL_R, GL_EYE_PLANE, textureBuffer);

        textureBuffer.put(0, depthModelViewProjection.m30);
        textureBuffer.put(1, depthModelViewProjection.m31);
        textureBuffer.put(2, depthModelViewProjection.m32);
        textureBuffer.put(3, depthModelViewProjection.m33);

        glTexGen(GL_Q, GL_EYE_PLANE, textureBuffer);
    }

    /**
     * Generate the shadow map.
     *
     * @param renderProgram
     */
    private static void drawShadowMap(RenderProgramStatement renderProgram) {
        /**
         * The model-view matrix of the light.
         */
        FloatBuffer lightModelView = BufferUtils.createFloatBuffer(16);
        /**
         * The projection matrix of the light.
         */
        FloatBuffer lightProjection = BufferUtils.createFloatBuffer(16);
        Matrix4f lightProjectionTemp = new Matrix4f();
        Matrix4f lightModelViewTemp = new Matrix4f();
        /**
         * The radius that encompasses all the objects that cast shadows in the scene. There should
         * be no object farther away than 50 units from [0, 0, 0] in any direction.
         * If an object exceeds the radius, the object may cast shadows wrongly.
         */
        float sceneBoundingRadius = 1500;
        /**
         * The distance from the light to the scene, assuming that the scene is located
         * at [0, 0, 0]. Using the Pythagorean theorem, the distance is calculated by taking the square-root of the
         * sum of each of the components of the light position squared.
         */
        float lightToSceneDistance = (float) Math.sqrt(lightPosition.get(0) * lightPosition.get(0) +
                lightPosition.get(1) * lightPosition.get(1) +
                lightPosition.get(2) * lightPosition.get(2));

        /**
         * The distance to the object that is nearest to the camera. This excludes objects that do not cast shadows.
         * This will be used as the zNear parameter in gluPerspective.
         */
        float nearPlane = lightToSceneDistance - sceneBoundingRadius;
        if (nearPlane < 0) {
            System.err.println("Camera is too close to scene. A valid shadow map cannot be generated.");
        }
        /**
         * The field-of-view of the shadow frustum in degrees. Formula taken from the OpenGL SuperBible.
         */
        float fieldOfView = (float) Math.toDegrees(2.0F * Math.atan(sceneBoundingRadius / lightToSceneDistance));
        glMatrixMode(GL_PROJECTION);
        // Store the current projection matrix.
        glPushMatrix();
        glLoadIdentity();
        // Generate the 'shadow frustum', a perspective projection matrix that shows all the objects in the scene.
        gluPerspective(fieldOfView, 1, nearPlane, nearPlane + sceneBoundingRadius * 2);
        // Store the shadow frustum in 'lightProjection'.
        glGetFloat(GL_PROJECTION_MATRIX, lightProjection);
        glMatrixMode(GL_MODELVIEW);
        // Store the current model-view matrix.
        glPushMatrix();
        glLoadIdentity();
        // Have the 'shadow camera' look toward [0, 0, 0] and be location at the light's position.
        gluLookAt(lightPosition.get(0), lightPosition.get(1), lightPosition.get(2), 0, 0, 0, 0, 1, 0);
        glGetFloat(GL_MODELVIEW_MATRIX, lightModelView);
        // Set the view port to the shadow map dimensions so no part of the shadow is cut off.
        glViewport(0, 0, shadowMapWidth, shadowMapHeight);
        // Bind the extra frame buffer in which to store the shadow map in the form a depth texture.
        glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer);
        // Clear only the depth buffer bit. Clearing the colour buffer is unnecessary, because it is disabled (we
        // only need depth components).
        glClear(GL_DEPTH_BUFFER_BIT);
        // Store the current attribute state.
        glPushAttrib(GL_ALL_ATTRIB_BITS);
        {
            // Disable smooth shading, because the shading in a shadow map is irrelevant. It only matters where the
            // shape
            // vertices are positioned, and not what colour they have.
            glShadeModel(GL_FLAT);
            // Enabling all these lighting states is unnecessary for reasons listed above.
            glDisable(GL_LIGHTING);
            glDisable(GL_COLOR_MATERIAL);
            glDisable(GL_NORMALIZE);
            // Disable the writing of the red, green, blue, and alpha colour components,
            // because we only need the depth component.
            glColorMask(false, false, false, false);
            // An offset is given to every depth value of every polygon fragment to prevent a visual quirk called
            // 'shadow
            // acne'.
            glEnable(GL_POLYGON_OFFSET_FILL);
            // Draw the objects that cast shadows.
            renderProgram.renderScene(16);

            /**
             * Copy the pixels of the shadow map to the frame buffer object depth attachment.
             *  int target -> GL_TEXTURE_2D
             *  int level  -> 0, has to do with mip-mapping, which is not applicable to shadow maps
             *  int internalformat -> GL_DEPTH_COMPONENT
             *  int x, y -> 0, 0
             *  int width, height -> shadowMapWidth, shadowMapHeight
             *  int border -> 0
             */

            glBindTexture(GL_TEXTURE_2D, shadowTexture);
            glCopyTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, 0, 0, shadowMapWidth, shadowMapHeight, 0);
            glBindTexture(GL_TEXTURE_2D, 0);


            // Restore the previous model-view matrix.
            glPopMatrix();
            glMatrixMode(GL_PROJECTION);
            // Restore the previous projection matrix.
            glPopMatrix();
            glMatrixMode(GL_MODELVIEW);
            glBindFramebuffer(GL_FRAMEBUFFER, 0);
        }// Restore the previous attribute state.
        glPopAttrib();
        // Restore the view port.
        glViewport(0, 0, Display.getWidth(), Display.getHeight());
        lightProjectionTemp.load(lightProjection);
        lightModelViewTemp.load(lightModelView);
        lightProjection.flip();
        lightModelView.flip();
        depthModelViewProjection.setIdentity();
        // [-1,1] -> [-0.5,0.5] -> [0,1]
        depthModelViewProjection.translate(new Vector3f(0.5F, 0.5F, 0.5F));
        depthModelViewProjection.scale(new Vector3f(0.5F, 0.5F, 0.5F));
        // Multiply the texture matrix by the projection and model-view matrices of the light.
        Matrix4f.mul(depthModelViewProjection, lightProjectionTemp, depthModelViewProjection);
        Matrix4f.mul(depthModelViewProjection, lightModelViewTemp, depthModelViewProjection);
        // Transpose the texture matrix.
        Matrix4f.transpose(depthModelViewProjection, depthModelViewProjection);
    }

    public void init() {
        Display.setVSyncEnabled(true);
//        Display.setResizable(true);
        glClear(GL_COLOR_BUFFER_BIT);
        shadowTexture = glGenTextures();



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
        loadTexture();
        setUpFrameBufferObject();

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


//        FloatBuffer lightPos2 = BufferUtils.createFloatBuffer(4);
//        lightPos2.put(0f).put(1000f).put(0).put(-1000f).flip();
//
//        FloatBuffer lightPos3 = BufferUtils.createFloatBuffer(4);
//        lightPos3.put(-10000f).put(1000f).put(1000).put(0).flip();
//
//        FloatBuffer lightPos4 = BufferUtils.createFloatBuffer(4);
//        lightPos4.put(1000f).put(1000f).put(1000f).put(0).flip();

        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPosition); // specify the
        // position
        // of the
        // light
        GL11.glEnable(GL11.GL_LIGHT0); // switch light #0 on // I've setup specific materials so in real light it will look abit strange
//
//        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION, lightPos); // specify the
//        // position
//        // of the
//        // light
//        GL11.glEnable(GL11.GL_LIGHT1); // switch light #0 on
//        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, Utils.ConvertForGL(spot));
//
//        GL11.glLight(GL11.GL_LIGHT2, GL11.GL_POSITION, lightPos3); // specify
//        // the
//        // position
//        // of the
//        // light
//        GL11.glEnable(GL11.GL_LIGHT2); // switch light #0 on
//        GL11.glLight(GL11.GL_LIGHT2, GL11.GL_DIFFUSE, Utils.ConvertForGL(grey));
//
//        GL11.glLight(GL11.GL_LIGHT3, GL11.GL_POSITION, lightPos4); // specify
//        // the
//        // position
//        // of the
//        // light
//        GL11.glEnable(GL11.GL_LIGHT3); // switch light #0 on
//        GL11.glLight(GL11.GL_LIGHT3, GL11.GL_DIFFUSE, Utils.ConvertForGL(grey));
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

    private static long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    public static int getDelta() {
        long currentTime = getTime();
        int delta = (int) (currentTime - lastFrameTime);
        lastFrameTime = getTime();
        return delta;
    }

    private void loadTexture() {
        TextureLoader.loadTexture(textures);
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
