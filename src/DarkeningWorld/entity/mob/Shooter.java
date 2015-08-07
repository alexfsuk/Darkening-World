/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DarkeningWorld.entity.mob;

import DarkeningWorld.entity.Entity;
import DarkeningWorld.entity.projectile.Projectile;
import DarkeningWorld.entity.projectile.TestProjectile;
import DarkeningWorld.entity.spawner.ParticleSpawner;
import DarkeningWorld.graphics.AnimatedSprite;
import DarkeningWorld.graphics.Screen;
import DarkeningWorld.graphics.Sprite;
import DarkeningWorld.graphics.SpriteSheet;

/**
 *
 * @author alex-_000
 */
public class Shooter extends Mob {
    
    private AnimatedSprite down = new AnimatedSprite(32, 32, 3, SpriteSheet.dummy_down);
    private AnimatedSprite up = new AnimatedSprite(32, 32, 3, SpriteSheet.dummy_up);
    private AnimatedSprite left = new AnimatedSprite(32, 32, 3, SpriteSheet.dummy_left);
    private AnimatedSprite right = new AnimatedSprite(32, 32, 3, SpriteSheet.dummy_right);
    
    private boolean walking;
    private AnimatedSprite animSprite = down;
    
    private double speed = 1;
    private int time = 0;
    private int fireRate = 0;
    private double xa = 0, ya = 0;
    private int maxHits = 10;
    
    public Shooter(int x, int y){
     this.x = x * 32;
     this.y = y * 32;
     sprite = Sprite.dummy;
    }

    @Override
    public void update() {
        time++;
        if (fireRate > 0) fireRate--;
        if (time % (random.nextInt(50) + 30) == 0){
            xa = random.nextInt(3) - 1;
            ya = random.nextInt(3) - 1;
            if (random.nextInt(3) == 0) {
                xa = 0;
                ya = 0;
            }
        }
        
        if (walking) animSprite.update();
        else animSprite.setframe(0);
        if (ya < 0) {
            animSprite = up;
            dir = Direction.UP;
        } else if (ya > 0) {
            animSprite = down;
            dir = Direction.DOWN;
        }
        if (xa < 0) {
            animSprite = left;
            dir = Direction.LEFT;
        } else if (xa > 0) {
            animSprite = right;
            dir = Direction.RIGHT;
        }
        
        if (xa != 0 || ya != 0) {
            move(xa, ya);
            walking = true;
        } else {
            walking  = false;
        }
        
        // Shoot at the Player
        if (fireRate <= 0){
            Entity p = level.getClientPlayer();
            double dx = p.getX() - x;
            double dy = p.getY() - y;
            double dir = Math.atan2(dy, dx);
                
            shoot(x, y, dir);
            fireRate = TestProjectile.FIRE_RATE;
            }
    }

    @Override
    public void render(Screen screen) {
        sprite = animSprite.getSprite();
        screen.renderMob(x - 16, y - 16, this);
    }
    
    @Override
    public boolean hit(Projectile p){
        if(!p.hitSomething && p.getFiredBy() != this){
            p.hitSomething = true;
            hits++;
            level.add(new ParticleSpawner(x + 10, y + 10, 32, 4, level, true));
        }
        if (hits >= maxHits){
            level.add(new ParticleSpawner(x + 10, y + 10, 512, 32, level, true));
            killedBy = p.getFiredBy();
            dead = true;
            remove();
        }
        return dead;
    }
    
    @Override
    public int getHits(){ return hits; }
    
}
