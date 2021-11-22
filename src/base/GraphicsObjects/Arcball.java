package base.GraphicsObjects;

import java.nio.FloatBuffer;

/*
 * Created by Owen Grogan on 06/05/2010.
 *  Copyright 2010 School of Computer Science,UCD. All rights reserved.
 *
 *  Update for Java by Abraham Campbell on 14/10/2015 for School of Computer Science,UCD.
 */
public class Arcball {
    private quat currQuat;
    private Vector4f v0;


    public Arcball() {
        currQuat = new quat();
        v0 = new Vector4f();
    }


    /*
     *		Converts screen coordinates to a point on a unit sphere
     *		needs to be modified based on what click coordinate system is being used
     */

    public Vector4f screenToPoint(int x, int y, int windowWidth, int windowHeight) {
        float retX, retY, s;
        float radius = (float) min(windowWidth, windowHeight) / 2.0f;


        retX = (x - (windowWidth / 2.0f)) / radius;
        retY = ((y - (windowHeight / 2.0f)) / radius);

        float r = (retX * retX) + (retY * retY);
        float srR = (float) Math.sqrt(r);

        if (r >= 1.0f) {
            s = 1.0f / srR;
            return new Vector4f(s * retX, s * retY, 0.0f, 0.0f);
        } else {
            return new Vector4f(retX, retY, (float) Math.sqrt(1.0f - r), 0.0f);
        }
    }

    public void startBall(int x, int y, int windowWidth, int windowHeight) {
        v0 = screenToPoint(x, y, windowWidth, windowHeight);
    }

    /*
     *	Uses ken shoemakes tecnique
     */
    public void updateBall(int x, int y, int windowWidth, int windowHeight) {

        Vector4f v1 = screenToPoint(x, y, windowWidth, windowHeight);
        Vector4f im = v0.cross(v1);
        float re = v0.dot(v1);
        quat newQuat = new quat(re, im);

        currQuat = newQuat.mult(currQuat);

        v0 = v1;
        //	System.out.println(" v1" + currQuat.getImag().x+"," +currQuat.getImag().y+"," +currQuat.getImag().z);
    }

    /*
     *	Takes current modelview matrix as 16 item array
     *	and replaces the rotation part with the current rotation
     */
    public void getMatrix(FloatBuffer currentMatrix) {
        currQuat.toMatrix(currentMatrix);

    }

    public int min(int i, int j) {
        if (i > j)
            return j;
        else
            return i;
    }

    public void reset() {
        currQuat = new quat();
    }


}
