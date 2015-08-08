/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DarkeningWorld.entity;

import DarkeningWorld.entity.projectile.Projectile;
import DarkeningWorld.graphics.Screen;
import DarkeningWorld.graphics.Sprite;
import DarkeningWorld.level.Level;
import java.util.Random;

/**
 *
 * @author alex-_000
 */
public class Entity {
    
    protected int x, y;
    protected Sprite sprite;
    private boolean removed = false;
    protected Level level;
    protected final Random random = new Random();
    public int xp;
    protected int xpValue;
    public int hp;
    
    
    // Make sure XP is only added once because without this code xp is added to the killer varying amounts of times
    protected int testDead = 1;
    
    public void update(){
    }
    
    public void render(Screen screen){
        if (sprite != null) screen.renderSprite((int)x, (int)y, sprite, true);
    }
    
    public Sprite getSprite(){
        return sprite;
    }
    
    public void remove(){
        // Remove Entity from Level!
        removed = true;
    }
    
    public double getX() { return x; }
    public double getY() { return y; }
    
    public boolean isRemoved(){
        return removed;
    }
    
    public void init(Level level){
        this.level = level;
    }
    
    public boolean hit(Projectile p){
        boolean dead = false;
        return dead;
    }
}
