/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DarkeningWorld;

import DarkeningWorld.entity.mob.Player;
import DarkeningWorld.graphics.Screen;
import DarkeningWorld.input.Keyboard;
import DarkeningWorld.input.Mouse;
import DarkeningWorld.level.Level;
import DarkeningWorld.level.TileCoordinates;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.JFrame;

/**
 *
 * @author alex-_000
 */
public class Game extends Canvas implements Runnable {
    private static final long serialVersionID = 1L;
    private static int build = 1;
    
    // Game Window
    private static int width = 600, height = (width / 16) * 9;
    private static int scale = 2;
    private static String title = "Darkening World";
    private JFrame frame;
    private Screen screen;
    
    // Screen Rendering
    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    
    // Input
    private Keyboard keyboard;
    private Mouse mouse;
    
    // Game Objects
    public Level level;
    private Player player;
    
    // Game Logic
    private Thread thread;
    private boolean running = false;
    private final int updatesPerSec = 60;
    
    public Game(){
        Dimension size = new Dimension((width * scale), (height * scale));
        setPreferredSize(size);
        
        frame = new JFrame();
        screen = new Screen(width, height);
        keyboard = new Keyboard();
        level = level.test_map;
        TileCoordinates playerSpawn = new TileCoordinates(43, 26);
        player = new Player(playerSpawn.x(), playerSpawn.y(), keyboard, 10, 0);
        level.add(player);
        addKeyListener(keyboard);
        mouse = new Mouse();
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        
    }
    
    public synchronized void start(){
        running = true;
        thread = new Thread(this, "Display");
        thread.start();
    }
    
    public synchronized void stop(){
        running = false;
        try {
            thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static int getWindowWidth(){ return width * scale; }
    public static int getWindowHeight(){ return height * scale; }
    

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / updatesPerSec;
        double delta = 0;
        int frames = 0;
        int update = 0;
        requestFocus();
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1){
                update();
                update++;
                delta--;
            }
             render();
             frames++;
             
             if (System.currentTimeMillis() - timer > 1000){
                 timer += 1000;
                 frame.setTitle(title + " | " + update + " UPS, " + frames + " FPS");
                 update = 0;
                 frames = 0;
             }
        }
        stop();
    }
    
    // Game Logic Update Method
    public void update(){
        keyboard.update();
        level.update();
        
        // Debugging stuff
        /*
        System.out.println(mouse.getX() + " | " + mouse.getY());
        */
    }
    
    // Grapics Rendering Update Method
    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if (bs == null){
            createBufferStrategy(3);
            return;
        }
        screen.clear();
        double xScroll = player.getX() - (screen.width / 2);
        double yScroll = player.getY() - (screen.height / 2);
        level.render((int)xScroll, (int)yScroll, screen);
        
        // Game pixels array copies data from Screen Class pixels array
        for (int i = 0; i < pixels.length; i++){
            pixels[i] = screen.pixels[i];
        }
        
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Consolas", 0, 32));
        
        g.drawString("XP: " + player.xp, 50, 70);
        // g.fillRect(Mouse.getX() - 32, Mouse.getY() - 32, 64, 64);
        // g.drawString("Button: " + Mouse.getB(), 80, 80);
        
        g.dispose();
        bs.show();
    }
    
    public static void main(String[] args){
        Game game = new Game();
        game.frame.setResizable(false);
        game.frame.setTitle(Game.title);
        game.frame.add(game);
        game.frame.pack();
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setLocationRelativeTo(null);
        game.frame.setVisible(true);
        
        game.start();
    }
}
