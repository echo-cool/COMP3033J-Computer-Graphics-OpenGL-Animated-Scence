package Scene.Objects;

import Scene.base.IDrawListener;
import base.GraphicsObjects.Point4f;
import base.GraphicsObjects.Vector4f;
import Scene.base.SceneObject;
import base.objects3D.TexCube;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import java.util.HashMap;

import static org.lwjgl.opengl.GL11.glColor3f;

/**
 * @Author: WangYuyang
 * @Date: 2021/11/2-16:01
 * @Project: Assignment3
 * @Package: Scene.Objects
 * @Description:
 **/
public class Ground extends SceneObject {
    private TexCube cube = new TexCube();

    public Ground(Point4f origin, Point4f position, Vector4f scale) {
        super(origin, position, scale);
    }

    public Ground(Point4f origin, Point4f position, Vector4f scale, HashMap<String, Texture> textures) {
        super(origin, position, scale, textures);
    }

    public Ground(Point4f origin, Point4f position, Vector4f scale, Vector4f rotation, HashMap<String, Texture> textures) {
        super(origin, position, scale, rotation, textures);
    }


    @Override
    public void draw() {
        ;
        GL11.glTexParameteri(
                GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                GL11.GL_REPEAT);
        GL11.glTexParameteri(
                GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S,
                GL11.GL_REPEAT);
        //bind texture
        Color.white.bind();
        getTextures().get("default_dirt").bind();

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        cube.DrawTexCube(10f);
        ;

    }
}
