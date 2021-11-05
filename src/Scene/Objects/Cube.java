package Scene.Objects;

import Scene.base.SceneObject;
import base.GraphicsObjects.Point4f;
import base.GraphicsObjects.Vector4f;
import base.objects3D.TexCube;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import java.util.HashMap;

/**
 * @Author: WangYuyang
 * @Date: 2021/11/5-23:09
 * @Project: Assignment3
 * @Package: Scene.Objects
 * @Description:
 **/
public class Cube extends SceneObject {
    private TexCube cube = new TexCube();

    public Cube(Point4f origin, Point4f position, Vector4f scale) {
        super(origin, position, scale);
    }

    public Cube(Point4f origin, Point4f position, Vector4f scale, HashMap<String, Texture> textures) {
        super(origin, position, scale, textures);
    }

    public Cube(Point4f origin, Point4f position, Vector4f scale, Vector4f rotation, HashMap<String, Texture> textures) {
        super(origin, position, scale, rotation, textures);
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
        getTextures().get("default_dirt").bind();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        cube.DrawTexCube(1f);
        GL11.glDisable(GL11.GL_TEXTURE_2D);

    }
}
