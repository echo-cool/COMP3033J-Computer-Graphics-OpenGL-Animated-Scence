package Scene.Objects;

import Scene.Scene;
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
 * @Date: 2021/11/25-16:33
 * @Project: Assignment3
 * @Package: Scene.Objects
 * @Description:
 **/
public class SuperJumpPaddle extends SceneObject {
    public static float height = 0;
    private final DisplayListTexCube cube = new DisplayListTexCube();

    public SuperJumpPaddle(Point4f origin, Point4f position, Vector4f scale) {
        super(origin, position, scale);
    }

    public SuperJumpPaddle(Point4f origin, Point4f position, Vector4f scale, HashMap<String, Texture> textures) {
        super(origin, position, scale, textures);
    }

    public SuperJumpPaddle(Point4f origin, Point4f position, Vector4f scale, Vector4f rotation, HashMap<String, Texture> textures) {
        super(origin, position, scale, rotation, textures);
    }

    @Override
    public void draw(Integer delta) {
        glDisable(GL_CULL_FACE);
        GL11.glTexParameteri(
                GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                GL11.GL_REPEAT);
        GL11.glTexParameteri(
                GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S,
                GL11.GL_REPEAT);
        Color.white.bind();
//        Color.black.bind();
        //bind texture
        getTextures().get("piston_top").bind();
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
//        oval.DrawOval();

        glScalef(1, height/2, 1);
        getShadowOffset().x = height;
        getShadowOffset().z = height;
        cube.DrawTexCube();
        glEnable(GL_CULL_FACE);
        Color.white.bind();
    }

    @Override
    public Boolean isHit(SceneObject other) {
        Point4f o1 = getWorldPosition();
        Point4f o2 = other.getWorldPosition();
        Point4f o3 = new Point4f(o1.x, 0, o1.z, 0);
        Point4f o4 = new Point4f(o2.x, 0, o2.z, 0);
        if (o3.MinusPoint(o4).length() - this.getScale().x - other.getScale().x < 0) {
//            float speed = Player.frame_delta / 1.6f;
//            height += 0.01;
//            Scene.player.move(new Vector4f(0,height,0,0));
//            Camera.position.y += height * 90;
            Scene.player.jump(4000);
        }

        return false;
    }
}
