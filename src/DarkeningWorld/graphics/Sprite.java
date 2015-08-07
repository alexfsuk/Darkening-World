/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DarkeningWorld.graphics;

/**
 *
 * @author alex-_000
 */
public class Sprite {
    
    public final int SIZE;
    private int width, height;
    private int x, y;
    public int[] pixels;
    protected SpriteSheet sheet;
    
    // Tile Sprites
    public static Sprite voidSprite = new Sprite(32, 8, 8, SpriteSheet.general_textures);
    
    // General Srites
    public static Sprite grassSprite = new Sprite(32, 0, 0, SpriteSheet.general_textures);
    public static Sprite brickFloorSprite = new Sprite(32, 1, 0, SpriteSheet.general_textures);
    public static Sprite woodFloorSprite = new Sprite(32, 2, 0, SpriteSheet.general_textures);
    public static Sprite cobblestoneSprite = new Sprite(32, 3, 0, SpriteSheet.general_textures);
    public static Sprite brickSprite = new Sprite(32, 0, 1, SpriteSheet.general_textures);
    public static Sprite mushroomRedSprite = new Sprite(32, 1, 1, SpriteSheet.general_textures);
    public static Sprite mushroomBrownSprite = new Sprite(32, 2, 1, SpriteSheet.general_textures);
    public static Sprite longGrassSprite = new Sprite(32, 3, 1, SpriteSheet.general_textures);
    public static Sprite hedgeSprite = new Sprite(32, 4, 0, SpriteSheet.general_textures);
    
    // Mob Sprites
    public static Sprite dummy = new Sprite(32, 0, 0, SpriteSheet.dummy_down);
    
    // Projectile Sprites
    public static Sprite test_projectile = new Sprite(32, 0, 0, SpriteSheet.projectiles); // New Sprite and SpriteSheet needed!
    
    // Particle Sprites
    public static Sprite particle_normal = new Sprite(3, 0x707070);
    public static Sprite particle_blood = new Sprite(3, 0xAD0002);
    
    // Map Colour Representations
    public static int col_grass = 0xff4CFF00;
    public static int col_brick_floor = 0xff8E8177;
    public static int col_wood_floor = 0xff913A00;
    public static int col_cobblestone = 0xff70665E;
    public static int col_brick = 0xff2F464C;
    public static int col_mushroom_red = 0xffFF3A3A;
    public static int col_mushroom_brown = 0xff893700;
    public static int col_grass_long = 0xff267F00;
    public static int col_hedge = 0xff576B37;
    
    
    protected Sprite(int width, int height, SpriteSheet sheet){
        SIZE = (width == height) ? width : -1;
        this.width = width;
        this.height = height;
        this.sheet = sheet;
    }
    
    public Sprite(int size, int x, int y, SpriteSheet sheet){
        SIZE = size;
        this.width = size;
        this.height = size;
        pixels = new int[SIZE * SIZE];
        this.x = x * size;
        this.y = y * size;
        this.sheet = sheet;
        load();
    }
    
    public Sprite(int width, int height, int colour){
        SIZE = -1;
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
        setColour(colour);
    }
    
    public Sprite(int size, int colour){
        SIZE = size;
        this.width = size;
        this.height = size;
        pixels = new int[SIZE * SIZE];
        setColour(colour);
    }
    
    public Sprite(int[] pixels, int width, int height){
        SIZE = (width == height) ? width : -1;
        this.width = width;
        this.height = height;
        this.pixels = pixels;
    }
    
    private void load(){
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++) {
                pixels[x + y * width] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.WIDTH];
            }
        }
    }

    private void setColour(int colour) {
        for (int i = 0; i < width * height; i++){
            pixels[i] = colour;
        }
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight() {
        return height;
    }
}
