package base.objects3D;

import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glGenLists;

public class DisplayListSphere {

    int displayListHandle = glGenLists(1);

    public DisplayListSphere(float radius, float nSlices, float nSegments) {
        GL11.glNewList(displayListHandle, GL11.GL_COMPILE);
        //Constant PI to use when drawing
        final float pi = (float) Math.PI;

        //From -pi to pi, this inctheta is the angle of each piece horizontally
        final float inctheta = (float) ((2.0f * Math.PI) / nSlices);

        //From -pi/2 to pi/2, this incphi is the angle of each piece vertically
        final float incphi = (float) (Math.PI / nSegments);

        //INIT GL
        //GL_QUADS draws quadrangle
        GL11.glBegin(GL11.GL_QUADS);

        //first loop is the horizontal loop
        for (float theta = -pi; theta < pi; theta += inctheta) {
            //second loop is the vertical loop
            for (float phi = -(pi / 2.0f); phi < (pi / 2.0f); phi += incphi) {
                //next angle vertically
                float next_phi = phi + incphi;

                //next angle horizontally
                float next_theta = theta + inctheta;

                //point 1, left lower point
                float x1 = (float) (radius * Math.cos(phi) * Math.cos(theta));
                float y1 = (float) (radius * Math.cos(phi) * Math.sin(theta));
                float z1 = (float) (radius * Math.sin(phi));

                //point 2, left upper point
                float x2 = (float) (radius * Math.cos(next_phi) * Math.cos(theta));
                float y2 = (float) (radius * Math.cos(next_phi) * Math.sin(theta));
                float z2 = (float) (radius * Math.sin(next_phi));

                //point 3, right lower point
                float x3 = (float) (radius * Math.cos(phi) * Math.cos(next_theta));
                float y3 = (float) (radius * Math.cos(phi) * Math.sin(next_theta));
                float z3 = (float) (radius * Math.sin(phi));

                //point 4, right upper point
                float x4 = (float) (radius * Math.cos(next_phi) * Math.cos(next_theta));
                float y4 = (float) (radius * Math.cos(next_phi) * Math.sin(next_theta));
                float z4 = (float) (radius * Math.sin(next_phi));

                //here the normal is the direction of this vector.
                //normal of point 1
                GL11.glNormal3f(x1, y1, z1);
                //point 1
                GL11.glVertex3f(x1, y1, z1);

                //normal of point 2
                GL11.glNormal3f(x2, y2, z2);
                //point 2
                GL11.glVertex3f(x2, y2, z2);

                //normal of point 3
                GL11.glNormal3f(x4, y4, z4);
                //point 3
                GL11.glVertex3f(x4, y4, z4);

                //normal of point 4
                GL11.glNormal3f(x3, y3, z3);
                //point 4
                GL11.glVertex3f(x3, y3, z3);


            }
        }
        //END GL
        GL11.glEnd();
        GL11.glEndList();
    }

    // Implement using notes and examine Tetrahedron to aid in the coding  look at lecture  7 , 7b and 8
    // 7b should be your primary source, we will cover more about circles in later lectures to understand why the code works
    public void DrawSphere() {
        glCallList(displayListHandle);
    }
}

 