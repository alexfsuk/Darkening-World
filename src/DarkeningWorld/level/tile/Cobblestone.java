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
public class Cobblestone extends Tile {
    
    public Cobblestone(Sprite sprite) {
        super(sprite);
    }
    
    public void render(int x, int y, Screen screen){
        screen.renderTile(x * 32, y * 32, this);
    }
}
