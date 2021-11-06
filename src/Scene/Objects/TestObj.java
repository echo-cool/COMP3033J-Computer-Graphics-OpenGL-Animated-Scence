package Scene.Objects;

import Scene.base.SceneObject;
import base.GraphicsObjects.Point4f;
import base.GraphicsObjects.Vector4f;
import base.objects3D.Oval;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import java.util.HashMap;

/**
 * @Author: WangYuyang
 * @Date: 2021/11/6-00:58
 * @Project: Assignment3
 * @Package: Scene.Objects
 * @Description:
 **/
public class TestObj extends SceneObject {
    Oval oval = new Oval();
    public TestObj(Point4f origin, Point4f position, Vector4f scale) {
        super(origin, position, scale);
    }

    public TestObj(Point4f origin, Point4f position, Vector4f scale, HashMap<String, Texture> textures) {
        super(origin, position, scale, textures);
    }

    public TestObj(Point4f origin, Point4f position, Vector4f scale, Vector4f rotation, HashMap<String, Texture> textures) {
        super(origin, position, scale, rotation, textures);
    }

    @Override
    public void draw() {
        Color.black.bind();
        oval.DrawOval(1, 32);
    }
}
