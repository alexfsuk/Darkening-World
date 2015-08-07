/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DarkeningWorld.entity.mob;

import DarkeningWorld.Game;
import DarkeningWorld.entity.projectile.Projectile;
import DarkeningWorld.entity.projectile.TestProjectile;
import DarkeningWorld.entity.spawner.ParticleSpawner;
import DarkeningWorld.graphics.AnimatedSprite;
import DarkeningWorld.graphics.Screen;
import DarkeningWorld.graphics.Sprite;
import DarkeningWorld.graphics.SpriteSheet;
import DarkeningWorld.input.Keyboard;
import DarkeningWorld.input.Mouse;

/**
 *
 * @author alex-_000
 */
public class Player extends Mob {
    
    private Keyboard input;
    private Sprite sprite;
    private int anim = 0;
    private boolean walking;
    private AnimatedSprite down = new AnimatedSprite(32, 32, 3, SpriteSheet.player_down);
    private AnimatedSprite up = new AnimatedSprite(32, 32, 3, SpriteSheet.player_up);
    private AnimatedSprite left = new AnimatedSprite(32, 32, 3, SpriteSheet.player_left);
    private AnimatedSprite right = new AnimatedSprite(32, 32, 3, SpriteSheet.player_right);
    
    // Player speed
    private double speed = 1.0;
    
    private AnimatedSprite animSprite = down;
    
    Projectile p;
    private int fireRate = 0;
    private int maxHits = 10;
    
    public Player(Keyboard input){
        this.input = input;
        
    }
    
    public Player(int x, int y, Keyboard input, int hp, int xpValue){
        this.x = x;
        this.y = y;
        this.input = input;
        this.hp = hp;
        this.xpValue = xpValue;
        // sprite = Sprite.player_down;
        fireRate = TestProjectile.FIRE_RATE;
        this.name = "Alex";
        
    }
    
    public void update(){
        if (walking) animSprite.update();
        else animSprite.setframe(0);
        if (fireRate > 0) fireRate--;
        double xa = 0, ya = 0;
        
        if (anim < 7500) anim++;
        else anim = 0;
        if (input.up) {
            animSprite = up;
            ya -= speed;
        } else if (input.down) {
            animSprite = down;
            ya += speed;
        }
        if (input.left) {
            animSprite = left;
            xa -= speed;
        } else if (input.right) {
            animSprite = right;
            xa += speed;
        }
        
        if (xa != 0 || ya != 0) {
            move(xa, ya);
            walking = true;
        } else {
            walking  = false;
        }
        clear();
        updateShooting();
    }
    
    public void render(Screen screen){
        sprite = animSprite.getSprite();
        screen.renderMob((int)(x - 16), (int)(y - 16), sprite, 0);
    }

    private void updateShooting() {
        
        if (Mouse.getB() == 1 && fireRate <= 0){
            int dy = (Mouse.getY() - (Game.getWindowHeight() / 2));
            int dx = (Mouse.getX() - (Game.getWindowWidth() / 2));
            double dir = Math.atan2(dy, dx);
            
            shoot(x, y, dir);
            fireRate = TestProjectile.FIRE_RATE;
        }
    }

    private void clear() {
        for (int i = 0; i < level.getProjectiles().size(); i++){
            Projectile p = level.getProjectiles().get(i);
            if (p.isRemoved())level.getProjectiles().remove(p);
        }
    }
    
    @Override
    public boolean hit(Projectile p){
        if(!p.hitSomething){
            p.hitSomething = true;
            hp -= p.getDamage();
            level.add(new ParticleSpawner(x + 10, y + 10, 32, 4, level, true));
        }
        if (hp <= 0){
            level.add(new ParticleSpawner(x + 10, y + 10, 512, 32, level, true));
            killedBy = p.getFiredBy();
            dead = true;
            killedBy.xp += xpValue;
            remove();
            // If player killed exit game!
            System.exit(0);
        }
        return dead;
    }
    
}
