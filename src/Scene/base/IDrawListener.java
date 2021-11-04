package Scene.base;

/**
 * @Author: WangYuyang
 * @Date: 2021/11/2-15:46
 * @Project: Assignment3
 * @Package: Scene
 * @Description:
 **/
public interface IDrawListener {
    void beforeEachDraw(SceneObject object);
    void afterEachDraw(SceneObject object);
}
