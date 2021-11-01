import GraphicsObjects.Arcball;
import GraphicsObjects.Utils;
import GraphicsObjects.Vector4f;
import objects3D.Grid;
import objects3D.Human;
import objects3D.TexCube;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.LinkedHashMap;

//Main windows class controls and creates the 3D virtual world , please do not change this class but edit the other classes to complete the assignment. 
// Main window is built upon the standard Helloworld LWJGL class which I have heavily modified to use as your standard openGL environment. 
// 

// Do not touch this class, I will be making a version of it for your 3rd Assignment 
public class MainWindow {
    //MY Hash map to store the textures
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
    /**
     * position of pointer
     */
    float x = 400, y = 300;
    /**
     * angle of rotation
     */
    float rotation = 0;
    /**
     * time at last frame
     */
    long lastFrame;
    /**
     * frames per second
     */
    int fps;
    /**
     * last fps time
     */
    long lastFPS;
    long myDelta = 0; //to use for animation
    float Alpha = 0; //to use for animation
    long StartTime; // beginAnimiation
    Arcball MyArcball = new Arcball();
    boolean DRAWGRID = false;
    boolean waitForKeyrelease = true;
    /**
     * Mouse movement
     */
    int LastMouseX = -1;
    int LastMouseY = -1;
    float pullX = 0.0f; // arc ball  X cord.
    float pullY = 0.0f; // arc ball  Y cord.
    int OrthoNumber = 1200; // using this for screen size, making a window of 1200 x 800 so aspect ratio 3:2 // do not change this for assignment 3 but you can change everything for your project
    Texture texture;
    private boolean MouseOnepressed = true;
    private boolean dragMode = false;

    // static GLfloat light_position[] = {0.0, 100.0, 100.0, 0.0};
    private boolean BadAnimation = false;
    //support method to aid in converting a java float array into a Floatbuffer which is faster for the opengl layer to process
    private boolean Earth = false;

    //mouse right button click record
    private int secMouseX = 700;
    private int secMouseY = 0;
    //if mouse right button click
    private Boolean secMousePressed = false;

    public static void main(String[] argv) {
        MainWindow hello = new MainWindow();
        hello.start();
    }

    public void start() {

        StartTime = getTime();
        try {
            Display.setDisplayMode(new DisplayMode(1200, 800));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        initGL(); // init OpenGL
        getDelta(); // call once before loop to initialise lastFrame
        lastFPS = getTime(); // call before loop to initialise fps timer

//        489 477
//        558 585
//        MyArcball.startBall(489 ,477,1200,800);
//        MyArcball.updateBall(558 ,585,1200,800);

        //move camera away
        OrthoNumber = 1440;


        while (!Display.isCloseRequested()) {
            int delta = getDelta();
            update(delta);
            renderGL();
            Display.update();
            Display.sync(120); // cap fps to 120fps
        }

        Display.destroy();
    }

    public void update(int delta) {
        // rotate quad
        //rotation += 0.01f * delta;

        //My translation function needs to log Mouse position
        int MouseX = Mouse.getX();
        int MouseY = Mouse.getY();
        int WheelPostion = Mouse.getDWheel();

        //If first button pressed then use the arcball, if second button then translate the scene
        boolean MouseButonPressed = Mouse.isButtonDown(0);
        this.secMousePressed = Mouse.isButtonDown(1);


        //for scene translation
        if (this.secMousePressed) {
            int x = (int) (MouseX - this.LastMouseX);
            int y = (int) (MouseY - this.LastMouseY);
            this.secMouseX += x;
            this.secMouseY += y;
        }

        //scene rotation
        if (MouseButonPressed && !MouseOnepressed) {
            MouseOnepressed = true;
//              System.out.println("Mouse drag mode");
            MyArcball.startBall(MouseX, MouseY, 1200, 800);
//            System.out.println(MouseX + " " + MouseY);
            System.out.println(OrthoNumber);
            dragMode = true;
        } else if (!MouseButonPressed) {
//            if(MouseX != LastMouseX)
//                System.out.println(MouseX + " " + MouseY);
            MouseOnepressed = false;
            dragMode = false;
        }

        if (dragMode) {
            //update arcball
            MyArcball.updateBall(MouseX, MouseY, 1200, 800);
        }

        if (WheelPostion > 0) {
            OrthoNumber += 10;

        }

        if (WheelPostion < 0) {
            OrthoNumber -= 10;
            if (OrthoNumber < 610) {
                OrthoNumber = 610;
            }

            //  System.out.println("Orth nubmer = " +  OrthoNumber);

        }

        /** rest key is R*/
        if (Keyboard.isKeyDown(Keyboard.KEY_R))
            MyArcball.reset();

        /* bad animation can be turn on or off using A key)*/

        if (Keyboard.isKeyDown(Keyboard.KEY_A))
            BadAnimation = !BadAnimation;
        if (Keyboard.isKeyDown(Keyboard.KEY_D))
            x += 0.35f * delta;

        if (Keyboard.isKeyDown(Keyboard.KEY_W))
            y += 0.35f * delta;
        if (Keyboard.isKeyDown(Keyboard.KEY_S))
            y -= 0.35f * delta;

        if (Keyboard.isKeyDown(Keyboard.KEY_Q))
            rotation += 0.35f * delta;
        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            Earth = !Earth;
        }

        if (waitForKeyrelease) // check done to see if key is released
        {
            if (Keyboard.isKeyDown(Keyboard.KEY_G)) {

                DRAWGRID = !DRAWGRID;
                Keyboard.next();
                if (Keyboard.isKeyDown(Keyboard.KEY_G)) {
                    waitForKeyrelease = true;
                } else {
                    waitForKeyrelease = false;

                }
            }
        }

        /** to check if key is released */
        if (Keyboard.isKeyDown(Keyboard.KEY_G) == false) {
            waitForKeyrelease = true;
        } else {
            waitForKeyrelease = false;

        }


        // keep quad on the screen
        if (x < 0)
            x = 0;
        if (x > 1200)
            x = 1200;
        if (y < 0)
            y = 0;
        if (y > 800)
            y = 800;

        updateFPS(); // update FPS Counter

        LastMouseX = MouseX;
        LastMouseY = MouseY;
    }

    /**
     * Calculate how many milliseconds have passed since last frame.
     *
     * @return milliseconds passed since last frame
     */
    public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;

        return delta;
    }

    /**
     * Get the accurate system time
     *
     * @return The system time in milliseconds
     */
    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    /**
     * Calculate the FPS and set it in the title bar
     */
    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("FPS: " + fps);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }

    public void initGL() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        changeOrth();
        MyArcball.startBall(0, 0, 1200, 800);
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
        try {
            init();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } //load in texture


    }

    public void changeOrth() {

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(1200 - OrthoNumber, OrthoNumber, (800 - (OrthoNumber * 0.66f)), (OrthoNumber * 0.66f), 100000, -100000);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        FloatBuffer CurrentMatrix = BufferUtils.createFloatBuffer(16);
        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, CurrentMatrix);

        //	if(MouseOnepressed)
        //	{

        MyArcball.getMatrix(CurrentMatrix);
        //	}

        GL11.glLoadMatrix(CurrentMatrix);


    }

    /*
     * You can edit this method to add in your own objects /  remember to load in textures in the INIT method as they take time to load
     *
     */
    public void renderGL() {
        changeOrth();

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glColor3f(0.5f, 0.5f, 1.0f);

        myDelta = getTime() - StartTime;
        float delta = ((float) myDelta) / 10000;

        // code to aid in animation
        float theta = (float) (delta * 2 * Math.PI);
        float thetaDeg = delta * 360;
        float posn_x = (float) Math.cos(theta); // same as your circle code in your notes
        float posn_y = (float) Math.sin(theta);

//        OrthoNumber = (int) (1240 + posn_x * 200); //letting the OrthoNumber change when the scene change

        if (!BadAnimation) {
//            secMouseX = 600;
//            secMouseY = 100;
            GL11.glTranslatef(secMouseX, secMouseY, 0);
            GLU.gluLookAt(2f, -1f, -1f, 0f, 0f, 0f, 0f, 1f, 0f);

//            Vector4f v = new Vector4f(-posn_x, -0.5f, -posn_y, 0f);
//            GL11.glTranslatef(secMouseX, secMouseY, 0);
//
//            Vector4f v1 = new Vector4f(posn_x, 0f, posn_y,0f);
//            Vector4f v2 = new Vector4f(0f, 1f, 0f,0f);
//            Vector4f v3 = v1.cross(v2);
//            v3 = new Vector4f(v3.x, v3.y + 0.3f, v3.z, 0f).Normal();
//
//            GLU.gluLookAt(-v3.x, -v3.y, -v3.z, 0f, 0f, 0f, 0f, 1f, 0f); //change the look at position
        } else {

            GL11.glTranslatef(secMouseX, secMouseY, 0);
            GLU.gluLookAt(2f, -1f, -1f, 0f, 0f, 0f, 0f, 1f, 0f);
        }


        //Draw a big plain
        GL11.glPushMatrix();
        TexCube MyGrid = new TexCube();
        GL11.glScalef(8000f, 1f, 8000f);
        GL11.glTexParameteri(
                GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                GL11.GL_REPEAT);
        GL11.glTexParameteri(
                GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S,
                GL11.GL_REPEAT);
        Color.white.bind();
        //bind texture
        textures.get("default_dirt").bind();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        MyGrid.DrawTexCube(30f);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        //human obj
        Human MyHuman = new Human(textures); //init human with textures
//		GL11.glTranslatef(secMouseX,secMouseY,0);
        GL11.glTranslatef(0, 130, 0);
        GL11.glScalef(90f, 90f, 90f);


        if (!BadAnimation) {
            // insert your animation code to correct the postion for the human rotating
            GL11.glTranslatef(posn_x * 3.0f, 0.0f, posn_y * 3.0f);
            float angle = -(float) (180 * (theta) / Math.PI);
            GL11.glRotatef(180 + angle, 0, 1, 0);
//			System.out.println(theta);

        } else {
            //bad animation  version
//            GL11.glTranslatef(posn_x * 3.0f, 0.0f, posn_y * 3.0f);
            GL11.glTranslatef(posn_x * 3.0f, 0.0f, posn_y * 3.0f);
            float angle = -(float) (180 * (theta) / Math.PI);
            GL11.glRotatef(180 + angle, 0, 1, 0);
        }

        MyHuman.DrawHuman(delta, true); // give a delta for the Human object ot be animated

        GL11.glPopMatrix();



        // Draw TNTs
        GL11.glPushMatrix();
        TexCube cube1 = new TexCube();
//        GL11.glTranslatef(secMouseX,secMouseY,0);
        GL11.glTranslatef(0, 50f, 0);
        GL11.glScalef(50f, 50f, 50f);
        Color.white.bind();
        textures.get("tnt_side").bind();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        cube1.DrawTexCube();
        //TNT
        GL11.glPushMatrix();
        {
            //go up
            GL11.glTranslatef(0, 2f, 0);
            cube1.DrawTexCube();
            GL11.glPushMatrix();
            {
                GL11.glTranslatef(0, 2f, 0);
                cube1.DrawTexCube();
                GL11.glPushMatrix();
                {
                    GL11.glTranslatef(0, 2f, 0);
                    cube1.DrawTexCube();
                    GL11.glPushMatrix();
                    {
                        GL11.glTranslatef(0, 2f, 0);
                        cube1.DrawTexCube();
                        GL11.glPushMatrix();
                        {
                            //right(top TNTs)
                            GL11.glTranslatef(2f, 0f, 0);
                            cube1.DrawTexCube();
                            GL11.glPushMatrix();
                            {
                                GL11.glTranslatef(2f, 0f, 0);
                                cube1.DrawTexCube();
                                GL11.glPushMatrix();
                                {
                                    GL11.glTranslatef(2f, 0f, 0);
                                    cube1.DrawTexCube();
                                    GL11.glPushMatrix();
                                    {
                                        GL11.glTranslatef(2f, 0f, 0);
                                        cube1.DrawTexCube();
                                        GL11.glPushMatrix();
                                        {
                                            //go down
                                            GL11.glTranslatef(0f, -2f, 0);
                                            cube1.DrawTexCube();
                                            GL11.glPushMatrix();
                                            {
                                                GL11.glTranslatef(0f, -2f, 0);
                                                cube1.DrawTexCube();
                                                GL11.glPushMatrix();
                                                {
                                                    GL11.glTranslatef(0f, -2f, 0);
                                                    cube1.DrawTexCube();
                                                    GL11.glPushMatrix();
                                                    {
                                                        GL11.glTranslatef(0f, -2f, 0);
                                                        cube1.DrawTexCube();
                                                    }
                                                    GL11.glPopMatrix();
                                                }
                                                GL11.glPopMatrix();
                                            }
                                            GL11.glPopMatrix();
                                        }
                                        GL11.glPopMatrix();
                                    }
                                    GL11.glPopMatrix();
                                }
                                GL11.glPopMatrix();
                            }
                            GL11.glPopMatrix();
                        }
                    }
                    GL11.glPopMatrix();
                }
                GL11.glPopMatrix();
            }
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();


        //Draw lava
        GL11.glPushMatrix();
        TexCube cube2 = new TexCube();
//        GL11.glTranslatef(secMouseX,secMouseY,0);
        GL11.glTranslatef(0, 50f, 0);
//        GL11.glTranslatef(0, -2f, 0);
        GL11.glScalef(50f, 50f, 50f);
        Color.white.bind();
        textures.get("default_lava").bind();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTranslatef(-2f, 0f, 0);
        cube2.DrawTexCube();
        //LAVA
        GL11.glPushMatrix();
        {
            //Go UP
            GL11.glTranslatef(0f, 2f, 0);
            cube2.DrawTexCube();
            GL11.glPushMatrix();
            {
                GL11.glTranslatef(0f, 2f, 0);
                cube2.DrawTexCube();
                GL11.glPushMatrix();
                {
                    GL11.glTranslatef(0f, 2f, 0);
                    cube2.DrawTexCube();
                    GL11.glPushMatrix();
                    {
                        GL11.glTranslatef(0f, 2f, 0);
                        cube2.DrawTexCube();
                        GL11.glPushMatrix();
                        {
                            //go left (TOP lava)
                            GL11.glTranslatef(-2f, 0f, 0);
                            cube2.DrawTexCube();
                            GL11.glPushMatrix();
                            {
                                GL11.glTranslatef(-2f, 0f, 0);
                                cube2.DrawTexCube();
                                GL11.glPushMatrix();
                                {
                                    GL11.glTranslatef(-2f, 0f, 0);
                                    cube2.DrawTexCube();
                                    GL11.glPushMatrix();
                                    {
                                        //Go DOWN
                                        GL11.glTranslatef(0f, -2f, 0);
                                        cube2.DrawTexCube();
                                        GL11.glPushMatrix();
                                        {
                                            GL11.glTranslatef(0f, -2f, 0);
                                            cube2.DrawTexCube();
                                            GL11.glPushMatrix();
                                            {
                                                GL11.glTranslatef(0f, -2f, 0);
                                                cube2.DrawTexCube();
                                                GL11.glPushMatrix();
                                                {
                                                    GL11.glTranslatef(0f, -2f, 0);
                                                    cube2.DrawTexCube();
                                                }
                                                GL11.glPopMatrix();
                                            }
                                            GL11.glPopMatrix();
                                        }
                                        GL11.glPopMatrix();
                                    }
                                    GL11.glPopMatrix();
                                }
                                GL11.glPopMatrix();
                            }
                            GL11.glPopMatrix();
                        }
                        GL11.glPopMatrix();
                    }
                    GL11.glPopMatrix();
                }
                GL11.glPopMatrix();

            }
            GL11.glPopMatrix();

        }
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();

        /*
         * This code puts the earth code in which is larger than the human so it appears to change the scene
         */
        if (Earth) {
            //Globe in the centre of the scene
            GL11.glPushMatrix();
//            TexSphere MyGlobe = new TexSphere();
            TexCube MyGlobe = new TexCube();
            GL11.glTranslatef(500, 500, 500);
            GL11.glScalef(140f, 140f, 140f);

            GL11.glTexParameteri(
                    GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                    GL11.GL_CLAMP);

            Color.white.bind();
            texture.bind();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

//            MyGlobe.DrawTexSphere(8f, 100, 100, texture);
            MyGlobe.DrawTexCube();
            GL11.glPopMatrix();
        }

    }

    /*
     * Any additional textures for your assignment should be written in here.
     * Make a new texture variable for each one so they can be loaded in at the beginning
     */
    public void init() throws IOException {

        //LOAD textures
        texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/earthspace.png"));
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


        System.out.println("Texture loaded okay ");
    }
}
