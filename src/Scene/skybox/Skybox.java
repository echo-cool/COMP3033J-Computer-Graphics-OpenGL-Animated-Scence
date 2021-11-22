package Scene.skybox;

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
 * @Date: 2021/11/5-22:43
 * @Project: Assignment3
 * @Package: Scene.skybox
 * @Description:
 **/
public class Skybox extends SceneObject {
    private final DisplayListTexCube cube = new DisplayListTexCube();
    int face1 = glGenLists(1);
    int face2 = glGenLists(1);
    int face3 = glGenLists(1);
    int face4 = glGenLists(1);
    int face5 = glGenLists(1);
    int face6 = glGenLists(1);


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
        glNewList(face1, GL11.GL_COMPILE);
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
        glEndList();

        glNewList(face2, GL11.GL_COMPILE);
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
        glEndList();

        glNewList(face3, GL11.GL_COMPILE);
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
        glEndList();

        glNewList(face4, GL11.GL_COMPILE);
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
        glEndList();

        glNewList(face5, GL11.GL_COMPILE);
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
        glEndList();

        glNewList(face6, GL11.GL_COMPILE);
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
        glEndList();

    }


    @Override
    public void draw(Integer delta) {

//        ;
        GL11.glTexParameteri(
                GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                GL11.GL_REPEAT);
        GL11.glTexParameteri(
                GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S,
                GL11.GL_REPEAT);
        Color.white.bind();
        //bind texture
        glDisable(GL_CULL_FACE);
        getTextures().get("skybox/down").bind();
        glCallList(face1);

        getTextures().get("skybox/front").bind();
        glCallList(face2);

        getTextures().get("skybox/right").bind();
        glCallList(face3);

        getTextures().get("skybox/left").bind();
        glCallList(face4);

        getTextures().get("skybox/back").bind();
        glCallList(face5);

        getTextures().get("skybox/up").bind();
        glCallList(face6);


        glEnable(GL_CULL_FACE);

    }

    @Override
    public void drawShadow() {

    }
}
