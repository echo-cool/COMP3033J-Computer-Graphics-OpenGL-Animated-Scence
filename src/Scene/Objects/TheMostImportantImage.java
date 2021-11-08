package Scene.Objects;

import Scene.base.SceneObject;
import base.GraphicsObjects.Point4f;
import base.GraphicsObjects.Vector4f;
import base.objects3D.DisplayListTexCube;
import main.Camera;
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
public class TheMostImportantImage extends SceneObject {
    public static Boolean isPlayVideo = false;
    private DisplayListTexCube cube = new DisplayListTexCube();
    private float counter = 1;

    public TheMostImportantImage(Point4f origin, Point4f position, Vector4f scale) {
        super(origin, position, scale);
    }

    public TheMostImportantImage(Point4f origin, Point4f position, Vector4f scale, HashMap<String, Texture> textures) {
        super(origin, position, scale, textures);
    }

    public TheMostImportantImage(Point4f origin, Point4f position, Vector4f scale, Vector4f rotation, HashMap<String, Texture> textures) {
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

        if (Camera.position.z > 1700
                        && Camera.loading_finished
                        && Camera.position.x < 100
                        && Camera.position.x > -100
                && Camera.position.z < 2000
        ) {
            isPlayVideo = true;
        } else {
            isPlayVideo = false;
        }

        if (isPlayVideo) {
            getTextures().get("video/video" + (int) counter).bind();
        } else {
            getTextures().get("2021").bind();
            counter = 1;
        }
//        GL11.glRotatef(90, 0,1,0);
        ;
//        GL11.glDisable(GL11.GL_LIGHTING); // switch lighting off
        GL11.glRotatef(180, 0, 0, 1);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        cube.DrawTexCube();
//        GL11.glEnable(GL11.GL_LIGHTING); // switch lighting off
        ;
        counter += 0.5f;
        if (counter >= 715)
            counter = 1.0f;

    }

    @Override
    public void drawShadow() {

    }
}
