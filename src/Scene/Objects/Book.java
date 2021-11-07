package Scene.Objects;

import Scene.base.ParticleEmitter;
import Scene.base.ParticleEmitterBuilder;
import Scene.base.SceneObject;
import base.GraphicsObjects.Point4f;
import base.GraphicsObjects.Vector4f;
import base.objects3D.DisplayListTexCube;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import java.util.HashMap;
import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

/**
 * @Author: WangYuyang
 * @Date: 2021/11/7-13:47
 * @Project: Assignment3
 * @Package: Scene.Objects
 * @Description:
 **/
public class Book extends SceneObject {
    DisplayListTexCube cube = new DisplayListTexCube();
    private float yRotation = 0;

    public Book(Point4f origin, Point4f position, Vector4f scale) {
        super(origin, position, scale);
    }

    public Book(Point4f origin, Point4f position, Vector4f scale, HashMap<String, Texture> textures) {
        super(origin, position, scale, textures);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    yRotation += 0.1f;
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }

    public Book(Point4f origin, Point4f position, Vector4f scale, Vector4f rotation, HashMap<String, Texture> textures) {
        super(origin, position, scale, rotation, textures);
    }


    @Override
    public void draw(Integer frame_delta) {
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
}
