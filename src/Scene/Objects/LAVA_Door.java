package Scene.Objects;

import base.GraphicsObjects.Point4f;
import base.GraphicsObjects.Vector4f;
import Scene.base.SceneObject;
import base.objects3D.TexCube;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import java.util.HashMap;

/**
 * @Author: WangYuyang
 * @Date: 2021/11/2-16:34
 * @Project: Assignment3
 * @Package: Scene.Objects
 * @Description:
 **/
public class LAVA_Door extends SceneObject {
    TexCube cube = new TexCube();
    public LAVA_Door(Point4f origin, Point4f position, Vector4f scale) {
        super(origin, position, scale);
    }

    public LAVA_Door(Point4f origin, Point4f position, Vector4f scale, HashMap<String, Texture> textures) {
        super(origin, position, scale, textures);
    }

    @Override
    public void draw() {

        Color.white.bind();
        getTextures().get("default_lava").bind();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTranslatef(-2f, 0f, 0);
        cube.DrawTexCube();
        //LAVA
        GL11.glPushMatrix();
        {
            //Go UP
            GL11.glTranslatef(0f, 2f, 0);
            cube.DrawTexCube();
            GL11.glPushMatrix();
            {
                GL11.glTranslatef(0f, 2f, 0);
                cube.DrawTexCube();
                GL11.glPushMatrix();
                {
                    GL11.glTranslatef(0f, 2f, 0);
                    cube.DrawTexCube();
                    GL11.glPushMatrix();
                    {
                        GL11.glTranslatef(0f, 2f, 0);
                        cube.DrawTexCube();
                        GL11.glPushMatrix();
                        {
                            //go left (TOP lava)
                            GL11.glTranslatef(-2f, 0f, 0);
                            cube.DrawTexCube();
                            GL11.glPushMatrix();
                            {
                                GL11.glTranslatef(-2f, 0f, 0);
                                cube.DrawTexCube();
                                GL11.glPushMatrix();
                                {
                                    GL11.glTranslatef(-2f, 0f, 0);
                                    cube.DrawTexCube();
                                    GL11.glPushMatrix();
                                    {
                                        //Go DOWN
                                        GL11.glTranslatef(0f, -2f, 0);
                                        cube.DrawTexCube();
                                        GL11.glPushMatrix();
                                        {
                                            GL11.glTranslatef(0f, -2f, 0);
                                            cube.DrawTexCube();
                                            GL11.glPushMatrix();
                                            {
                                                GL11.glTranslatef(0f, -2f, 0);
                                                cube.DrawTexCube();
                                                GL11.glPushMatrix();
                                                {
                                                    GL11.glTranslatef(0f, -2f, 0);
                                                    cube.DrawTexCube();
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
                    GL11.glPopMatrix();
                }
                GL11.glPopMatrix();

            }
            GL11.glPopMatrix();

        }
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }
}
