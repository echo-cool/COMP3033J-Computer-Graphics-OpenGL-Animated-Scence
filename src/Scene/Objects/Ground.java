package Scene.Objects;

import Scene.base.SceneObject;
import base.GraphicsObjects.Point4f;
import base.GraphicsObjects.Vector4f;
import base.objects3D.DisplayListTexCube;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import java.util.HashMap;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;

/**
 * @Author: WangYuyang
 * @Date: 2021/11/2-16:01
 * @Project: Assignment3
 * @Package: Scene.Objects
 * @Description:
 **/
public class Ground extends SceneObject {
    DisplayListTexCube cube = new DisplayListTexCube(30);

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
    public void draw(Integer frame_delta) {
        GL11.glTexParameteri(
                GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                GL11.GL_REPEAT);
        GL11.glTexParameteri(
                GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S,
                GL11.GL_REPEAT);
        //bind texture
//        Color.blue.bind();
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        getTextures().get("wood").bind();
//
//        glBindTexture(GL11.GL_TEXTURE_2D, Engine.shadowTexture);
//        glDisable(GL_LIGHTING);
        cube.DrawTexCube();
//        glBindTexture(GL11.GL_TEXTURE_2D, 0);
        //        glEnable(GL_LIGHTING);

    }

    @Override
    public void drawShadow() {

    }
}
