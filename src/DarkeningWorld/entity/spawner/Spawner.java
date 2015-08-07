/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DarkeningWorld.entity.spawner;

import DarkeningWorld.entity.Entity;
import DarkeningWorld.level.Level;

/**
 *
 * @author alex-_000
 */
public abstract class Spawner extends Entity {
    
    public enum Type {
        MOB, PARTICLE;
    }
    
    private Type type;
    
    public Spawner(int x, int y, Type type, int amount, Level level){
        init(level);
        this.x = x;
        this.y = y;
        this.type = type;
    }
    
}
