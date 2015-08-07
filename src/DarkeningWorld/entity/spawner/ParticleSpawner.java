/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DarkeningWorld.entity.spawner;

import DarkeningWorld.entity.particle.Particle;
import DarkeningWorld.graphics.Sprite;
import DarkeningWorld.level.Level;

/**
 *
 * @author alex-_000
 */
public class ParticleSpawner extends Spawner {
    
    private int life;

    public ParticleSpawner(int x, int y, int life, int amount, Level level, boolean mob) {
        super(x, y, Type.PARTICLE, amount, level);
        this.life = life;
        for (int i = 0; i < amount; i++){
                level.add(new Particle(x, y, life, mob));
        }
    }
    
    
}
