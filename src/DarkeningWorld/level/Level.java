/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DarkeningWorld.level;

import DarkeningWorld.entity.Entity;
import DarkeningWorld.entity.mob.Chaser;
import DarkeningWorld.entity.mob.Dummy;
import DarkeningWorld.entity.mob.Player;
import DarkeningWorld.entity.mob.Shooter;
import DarkeningWorld.entity.mob.Star;
import DarkeningWorld.entity.particle.Particle;
import DarkeningWorld.entity.projectile.Projectile;
import DarkeningWorld.entity.projectile.TestProjectile;
import DarkeningWorld.entity.spawner.ParticleSpawner;
import DarkeningWorld.graphics.Screen;
import DarkeningWorld.graphics.Sprite;
import DarkeningWorld.level.tile.Tile;
import DarkeningWorld.util.Vector2i;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author alex-_000
 */
public class Level {
    
    protected int width, height;
    protected int[] tilesInt;
    protected int[] tiles;
    protected int tile_size;
    
    // ArrayLists of objects in the level
    
    private List<Entity> entities = new ArrayList<Entity>();
    public List<Projectile> projectiles = new ArrayList<Projectile>();
    public List<Particle> particles = new ArrayList<Particle>();
    private List<Player> players = new ArrayList<Player>();
    
    // Initialise Levels here:
    public static Level test_map = new TestMap("/levels/test_map.png");
    
    private Comparator<Node> nodeSorter = new Comparator<Node>(){
        @Override
        public int compare(Node n0, Node n1) {
            if (n1.fCost < n0.fCost) return +1;
            if (n1.fCost > n0.fCost) return -1;
            return 0;
        }
    };
    
    public Level(int width, int height){
        this.width = width;
        this.height = height;
        tilesInt = new int[width * height];
        generateLevel();
    }
    
    public Level(String path){
        loadLevel(path);
        generateLevel();
        
    }

    protected void generateLevel() {
        for (int y = 0; y < 64; y++) {
            for (int x = 0; x < 64; x++) {
                getTile(x, y);
            }
        }
        tile_size = 32;
    }

    protected void loadLevel(String path) {
    }
    
    public void update(){
        for (int i = 0; i < entities.size(); i++){
            entities.get(i).update();
        }
        for (int i = 0; i < projectiles.size(); i++){
            projectiles.get(i).update();
        }
        for (int i = 0; i < particles.size(); i++){
            particles.get(i).update();
        }
        for (int i = 0; i < players.size(); i++){
            players.get(i).update();
        }
        remove();
        mobProjectileCollision();
    }
    
    private void remove(){
        for (int i = 0; i < entities.size(); i++){
            if (entities.get(i).isRemoved()) entities.remove(i);
        }
        for (int i = 0; i < projectiles.size(); i++){
            if (projectiles.get(i).isRemoved()) projectiles.remove(i);
        }
        for (int i = 0; i < particles.size(); i++){
            if (particles.get(i).isRemoved()) particles.remove(i);
        }
        for (int i = 0; i < players.size(); i++){
            if (players.get(i).isRemoved()) players.remove(i);
        }
    }
    
    public List<Projectile> getProjectiles(){
        return projectiles;
    }
    
    // Time of day in the level - Night/Day etc.
    private void time(){
    }
    
    public void render(int xScroll, int yScroll, Screen screen){
        screen.setOffset(xScroll, yScroll);
        int x0 = xScroll / 32;
        int x1 = (xScroll + screen.width + 32) / 32;
        int y0 = yScroll / 32;
        int y1 = (yScroll + screen.height + 32) / 32;
        for(int y = y0; y < y1; y++){
            for(int x = x0; x < x1; x++){
                getTile(x, y).render(x, y, screen);
            }
        }
        for (int i = 0; i < particles.size(); i++){
            particles.get(i).render(screen);
        }
        for (int i = 0; i < entities.size(); i++){entities.get(i).getY();
            if (entities.get(i).getY() <= getClientPlayer().getY()) entities.get(i).render(screen);
        }
        for (int i = 0; i < players.size(); i++){
            players.get(i).render(screen);
        }
        for (int i = 0; i < entities.size(); i++){entities.get(i).getY();
            if (entities.get(i).getY() > getClientPlayer().getY()) entities.get(i).render(screen);
        }
        for (int i = 0; i < projectiles.size(); i++){
            projectiles.get(i).render(screen);
        }
    }
    
    public void add(Entity e){
        e.init(this);
        if (e instanceof Particle){
            particles.add((Particle)e);
        } else if (e instanceof Projectile) {
            projectiles.add((Projectile)e);
        } else if (e instanceof Player) {
            players.add((Player)e);
        } else {
        entities.add(e);
        }
    }
    
    public List<Player> getPlayers(){
        return players;
    }
    
    public Player getPlayerAt(int index) {
        return players.get(index);
    }
    
    public Player getClientPlayer(){
        return players.get(0);
    }
    
    public List<Node> findPath(Vector2i start, Vector2i goal){
        List<Node> openList = new ArrayList<Node>();
        List<Node> closedList = new ArrayList<Node>();
        
        Node current = new Node(start, null, 0, getDistance(start, goal));
        openList.add(current);
        
        while (openList.size() > 0){
            Collections.sort(openList, nodeSorter);
            current = openList.get(0);
            if (current.tile.equals(goal)){
                List<Node> path = new ArrayList<Node>();
                while (current.parent != null){
                    path.add(current);
                    current = current.parent;
                }
                openList.clear();
                closedList.clear();
                return path;
            }
            openList.remove(current);
            closedList.add(current);
            for (int i = 0; i < 9; i++){
                if (i == 4) continue;
                int x = current.tile.getX();
                int y = current.tile.getY();
                int xi = (i % 3) - 1;
                int yi = (i / 3) - 1;
                Tile at = getTile(x + xi, y + yi);
                if (at == null) continue;
                if (at.solid()) continue;
                Vector2i a = new Vector2i((x + xi), (y + yi));
                double gCost = current.gCost + (getDistance(current.tile, a) == 1 ? 1 : 0.95);
                double hCost = getDistance(a, goal);
                Node node = new Node(a, current, gCost, hCost);
                if (vecInList(closedList, a) && gCost >= node.gCost) continue;
                if (!vecInList(openList, a) || gCost < node.gCost) openList.add(node);
            }
        }
        closedList.clear();
        return null;
    }
    
    private boolean vecInList(List<Node> list, Vector2i vector){
        for (Node n : list){
            if (n.tile.equals(vector)) return true;
        }
        return false;
    }
    
    private double getDistance(Vector2i tile, Vector2i goal){
        double dx = tile.getX() - goal.getX();
        double dy = tile.getY() - goal.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    public List<Entity> getEntities(Entity e, int radius){
        List<Entity> result = new ArrayList<Entity>();
        int ex = (int)e.getX();
        int ey = (int)e.getY();
        for (int i = 0; i < entities.size(); i++){
            Entity entity = entities.get(i);
            if (entity.equals(e)) continue;
            int x = (int)entity.getX();
            int y = (int)entity.getY();
            int dx = Math.abs(x - ex);
            int dy = Math.abs(y - ey);
            double distance = Math.sqrt((dx * dx) + (dy * dy));
            if (distance <= radius) result.add(entity);
        }
        return result;
    }
    
    public List<Player> getPlayers(Entity e, int radius){
        List<Player> result = new ArrayList<Player>();
        int ex = (int)e.getX();
        int ey = (int)e.getY();
        for (int i = 0; i < players.size(); i++){
            Player player = players.get(i);
            int x = (int)player.getX();
            int y = (int)player.getY();
            int dx = Math.abs(x - ex);
            int dy = Math.abs(y - ey);
            double distance = Math.sqrt((dx * dx) + (dy * dy));
            if (distance <= radius) result.add(player);
        }
        return result;
    }
    
    public boolean tileCollision(int x, int y, int size, int xOffset, int yOffset){
        boolean solid = false;
        for (int c = 0; c < 4; c++){
            int xt = (x - c % 2 * size + xOffset + 10) / 32;
            int yt = (y - c / 2 * size + yOffset + 10) / 32;
            if (getTile(xt, yt).solid()) solid = true;
        }
        return solid;
    }
    
    public void mobProjectileCollision(){
        List<Entity> e = new ArrayList<Entity>();
        List<Projectile> b = new ArrayList<Projectile>();
        
        // Add Player to the list of entities that are able to be shot!
        /*
        e.add(getClientPlayer());
        */
        
        for (int s = 0; s < projectiles.size(); s++){
            if (projectiles.get(s) instanceof TestProjectile){
                b.add(projectiles.get(s));
            }
        }
        
        for (int i = 0; i < entities.size(); i++){
            if (entities.get(i) instanceof Dummy
             || entities.get(i) instanceof Shooter
             || entities.get(i) instanceof Chaser
             || entities.get(i) instanceof Star
             || entities.get(i) instanceof Player)
            {
                e.add(entities.get(i));
            }
        }
        for (int pl = 0; pl < b.size(); pl++){
            int x0 = (int)b.get(pl).getX();
            int x1 = (int)b.get(pl).getX() + b.get(pl).getSize();
            int y0 = (int)b.get(pl).getY();
            int y1 = (int)b.get(pl).getY() + b.get(pl).getSize();
        
            for (int i = 0; i < e.size(); i++){
                int ex0 = (int)e.get(i).getX() - 32;
                int ex1 = (int)(e.get(i).getX());
                int ey0 = (int)e.get(i).getY() - 32;
                int ey1 = (int)(e.get(i).getY());
                
                for(int py = y0; py < y1; py++){
                    for(int px = x0; px < x1; px++){
                        for (int ex = ex0; ex < ex1; ex++) {
                            for (int ey = ey0; ey < ey1; ey++) {
                                if (py == ey && px == ex){
                                    e.get(i).hit(b.get(pl));
                                    b.get(pl).hitSomething = true;
                                    if (b.get(pl).hitSomething && b.get(pl).getFiredBy().equals(e.get(i))) {
                                        b.get(pl).hitSomething = false;
                                    }
                                    if (b.get(pl).hitSomething) b.get(pl).remove();
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public Tile getTile(int x, int y){
        if (x < 0 || y < 0 || x >= width || y >= height) return Tile.voidTile;
        // Add Tile types here:
        
        if (tiles[x + y * width] == Sprite.col_grass) return Tile.grassTile;
        if (tiles[x + y * width] == Sprite.col_brick_floor) return Tile.brickFloorTile;
        if (tiles[x + y * width] == Sprite.col_wood_floor) return Tile.woodFloorTile;
        if (tiles[x + y * width] == Sprite.col_cobblestone) return Tile.cobblestoneTile;
        if (tiles[x + y * width] == Sprite.col_brick) return Tile.brickTile;
        if (tiles[x + y * width] == Sprite.col_mushroom_red) return Tile.mushroomRedTile;
        if (tiles[x + y * width] == Sprite.col_mushroom_brown) return Tile.mushroomBrownTile;
        if (tiles[x + y * width] == Sprite.col_grass_long) return Tile.longGrassTile;
        if (tiles[x + y * width] == Sprite.col_hedge) return Tile.hedgeTile;
        
        return Tile.voidTile;
    }
}
