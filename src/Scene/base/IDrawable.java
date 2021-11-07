package Scene.base;

/**
 * @Author: WangYuyang
 * @Date: 2021/11/2-15:06
 * @Project: Assignment3
 * @Package: Scene
 * @Description:
 **/
public interface IDrawable {
    void draw(IDrawListener listener, Integer delta);
    void draw(Integer delta);
}
