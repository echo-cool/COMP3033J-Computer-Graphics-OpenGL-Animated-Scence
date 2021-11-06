package Scene.Objects;

import Scene.base.SceneObject;
import base.GraphicsObjects.Point4f;
import base.GraphicsObjects.Vector4f;
import base.objects3D.BunnyObjTest;
import org.newdawn.slick.opengl.Texture;

import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glCallList;

/**
 * @Author: WangYuyang
 * @Date: 2021/11/6-13:23
 * @Project: Assignment3
 * @Package: Scene.Objects
 * @Description:
 **/
public class BunnyTestObject extends SceneObject {
    BunnyObjTest bunnyObjTest = new BunnyObjTest();
    public BunnyTestObject(Point4f origin, Point4f position, Vector4f scale) {
        super(origin, position, scale);
    }

    public BunnyTestObject(Point4f origin, Point4f position, Vector4f scale, HashMap<String, Texture> textures) {
        super(origin, position, scale, textures);
    }

    public BunnyTestObject(Point4f origin, Point4f position, Vector4f scale, Vector4f rotation, HashMap<String, Texture> textures) {
        super(origin, position, scale, rotation, textures);
    }

    @Override
    public void draw() {
//        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        glCallList(bunnyObjTest.getBunnyDisplayList());
    }
}
