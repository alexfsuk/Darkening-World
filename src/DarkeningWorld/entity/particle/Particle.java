/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DarkeningWorld.entity.particle;

import DarkeningWorld.entity.Entity;
import DarkeningWorld.graphics.Screen;
import DarkeningWorld.graphics.Sprite;

/**
 *
 * @author alex-_000
 */
public class Particle extends Entity {
    
    private Sprite sprite;
    
    private int life;
    private int time = 0;
    
    protected double xa, ya, za;
    protected double xx, yy, zz;
    
    public Particle(int x, int y, int life){
        this.x = x;
        this.y = y;
        this.xx = x;
        this.yy = y;
        this.life = life + (random.nextInt(20) - 10);
        sprite = Sprite.particle_normal;
        
        this.xa = random.nextGaussian();
        this.ya = random.nextGaussian();
        this.zz = random.nextFloat() + 2.0;
    }
    
    public Particle(int x, int y, int life, boolean mob){
        this.x = x;
        this.y = y;
        this.xx = x;
        this.yy = y;
        this.life = life + (random.nextInt(20) - 10);
        if (mob){
            sprite = Sprite.particle_blood;
        } else {
            sprite = Sprite.particle_normal;
        }
        
        this.xa = random.nextGaussian();
        this.ya = random.nextGaussian();
        this.zz = random.nextFloat() + 2.0;
    }
    
    public void update(){
        time++;
        if (time >= 7400) time = 0;
        if (time > life) remove();
        za -= 0.1;
        
        if (zz < 0){
            zz = 0;
            za *= -0.55;
            xa *= 0.25;
            ya *= 0.25;
        }
        
        move((xx + xa), (yy + ya), (zz + za));
        
    }
    
    private void move(double x, double y, double z) {
        if (collision(x, y)) {
            this.xa *= -0.5;
            this.ya *= -0.5;
            this.za *= -0.5;
        }
        this.xx += xa;
        this.yy += ya;
        this.zz += za;
    }
    
    public boolean collision(double x, double y){
        boolean solid = false;
        for (int c = 0; c < 4; c++){
            double xt = (x - c % 2 * 32) / 32;
            double yt = (y - c / 2 * 32) / 32;
            
            int ix = (int) Math.ceil(xt);
            int iy = (int) Math.ceil(yt);
            
            if (c % 2 == 0) ix = (int) Math.floor(xt);
            if (c / 2 == 0) iy = (int) Math.floor(yt);
            if (level.getTile(ix, iy).solid()) solid = true;
        }
        return solid;
    }
    
    public void render(Screen screen){
        screen.renderSprite((int) xx, (int) yy, sprite, true);
    }
    
}
