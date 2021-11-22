package Scene.base;

import base.GraphicsObjects.Point4f;
import base.GraphicsObjects.Vector4f;

/**
 * @Author: WangYuyang
 * @Date: 2021/11/2-15:42
 * @Project: Assignment3
 * @Package: Scene
 * @Description:
 **/
public interface IMovable {
    void setPosition(Point4f point);

    void move(Vector4f vector);
}
