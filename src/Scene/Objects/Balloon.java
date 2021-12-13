package Scene.Objects;

import Scene.Scene;
import Scene.base.SceneObject;
import base.GraphicsObjects.Point4f;
import base.GraphicsObjects.Vector4f;
import base.objects3D.DisplayListTexCube;
import base.objects3D.DisplayListTexSphere;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;

/**
 * @Author: WangYuyang
 * @Date: 2021/12/13-12:39
 * @Project: Assignment3
 * @Package: Scene.Objects
 * @Description:
 **/
public class Balloon extends SceneObject {
    DisplayListTexCube cube = new DisplayListTexCube();
    DisplayListTexSphere sphere = new DisplayListTexSphere(1f, 16, 16, getTextures().get("wool_pink"));
    private Boolean openDoor = false;

    public Balloon(Point4f origin, Point4f position, Vector4f scale) {
        super(origin, position, scale);
    }

    public Balloon(Point4f origin, Point4f position, Vector4f scale, HashMap<String, Texture> textures) {
        super(origin, position, scale, textures);
    }

    public Balloon(Point4f origin, Point4f position, Vector4f scale, Vector4f rotation, HashMap<String, Texture> textures) {
        super(origin, position, scale, rotation, textures);
    }

    @Override
    public void draw(Integer delta) {
        if (Keyboard.isKeyDown(Keyboard.KEY_RETURN) || Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            if (!Scene.player.getJumping()) {
                setPosition(new Point4f(
                        0,
                        Scene.player.getPosition().y / 10,
                        0,
                        0
                ));
                setShadowOffset(new Vector4f(
                        -Scene.player.getPosition().y * 900,
                        0,
                        -Scene.player.getPosition().y * 900,
                        0
                ));
            }
        }
        float distance = getWorldPosition().MinusPoint(Scene.player.getWorldPosition()).length();
        if (distance > 2375 && distance < 2500) {
            openDoor = true;
        } else {
            openDoor = false;
        }
        Color.white.bind();
        GL11.glBindTexture(GL_TEXTURE_2D, 0);
        GL11.glTexParameteri(
                GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                GL11.GL_CLAMP);
        GL11.glTexParameteri(
                GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S,
                GL11.GL_CLAMP);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glDisable(GL_CULL_FACE);
        getTextures().get("earthspace").bind();
        sphere.DrawTexSphere();

        getTextures().get("default_dirt").bind();
        glPushMatrix();
        {
            glTranslatef(0, -2.75f, 0);
            glScalef(0.3f, 0.01f, 0.3f);
            cube.DrawTexCube();
        }
        glPopMatrix();
        glPushMatrix();
        {
            glTranslatef(0.3f, 0, 0);
            glTranslatef(0, -2.65f, 0);
            glRotatef(90, 0, 0, 1f);
            glScalef(0.1f, 0.01f, 0.3f);

            cube.DrawTexCube();
        }
        glPopMatrix();
        glPushMatrix();
        {
            glTranslatef(-0.3f, 0, 0);
            glTranslatef(0, -2.65f, 0);
            glRotatef(90, 0, 0, 1f);
            glScalef(0.1f, 0.01f, 0.3f);
            cube.DrawTexCube();
        }
        glPopMatrix();
        glPushMatrix();
        {
            glTranslatef(0f, -0f, 0.3f);
            glTranslatef(0, -2.65f, 0);
            glRotatef(90, 0, 0, 1f);
            glRotatef(90, 1, 0, 0f);
            glScalef(0.1f, 0.01f, 0.3f);
            cube.DrawTexCube();
        }
        glPopMatrix();
        glPushMatrix();
        {
            glTranslatef(0f, -0f, -0.3f);
            glTranslatef(0, -2.65f, 0);
            glRotatef(90, 0, 0, 1f);
            glRotatef(90, 1, 0, 0f);
            glScalef(0.1f, 0.01f, 0.3f);
            if (!openDoor)
                cube.DrawTexCube();
        }
        glPopMatrix();
        GL11.glBindTexture(GL_TEXTURE_2D, 0);
        getTextures().get("default_cobble").bind();

        glPushMatrix();
        {
            glTranslatef(0.35f, 0f, 0.62f);
            glTranslatef(0, -1.19f, 0);
            glRotatef(20, 1, 1, 0f);
            glScalef(0.01f, 1.4f, 0.01f);
            cube.DrawTexCube();
        }
        glPopMatrix();
        glPushMatrix();
        {
            glRotatef(90, 0, 1, 0f);
            glTranslatef(0.35f, 0f, 0.62f);
            glTranslatef(0, -1.19f, 0);
            glRotatef(20, 1, 1, 0f);
            glScalef(0.01f, 1.4f, 0.01f);
            cube.DrawTexCube();
        }
        glPopMatrix();
        glPushMatrix();
        {
            glRotatef(180, 0, 1, 0f);
            glTranslatef(0.35f, 0f, 0.62f);
            glTranslatef(0, -1.19f, 0);
            glRotatef(20, 1, 1, 0f);
            glScalef(0.01f, 1.4f, 0.01f);
            cube.DrawTexCube();
        }
        glPopMatrix();
        glPushMatrix();
        {
            glRotatef(270, 0, 1, 0f);
            glTranslatef(0.35f, 0f, 0.62f);
            glTranslatef(0, -1.19f, 0);
            glRotatef(20, 1, 1, 0f);
            glScalef(0.01f, 1.4f, 0.01f);
            cube.DrawTexCube();
        }
        glPopMatrix();
        GL11.glBindTexture(GL_TEXTURE_2D, 0);


        glEnable(GL_CULL_FACE);
        Color.white.bind();
    }

}
