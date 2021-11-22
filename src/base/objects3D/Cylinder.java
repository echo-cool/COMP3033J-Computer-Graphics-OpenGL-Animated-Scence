package base.objects3D;

import base.GraphicsObjects.Vector4f;
import org.lwjgl.opengl.GL11;

public class Cylinder {
    public Cylinder() {
    }

    // remember to use Math.PI isntead PI
    // Implement using notes and examine Tetrahedron to aid in the coding  look at lecture  7 , 7b and 8
    // This method draws a Cylinder
    public void DrawCylinder(float radius, float height, int nSegments) {
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

            //upper triangle
            GL11.glNormal3f(x1, y1, 0);
            GL11.glVertex3f(x1, y1, 0);
            GL11.glNormal3f(x2, y2, 0);
            GL11.glVertex3f(x2, y2, height);
            GL11.glNormal3f(x1, y1, 0);
            GL11.glVertex3f(x1, y1, height);

            //lower triangle
            GL11.glNormal3f(x1, y1, 0);
            GL11.glVertex3f(x1, y1, 0);
            GL11.glNormal3f(x2, y2, 0);
            GL11.glVertex3f(x2, y2, 0.0f);
            GL11.glNormal3f(x2, y2, 0);
            GL11.glVertex3f(x2, y2, height);

//            //top & bottom
            Vector4f normalTop = (new Vector4f(x1, y1, 0, 0)).cross(new Vector4f(x2 - x1, y2 - y1, 0, 0));
            Vector4f normalBottom = (new Vector4f(x1, y1, 0, 0)).cross(new Vector4f(x2 - x1, y2 - y1, 0, 0));

            //the top normal vector
            GL11.glNormal3f(-normalTop.x, -normalTop.y, -normalTop.z);
            GL11.glVertex3f(x1, y1, height);
            GL11.glVertex3f(x2, y2, height);
            GL11.glVertex3f(0, 0, height);

            //the bottom normal vector
            GL11.glNormal3f(normalBottom.x, normalBottom.y, normalBottom.z);
            GL11.glVertex3f(x1, y1, 0);
            GL11.glVertex3f(x2, y2, 0);
            GL11.glVertex3f(0, 0, 0);


        }
        //END GL
        GL11.glEnd();


    }
}
