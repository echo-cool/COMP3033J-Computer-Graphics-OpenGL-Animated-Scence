package Scene.base;

import java.util.ArrayList;

/**
 * @Author: WangYuyang
 * @Date: 2021/11/2-15:39
 * @Project: Assignment3
 * @Package: Scene
 * @Description:
 **/
public class SceneManager {
    private ArrayList<SceneObject> sceneObjects = new ArrayList<>();

    public void drawAll(IDrawListener listener, Integer delta) {
        for (int i = 0; i < sceneObjects.size(); i++) {
            sceneObjects.get(i).draw(listener, delta);
        }
    }

    public void addSceneObject(SceneObject sceneObject) {
        sceneObjects.add(sceneObject);
    }

    public ArrayList<SceneObject> getSceneObjects() {
        return sceneObjects;
    }

    public void setSceneObjects(ArrayList<SceneObject> sceneObjects) {
        this.sceneObjects = sceneObjects;
    }
}
