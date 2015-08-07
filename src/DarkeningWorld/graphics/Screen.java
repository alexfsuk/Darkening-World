/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DarkeningWorld.graphics;

import DarkeningWorld.entity.mob.Mob;
import java.util.Random;
import DarkeningWorld.entity.projectile.Projectile;
import DarkeningWorld.level.tile.Tile;

/**
 *
 * @author alex-_000
 */
public class Screen {
    
    public int width, height;
    public int[] pixels;
    public int xOffset, yOffset;
    private Random random = new Random();
    
    public Screen(int width, int height){
        this.width = width;
        this.height = height;
        
        // Make pixels Array the size of the screen
        pixels = new int[width * height];
    }
    
    public void clear(){
        for (int i = 0; i < pixels.length; i++){
            pixels[i] = 0;
        }
    }
    
    public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed){
        if (fixed){
            xp -= xOffset;
            yp -= yOffset;
        }
        for (int y = 0; y < sprite.getHeight(); y++){
            int ya = y + yp;
            for (int x = 0; x < sprite.getWidth(); x++){
                int xa = x + xp;
                if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
                pixels[xa + ya * width] = sprite.pixels[x+y*sprite.getWidth()];
            }
        }
    }
    
    // Not really necessary - renders a SpriteSheet
    public void renderSheet(int xp, int yp, SpriteSheet sheet, boolean fixed){
        if (fixed){
            xp -= xOffset;
            yp -= yOffset;
        }
        for (int y = 0; y < sheet.HEIGHT; y++){
            int ya = y + yp;
            for (int x = 0; x < sheet.WIDTH; x++){
                int xa = x + xp;
                if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
                pixels[xa + ya * width] = sheet.pixels[x + y * sheet.WIDTH];
            }
        }
        
    }
    
    public void renderTile(int xp, int yp, Tile tile){
        xp -= xOffset;
        yp -= yOffset;
        for (int y = 0; y < tile.sprite.SIZE; y++){
            int ya = y + yp;
            for (int x = 0; x < tile.sprite.SIZE; x++){
                int xa = x + xp;
                if (xa < -tile.sprite.SIZE || xa >= width || ya < 0
                    || ya >= height) break;
                if (xa < 0) xa = 0;
                int col = tile.sprite.pixels[x + y * tile.sprite.SIZE];
                if (col != 0xffff00ff) pixels[xa + ya * width] = col;
            }
        }
    }
    
    public void renderTile(int xp, int yp, Sprite sprite){
        xp -= xOffset;
        yp -= yOffset;
        for (int y = 0; y < sprite.SIZE; y++){
            int ya = y + yp;
            for (int x = 0; x < sprite.SIZE; x++){
                int xa = x + xp;
                if (xa < - sprite.SIZE || xa >= width || ya < 0 || ya >= height)
                    break;
                if (xa < 0) xa = 0;
                int col = sprite.pixels[x + y * sprite.SIZE];
                if (col != 0xffff00ff) pixels[xa + ya * width] = col;
            }
        }
    }
    public void renderProjectile(int xp, int yp, Projectile p){
        xp -= xOffset;
        yp -= yOffset;
        for (int y = 0; y < p.getSpriteSize(); y++){
            int ya = y + yp;
            for (int x = 0; x < p.getSpriteSize(); x++){
                int xa = x + xp;
                if (xa < - p.getSpriteSize() || xa >= width || ya < 0 
                    || ya >= height) break;
                if (xa < 0) xa = 0;
                int col = p.getSprite().pixels[x + y * p.getSpriteSize()];
                if (col != 0xffff00ff) pixels[xa + ya * width] = col;
            }
        }
    }
    
    public void renderMob(int xp, int yp, Sprite sprite, int flip){
        xp -= xOffset;
        yp -= yOffset;
        for (int y = 0; y < sprite.SIZE; y++){
            int ya = y + yp;
            int ys = y;
            if (flip ==2 || flip == 3) ys = (sprite.SIZE - 1) - y;
            for (int x = 0; x < sprite.SIZE; x++){
                int xa = x + xp;
                int xs = x;
                if (xa < -sprite.SIZE || xa >= width || ya < 0 || ya >= height) break;
                if (xa < 0) xa = 0;
                int col = sprite.pixels[xs + ys * sprite.SIZE];
                if (col != 0xffff00ff) pixels[xa + ya * width] = col;
            }
        }
    }
    
    public void renderMob(int xp, int yp, Mob mob){
        xp -= xOffset;
        yp -= yOffset;
        for (int y = 0; y < 32; y++){
            int ya = y + yp;
            int ys = y;
            for (int x = 0; x < 32; x++){
                int xa = x + xp;
                int xs = x;
                if (xa < -32 || xa >= width || ya < 0 || ya >= height) break;
                if (xa < 0) xa = 0;
                int col = mob.getSprite().pixels[xs + ys * 32];
                if (col != 0xffff00ff) pixels[xa + ya * width] = col;
            }
        }
    }
    
    public void setOffset(int xOffset, int yOffset){
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void drawRect(int xp, int yp, int width, int height, int colour,  boolean fixed) {
        if (fixed){
            xp -= xOffset;
            yp -= yOffset;
        }
        for (int x = xp; x < xp + width; x++){
            if (x < 0 || x >= this.width || yp >= this.height) continue;
            if (yp > 0) pixels[x + yp * this.width] = colour;
            if (yp + height >= this.height) continue;
            if (yp + height > 0) pixels[x + (yp + height) * this.width] = colour;
        }
        for (int y = yp; y <= yp + height; y++){
            if (xp >= this.width || y < 0 || y >= this.height) continue;
            if (xp > 0) pixels[xp + y * this.width] = colour;
            if (xp + width >= this.width) continue;
            if (xp + width > 0) pixels[xp + width + y * this.width] = colour;
        }
    }
    
}
