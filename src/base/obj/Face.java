package base.obj;

import base.objects3D.Material;

/**
 * @Author: WangYuyang
 * @Date: 2021/11/23-00:08
 * @Project: Assignment3
 * @Package: base.obj
 * @Description:
 **/

public class Face {

    private final int[] vertexIndices = {-1, -1, -1};
    private final int[] normalIndices = {-1, -1, -1};
    private final int[] textureCoordinateIndices = {-1, -1, -1};
    private Material material;

    public Face(int[] vertexIndices) {
        this.vertexIndices[0] = vertexIndices[0];
        this.vertexIndices[1] = vertexIndices[1];
        this.vertexIndices[2] = vertexIndices[2];
    }

    public Face(int[] vertexIndices, int[] normalIndices) {
        this.vertexIndices[0] = vertexIndices[0];
        this.vertexIndices[1] = vertexIndices[1];
        this.vertexIndices[2] = vertexIndices[2];
        this.normalIndices[0] = normalIndices[0];
        this.normalIndices[1] = normalIndices[1];
        this.normalIndices[2] = normalIndices[2];
    }

    public Face(int[] vertexIndices, int[] normalIndices, int[] textureCoordinateIndices, Material material) {
        this.vertexIndices[0] = vertexIndices[0];
        this.vertexIndices[1] = vertexIndices[1];
        this.vertexIndices[2] = vertexIndices[2];
        this.textureCoordinateIndices[0] = textureCoordinateIndices[0];
        this.textureCoordinateIndices[1] = textureCoordinateIndices[1];
        this.textureCoordinateIndices[2] = textureCoordinateIndices[2];
        this.normalIndices[0] = normalIndices[0];
        this.normalIndices[1] = normalIndices[1];
        this.normalIndices[2] = normalIndices[2];
        this.material = material;
    }

    public int[] getVertexIndices() {
        return vertexIndices;
    }

    public int[] getNormalIndices() {
        return normalIndices;
    }
}