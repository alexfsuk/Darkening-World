/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DarkeningWorld.util;

import DarkeningWorld.graphics.Screen;

/**
 *
 * @author alex-_000
 */
public class Debug {
    
    private Debug(){
        
    }
    
    public static void drawRect(Screen screen, int x, int y, int width, int height, boolean fixed){
        screen.drawRect(x, y, width, height, 0xff0000, fixed);
    }
    
    public static void drawRect(Screen screen, int x, int y, int width, int height, int colour, boolean fixed){
        screen.drawRect(x, y, width, height, colour, fixed);
    }
}
