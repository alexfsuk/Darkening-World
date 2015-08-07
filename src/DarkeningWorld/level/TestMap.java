/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DarkeningWorld.level;

import DarkeningWorld.entity.mob.Chaser;
import DarkeningWorld.entity.mob.Dummy;
import DarkeningWorld.entity.mob.Shooter;
import DarkeningWorld.entity.mob.Star;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author alex-_000
 */
public class TestMap extends Level {
    
    public TestMap(String path){
        super(path);
        
    }
    
    @Override
     protected void loadLevel(String path) {
         try {
             BufferedImage image = ImageIO.read(TestMap.class.getResource(path));
             int w = width = image.getWidth();
             int h = height = image.getHeight();
             tiles = new int[w * h];
             image.getRGB(0, 0, w, h, tiles, 0, w);
         } catch (IOException e) {
             System.out.println("Exception! Could not load level file!");
         }
         for (int i = 0; i < 1; i++){
             add(new Shooter(48, 28));
         }
         for (int i = 0; i < 10; i++){
             add(new Dummy(45, 29));
         }
         for (int i = 0; i < 1; i++){
             add(new Star(45, 22));
         }
    }
     
    @Override
     protected void generateLevel(){
     }
    
}