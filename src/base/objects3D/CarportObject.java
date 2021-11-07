package base.objects3D;

import base.obj.Model;
import base.obj.OBJLoader;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

/**
 * @Author: WangYuyang
 * @Date: 2021/11/6-13:20
 * @Project: Assignment3
 * @Package: base.objects3D
 * @Description:
 **/
public class CarportObject {
    private static int houseDisplayList;

    private static final String MODEL_LOCATION = "res/models/carport.obj";

    public CarportObject() {
        drawObj();
    }

    public void drawObj(){
        houseDisplayList = glGenLists(1);
        glNewList(houseDisplayList, GL_COMPILE);
        Model m = null;
        try {
            m = OBJLoader.loadTexturedModel(new File(MODEL_LOCATION));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Display.destroy();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            Display.destroy();
            System.exit(1);
        }
        glBegin(GL_TRIANGLES);
        for (Model.Face face : m.getFaces()) {
            Vector3f n1 = m.getNormals().get(face.getNormalIndices()[0] - 1);
            glNormal3f(n1.x, n1.y, n1.z);
            Vector3f v1 = m.getVertices().get(face.getVertexIndices()[0] - 1);
            glVertex3f(v1.x, v1.y, v1.z);
            Vector3f n2 = m.getNormals().get(face.getNormalIndices()[1] - 1);
            glNormal3f(n2.x, n2.y, n2.z);
            Vector3f v2 = m.getVertices().get(face.getVertexIndices()[1] - 1);
            glVertex3f(v2.x, v2.y, v2.z);
            Vector3f n3 = m.getNormals().get(face.getNormalIndices()[2] - 1);
            glNormal3f(n3.x, n3.y, n3.z);
            Vector3f v3 = m.getVertices().get(face.getVertexIndices()[2] - 1);
            glVertex3f(v3.x, v3.y, v3.z);
        }
        glEnd();
        glEndList();
    }

    public static int getHouseDisplayList() {
        return houseDisplayList;
    }
}
