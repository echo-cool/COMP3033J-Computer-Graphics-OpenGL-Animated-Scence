package main;

import base.GraphicsObjects.Vector4f;

/**
 * @Author: WangYuyang
 * @Date: 2021/11/4-18:00
 * @Project: Assignment3
 * @Package: main
 * @Description:
 **/
public class Util {
    public static float angle(Vector4f a, Vector4f b) {
        float dls = a.dot(b) / (a.length() * b.length());
        if (dls < -1.0F) {
            dls = -1.0F;
        } else if (dls > 1.0F) {
            dls = 1.0F;
        }

        return (float) Math.acos(dls);
    }
}
