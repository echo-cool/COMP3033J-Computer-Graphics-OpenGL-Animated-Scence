package Scene.Objects;

import Scene.base.SceneObject;
import base.GraphicsObjects.Point4f;
import base.GraphicsObjects.Vector4f;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import java.util.HashMap;

import static org.lwjgl.opengl.GL11.glCallList;

/**
 * @Author: WangYuyang
 * @Date: 2021/11/6-13:23
 * @Project: Assignment3
 * @Package: Scene.Objects
 * @Description:
 **/
public class CarportObject extends SceneObject {
    base.objects3D.CarportObject houseObjTest = new base.objects3D.CarportObject();
    public CarportObject(Point4f origin, Point4f position, Vector4f scale) {
        super(origin, position, scale);
    }

    public CarportObject(Point4f origin, Point4f position, Vector4f scale, HashMap<String, Texture> textures) {
        super(origin, position, scale, textures);

    }

    public CarportObject(Point4f origin, Point4f position, Vector4f scale, Vector4f rotation, HashMap<String, Texture> textures) {
        super(origin, position, scale, rotation, textures);
    }

    @Override
    public Boolean isHit(SceneObject other) {
        return false;
    }


    @Override
    public void draw(Integer frame_delta) {
        Color.white.bind();
        glCallList(base.objects3D.CarportObject.getHouseDisplayList());
        Color.white.bind();
    }
}
