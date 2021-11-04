package base.objects3D;

import base.GraphicsObjects.Point4f;
import base.GraphicsObjects.Vector4f;
import org.lwjgl.opengl.GL11;

public class Cube {


    public Cube() {

    }

    // Implement using notes and examine Tetrahedron to aid in the coding  look at lecture  7 , 7b and 8
    // Implement using notes and examine Tetrahedron to aid in the coding  look at lecture  7 , 7b and 8
    //This method will draw a cube, base on the vertices and the faces
    public void DrawCube() {
        //a cube have 8 vertices so this is the coordinates of the vertices
        Point4f vertices[] = {new Point4f(-1.0f, -1.0f, -1.0f, 0.0f),
                new Point4f(-1.0f, -1.0f, 1.0f, 0.0f),
                new Point4f(-1.0f, 1.0f, -1.0f, 0.0f),
                new Point4f(-1.0f, 1.0f, 1.0f, 0.0f),
                new Point4f(1.0f, -1.0f, -1.0f, 0.0f),
                new Point4f(1.0f, -1.0f, 1.0f, 0.0f),
                new Point4f(1.0f, 1.0f, -1.0f, 0.0f),
                new Point4f(1.0f, 1.0f, 1.0f, 0.0f)};

        //each face of the cube consists four edges, there are 6 faces in total
        //so, this array has 6 element, each element contains the related edges
        int faces[][] = {{0, 4, 5, 1},
                {0, 2, 6, 4},
                {0, 1, 3, 2},
                {4, 6, 7, 5},
                {1, 5, 7, 3},
                {2, 3, 7, 6}};


		//INIT GL
        GL11.glBegin(GL11.GL_QUADS);

        for (int face = 0; face < 6; face++) { // per face
			//calculate the normal vector using cross product which returns a vector
			//that is perpendicular to these two vector
            Vector4f v = vertices[faces[face][1]].MinusPoint(vertices[faces[face][0]]);
            Vector4f w = vertices[faces[face][3]].MinusPoint(vertices[faces[face][0]]);
            //calc the normal vector
            Vector4f normal = v.cross(w).Normal();
			//set the normal vector of this face in GL
            GL11.glNormal3f(normal.x, normal.y, normal.z);


			//draw one face
            //point 1
            GL11.glVertex3f(vertices[faces[face][0]].x, vertices[faces[face][0]].y, vertices[faces[face][0]].z);
            //point 2
            GL11.glVertex3f(vertices[faces[face][1]].x, vertices[faces[face][1]].y, vertices[faces[face][1]].z);
            //point 3
            GL11.glVertex3f(vertices[faces[face][2]].x, vertices[faces[face][2]].y, vertices[faces[face][2]].z);
            //point 4
            GL11.glVertex3f(vertices[faces[face][3]].x, vertices[faces[face][3]].y, vertices[faces[face][3]].z);
        } // per face

		//END GL
        GL11.glEnd();


    }


}
 
	/*
	 
	 
}

	*/
	 