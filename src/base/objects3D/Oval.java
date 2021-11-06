package base.objects3D;

import base.GraphicsObjects.Vector4f;
import org.lwjgl.opengl.GL11;

/**
 * @Author: WangYuyang
 * @Date: 2021/11/6-01:04
 * @Project: Assignment3
 * @Package: base.objects3D
 * @Description:
 **/
public class Oval {
    public void DrawOval(float radius, int nSegments) {
        //INIT GL
        GL11.glBegin(GL11.GL_TRIANGLES);

        //Cylinder is drawn piece by piece, and each piece consist two triangle
        //so, here we loop through the nSegments, to draw each piece.
        for (float i = 0.0f; i < nSegments; i += 1.0) {
            //left side angle of the piece
            float angle = (float) (Math.PI * i * 2.0 / nSegments);
            //right side angle of the piece
            float nextAngle = (float) (Math.PI * (i + 1.0) * 2.0 / nSegments);

            //left side angle of the piece
            float x1 = (float) Math.sin(angle) * radius;
            float y1 = (float) Math.cos(angle) * radius;
            //right side angle of the piece
            float x2 = (float) Math.sin(nextAngle) * radius;
            float y2 = (float) Math.cos(nextAngle) * radius;
//            //top & bottom
            Vector4f normalTop = (new Vector4f(x1, y1, 0, 0)).cross(new Vector4f(x2 - x1, y2 - y1, 0, 0));
            Vector4f normalBottom = (new Vector4f(x1, y1, 0, 0)).cross(new Vector4f(x2 - x1, y2 - y1, 0, 0));

            //the top normal vector
            GL11.glNormal3f(-normalTop.x, -normalTop.y, -normalTop.z);
            GL11.glVertex3f(x1, 0, y1);
            GL11.glVertex3f(x2, 0, y2);
            GL11.glVertex3f(0, 0, 0);



        }
        //END GL
        GL11.glEnd();


    }
}
