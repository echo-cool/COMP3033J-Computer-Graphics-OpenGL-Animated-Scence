package main;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.util.HashMap;

/**
 * @Author: WangYuyang
 * @Date: 2021/11/5-22:46
 * @Project: Assignment3
 * @Package: main
 * @Description:
 **/
public class TextureLoader {
    private static HashMap<String, Texture> textures_map;

    public static void loadTexture(HashMap<String, Texture> textures) {
        //LOAD textures
        textures_map = textures;
        try {
            loadTexture("awesomeface.png");
            loadTexture("default_dirt.png");
            loadTexture("earthspace.png");
            loadTexture("tnt_side.png");
            loadTexture("farming_wheat_8.png");
            loadTexture("wool_pink.png");
            loadTexture("default_lava.png");
            loadTexture("default_stone.png");
            loadTexture("default_cobble.png");

            loadTexture("bookshelf.png");
            loadTexture("enchanted_book.png");
            loadTexture("warped_door_bottom.png");
            loadTexture("enchanting_table_side.png");
            loadTexture("enchanting_table_top.png");
            loadTexture("copyleft.png");
            loadTexture("2021.png");

            loadTexture("end_portal_frame_top.png");
            loadTexture("wood.png");
            loadTexture("debug.png");
            loadTexture("new_face1.png");
            loadTexture("new_face2.png");
            loadTexture("new_face3.png");
            loadTexture("iron_trapdoor.png");
            loadTexture("piston_top.png");

            //sky box
            loadTexture("skybox/back.png");
            loadTexture("skybox/down.png");
            loadTexture("skybox/front.png");
            loadTexture("skybox/left.png");
            loadTexture("skybox/right.png");
            loadTexture("skybox/up.png");

            //video
            for (int i = 1; i <= 715; i++) {
                loadTexture("video/video" + i + ".png");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadTexture(String filename) throws IOException {
        String key_name = filename.split("\\.")[0];
        Display.setTitle("CG Project 1 Loading....... Texture:" + filename);
        textures_map.put(
                key_name,
                org.newdawn.slick.opengl.TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/" + filename))
        );
    }

    private static void loadTexture(String filename, String fileType) throws IOException {
        String key_name = filename.split("\\.")[0];
        textures_map.put(
                key_name,
                org.newdawn.slick.opengl.TextureLoader.getTexture(fileType, ResourceLoader.getResourceAsStream("res/" + filename))
        );
    }

    public static Texture getTexture(String filename, String fileType) throws IOException {
        String key_name = filename.split("\\.")[0];
        return org.newdawn.slick.opengl.TextureLoader.getTexture(fileType, ResourceLoader.getResourceAsStream("res/" + filename));
    }
}
