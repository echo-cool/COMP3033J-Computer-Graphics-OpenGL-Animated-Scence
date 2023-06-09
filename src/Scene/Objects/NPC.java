package Scene.Objects;

import Scene.base.SceneObject;
import base.GraphicsObjects.Point4f;
import base.GraphicsObjects.Utils;
import base.GraphicsObjects.Vector4f;
import base.objects3D.DisplayListCylinder;
import base.objects3D.DisplayListOval;
import base.objects3D.DisplayListTexSphere;
import main.Engine;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import java.util.HashMap;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;

/**
 * @Author: WangYuyang
 * @Date: 2021/11/6-19:33
 * @Project: Assignment3
 * @Package: Scene.Objects
 * @Description:
 **/
public class NPC extends SceneObject {
    public static int angle_target = 0;
    public static int angle = 0;
    // basic colours
    static float[] black = {0.0f, 0.0f, 0.0f, 1.0f};
    static float[] white = {1.0f, 1.0f, 1.0f, 1.0f};
    static float[] grey = {0.5f, 0.5f, 0.5f, 1.0f};
    static float[] spot = {0.1f, 0.1f, 0.1f, 0.5f};
    // primary colours
    static float[] red = {1.0f, 0.0f, 0.0f, 1.0f};
    static float[] green = {0.0f, 1.0f, 0.0f, 1.0f};
    static float[] blue = {0.0f, 0.0f, 1.0f, 1.0f};
    // secondary colours
    static float[] yellow = {1.0f, 1.0f, 0.0f, 1.0f};
    static float[] magenta = {1.0f, 0.0f, 1.0f, 1.0f};
    static float[] cyan = {0.0f, 1.0f, 1.0f, 1.0f};
    // other colours
    static float[] orange = {1.0f, 0.5f, 0.0f, 1.0f, 1.0f};
    static float[] brown = {0.5f, 0.25f, 0.0f, 1.0f, 1.0f};
    static float[] dkgreen = {0.0f, 0.5f, 0.0f, 1.0f, 1.0f};
    static float[] pink = {1.0f, 0.6f, 0.6f, 1.0f, 1.0f};
    DisplayListTexSphere s1 = new DisplayListTexSphere(0.5f, 16, 16, getTextures().get("wool_pink"));
    DisplayListTexSphere s2 = new DisplayListTexSphere(0.5f, 16, 16, getTextures().get("tnt_side"));
    DisplayListTexSphere s3 = new DisplayListTexSphere(0.25f, 16, 16, getTextures().get("default_stone"));
    DisplayListTexSphere s4 = new DisplayListTexSphere(0.2f, 16, 16, getTextures().get("default_stone"));
    DisplayListTexSphere s5 = new DisplayListTexSphere(0.2f, 16, 16, getTextures().get("default_stone"));
    DisplayListTexSphere s6 = new DisplayListTexSphere(0.25f, 16, 16, getTextures().get("default_stone"));
    DisplayListTexSphere s7 = new DisplayListTexSphere(0.2f, 16, 16, getTextures().get("default_stone"));
    DisplayListTexSphere s8 = new DisplayListTexSphere(0.2f, 16, 16, getTextures().get("default_stone"));
    DisplayListTexSphere s9 = new DisplayListTexSphere(0.25f, 16, 16, getTextures().get("default_cobble"));
    DisplayListTexSphere s10 = new DisplayListTexSphere(0.25f, 16, 16, getTextures().get("default_cobble"));
    DisplayListTexSphere s11 = new DisplayListTexSphere(0.3f, 16, 16, getTextures().get("default_cobble"));
    DisplayListTexSphere s12 = new DisplayListTexSphere(0.25f, 16, 16, getTextures().get("default_cobble"));
    DisplayListTexSphere s13 = new DisplayListTexSphere(0.25f, 16, 16, getTextures().get("default_cobble"));
    DisplayListTexSphere s14 = new DisplayListTexSphere(0.3f, 16, 16, getTextures().get("default_cobble"));
    DisplayListTexSphere s15 = new DisplayListTexSphere(0.5f, 16, 16, getTextures().get("awesomeface"));
    DisplayListCylinder c1 = new DisplayListCylinder(0.15f, 0.7f, 16);
    DisplayListCylinder c2 = new DisplayListCylinder(0.15f, 0.7f, 16);
    DisplayListCylinder c3 = new DisplayListCylinder(0.1f, 0.7f, 16);
    DisplayListCylinder c4 = new DisplayListCylinder(0.15f, 0.7f, 16);
    DisplayListCylinder c5 = new DisplayListCylinder(0.1f, 0.7f, 16);
    DisplayListCylinder c6 = new DisplayListCylinder(0.15f, 0.7f, 16);
    DisplayListCylinder c7 = new DisplayListCylinder(0.15f, 0.7f, 16);
    DisplayListCylinder c8 = new DisplayListCylinder(0.15f, 0.7f, 16);
    DisplayListCylinder c9 = new DisplayListCylinder(0.15f, 0.7f, 16);
    DisplayListOval shadow = new DisplayListOval(3f, 32);
    private float delta;
    private Boolean isWalking = false;
    private long walkStartTime;
    private int stopCount = 0;
    private Boolean isJumping = false;
    private float jump_height = 0f;
    private float timePassed = Engine.getTimePassed();
    public NPC(Point4f origin, Point4f position, Vector4f scale) {
        super(origin, position, scale);
    }

    public NPC(Point4f origin, Point4f position, Vector4f scale, HashMap<String, Texture> textures) {
        super(origin, position, scale, textures);
    }

    public void walk() {
        stopCount = 0;
        if (isWalking == false) {
            isWalking = true;
            walkStartTime = Engine.getTimePassed();
//            System.out.println(walkStartTime);
        }
        if (isWalking) {
            this.delta = (Engine.getTimePassed() - walkStartTime) / 10000f;
        }
    }

    public void jump(int speed) {
        float g = 9.8f;
        if (!isJumping) {
            isJumping = true;
            long start_time = System.currentTimeMillis();
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    double h = 0;
                    long t = 0;
                    while (h >= 0) {
                        long start = System.currentTimeMillis();
                        long current_time = System.currentTimeMillis();
                        t = (current_time - start_time) / 10;
                        h = (speed * t - 0.5 * g * t * t) / 10000;
                        jump_height = (float) h;
                        long end = System.currentTimeMillis();
                        while (end - start < 16) {
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            end = System.currentTimeMillis();
                        }
                    }
                    jump_height = 0;
                    isJumping = false;

                }
            });
            thread.start();
        }

    }

    public void stop() {
        if (stopCount > 5) {
            isWalking = false;
            this.delta = 0;
            stopCount = 0;
        }
        stopCount++;
    }

    public void setAngle(float angle) {
        angle_target = (int) angle;
    }

    @Override
    public void draw(Integer frame_delta) {
        timePassed = Engine.getTimePassed() / 10000.0f;
        float speed = frame_delta / 160f;
//        System.out.println(speed);
        walk();
        if ((int) (timePassed % 4) == 0) {
            move(new Vector4f(speed, 0, 0, 0));
            angle_target = 90;

        }
        if ((int) (timePassed % 4) == 1) {
            move(new Vector4f(0, 0, -speed, 0));
            angle_target = 90 + 90;
        }
        if ((int) (timePassed % 4) == 2) {
            move(new Vector4f(-speed, 0, 0, 0));
            angle_target = 90 + 90 + 90;
        }
        if ((int) (timePassed % 4) == 3) {
            move(new Vector4f(0, 0, speed, 0));
            angle_target = 90 + 90 + 90 + 90;
        }
        GL11.glTranslatef(0, jump_height, 0);
        Boolean GoodAnimation = true;

        float theta_face = (float) (delta * 2 * Math.PI);
//        float angle = -(float) (180 * (theta_face) / Math.PI);
//        System.out.println(angle);
//
        GL11.glRotatef(angle + 180, 0, 1, 0);

        float theta = (float) (delta * 2 * Math.PI) * 8;
        //a variable for anim sync
        float LimbRotation = 0;
        //two animation
        if (GoodAnimation) {
            LimbRotation = (float) Math.sin(theta) * 60;
        } else {
            LimbRotation = 0;
        }

//        //a sphere for drawing
//        Sphere sphere = new Sphere();
//        //a sphere with texture
//        TexSphere texSphere = new TexSphere();
//        //a cylinder for drawing
//        Cylinder cylinder = new Cylinder();

        //Start to draw
        GL11.glPushMatrix();
        {
            // move to pelvis
            GL11.glTranslatef(0.0f, 0.5f, 0.0f);
            //set up TEXTURE
            GL11.glTexParameteri(
                    GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                    GL11.GL_REPEAT);
            //bind a color texture
            Color.white.bind();

            getTextures().get("wool_pink").bind();
            //Enable TEXTURE

            //set texture Parameters
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

            s1.DrawTexSphere();
            //Draw finish Disable TEXTURE


//            sphere.DrawSphere(0.5f, 16, 16); // pelvis sphere

            //  chest
            GL11.glColor3f(green[0], green[1], green[2]);
            GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(green));
            GL11.glPushMatrix();
            {
                GL11.glTranslatef(0.0f, 0.5f, 0.0f);           // move to chest
                GL11.glTexParameteri(
                        GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                        GL11.GL_REPEAT);
                Color.white.bind(); //bind color
                //bind texture
                getTextures().get("tnt_side").bind(); //set texture
                //Enable TEXTURE
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL_NEAREST);
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

                s2.DrawTexSphere();
                //disable texture


//                sphere.DrawSphere(0.5f, 16, 16);// chest sphere
                GL11.glRotatef((float) (LimbRotation * 0.2), 0.0f, 0.0f, 1.0f);
//                GL11.glRotatef((float) (LimbRotation * 3), 1f, 1f, 0f);

                // neck
                GL11.glColor3f(orange[0], orange[1], orange[2]); //set color
                GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange)); //set material render mode
                GL11.glPushMatrix();
                {
                    GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                    GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                    //                    GL11.glRotatef(45.0f,0.0f,1.0f,0.0f);

                    c1.DrawCylinder();


                    // head
                    GL11.glColor3f(red[0], red[1], red[2]);
                    GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(red));
                    GL11.glPushMatrix();
                    {
                        GL11.glTranslatef(0.0f, 0.0f, 1.0f);

                        GL11.glTexParameteri(
                                GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                                GL11.GL_REPEAT);
                        Color.white.bind();
                        getTextures().get("new_face2").bind(); //set texture
                        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

                        s15.DrawTexSphere();


//                        sphere.DrawSphere(0.5f, 16, 16);
                        GL11.glPopMatrix();
                    }
                    GL11.glPopMatrix();


                    // left shoulder
                    GL11.glColor3f(blue[0], blue[1], blue[2]);
                    GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(blue));
                    GL11.glPushMatrix();
                    {
                        GL11.glRotatef((float) (LimbRotation * 0.5), 0f, 1f, 0f);

                        GL11.glTranslatef(0.5f, 0.4f, 0.0f);
                        //set texture Parameter
                        GL11.glTexParameteri(
                                GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                                GL11.GL_REPEAT);
                        Color.white.bind();
                        getTextures().get("default_stone").bind();  //set texture
                        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

                        s3.DrawTexSphere();
//                        sphere.DrawSphere(0.25f, 16, 16);


                        // left arm
                        GL11.glColor3f(orange[0], orange[1], orange[2]);
                        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
                        GL11.glPushMatrix();
                        {
                            GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                            GL11.glRotatef(20, 0f, 0f, 1f);
                            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);


                            GL11.glRotatef(LimbRotation, 1.0f, 0.0f, 0.0f);
                            //   GL11.glRotatef(27.5f,0.0f,1.0f,0.0f);

                            c2.DrawCylinder();


                            // left elbow
                            GL11.glColor3f(blue[0], blue[1], blue[2]);
                            GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(blue));
                            GL11.glPushMatrix();
                            {
                                GL11.glRotatef((float) (LimbRotation * -0.5), 0f, 0f, 1f);
                                GL11.glTranslatef(0.0f, 0.0f, 0.75f);
                                GL11.glTexParameteri(
                                        GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                                        GL11.GL_REPEAT);
                                Color.white.bind();
                                getTextures().get("default_stone").bind();
                                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

                                s4.DrawTexSphere();
                                //                                sphere.DrawSphere(0.2f, 16, 16);

                                //left forearm
                                GL11.glColor3f(orange[0], orange[1], orange[2]);
                                GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
                                GL11.glPushMatrix();
                                {
                                    GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                                    GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                                    //   GL11.glRotatef(90.0f,0.0f,1.0f,0.0f);

                                    c3.DrawCylinder();

                                    // left hand
                                    GL11.glColor3f(blue[0], blue[1], blue[2]);
                                    GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(blue));
                                    GL11.glPushMatrix();
                                    {
                                        GL11.glTranslatef(0.0f, 0.0f, 0.75f);
                                        GL11.glTexParameteri(
                                                GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                                                GL11.GL_REPEAT);
                                        Color.white.bind();
                                        getTextures().get("default_stone").bind();
                                        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

                                        s5.DrawTexSphere();
                                        //                                        sphere.DrawSphere(0.2f, 16, 16);


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


                    // to chest
                    GL11.glColor3f(blue[0], blue[1], blue[2]);
                    GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(blue));
                    GL11.glPushMatrix();
                    {
                        GL11.glRotatef((float) (LimbRotation * 0.5), 0f, 1f, 0f);

                        GL11.glTranslatef(-0.5f, 0.4f, 0.0f); // move to right arm
                        GL11.glTexParameteri(
                                GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                                GL11.GL_REPEAT);
                        Color.white.bind();
                        getTextures().get("default_stone").bind();
                        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

                        s6.DrawTexSphere();
                        //                        sphere.DrawSphere(0.25f, 16, 16);


                        // right arm
                        GL11.glColor3f(orange[0], orange[1], orange[2]);
                        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
                        GL11.glPushMatrix();
                        {
                            GL11.glRotatef(-20, 0f, 0f, 1f);
                            GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);


                            GL11.glRotatef(-LimbRotation, 1.0f, 0.0f, 0.0f);
                            //   GL11.glRotatef(27.5f,0.0f,1.0f,0.0f);

                            c4.DrawCylinder();


                            // right elbow
                            GL11.glColor3f(blue[0], blue[1], blue[2]);
                            GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(blue));
                            GL11.glPushMatrix();
                            {
                                GL11.glRotatef((float) (LimbRotation * -0.5), 0f, 0f, 1f);
                                GL11.glTranslatef(0.0f, 0.0f, 0.75f);
                                GL11.glTexParameteri(
                                        GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                                        GL11.GL_REPEAT);
                                Color.white.bind();
                                getTextures().get("default_stone").bind();
                                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

                                s7.DrawTexSphere();
                                //                                sphere.DrawSphere(0.2f, 16, 16);

                                //right forearm
                                GL11.glColor3f(orange[0], orange[1], orange[2]);
                                GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
                                GL11.glPushMatrix();
                                {
                                    GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                                    GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                                    //   GL11.glRotatef(90.0f,0.0f,1.0f,0.0f);

                                    c5.DrawCylinder();

                                    // right hand
                                    GL11.glColor3f(blue[0], blue[1], blue[2]);
                                    GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(blue));
                                    GL11.glPushMatrix();
                                    {
                                        GL11.glTranslatef(0.0f, 0.0f, 0.75f);
                                        GL11.glTexParameteri(
                                                GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                                                GL11.GL_REPEAT);
                                        Color.white.bind();
                                        getTextures().get("default_stone").bind();
                                        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

                                        s8.DrawTexSphere();
                                        //                                        sphere.DrawSphere(0.2f, 16, 16);


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

                    //now chest


                }
                GL11.glPopMatrix();
//                GL11.glRotatef(LimbRotation * 3, 0.0f, 1.0f, 0.0f);
//                GL11.glRotatef((float) (LimbRotation * -0.2), 0.0f, 1.0f, 0.0f);


//                GL11.glPushMatrix();
//                GL11.glTranslatef(0,-1.8f,0);
//                glDisable(GL_LIGHTING);
//                glDisable(GL_TEXTURE_2D);
//                Color.black.bind();
//                shadow.DrawOval();
//                Color.white.bind();
//                glEnable(GL_TEXTURE_2D);
//                glEnable(GL_LIGHTING);
//                GL11.glPopMatrix();
                // pelvis
                // left hip
                GL11.glColor3f(blue[0], blue[1], blue[2]);
                GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(blue));
                GL11.glPushMatrix();
                {
                    GL11.glTranslatef(0.5f, -0.2f, 0.0f);
                    GL11.glTexParameteri(
                            GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                            GL11.GL_REPEAT);
                    Color.white.bind();
                    getTextures().get("default_cobble").bind();
                    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

                    s9.DrawTexSphere();
                    //                    sphere.DrawSphere(0.25f, 16, 16);


                    // left high leg
                    GL11.glColor3f(orange[0], orange[1], orange[2]);
                    GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
                    GL11.glPushMatrix();
                    {
                        GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                        GL11.glRotatef(0.0f, 0.0f, 0.0f, 0.0f);


                        GL11.glRotatef((-LimbRotation / 2) + 90, 1.0f, 0.0f, 0.0f);
                        //   GL11.glRotatef(90.0f,1.0f,0.0f,0.0f);

                        c6.DrawCylinder();


                        // left knee
                        GL11.glColor3f(blue[0], blue[1], blue[2]);
                        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(blue));
                        GL11.glPushMatrix();
                        {
                            GL11.glTranslatef(0.0f, 0.0f, 0.75f);
                            GL11.glRotatef(0.0f, 0.0f, 0.0f, 0.0f);
                            GL11.glTexParameteri(
                                    GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                                    GL11.GL_REPEAT);
                            Color.white.bind();
                            getTextures().get("default_cobble").bind();
                            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

                            s10.DrawTexSphere();
                            //                            sphere.DrawSphere(0.25f, 16, 16);

                            //left low leg
                            GL11.glColor3f(orange[0], orange[1], orange[2]);
                            GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
                            GL11.glPushMatrix();
                            {
                                GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                                // GL11.glRotatef(120.0f,1.0f,0.0f,0.0f);
                                //  GL11.glRotatef(0.0f,0.0f,0.0f,0.0f);

                                c7.DrawCylinder();

                                // left foot
                                GL11.glColor3f(blue[0], blue[1], blue[2]);
                                GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(blue));
                                GL11.glPushMatrix();
                                {
                                    GL11.glTranslatef(0.0f, 0.0f, 0.75f);
                                    GL11.glTexParameteri(
                                            GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                                            GL11.GL_REPEAT);
                                    Color.white.bind();
                                    getTextures().get("default_cobble").bind();
                                    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

                                    s11.DrawTexSphere();

//                                    sphere.DrawSphere(0.3f, 16, 16);

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

                // pelvis
                // right hip
                GL11.glColor3f(blue[0], blue[1], blue[2]);
                GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(blue));
                GL11.glPushMatrix();
                {
                    GL11.glTranslatef(-0.5f, -0.2f, 0.0f);
                    GL11.glTexParameteri(
                            GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                            GL11.GL_REPEAT);
                    Color.white.bind();
                    getTextures().get("default_cobble").bind();
                    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

                    s12.DrawTexSphere();

//                    sphere.DrawSphere(0.25f, 16, 16);


                    // right high leg
                    GL11.glColor3f(orange[0], orange[1], orange[2]);
                    GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
                    GL11.glPushMatrix();
                    {
                        GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                        GL11.glRotatef(0.0f, 0.0f, 0.0f, 0.0f);


                        GL11.glRotatef((LimbRotation / 2) + 90, 1.0f, 0.0f, 0.0f);
                        //   GL11.glRotatef(90.0f,1.0f,0.0f,0.0f);

                        c8.DrawCylinder();


                        // right knee
                        GL11.glColor3f(blue[0], blue[1], blue[2]);
                        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(blue));
                        GL11.glPushMatrix();
                        {
                            GL11.glTranslatef(0.0f, 0.0f, 0.75f);
                            GL11.glRotatef(0.0f, 0.0f, 0.0f, 0.0f);
                            GL11.glTexParameteri(
                                    GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                                    GL11.GL_REPEAT);
                            Color.white.bind();
                            getTextures().get("default_cobble").bind();
                            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

                            s13.DrawTexSphere();
                            //                            sphere.DrawSphere(0.25f, 16, 16);

                            //right low leg
                            GL11.glColor3f(orange[0], orange[1], orange[2]);
                            GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
                            GL11.glPushMatrix();
                            {
                                GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                                // GL11.glRotatef(120.0f,1.0f,0.0f,0.0f);
                                //  GL11.glRotatef(0.0f,0.0f,0.0f,0.0f);

                                c9.DrawCylinder();

                                // left foot
                                GL11.glColor3f(blue[0], blue[1], blue[2]);
                                GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(blue));
                                GL11.glPushMatrix();
                                {
                                    GL11.glTranslatef(0.0f, 0.0f, 0.75f);
                                    GL11.glTexParameteri(
                                            GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                                            GL11.GL_REPEAT);
                                    Color.white.bind();
                                    getTextures().get("default_cobble").bind();
                                    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

                                    s14.DrawTexSphere();
                                    //                                    sphere.DrawSphere(0.3f, 16, 16);

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
//    @Override
//    public void drawShadow() {
//
//    }
}