/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DarkeningWorld.level;

/**
 *
 * @author alex-_000
 */
public class TileCoordinates {
    
    private int x, y;
    private final int TILE_SIZE = 32;
    
    public TileCoordinates(int x, int y){
        this.x = x * TILE_SIZE;
        this.y = y * TILE_SIZE;
    }
    
    public int x(){
        return x;
    }
    
    public int y(){
        return y;
    }
    
    public int[] xy(){
        
        int[] r = new int[2];
        r[0] = x;
        r[1] = y;
        return r;
    }
}
