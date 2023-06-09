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
 * @Date: 2021/11/2-15:06
 * @Project: Assignment3
 * @Package: Scene
 * @Description:
 **/
public class TNT_Door extends SceneObject {

    DisplayListTexCube cube1 = new DisplayListTexCube();

    public TNT_Door(Point4f origin, Point4f position, Vector4f scale) {
        super(origin, position, scale);
    }

    public TNT_Door(Point4f origin, Point4f position, Vector4f scale, HashMap<String, Texture> textures) {
        super(origin, position, scale, textures);
    }


    @Override
    public void draw(Integer frame_delta) {
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        Color.white.bind();
        getTextures().get("tnt_side").bind();
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

    }

    @Override
    public void drawShadow() {

    }
}
