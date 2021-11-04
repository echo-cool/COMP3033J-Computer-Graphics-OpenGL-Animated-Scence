package main;

import base.GraphicsObjects.Vector4f;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;

/**
 * @Author: WangYuyang
 * @Date: 2021/11/4-16:56
 * @Project: Assignment3
 * @Package: main
 * @Description:
 **/
public class Camera {
    private static final int maxLookUp = 30;
    private static final int maxLookDown = -20;
    public static int OrthoNumber = 0; // using this for screen size, making a window of 1200 x 800 so aspect ratio 3:2
    public static Vector3f rotation = new Vector3f(10, 180, 0);
    private static int mouseSpeed = 1;
    private static Vector4f inital_camera_position = new Vector4f();
    private Vector4f position = new Vector4f(0, 0, 0, 0);
    private float rotationX = -20;
    private float rotationY = 0;
    private float rotationZ = 0;
    private boolean MouseOnepressed = true;
    private boolean dragMode = false;


    public Camera() {
    }

    public void setCamera(Vector4f v) {
        inital_camera_position = v;
//        glRotatef(rotationX, 1, 0, 0);
//        glRotatef(rotationY, 0, 1, 0);
//        glRotatef(rotationZ, 0, 0, 1);
    }

    public void update() {
        int WheelPosition = Mouse.getDWheel();
        int MouseX = Mouse.getX();
        int MouseY = Mouse.getY();
        int MouseDX = Mouse.getDX();
        int MouseDY = Mouse.getDY();
        boolean MouseButtonPressed = Mouse.isButtonDown(0);
        boolean isGrabbed = Mouse.isGrabbed();

        glTranslatef(inital_camera_position.x, inital_camera_position.y, inital_camera_position.z);
        if (WheelPosition > 0) {
            OrthoNumber += 10;
        }
        if (WheelPosition < 0) {
            OrthoNumber -= 10;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            Vector4f move = new Vector4f();
            move = move.PlusVector(new Vector4f(0, 0, -10, 0));
//            position.z -= 10f;
            float angle = rotation.y - 180;
            float new_x = (float) (move.length() * Math.sin(Math.toRadians(angle)));
            float new_z = (float) (move.length() * Math.cos(Math.toRadians(angle)));
            move.x = new_x;
            move.z = -new_z;
            position = position.PlusVector(move);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
//            move = move.PlusVector(new Vector4f(10, 0, 0, 0));
//            position.x += 10f;
            Vector4f move = new Vector4f();
            move = move.PlusVector(new Vector4f(10, 0, 0, 0));
//            position.z -= 10f;
            float angle = 180 - rotation.y + 90;
            float new_x = (float) (move.length() * Math.sin(Math.toRadians(angle)));
            float new_z = (float) (move.length() * Math.cos(Math.toRadians(angle)));
            move.x = new_x;
            move.z = new_z;
            position = position.PlusVector(move);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            Vector4f move = new Vector4f();
            move = move.PlusVector(new Vector4f(0, 0, 10, 0));
//            position.z += 10f;
            float angle = rotation.y - 180;
            float new_x = (float) (move.length() * Math.sin(Math.toRadians(angle)));
            float new_z = (float) (move.length() * Math.cos(Math.toRadians(angle)));
            move.x = -new_x;
            move.z = new_z;
            position = position.PlusVector(move);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
//            move = move.PlusVector(new Vector4f(-10, 0, 0, 0));
            Vector4f move = new Vector4f();
            move = move.PlusVector(new Vector4f(-10, 0, 0, 0));
//            position.z -= 10f;
            float angle = 180 - rotation.y - 90;
            float new_x = (float) (move.length() * Math.sin(Math.toRadians(angle)));
            float new_z = (float) (move.length() * Math.cos(Math.toRadians(angle)));
            move.x = new_x;
            move.z = new_z;
            position = position.PlusVector(move);
        }


        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            position.y -= 10f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            position.y += 10f;
        }

        while (Mouse.next()) {
            if (Mouse.isButtonDown(0)) {
                Mouse.setGrabbed(true);
            }
            if (Mouse.isButtonDown(1)) {
                Mouse.setGrabbed(false);
            }
        }

        if (Mouse.isGrabbed()) {
            float mouseDX = MouseDX * mouseSpeed * 0.16f;
            float mouseDY = MouseDY * mouseSpeed * 0.16f;
            if (rotation.y + mouseDX >= 360) {
                rotation.y = rotation.y + mouseDX - 360;
            } else if (rotation.y + mouseDX < 0) {
                rotation.y = 360 - rotation.y + mouseDX;
            } else {
                rotation.y += mouseDX;
            }
            if (rotation.x - mouseDY >= maxLookDown && rotation.x - mouseDY <= maxLookUp) {
                rotation.x += -mouseDY;
            } else if (rotation.x - mouseDY < maxLookDown) {
                rotation.x = maxLookDown;
            } else if (rotation.x - mouseDY > maxLookUp) {
                rotation.x = maxLookUp;
            }
        }


        glRotatef(rotation.x, 1, 0, 0);
        glRotatef(rotation.y, 0, 1, 0);
        glRotatef(-rotation.z, 0, 0, 1);
        glTranslatef(position.x, position.y, position.z);

        Main.engine.setOrtho(Camera.OrthoNumber);


//        GL11.glMatrixMode(GL11.GL_MODELVIEW);
//        FloatBuffer CurrentMatrix = BufferUtils.createFloatBuffer(16);
//        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, CurrentMatrix);
//        MyArcball.getMatrix(CurrentMatrix);
//        GL11.glMultMatrix(CurrentMatrix);

    }
}
