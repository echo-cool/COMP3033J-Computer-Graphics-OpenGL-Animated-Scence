package main;

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

            //sky box
            loadTexture("skybox/back.png");
            loadTexture("skybox/down.png");
            loadTexture("skybox/front.png");
            loadTexture("skybox/left.png");
            loadTexture("skybox/right.png");
            loadTexture("skybox/up.png");

            //video
            for (int i = 1; i <= 803; i++) {
                loadTexture("video/video" + i + ".png");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadTexture(String filename) throws IOException {
        String key_name = filename.split("\\.")[0];
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
}
