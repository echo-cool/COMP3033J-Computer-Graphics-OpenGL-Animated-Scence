package Scene.Objects;

import Scene.base.SceneObject;
import base.GraphicsObjects.Point4f;
import base.GraphicsObjects.Vector4f;
import base.objects3D.BunnyObjTest;
import main.Engine;
import main.ShaderLoader;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL20.glUseProgram;

/**
 * @Author: WangYuyang
 * @Date: 2021/11/6-13:23
 * @Project: Assignment3
 * @Package: Scene.Objects
 * @Description:
 **/
public class BunnyTestObject extends SceneObject {
    public int angle_target = 0;
    private float delta;
    private Boolean isWalking = false;
    private long walkStartTime;
    private int stopCount = 0;
    private int angle = 0;
    private Boolean isJumping = false;
    private float jump_height = 0f;
    private float timePassed = Engine.getTimePassed();
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
    public BunnyTestObject(Point4f origin, Point4f position, Vector4f scale) {
        super(origin, position, scale);
    }

    public BunnyTestObject(Point4f origin, Point4f position, Vector4f scale, HashMap<String, Texture> textures) {
        super(origin, position, scale, textures);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (Math.abs(angle - angle_target) > 200) {
                        angle = angle_target;
                    }

                    if (angle > angle_target) {
                        angle -= 1f;
                    } else if (angle < angle_target) {
                        angle += 1f;
                    }
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public BunnyTestObject(Point4f origin, Point4f position, Vector4f scale, Vector4f rotation, HashMap<String, Texture> textures) {
        super(origin, position, scale, rotation, textures);
    }

    @Override
    public void draw(Integer frame_delta) {
        timePassed = Engine.getTimePassed()/10000.0f;
        float speed = frame_delta/160f;
        if((int) (timePassed % 4) == 0){
            move(new Vector4f(speed,0,0,0));
            angle_target = 90;

        }
        if((int) (timePassed % 4) == 1){
            move(new Vector4f(0,0,-speed,0));
            angle_target = 90 + 90;
        }
        if((int) (timePassed % 4) == 2){
            move(new Vector4f(-speed,0,0,0));
            angle_target = 90 + 90 + 90;
        }
        if((int) (timePassed % 4) == 3){
            move(new Vector4f(0,0,speed,0));
            angle_target = 90 + 90 + 90 + 90;
        }
//        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        GL11.glRotatef(angle + 180, 0, 1, 0);
        Color.white.bind();
//        glUseProgram(ShaderLoader.shaders.get(0));
        glCallList(BunnyObjTest.getBunnyDisplayList());
//        glUseProgram(0);
    }
    @Override
    public void drawShadow() {

    }
}
