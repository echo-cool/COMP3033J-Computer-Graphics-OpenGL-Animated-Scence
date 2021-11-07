package base.objects3D;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.GL11.glGenLists;

public class DisplayListTexSphere {
    static float red[] = {1.0f, 0.0f, 0.0f, 1.0f};
    static float green[] = {0.0f, 1.0f, 0.0f, 1.0f};
    static float blue[] = {0.0f, 0.0f, 1.0f, 1.0f};
    static float white[] = {1.0f, 1.0f, 1.0f, 1.0f};
    int displayListHandle = glGenLists(1);
    public DisplayListTexSphere(float radius, float nSlices, float nSegments, Texture myTexture) {
        GL11.glNewList(displayListHandle, GL11.GL_COMPILE);

        float x, y, z;
        float s, t; // texture coordinates

        //angle for drawing the sphere
        //From -pi to pi, this inctheta is the angle of each piece horizontally
        float inctheta = (float) ((2.0f * Math.PI) / nSlices);
        //From -pi/2 to pi/2, this incphi is the angle of each piece vertically
        float incphi = (float) (Math.PI / nSegments);

        //INIT GL
        //GL_QUADS draws quadrangle
        GL11.glBegin(GL11.GL_QUADS);
        //first loop is the horizontal loop
        for (float theta = (float) -Math.PI; theta < Math.PI; theta += inctheta) {
            //second loop is the vertical loop
            for (float phi = (float) -(Math.PI / 2.0f); phi < (Math.PI / 2.0f); phi += incphi) {

                //point 1
                x = (float) (Math.cos(phi) * Math.cos(theta) * radius);
                y = (float) (Math.cos(phi) * Math.sin(theta) * radius);
                z = (float) (Math.sin(phi) * radius);

                //texture coordinate
                t = (float) (phi / (float) Math.PI) + 0.5f;
                s = (float) (theta / Math.PI * 2.0f) + 0.5f;

                //GL11.glTexCoord2f(s,t);  // should be here but seems to be a bug in LWJGL
                GL11.glNormal3f(x, y, z);
                GL11.glVertex3f(x, y, z);

                //point 2
                x = (float) (Math.cos(phi) * Math.cos(theta + inctheta) * radius);
                y = (float) (Math.cos(phi) * Math.sin(theta + inctheta) * radius);
                z = (float) (Math.sin(phi) * radius);
                t = (float) (((float) phi / (float) Math.PI) + 0.5f);
                s = (float) ((((float) theta + inctheta) / ((float) Math.PI * 2.0f))) + 0.5f;

                //texture coordinate
                GL11.glTexCoord2f(s, t);

                GL11.glNormal3f(x, y, z); //Mistake in previous version fixed ( abey 11/1/2018)
                GL11.glVertex3f(x, y, z);  // Top Right corner

                //point 3
                x = (float) (Math.cos(phi + incphi) * Math.cos(theta + inctheta) * radius);
                y = (float) (Math.cos(phi + incphi) * Math.sin(theta + inctheta) * radius);
                z = (float) (Math.sin(phi + incphi) * radius);
                t = (float) ((((float) phi + incphi) / (float) Math.PI) + 0.5f);
                s = (float) ((((float) theta + inctheta) / ((float) Math.PI * 2.0f)) + 0.5f);

                GL11.glTexCoord2f(s, t);
                GL11.glNormal3f(x, y, z);
                GL11.glVertex3f(x, y, z);

                //point 4
                x = (float) (Math.cos(phi + incphi) * Math.cos(theta) * radius);
                y = (float) (Math.cos(phi + incphi) * Math.sin(theta) * radius);
                z = (float) (Math.sin(phi + incphi) * radius);
                t = (float) ((((float) phi + incphi) / (float) Math.PI) + 0.5f);
                s = (float) (((float) theta / ((float) Math.PI * 2.0f)) + 0.5f);

                //set texture coordinate
                GL11.glTexCoord2f(s, t);
                GL11.glNormal3f(x, y, z);
                GL11.glVertex3f(x, y, z);
            }
        }
        GL11.glEnd();
        GL11.glEndList();
    }

    // Implement using notes and examine Tetrahedron to aid in the coding  look at lecture  7 , 7b and 8
    // 7b should be your primary source, we will cover more about circles in later lectures to understand why the code works
    public void DrawTexSphere() {
        GL11.glCallList(displayListHandle);
    }
}
 