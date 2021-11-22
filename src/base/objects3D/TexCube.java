package base.objects3D;

import base.GraphicsObjects.Point4f;
import base.GraphicsObjects.Vector4f;
import org.lwjgl.opengl.GL11;

public class TexCube {

    //a cube have 8 vertices so this is the coordinates of the vertices
    Point4f[] vertices = {new Point4f(-1.0f, -1.0f, -1.0f, 0.0f),
            new Point4f(-1.0f, -1.0f, 1.0f, 0.0f),
            new Point4f(-1.0f, 1.0f, -1.0f, 0.0f),
            new Point4f(-1.0f, 1.0f, 1.0f, 0.0f),
            new Point4f(1.0f, -1.0f, -1.0f, 0.0f),
            new Point4f(1.0f, -1.0f, 1.0f, 0.0f),
            new Point4f(1.0f, 1.0f, -1.0f, 0.0f),
            new Point4f(1.0f, 1.0f, 1.0f, 0.0f)};

    //each face of the cube consists four edges, there are 6 faces in total
    //so, this array has 6 element, each element contains the related edges
    int[][] faces = {{1, 5, 4, 0},
            {2, 6, 4, 0},
            {3, 2, 0, 1},
            {6, 7, 5, 4},
            {7, 3, 1, 5},
            {7, 6, 2, 3}};

    public TexCube() {

    }

    // Implement using notes  and looking at TexSphere
    public void DrawTexCube() {

        //INIT GL
        GL11.glBegin(GL11.GL_QUADS);

        for (int face = 0; face < 6; face++) { // per face
            //a new way of calculating the normal vector by letting the face center minus the model center
            Point4f face_center = new Point4f(
                    (vertices[faces[face][0]].x + vertices[faces[face][2]].x) / 2,
                    (vertices[faces[face][0]].y + vertices[faces[face][2]].y) / 2,
                    (vertices[faces[face][0]].z + vertices[faces[face][2]].z) / 2,
                    0
            );
            Point4f model_center = new Point4f(0, 0, 0, 0);
            Vector4f normal = face_center.MinusPoint(model_center);

            //calculate the normal vector using cross product which returns a vector
            //that is perpendicular to these two vector
//            Vector4f v = vertices[n_faces[face][1]].MinusPoint(vertices[n_faces[face][0]]);
//            Vector4f w = vertices[n_faces[face][3]].MinusPoint(vertices[n_faces[face][0]]);
//            Vector4f normal = v.cross(w).Normal();
            GL11.glNormal3f(normal.x, normal.y, normal.z);

            //top left
            GL11.glTexCoord2f(0f, 1f);
            GL11.glVertex3f(vertices[faces[face][0]].x, vertices[faces[face][0]].y, vertices[faces[face][0]].z);

            //top right
            GL11.glTexCoord2f(1f, 1f);
            GL11.glVertex3f(vertices[faces[face][1]].x, vertices[faces[face][1]].y, vertices[faces[face][1]].z);

            //right bottom
            GL11.glTexCoord2f(1f, 0f);
            GL11.glVertex3f(vertices[faces[face][2]].x, vertices[faces[face][2]].y, vertices[faces[face][2]].z);

            //left bottom
            GL11.glTexCoord2f(0f, 0f);
            GL11.glVertex3f(vertices[faces[face][3]].x, vertices[faces[face][3]].y, vertices[faces[face][3]].z);
        } // per face

        GL11.glEnd();


    }

    public void DrawTexCube(float number) {

        GL11.glBegin(GL11.GL_QUADS);


        for (int face = 0; face < 6; face++) { // per face

            //a new way of calculating the normal vector by letting the face center minus the model center
            Point4f face_center = new Point4f(
                    (vertices[faces[face][0]].x + vertices[faces[face][2]].x) / 2,
                    (vertices[faces[face][0]].y + vertices[faces[face][2]].y) / 2,
                    (vertices[faces[face][0]].z + vertices[faces[face][2]].z) / 2,
                    0
            );
            Point4f model_center = new Point4f(0, 0, 0, 0);
            Vector4f normal = face_center.MinusPoint(model_center);

            //calculate the normal vector using cross product which returns a vector
            //that is perpendicular to these two vector
//            Vector4f v = vertices[n_faces[face][1]].MinusPoint(vertices[n_faces[face][0]]);
//            Vector4f w = vertices[n_faces[face][3]].MinusPoint(vertices[n_faces[face][0]]);
//            Vector4f normal = v.cross(w).Normal();
            GL11.glNormal3f(normal.x, normal.y, normal.z);


            //number indicate the repeating times
            //top left
            GL11.glTexCoord2f(0f, number);
            GL11.glVertex3f(vertices[faces[face][0]].x, vertices[faces[face][0]].y, vertices[faces[face][0]].z);

            //top right
            GL11.glTexCoord2f(number, number);
            GL11.glVertex3f(vertices[faces[face][1]].x, vertices[faces[face][1]].y, vertices[faces[face][1]].z);

            //right bottom
            GL11.glTexCoord2f(number, 0f);
            GL11.glVertex3f(vertices[faces[face][2]].x, vertices[faces[face][2]].y, vertices[faces[face][2]].z);

            //left bottom
            GL11.glTexCoord2f(0f, 0f);
            GL11.glVertex3f(vertices[faces[face][3]].x, vertices[faces[face][3]].y, vertices[faces[face][3]].z);
        } // per face

        GL11.glEnd();
    }

    // Implement using notes  and looking at TexSphere
    public void DrawTexCubeFace(int FaceNumber) {
        // 0 bottom
        // 1 front
        // 2 right
        // 3 left
        // 4 back
        // 5 top

        //INIT GL
        GL11.glBegin(GL11.GL_QUADS);
        int face = FaceNumber;

        //a new way of calculating the normal vector by letting the face center minus the model center
        Point4f face_center = new Point4f(
                (vertices[faces[face][0]].x + vertices[faces[face][2]].x) / 2,
                (vertices[faces[face][0]].y + vertices[faces[face][2]].y) / 2,
                (vertices[faces[face][0]].z + vertices[faces[face][2]].z) / 2,
                0
        );
        Point4f model_center = new Point4f(0, 0, 0, 0);
        Vector4f normal = face_center.MinusPoint(model_center);

        //calculate the normal vector using cross product which returns a vector
        //that is perpendicular to these two vector
//            Vector4f v = vertices[n_faces[face][1]].MinusPoint(vertices[n_faces[face][0]]);
//            Vector4f w = vertices[n_faces[face][3]].MinusPoint(vertices[n_faces[face][0]]);
//            Vector4f normal = v.cross(w).Normal();
        GL11.glNormal3f(normal.x, normal.y, normal.z);

        //top left
        GL11.glTexCoord2f(0f, 1f);
        GL11.glVertex3f(vertices[faces[face][0]].x, vertices[faces[face][0]].y, vertices[faces[face][0]].z);

        //top right
        GL11.glTexCoord2f(1f, 1f);
        GL11.glVertex3f(vertices[faces[face][1]].x, vertices[faces[face][1]].y, vertices[faces[face][1]].z);

        //right bottom
        GL11.glTexCoord2f(1f, 0f);
        GL11.glVertex3f(vertices[faces[face][2]].x, vertices[faces[face][2]].y, vertices[faces[face][2]].z);

        //left bottom
        GL11.glTexCoord2f(0f, 0f);
        GL11.glVertex3f(vertices[faces[face][3]].x, vertices[faces[face][3]].y, vertices[faces[face][3]].z);


        GL11.glEnd();


    }
}
 
	/*
	 
	 
}

	*/
	 