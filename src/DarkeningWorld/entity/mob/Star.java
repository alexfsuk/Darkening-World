/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DarkeningWorld.entity.mob;

import DarkeningWorld.entity.projectile.Projectile;
import DarkeningWorld.entity.spawner.ParticleSpawner;
import DarkeningWorld.graphics.AnimatedSprite;
import DarkeningWorld.graphics.Screen;
import DarkeningWorld.graphics.Sprite;
import DarkeningWorld.graphics.SpriteSheet;
import DarkeningWorld.level.Node;
import DarkeningWorld.util.Vector2i;
import java.util.List;

/**
 *
 * @author alex-_000
 */
public class Star extends Mob {
    
    private AnimatedSprite down = new AnimatedSprite(32, 32, 3, SpriteSheet.dummy_down);
    private AnimatedSprite up = new AnimatedSprite(32, 32, 3, SpriteSheet.dummy_up);
    private AnimatedSprite left = new AnimatedSprite(32, 32, 3, SpriteSheet.dummy_left);
    private AnimatedSprite right = new AnimatedSprite(32, 32, 3, SpriteSheet.dummy_right);
    
    private boolean walking;
    private AnimatedSprite animSprite = down;
    
    private double speed = 1;
    private double xa = 0, ya = 0;
    
    private List<Node> path = null;
    private int time = 0;
    private int maxHits = 10;
    
    public Star(int x, int y){
        this.x = x * 32;
        this.y = y * 32;
        sprite = Sprite.dummy;
    }
    
    private void move(){
        xa = 0;
        ya = 0;
        double px = level.getClientPlayer().getX();
        double py = level.getClientPlayer().getY();
        
        Vector2i start = new Vector2i( ((int) getX() / 32), ((int) getY() / 32) );
        Vector2i destination = new Vector2i( (int)(px / 32), (int)(py / 32) );
        
        if (time % 30 == 0) path = level.findPath(start, destination);
        if (path != null) {
            if (path.size() > 0) {
                Vector2i vec = path.get(path.size() - 1).tile;
                if (x < vec.getX() * 32) xa++;
                if (x > vec.getX() * 32) xa--;
                if (y < vec.getY() * 32) ya++;
                if (y > vec.getY() * 32) ya--;
            }
        }
        if (xa != 0 || ya != 0) {
            move(xa, ya);
            walking = true;
        } else {
            walking  = false;
        }
    }

    @Override
    public void update() {
        time++;
        move();
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
        
        
    }

    @Override
    public void render(Screen screen) {
        sprite = animSprite.getSprite();
        screen.renderMob((int)(x - 16), (int)(y - 16), this);
    }
    
    @Override
    public boolean hit(Projectile p){
        if(!p.hitSomething){
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
