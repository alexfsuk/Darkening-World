/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DarkeningWorld.level.tile;

import DarkeningWorld.graphics.Screen;
import DarkeningWorld.graphics.Sprite;

/**
 *
 * @author alex-_000
 */
public class Tile {
    
    public Sprite sprite;
    
    // Void Tile
    public static Tile voidTile = new VoidTile(Sprite.voidSprite);
    
    // Initialise new Tiles here:
    // General Tiles
    public static Tile grassTile = new GrassTile(Sprite.grassSprite);
    public static Tile brickFloorTile = new BrickFloor(Sprite.brickFloorSprite);
    public static Tile woodFloorTile = new WoodFloor(Sprite.woodFloorSprite);
    public static Tile cobblestoneTile = new Cobblestone(Sprite.cobblestoneSprite);
    public static Tile brickTile = new Brick(Sprite.brickSprite);
    public static Tile mushroomRedTile = new MushroomRed(Sprite.mushroomRedSprite);
    public static Tile mushroomBrownTile = new MushroomBrown(Sprite.mushroomBrownSprite);
    public static Tile longGrassTile = new LongGrass(Sprite.longGrassSprite);
    public static Tile hedgeTile = new Hedge(Sprite.hedgeSprite);
    
    public Tile(Sprite sprite){
        this.sprite = sprite;
    }
    
    public void render(int x, int y, Screen screen){
    }
    
    public boolean solid(){
        return false;
    }
    
    public boolean breakable(){
        return false;
    }
}
