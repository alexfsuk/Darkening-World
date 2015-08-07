/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DarkeningWorld.entity.projectile;

import DarkeningWorld.entity.Entity;
import DarkeningWorld.entity.spawner.ParticleSpawner;
import DarkeningWorld.graphics.Screen;
import DarkeningWorld.graphics.Sprite;

/**
 *
 * @author alex-_000
 */
public class TestProjectile extends Projectile {
    
    public static final int FIRE_RATE = 5; // Higher Value = Slower Fire Rate
    
    public TestProjectile(double x, double y, double dir, int size, Entity firedBy){
        super(x, y, dir, size, firedBy);
        size = 4;
        range = 500;
        speed = 10;
        this.damage = 1;
        sprite = Sprite.test_projectile;
        nx = speed * Math.cos(angle);
        ny = speed * Math.sin(angle);
    }
    
    @Override
    
    public void update(){
        if (level.tileCollision((int)(x + nx), (int)(y + ny), size, 6, 6)){
            hitSomething = true;
            level.add(new ParticleSpawner((int)x + 10, (int)y + 10, 32, 8, level, false));
            remove();
        }
        move();
    }
    
    @Override
    protected void move(){
        x += nx;
        y += ny;
        if (distance() > range) remove();
    }
        
    
    private double distance(){
        double dist = 0;
        dist  = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin - y) * (yOrigin - y)));
        return dist;
    }
    
    public void render(Screen screen){
        screen.renderProjectile((int)x, (int)y, this);
    }
    
}
