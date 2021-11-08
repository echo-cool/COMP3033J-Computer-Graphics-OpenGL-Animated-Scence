package Scene.Objects;

import Scene.Scene;
import Scene.base.SceneObject;
import base.GraphicsObjects.Point4f;
import base.GraphicsObjects.Vector4f;
import base.objects3D.DisplayListTexCube;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;

/**
 * @Author: WangYuyang
 * @Date: 2021/11/7-13:47
 * @Project: Assignment3
 * @Package: Scene.Objects
 * @Description:
 **/
public class Book extends SceneObject {
    public static float yRotation = 0;
    public static Boolean hasSomeJumpping = false;
    DisplayListTexCube cube = new DisplayListTexCube();
    private Boolean isJumping = false;
    private float jump_height = 0f;
    private Integer jump_count = 1;

    public Book(Point4f origin, Point4f position, Vector4f scale) {
        super(origin, position, scale);
    }

    public Book(Point4f origin, Point4f position, Vector4f scale, HashMap<String, Texture> textures) {
        super(origin, position, scale, textures);

    }

    public Book(Point4f origin, Point4f position, Vector4f scale, Vector4f rotation, HashMap<String, Texture> textures) {
        super(origin, position, scale, rotation, textures);
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
                    hasSomeJumpping = false;
                }
            });
            thread.start();
            jump_count += 1;

        }
    }

    @Override
    public void draw(Integer frame_delta) {
        if (getPlayerDistance() < 500) {
            jump(500);
            hasSomeJumpping = true;
        } else {
            jump_count = 1;
//            Scene.bookRotate_Speed = 0.2f;
        }
        if (hasSomeJumpping == false && Scene.bookRotate_Speed >= 0.2f) {
            Scene.bookRotate_Speed -= 0.0001f;
        }
        if (Scene.bookRotate_Speed < 0.2f * jump_count && Scene.bookRotate_Speed < 2f) {
            Scene.bookRotate_Speed += 0.2f * jump_count;
        }
//        System.out.println(Scene.bookRotate_Speed);
//        if(jump_count > 3){
//
//        }
        GL11.glTranslatef(0, jump_height, 0);
        Color.white.bind();
        glRotatef(yRotation, 0, 1, 0);
        getTextures().get("warped_door_bottom").bind();
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glPushMatrix();
        {
            glRotatef(-45, 1, 0, 0);
            GL11.glPushMatrix();
            {
                glTranslatef(-0.4f, 0, 0);
                glRotatef(45, 0, 0, 1);
                glScalef(0.1f, 0.65f, 0.65f);
                cube.DrawTexCube();

            }
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            {
                glTranslatef(0.4f, 0, 0);
                glRotatef(-45, 0, 0, 1);
                glScalef(0.1f, 0.65f, 0.65f);
                cube.DrawTexCube();

            }
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
    }

    @Override
    public void drawShadow() {

    }
}
