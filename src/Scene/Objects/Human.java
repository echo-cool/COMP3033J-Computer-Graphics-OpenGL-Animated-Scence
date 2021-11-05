package Scene.Objects;

import Scene.base.SceneObject;
import base.GraphicsObjects.Point4f;
import base.GraphicsObjects.Utils;
import base.GraphicsObjects.Vector4f;
import base.objects3D.Cylinder;
import base.objects3D.Sphere;
import base.objects3D.TexSphere;
import main.Camera;
import main.Engine;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import java.util.HashMap;

public class Human extends SceneObject {


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

    private float delta;
    private Boolean isWalking = false;
    private long walkStartTime;
    private int stopCount = 0;
    private int angle = 0;
    public static int angle_target = 0;

    private Boolean isJumping = false;
    private float jump_height = 0f;

    public Human(Point4f origin, Point4f position, Vector4f scale) {
        super(origin, position, scale);
    }

    public Human(Point4f origin, Point4f position, Vector4f scale, HashMap<String, Texture> textures) {
        super(origin, position, scale, textures);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if(Math.abs(angle - angle_target) > 200){
                        angle = angle_target;
                    }

                    if (angle > angle_target) {
                        angle -= 1f;
                    } else if (angle < angle_target) {
                        angle += 1f;
                    }
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    public float getDelta() {
        return delta;
    }

    public void setDelta(float delta) {
        this.delta = delta;
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
        this.angle_target = (int)angle;
    }

    @Override
    public void draw() {

        checkInput();

        GL11.glTranslatef(0, jump_height, 0);
        Boolean GoodAnimation = true;
        float theta_face = (float) (delta * 2 * Math.PI);
//        float angle = -(float) (180 * (theta_face) / Math.PI);

        GL11.glRotatef(angle + 180, 0, 1, 0);


        float theta = (float) (delta * 2 * Math.PI) * 8;
        //a variable for anim sync
        float LimbRotation;
        //two animation
        if (GoodAnimation) {
            LimbRotation = (float) Math.sin(theta) * 60;
        } else {
            LimbRotation = 0;
        }

        //a sphere for drawing
        Sphere sphere = new Sphere();
        //a sphere with texture
        TexSphere texSphere = new TexSphere();
        //a cylinder for drawing
        Cylinder cylinder = new Cylinder();

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
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            //set texture Parameters
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            texSphere.DrawTexSphere(0.5f, 32, 32, getTextures().get("wool_pink"));
            //Draw finish Disable TEXTURE
            GL11.glDisable(GL11.GL_TEXTURE_2D);


//            sphere.DrawSphere(0.5f, 32, 32); // pelvis sphere

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
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
                texSphere.DrawTexSphere(0.5f, 32, 32, getTextures().get("tnt_side"));
                GL11.glDisable(GL11.GL_TEXTURE_2D); //disable texture


//                sphere.DrawSphere(0.5f, 32, 32);// chest sphere
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
                    cylinder.DrawCylinder(0.15f, 0.7f, 32);


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
                        getTextures().get("awesomeface").bind(); //set texture
                        GL11.glEnable(GL11.GL_TEXTURE_2D);
                        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
                        texSphere.DrawTexSphere(0.5f, 32, 32, getTextures().get("awesomeface"));
                        GL11.glDisable(GL11.GL_TEXTURE_2D);


//                        sphere.DrawSphere(0.5f, 32, 32);
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
                        GL11.glEnable(GL11.GL_TEXTURE_2D);
                        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
                        texSphere.DrawTexSphere(0.25f, 32, 32, getTextures().get("default_stone"));
                        GL11.glDisable(GL11.GL_TEXTURE_2D);
//                        sphere.DrawSphere(0.25f, 32, 32);


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
                            cylinder.DrawCylinder(0.15f, 0.7f, 32);


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
                                GL11.glEnable(GL11.GL_TEXTURE_2D);
                                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
                                texSphere.DrawTexSphere(0.2f, 32, 32, getTextures().get("default_stone"));
                                GL11.glDisable(GL11.GL_TEXTURE_2D);
//                                sphere.DrawSphere(0.2f, 32, 32);

                                //left forearm
                                GL11.glColor3f(orange[0], orange[1], orange[2]);
                                GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
                                GL11.glPushMatrix();
                                {
                                    GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                                    GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                                    //   GL11.glRotatef(90.0f,0.0f,1.0f,0.0f);
                                    cylinder.DrawCylinder(0.1f, 0.7f, 32);

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
                                        GL11.glEnable(GL11.GL_TEXTURE_2D);
                                        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
                                        texSphere.DrawTexSphere(0.2f, 32, 32, getTextures().get("default_stone"));
                                        GL11.glDisable(GL11.GL_TEXTURE_2D);
//                                        sphere.DrawSphere(0.2f, 32, 32);


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
                        GL11.glEnable(GL11.GL_TEXTURE_2D);
                        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
                        texSphere.DrawTexSphere(0.25f, 32, 32, getTextures().get("default_stone"));
                        GL11.glDisable(GL11.GL_TEXTURE_2D);
//                        sphere.DrawSphere(0.25f, 32, 32);


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
                            cylinder.DrawCylinder(0.15f, 0.7f, 32);


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
                                GL11.glEnable(GL11.GL_TEXTURE_2D);
                                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
                                texSphere.DrawTexSphere(0.2f, 32, 32, getTextures().get("default_stone"));
                                GL11.glDisable(GL11.GL_TEXTURE_2D);
//                                sphere.DrawSphere(0.2f, 32, 32);

                                //right forearm
                                GL11.glColor3f(orange[0], orange[1], orange[2]);
                                GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
                                GL11.glPushMatrix();
                                {
                                    GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                                    GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                                    //   GL11.glRotatef(90.0f,0.0f,1.0f,0.0f);
                                    cylinder.DrawCylinder(0.1f, 0.7f, 32);

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
                                        GL11.glEnable(GL11.GL_TEXTURE_2D);
                                        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
                                        texSphere.DrawTexSphere(0.2f, 32, 32, getTextures().get("default_stone"));
                                        GL11.glDisable(GL11.GL_TEXTURE_2D);
//                                        sphere.DrawSphere(0.2f, 32, 32);


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
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
                    texSphere.DrawTexSphere(0.25f, 32, 32, getTextures().get("default_cobble"));
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
//                    sphere.DrawSphere(0.25f, 32, 32);


                    // left high leg
                    GL11.glColor3f(orange[0], orange[1], orange[2]);
                    GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
                    GL11.glPushMatrix();
                    {
                        GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                        GL11.glRotatef(0.0f, 0.0f, 0.0f, 0.0f);


                        GL11.glRotatef((-LimbRotation / 2) + 90, 1.0f, 0.0f, 0.0f);
                        //   GL11.glRotatef(90.0f,1.0f,0.0f,0.0f);
                        cylinder.DrawCylinder(0.15f, 0.7f, 32);


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
                            GL11.glEnable(GL11.GL_TEXTURE_2D);
                            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
                            texSphere.DrawTexSphere(0.25f, 32, 32, getTextures().get("default_cobble"));
                            GL11.glDisable(GL11.GL_TEXTURE_2D);
//                            sphere.DrawSphere(0.25f, 32, 32);

                            //left low leg
                            GL11.glColor3f(orange[0], orange[1], orange[2]);
                            GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
                            GL11.glPushMatrix();
                            {
                                GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                                // GL11.glRotatef(120.0f,1.0f,0.0f,0.0f);
                                //  GL11.glRotatef(0.0f,0.0f,0.0f,0.0f);
                                cylinder.DrawCylinder(0.15f, 0.7f, 32);

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
                                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                                    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
                                    texSphere.DrawTexSphere(0.3f, 32, 32, getTextures().get("default_cobble"));
                                    GL11.glDisable(GL11.GL_TEXTURE_2D);

//                                    sphere.DrawSphere(0.3f, 32, 32);

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
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
                    texSphere.DrawTexSphere(0.25f, 32, 32, getTextures().get("default_cobble"));
                    GL11.glDisable(GL11.GL_TEXTURE_2D);

//                    sphere.DrawSphere(0.25f, 32, 32);


                    // right high leg
                    GL11.glColor3f(orange[0], orange[1], orange[2]);
                    GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
                    GL11.glPushMatrix();
                    {
                        GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                        GL11.glRotatef(0.0f, 0.0f, 0.0f, 0.0f);


                        GL11.glRotatef((LimbRotation / 2) + 90, 1.0f, 0.0f, 0.0f);
                        //   GL11.glRotatef(90.0f,1.0f,0.0f,0.0f);
                        cylinder.DrawCylinder(0.15f, 0.7f, 32);


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
                            GL11.glEnable(GL11.GL_TEXTURE_2D);
                            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
                            texSphere.DrawTexSphere(0.25f, 32, 32, getTextures().get("default_cobble"));
                            GL11.glDisable(GL11.GL_TEXTURE_2D);
//                            sphere.DrawSphere(0.25f, 32, 32);

                            //right low leg
                            GL11.glColor3f(orange[0], orange[1], orange[2]);
                            GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
                            GL11.glPushMatrix();
                            {
                                GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                                // GL11.glRotatef(120.0f,1.0f,0.0f,0.0f);
                                //  GL11.glRotatef(0.0f,0.0f,0.0f,0.0f);
                                cylinder.DrawCylinder(0.15f, 0.7f, 32);

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
                                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                                    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
                                    texSphere.DrawTexSphere(0.3f, 32, 32, getTextures().get("default_cobble"));
                                    GL11.glDisable(GL11.GL_TEXTURE_2D);
//                                    sphere.DrawSphere(0.3f, 32, 32);

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

    private void checkInput() {
        Boolean KEY_W = Keyboard.isKeyDown(Keyboard.KEY_W);
        Boolean KEY_A = Keyboard.isKeyDown(Keyboard.KEY_A);
        Boolean KEY_S = Keyboard.isKeyDown(Keyboard.KEY_S);
        Boolean KEY_D = Keyboard.isKeyDown(Keyboard.KEY_D);

        Human human = (Human) this;
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
 
	/*
	 
	 
}

	*/
	 