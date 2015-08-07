/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DarkeningWorld.graphics;

/**
 *
 * @author alex-_000
 */
public class AnimatedSprite extends Sprite {
    
    private int frame, rate = 5, length = -1, time = 0;
    private Sprite sprite;

    public AnimatedSprite(int width, int height, int length, SpriteSheet sheet) {
        super(width, height, sheet);
        this.length = length;
        sprite = sheet.getSprites()[0];
        if (length > sheet.getSprites().length) System.err.println("Error! Length of animation is too long!");
    }
    
    public void update(){
        time ++;
        if (time % rate == 0){
            if (frame >= length - 1) frame = 0;
            else frame++;
            sprite = sheet.getSprites()[frame];
        }
    }
    
    public Sprite getSprite(){
        return sprite;
    }
    
    public void setframeRate(int frames){
        rate = frames;
    }

    public void setframe(int index) {
        if (index > sheet.getSprites().length - 1) {
            System.err.println("Index Out of bounds in " + this);
            return;
        }
        sprite = sheet.getSprites()[index];
    }
    
}
