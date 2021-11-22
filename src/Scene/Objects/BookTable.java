package Scene.Objects;

import Scene.Scene;
import Scene.base.SceneObject;
import base.GraphicsObjects.Point4f;
import base.GraphicsObjects.Vector4f;
import base.objects3D.DisplayListOval;
import base.objects3D.DisplayListTexCube;
import main.Camera;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;

/**
 * @Author: WangYuyang
 * @Date: 2021/11/7-13:40
 * @Project: Assignment3
 * @Package: Scene.Objects
 * @Description:
 **/
public class BookTable extends SceneObject {
    DisplayListTexCube cube = new DisplayListTexCube();
    DisplayListOval displayListOval = new DisplayListOval(2f, 32);
    private float scale_number = 0f;


    public BookTable(Point4f origin, Point4f position, Vector4f scale) {
        super(origin, position, scale);
    }

    public BookTable(Point4f origin, Point4f position, Vector4f scale, HashMap<String, Texture> textures) {
        super(origin, position, scale, textures);
    }

    public BookTable(Point4f origin, Point4f position, Vector4f scale, Vector4f rotation, HashMap<String, Texture> textures) {
        super(origin, position, scale, rotation, textures);
    }

    @Override
    public void draw(Integer frame_delta) {
        Color.white.bind();
        if (Book.yRotation % 360 > 180) {
            getTextures().get("end_portal_frame_top").bind();
        } else {
            getTextures().get("enchanting_table_top").bind();
        }
        if (Scene.bookRotate_Speed > 1.8f) {
            if (scale_number < 40f) {
                scale_number += 0.5f;
            }
        } else {
            if (scale_number > 0f) {
                scale_number -= 0.5f;
            }
        }
        if (!Camera.loading_finished) {
            scale_number = 0;
        }


        GL11.glTexParameteri(
                GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                GL11.GL_CLAMP);
        GL11.glTexParameteri(
                GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S,
                GL11.GL_CLAMP);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        GL11.glPushMatrix();
        {
            GL11.glTranslatef(0, scale_number, 0);
            GL11.glScalef(0.5f, scale_number, 0.5f);
            cube.DrawTexCube();
        }
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        {
            cube.DrawTexCube();
        }
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        {
            glScalef(1 / getScale().x, 1 / getScale().y, 1 / getScale().z);
            glTranslatef(-240, -getOrigin().y + 2f, -240);
            glScalef(getScale().x, getScale().y, getScale().z);
            GL11.glRotatef(-45, 0, 1, 0);
            GL11.glScalef(2f, 0f, 1f);
            glDisable(GL_LIGHTING);
            glDisable(GL_TEXTURE_2D);
            Color.black.bind();
            displayListOval.DrawOval();
            Color.white.bind();
            glEnable(GL_TEXTURE_2D);
            glEnable(GL_LIGHTING);
        }
        GL11.glPopMatrix();
    }

    @Override
    public void drawShadow() {


    }
}
