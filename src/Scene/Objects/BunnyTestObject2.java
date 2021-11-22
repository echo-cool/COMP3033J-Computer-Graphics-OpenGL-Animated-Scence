package Scene.Objects;

import Scene.base.SceneObject;
import base.GraphicsObjects.Point4f;
import base.GraphicsObjects.Vector4f;
import base.objects3D.BunnyObjTest;
import main.Engine;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import java.util.HashMap;
import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

/**
 * @Author: WangYuyang
 * @Date: 2021/11/6-13:23
 * @Project: Assignment3
 * @Package: Scene.Objects
 * @Description:
 **/
public class BunnyTestObject2 extends SceneObject {
    public static float angle = 0;
    private final Boolean isJumping = false;
    private final float jump_height = 0f;
    public int angle_target = 0;
    private float delta;
    private Boolean isWalking = false;
    private long walkStartTime;
    private int stopCount = 0;
    private float timePassed = Engine.getTimePassed();

    public BunnyTestObject2(Point4f origin, Point4f position, Vector4f scale) {
        super(origin, position, scale);
    }

    public BunnyTestObject2(Point4f origin, Point4f position, Vector4f scale, HashMap<String, Texture> textures) {
        super(origin, position, scale, textures);
        Random random = new Random();
        float a = 0.1f;

    }

    public BunnyTestObject2(Point4f origin, Point4f position, Vector4f scale, Vector4f rotation, HashMap<String, Texture> textures) {
        super(origin, position, scale, rotation, textures);
    }

    public void walk() {
        stopCount = 0;
        if (isWalking == false) {
            isWalking = true;
            walkStartTime = Engine.getTimePassed();
//            System.out.println(walkStartTime);
        }
        if (isWalking) {
            this.delta = (Engine.getTimePassed() - walkStartTime) / 10000f;
        }
    }

    @Override
    public void draw(Integer frame_delta) {
        GL11.glDisable(GL_TEXTURE_2D);
        timePassed = Engine.getTimePassed() / 10000.0f;
        float speed = 0.1f;
//        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        glTranslatef(0, 5f, 0);
        GL11.glRotatef(angle + 180, 0, 1, 0);
        Color.white.bind();
//        glUseProgram(ShaderLoader.shaders.get(0));
        glCallList(BunnyObjTest.getBunnyDisplayList());
//        glUseProgram(0);
        GL11.glEnable(GL_TEXTURE_2D);
    }

    @Override
    public void drawShadow() {

    }
}
