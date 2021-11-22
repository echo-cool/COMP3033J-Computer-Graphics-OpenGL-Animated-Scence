package Scene.Objects;

import Scene.base.SceneObject;
import base.GraphicsObjects.Point4f;
import base.GraphicsObjects.Vector4f;
import base.objects3D.DisplayListTexCube;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import java.util.HashMap;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;

/**
 * @Author: WangYuyang
 * @Date: 2021/11/7-13:40
 * @Project: Assignment3
 * @Package: Scene.Objects
 * @Description:
 **/
public class BookShelf extends SceneObject {
    DisplayListTexCube cube = new DisplayListTexCube();

    public BookShelf(Point4f origin, Point4f position, Vector4f scale) {
        super(origin, position, scale);
    }

    public BookShelf(Point4f origin, Point4f position, Vector4f scale, HashMap<String, Texture> textures) {
        super(origin, position, scale, textures);
    }

    public BookShelf(Point4f origin, Point4f position, Vector4f scale, Vector4f rotation, HashMap<String, Texture> textures) {
        super(origin, position, scale, rotation, textures);
    }

    @Override
    public void draw(Integer frame_delta) {
        Color.white.bind();
        getTextures().get("bookshelf").bind();
        GL11.glTexParameteri(
                GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                GL11.GL_CLAMP);
        GL11.glTexParameteri(
                GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S,
                GL11.GL_CLAMP);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        GL11.glTranslatef(0, 0, 5);
        GL11.glPushMatrix();
        {
            GL11.glPushMatrix();
            {
                cube.DrawTexCube();
                GL11.glTranslatef(0, 2, 0);
                cube.DrawTexCube();
                GL11.glTranslatef(0, 2, 0);
                cube.DrawTexCube();
            }
            GL11.glPopMatrix();


            GL11.glPushMatrix();
            {
                GL11.glTranslatef(-2, 0, 0);
                cube.DrawTexCube();
                GL11.glTranslatef(0, 2, 0);
                cube.DrawTexCube();
                GL11.glTranslatef(0, 2, 0);
                cube.DrawTexCube();
            }
            GL11.glPopMatrix();

            GL11.glPushMatrix();
            {
                GL11.glTranslatef(-4, 0, 0);
                cube.DrawTexCube();
                GL11.glPushMatrix();
                {
                    GL11.glTranslatef(0, 0, -2);
                    cube.DrawTexCube();
                    GL11.glTranslatef(0, 2, 0);
                    cube.DrawTexCube();
                    GL11.glTranslatef(0, 2, 0);
                    cube.DrawTexCube();
                }
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                {
                    GL11.glTranslatef(0, 0, -4);
                    cube.DrawTexCube();
                    GL11.glTranslatef(0, 2, 0);
                    cube.DrawTexCube();
                    GL11.glTranslatef(0, 2, 0);
                    cube.DrawTexCube();
                }
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                {
                    GL11.glTranslatef(0, 0, -6);
                    cube.DrawTexCube();
                    GL11.glTranslatef(0, 2, 0);
                    cube.DrawTexCube();
                    GL11.glTranslatef(0, 2, 0);
                    cube.DrawTexCube();
                }
                GL11.glPopMatrix();
                GL11.glTranslatef(0, 2, 0);
                cube.DrawTexCube();
                GL11.glTranslatef(0, 2, 0);
                cube.DrawTexCube();

            }
            GL11.glPopMatrix();

            GL11.glPushMatrix();
            {
                GL11.glTranslatef(2, 0, 0);
                cube.DrawTexCube();
                GL11.glTranslatef(0, 2, 0);
                cube.DrawTexCube();
                GL11.glTranslatef(0, 2, 0);
                cube.DrawTexCube();
            }
            GL11.glPopMatrix();

            GL11.glPushMatrix();
            {
                GL11.glTranslatef(4, 0, 0);
                cube.DrawTexCube();
                GL11.glPushMatrix();
                {
                    GL11.glTranslatef(0, 0, -2);
                    cube.DrawTexCube();
                    GL11.glTranslatef(0, 2, 0);
                    cube.DrawTexCube();
                    GL11.glTranslatef(0, 2, 0);
                    cube.DrawTexCube();
                }
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                {
                    GL11.glTranslatef(0, 0, -4);
                    cube.DrawTexCube();
                    GL11.glTranslatef(0, 2, 0);
                    cube.DrawTexCube();
                    GL11.glTranslatef(0, 2, 0);
                    cube.DrawTexCube();
                }
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                {
                    GL11.glTranslatef(0, 0, -6);
                    cube.DrawTexCube();
                    GL11.glTranslatef(0, 2, 0);
                    cube.DrawTexCube();
                    GL11.glTranslatef(0, 2, 0);
                    cube.DrawTexCube();
                }
                GL11.glPopMatrix();
                GL11.glTranslatef(0, 2, 0);
                cube.DrawTexCube();
                GL11.glTranslatef(0, 2, 0);
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
