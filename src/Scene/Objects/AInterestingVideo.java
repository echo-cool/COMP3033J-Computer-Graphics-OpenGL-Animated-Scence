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
 * @Date: 2021/11/5-23:09
 * @Project: Assignment3
 * @Package: Scene.Objects
 * @Description:
 **/
public class AInterestingVideo extends SceneObject {
    private DisplayListTexCube cube = new DisplayListTexCube();
    private Integer counter = 1;
    public AInterestingVideo(Point4f origin, Point4f position, Vector4f scale) {
        super(origin, position, scale);
    }

    public AInterestingVideo(Point4f origin, Point4f position, Vector4f scale, HashMap<String, Texture> textures) {
        super(origin, position, scale, textures);
    }

    public AInterestingVideo(Point4f origin, Point4f position, Vector4f scale, Vector4f rotation, HashMap<String, Texture> textures) {
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
        Color.white.bind();

        //bind texture
        getTextures().get("video/video" + counter).bind();
//        GL11.glRotatef(90, 0,1,0);;
        GL11.glDisable(GL11.GL_LIGHTING); // switch lighting off
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        cube.DrawTexCube();
        GL11.glEnable(GL11.GL_LIGHTING); // switch lighting off
        ;
        counter += 1;
        if (counter >= 300)
            counter = 1;

    }

    @Override
    public void drawShadow() {

    }
}
