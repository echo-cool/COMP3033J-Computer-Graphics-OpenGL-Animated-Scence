package Scene.base;

import Scene.Objects.Player;
import base.GraphicsObjects.Point4f;
import base.GraphicsObjects.Vector4f;
import base.objects3D.DisplayListOval;
import main.Engine;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.lwjgl.opengl.GL11.*;

/**
 * @Author: WangYuyang
 * @Date: 2021/11/2-15:07
 * @Project: Assignment3
 * @Package: Scene
 * @Description:
 **/
public abstract class SceneObject implements IDrawable, IMovable, IScalable, IHittable {
    private Point4f origin = new Point4f();
    private Point4f position = new Point4f();
    private Vector4f scale = new Vector4f();
    private Vector4f rotation = new Vector4f();
    private HashMap<String, Texture> textures = new LinkedHashMap<>();
    private DisplayListOval shadow;

    public SceneObject(Point4f origin, Point4f position, Vector4f scale) {
        this.origin = new Point4f(origin.x, origin.y, origin.z, 0);
        this.position = new Point4f(position.x, position.y, position.z, 0);
        this.scale = new Vector4f(scale.x, scale.y, scale.z, 0);
        shadow = new DisplayListOval(scale.x, 32);
    }

    public SceneObject(Point4f origin, Point4f position, Vector4f scale, HashMap<String, Texture> textures) {
        this.origin = new Point4f(origin.x, origin.y, origin.z, 0);
        this.position = new Point4f(position.x, position.y, position.z, 0);
        this.textures = new LinkedHashMap<>(textures);
        this.scale = new Vector4f(scale.x, scale.y, scale.z, 0);
        shadow = new DisplayListOval(scale.x, 32);

    }

    public SceneObject(Point4f origin, Point4f position, Vector4f scale, Vector4f rotation, HashMap<String, Texture> textures) {
        this.origin = origin;
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
        this.textures = textures;
        shadow = new DisplayListOval(scale.x, 32);
    }

    @Override
    public Boolean isHit(SceneObject other) {
        Point4f o1 = getWorldPosition();
        Point4f o2 = other.getWorldPosition();
        if (o1.MinusPoint(o2).length() - this.scale.x - other.scale.x < 0)
            return true;
        else
            return false;
    }

    public float getPlayerDistance(){
        Point4f o1 = getWorldPosition();
        Point4f o2 = Player.world_position;
        return o1.MinusPoint(o2).length() - this.scale.x - Player.scale_vec.x;
    }

    @Override
    public void move(Vector4f vector) {
        this.position = position.PlusVector(vector);

//        GL11.glTranslatef(getOrigin().x, getOrigin().y, getOrigin().z);
//        GL11.glScalef(90f, 90f, 90f);
//        GL11.glTranslatef(getPosition().x , getPosition().y, getPosition().z);
    }

    @Override
    public void draw(IDrawListener listener, Integer delta) {
        //move object to correct position
        listener.beforeEachDraw(this);
        glDisable(GL_TEXTURE_GEN_S);
        glDisable(GL_TEXTURE_GEN_T);
        glDisable(GL_TEXTURE_GEN_R);
        glDisable(GL_TEXTURE_GEN_Q);

//        glPushMatrix();
        GL11.glTranslatef(origin.x, origin.y, origin.z);
        this.drawShadow();
        GL11.glRotatef(rotation.a, rotation.x, rotation.y, rotation.z);
        GL11.glScalef(scale.x, scale.y, scale.z);
        GL11.glTranslatef(position.x, position.y, position.z);
        this.draw(delta);

//        glPopMatrix();

//        glPopMatrix();

        listener.afterEachDraw(this);
//
        glEnable(GL_TEXTURE_GEN_S);
        glEnable(GL_TEXTURE_GEN_T);
        glEnable(GL_TEXTURE_GEN_R);
        glEnable(GL_TEXTURE_GEN_Q);

    }

    @Override
    public void drawShadow() {
//        Vector4f vector4f = new Vector4f(
//                Engine.lightPosition.get(0),
//                Engine.lightPosition.get(1),
//                Engine.lightPosition.get(2),
//                Engine.lightPosition.get(3)
//                );
//        Point4f tmp = position.MinusVector(vector4f);
//        Vector4f dir = tmp.MinusPoint(new Point4f(0,0,0,0)).Normal();
//        System.out.println();
        glPushMatrix();
//        GL11.glTranslatef(-position.x, -position.y, -position.z);
//        GL11.glScalef(1/scale.x, 1/scale.y, 1/scale.z);
//        GL11.glRotatef(-rotation.a, -rotation.x, -rotation.y, -rotation.z);
//        GL11.glTranslatef(-origin.x, -origin.y, -origin.z);
//        GL11.glTranslatef(0, 2f, 0);
        GL11.glScalef(scale.x, scale.y, scale.z);
        GL11.glTranslatef(position.x, position.y, position.z);
        GL11.glScalef(1 / scale.x, 1 / scale.y, 1 / scale.z);
        GL11.glTranslatef(0, -origin.y, 0);
        GL11.glTranslatef(-140f, 2f, -140f);
        GL11.glRotatef(-45, 0,1,0);
        GL11.glScalef(2f, 0f, 1f);

        glDisable(GL_LIGHTING);
        glDisable(GL_TEXTURE_2D);
        Color.black.bind();
        shadow.DrawOval();
        Color.white.bind();
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_LIGHTING);
        glPopMatrix();
    }

    public Point4f getWorldPosition() {
        Point4f point4f = new Point4f();
        point4f.x = getOrigin().x + position.x * scale.x;
        point4f.y = getOrigin().y + position.y * scale.y;
        point4f.z = getOrigin().z + position.z * scale.z;

        return point4f;
    }

    public Point4f getOrigin() {
        return origin;
    }

    public void setOrigin(Point4f origin) {
        this.origin = origin;
    }

    public Point4f getPosition() {
        return position;
    }

    @Override
    public void setPosition(Point4f point) {
        this.position = new Point4f(
                point.x,
                point.y,
                point.z,
                0
        );
//        GL11.glTranslatef(getOrigin().x, getOrigin().y, getOrigin().z);
//        GL11.glScalef(90f, 90f, 90f);
//        GL11.glTranslatef(getPosition().x , getPosition().y, getPosition().z);
    }

    public HashMap<String, Texture> getTextures() {
        return textures;
    }

    public void setTextures(HashMap<String, Texture> textures) {
        this.textures = textures;
    }

    public Vector4f getScale() {
        return scale;
    }

    @Override
    public void setScale(Vector4f scale) {
        scale = new Vector4f(
                scale.x,
                scale.y,
                scale.z,
                0
        );
    }

    public Vector4f getRotation() {
        return rotation;
    }
}
