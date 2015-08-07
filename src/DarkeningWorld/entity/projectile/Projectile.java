/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DarkeningWorld.entity.projectile;

import DarkeningWorld.entity.Entity;
import DarkeningWorld.graphics.Sprite;
import java.util.Random;

/**
 *
 * @author alex-_000
 */
public class Projectile extends Entity {
    
    protected final double xOrigin, yOrigin;
    protected double angle;
    protected Sprite sprite;
    protected double x, y;
    protected double nx, ny;
    protected double distance;
    protected double speed, range, damage;
    protected int size;
    protected Entity firedBy;
    protected Entity entityHit;
    public boolean hitSomething;
    
    protected final Random random = new Random();
    
    public Projectile(double x, double y, double dir, int size, Entity firedBy){
        xOrigin = x;
        yOrigin  = y;
        angle = dir;
        this.x = x;
        this.y = y;
        this.size = size;
        this.firedBy = firedBy;
    }
    
    public Sprite getSprite(){
        return sprite;
    }
    
    public int getSpriteSize(){
        return sprite.SIZE;
    }
    
    protected void move(){
        
    }
    
    public int getSize(){
        return size;
    }
    
    public Entity getFiredBy(){
        return firedBy;
    }
    
    @Override
    public double getX() { return x; }
    @Override
    public double getY() { return y; }
    
}
