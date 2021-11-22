package base.obj;

import base.objects3D.Material;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.*;

public class OBJLoader {

    private static Vector3f parseVertex(String line) {
        String[] xyz = line.split(" ");
        float x = Float.parseFloat(xyz[1]);
        float y = Float.parseFloat(xyz[2]);
        float z = Float.parseFloat(xyz[3]);
        return new Vector3f(x, y, z);
    }

    private static Vector3f parseNormal(String line) {
        String[] xyz = line.split(" ");
        float x = Float.parseFloat(xyz[1]);
        float y = Float.parseFloat(xyz[2]);
        float z = Float.parseFloat(xyz[3]);
        return new Vector3f(x, y, z);
    }

    private static Face parseFace(boolean hasNormals, String line) {
        String[] faceIndices = line.split(" ");
        int[] vertexIndicesArray = {Integer.parseInt(faceIndices[1].split("/")[0]),
                Integer.parseInt(faceIndices[2].split("/")[0]), Integer.parseInt(faceIndices[3].split("/")[0])};
        if (hasNormals) {
            int[] normalIndicesArray = new int[3];
            normalIndicesArray[0] = Integer.parseInt(faceIndices[1].split("/")[2]);
            normalIndicesArray[1] = Integer.parseInt(faceIndices[2].split("/")[2]);
            normalIndicesArray[2] = Integer.parseInt(faceIndices[3].split("/")[2]);
            return new Face(vertexIndicesArray, normalIndicesArray);
        } else {
            return new Face((vertexIndicesArray));
        }
    }

    public static Model loadModel(File f) throws IOException {
        Model m;
        try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
            m = new Model();
            String line;
            while ((line = reader.readLine()) != null) {
                String prefix = line.split(" ")[0];
                switch (prefix) {
                    case "#":
                        continue;
                    case "v":
                        m.getVertices().add(parseVertex(line));
                        break;
                    case "vn":
                        m.getNormals().add(parseNormal(line));
                        break;
                    case "f":
                        m.getFaces().add(parseFace(m.hasNormals(), line));
                        break;
                    default:
                        //                throw new RuntimeException("OBJ file contains line which cannot be parsed correctly: " + line);
                        break;
                }
            }
            reader.close();
        }
        return m;
    }

    public static Model loadTexturedModel(File f) throws IOException {
        Model m;
        try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
            m = new Model();
            Material currentMaterial = new Material();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#")) {
                    continue;
                }
                if (line.startsWith("mtllib ")) {
                    String materialFileName = line.split(" ")[1];
                    File materialFile = new File(f.getParentFile().getAbsolutePath() + "/" + materialFileName);
                    BufferedReader materialFileReader = new BufferedReader(new FileReader(materialFile));
                    String materialLine;
                    Material parseMaterial = new Material();
                    String parseMaterialName = "";
                    while ((materialLine = materialFileReader.readLine()) != null) {
                        if (materialLine.startsWith("#")) {
                            continue;
                        }
                        if (materialLine.startsWith("newmtl ")) {
                            if (!parseMaterialName.equals("")) {
                                m.getMaterials().put(parseMaterialName, parseMaterial);
                            }
                            parseMaterialName = materialLine.split(" ")[1];
                            parseMaterial = new Material();
                        } else {
                            if (materialLine.startsWith("Ns ")) {
                                parseMaterial.spC = Float.valueOf(materialLine.split(" ")[1]);
                            } else {
                                if (materialLine.startsWith("Ka ")) {
                                    String[] rgb = materialLine.split(" ");
                                    parseMaterial.ambientColour[0] = Float.valueOf(rgb[1]);
                                    parseMaterial.ambientColour[1] = Float.valueOf(rgb[2]);
                                    parseMaterial.ambientColour[2] = Float.valueOf(rgb[3]);
                                } else {
                                    if (materialLine.startsWith("Ks ")) {
                                        String[] rgb = materialLine.split(" ");
                                        parseMaterial.specularColour[0] = Float.valueOf(rgb[1]);
                                        parseMaterial.specularColour[1] = Float.valueOf(rgb[2]);
                                        parseMaterial.specularColour[2] = Float.valueOf(rgb[3]);
                                    } else {
                                        if (materialLine.startsWith("Kd ")) {
                                            String[] rgb = materialLine.split(" ");
                                            parseMaterial.diffuseColour[0] = Float.valueOf(rgb[1]);
                                            parseMaterial.diffuseColour[1] = Float.valueOf(rgb[2]);
                                            parseMaterial.diffuseColour[2] = Float.valueOf(rgb[3]);
                                        } else {
                                            if (materialLine.startsWith("map_Kd")) {
                                                parseMaterial.texture = TextureLoader.getTexture("PNG",
                                                        new FileInputStream(new File(f.getParentFile().getAbsolutePath() + "/" + materialLine
                                                                .split(" ")[1])));
                                            } else {
                                                System.err.println("[MTL] Unknown Line: " + materialLine);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    m.getMaterials().put(parseMaterialName, parseMaterial);
                    materialFileReader.close();
                } else {
                    if (!line.startsWith("usemtl ")) {
                        if (line.startsWith("v ")) {
                            String[] xyz = line.split(" ");
                            float x = Float.valueOf(xyz[1]);
                            float y = Float.valueOf(xyz[2]);
                            float z = Float.valueOf(xyz[3]);
                            m.getVertices().add(new Vector3f(x, y, z));
                        } else {
                            if (line.startsWith("vn ")) {
                                String[] xyz = line.split(" ");
                                float x = Float.valueOf(xyz[1]);
                                float y = Float.valueOf(xyz[2]);
                                float z = Float.valueOf(xyz[3]);
                                m.getNormals().add(new Vector3f(x, y, z));
                            } else {
                                if (line.startsWith("vt ")) {
                                    String[] xyz = line.split(" ");
                                    float s = Float.valueOf(xyz[1]);
                                    float t = Float.valueOf(xyz[2]);
                                    m.getTextureCoord().add(new Vector2f(s, t));
                                } else {
                                    if (line.startsWith("f ")) {
                                        String[] faceIndices = line.split(" ");
                                        int[] vertexIndicesArray = {Integer.parseInt(faceIndices[1].split("/")[0]),
                                                Integer.parseInt(faceIndices[2].split("/")[0]), Integer.parseInt(faceIndices[3].split("/")[0])};
                                        int[] textureCoordinateIndicesArray = {-1, -1, -1};
                                        if (m.hasTextureCoordinates()) {
                                            textureCoordinateIndicesArray[0] = Integer.parseInt(faceIndices[1].split("/")[1]);
                                            textureCoordinateIndicesArray[1] = Integer.parseInt(faceIndices[2].split("/")[1]);
                                            textureCoordinateIndicesArray[2] = Integer.parseInt(faceIndices[3].split("/")[1]);
                                        }
                                        int[] normalIndicesArray = {0, 0, 0};
                                        if (m.hasNormals()) {
                                            normalIndicesArray[0] = Integer.parseInt(faceIndices[1].split("/")[2]);
                                            normalIndicesArray[1] = Integer.parseInt(faceIndices[2].split("/")[2]);
                                            normalIndicesArray[2] = Integer.parseInt(faceIndices[3].split("/")[2]);
                                        }
                                        //                Vector3f vertexIndices = new Vector3f(Float.valueOf(faceIndices[1].split("/")[0]),
                                        //                        Float.valueOf(faceIndices[2].split("/")[0]),
                                        // Float.valueOf(faceIndices[3].split("/")[0]));
                                        //                Vector3f normalIndices = new Vector3f(Float.valueOf(faceIndices[1].split("/")[2]),
                                        //                        Float.valueOf(faceIndices[2].split("/")[2]),
                                        // Float.valueOf(faceIndices[3].split("/")[2]));
                                        m.getFaces().add(new Face(vertexIndicesArray, normalIndicesArray,
                                                textureCoordinateIndicesArray, currentMaterial));
                                    } else {
                                        if (line.startsWith("s ")) {
                                            boolean enableSmoothShading = !line.contains("off");
                                            m.setSmoothShadingEnabled(enableSmoothShading);
                                        } else {
                                            //                System.err.println("[OBJ] Unknown Line: " + line);
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        currentMaterial = m.getMaterials().get(line.split(" ")[1]);
                    }
                }
            }
            reader.close();
        }
        return m;
    }
}
