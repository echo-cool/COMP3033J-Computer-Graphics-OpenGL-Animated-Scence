package Scene.skybox;

import Scene.base.SceneObject;
import base.GraphicsObjects.Point4f;
import base.GraphicsObjects.Vector4f;
import base.objects3D.TexCube;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import java.util.HashMap;

/**
 * @Author: WangYuyang
 * @Date: 2021/11/5-22:43
 * @Project: Assignment3
 * @Package: Scene.skybox
 * @Description:
 **/
public class Skybox extends SceneObject {
    private TexCube cube = new TexCube();

    public Skybox(Point4f origin, Point4f position, Vector4f scale) {
        super(origin, position, scale);
        init();
    }

    public Skybox(Point4f origin, Point4f position, Vector4f scale, HashMap<String, Texture> textures) {
        super(origin, position, scale, textures);
        init();
    }

    public Skybox(Point4f origin, Point4f position, Vector4f scale, Vector4f rotation, HashMap<String, Texture> textures) {
        super(origin, position, scale, rotation, textures);
        init();
    }

    private void init() {

    }


    @Override
    public void draw() {

        GL11.glTexParameteri(
                GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                GL11.GL_REPEAT);
        GL11.glTexParameteri(
                GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S,
                GL11.GL_REPEAT);
        Color.white.bind();
        //bind texture

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
        getTextures().get("skybox/down").bind();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex3f(-0.5f, -0.5f, 0.5f);
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex3f(0.5f, -0.5f, 0.5f);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex3f(0.5f, -0.5f, -0.5f);
        GL11.glEnd();

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
        getTextures().get("skybox/front").bind();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex3f(0.5f, -0.5f, -0.5f);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex3f(-0.5f, 0.5f, -0.5f);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex3f(0.5f, 0.5f, -0.5f);
        GL11.glEnd();

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
        getTextures().get("skybox/right").bind();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex3f(-0.5f, -0.5f, 0.5f);
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex3f(-0.5f, 0.5f, 0.5f);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex3f(-0.5f, 0.5f, -0.5f);
        GL11.glEnd();

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
        getTextures().get("skybox/left").bind();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex3f(0.5f, -0.5f, 0.5f);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex3f(0.5f, -0.5f, -0.5f);
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex3f(0.5f, 0.5f, -0.5f);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex3f(0.5f, 0.5f, 0.5f);
        GL11.glEnd();

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
        getTextures().get("skybox/back").bind();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex3f(-0.5f, -0.5f, 0.5f);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex3f(0.5f, -0.5f, 0.5f);
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex3f(0.5f, 0.5f, 0.5f);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex3f(-0.5f, 0.5f, 0.5f);
        GL11.glEnd();

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
        getTextures().get("skybox/up").bind();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex3f(-0.5f, 0.5f, -0.5f);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex3f(-0.5f, 0.5f, 0.5f);
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex3f(0.5f, 0.5f, 0.5f);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex3f(0.5f, 0.5f, -0.5f);
        GL11.glEnd();


        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }
}
