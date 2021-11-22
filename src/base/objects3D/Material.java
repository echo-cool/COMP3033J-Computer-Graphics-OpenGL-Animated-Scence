package base.obj;

import org.newdawn.slick.opengl.Texture;

/**
 * @Author: WangYuyang
 * @Date: 2021/11/23-00:07
 * @Project: Assignment3
 * @Package: base.obj
 * @Description:
 **/
public class Material {
    public float spC = 100;
    public float[] ambientColour = {0.2f, 0.2f, 0.2f};
    public float[] diffuseColour = {0.3f, 1, 1};
    public float[] specularColour = {1, 1, 1};
    public Texture texture;

    @Override
    public String toString() {
        return "Material{" +
                "specularCoefficient=" + spC +
                ", ambientColour=" + ambientColour +
                ", diffuseColour=" + diffuseColour +
                ", specularColour=" + specularColour +
                '}';
    }
}
