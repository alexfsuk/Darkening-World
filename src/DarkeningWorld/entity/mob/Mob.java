/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DarkeningWorld.entity.mob;

import DarkeningWorld.entity.Entity;
import DarkeningWorld.entity.projectile.Projectile;
import DarkeningWorld.entity.projectile.TestProjectile;
import DarkeningWorld.graphics.Screen;

/**
 *
 * @author alex-_000
 */
public abstract class Mob extends Entity {
    
    protected boolean moving = false;
    private Object Maths;
    public boolean walking;
    protected boolean dead;
    
    protected enum Direction {
        UP, DOWN, LEFT, RIGHT;
    }
    
    protected Direction dir;
    
    public void move(double xa, double ya){
        if (xa != 0 && ya != 0) {
            move(xa, 0);
            move(0, ya);
            return;
        }
        
        if (xa > 0) dir = Direction.RIGHT;  // Right
        if (xa < 0) dir = Direction.LEFT;   // Left
        if (ya > 0) dir = Direction.DOWN;   // Down
        if (ya < 0) dir = Direction.UP;     // Up
        
        while (xa != 0){
            if (Math.abs(xa) > 1){
                if (!collision(abs(xa), ya)){
                    this.x += abs(xa);
                }
                xa-= abs(xa);
            } else {
                if (!collision(abs(xa), ya)){
                    this.x += xa;
                }
                xa = 0;
            }
        }
        while (ya != 0){
            if (Math.abs(ya) > 1){
                if (!collision(xa, abs(ya))){
                    this.y += abs(ya);
                }
                ya-= abs(ya);
            } else {
                if (!collision(xa, abs(ya))){
                    this.y += ya;
                }
                ya = 0;
            }
        }
    }
    
    private int abs(double value){
        if (value < 0) return -1;
        return 1;
    }
    
    public abstract void update();
    
    protected void shoot(double x, double y, double dir){
        Projectile p = new TestProjectile(x - 16, y - 16, dir, 4, this);
        level.add(p);
        
    }
    
    private boolean collision(double xa, double ya){
        boolean solid = false;
        for (int c = 0; c < 4; c++){
            
            double xt = ((x + xa) - c % 2 * 30) / 32;
            double yt = ((y + ya) - c / 2 * 16) / 32;
            
            int ix = (int) Math.ceil(xt);
            int iy = (int) Math.ceil(yt);
            
            if (c % 2 == 0) ix = (int) Math.floor(xt);
            if (c / 2 == 0) iy = (int) Math.floor(yt);
            
            if (level.getTile(ix, iy).solid()) solid = true;
        }
        return solid;
    }
    
    
    @Override
    public abstract void render(Screen screen);
    
    @Override
    public boolean hit(Projectile p){
        boolean dead = false;
        hits++;
        return dead;
    } 
    
}
