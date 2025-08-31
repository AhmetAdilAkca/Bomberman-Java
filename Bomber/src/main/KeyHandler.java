package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean upPressed,downPressed,leftPressed,rightPressed,zPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_UP) {
            upPressed = true;
        }
        if (keyCode == KeyEvent.VK_DOWN){
            downPressed = true;
        }
        if(keyCode == KeyEvent.VK_LEFT){
            leftPressed = true;
        }
        if(keyCode == KeyEvent.VK_RIGHT){
            rightPressed = true;
        }
        if (keyCode == KeyEvent.VK_Z) {
            zPressed =  true;
        }
        if(keyCode == KeyEvent.VK_P){
            if(gp.gameState == gp.pauseState){
                gp.gameState = gp.playState;
            }
            else if(gp.gameState == gp.playState){
                gp.gameState = gp.pauseState;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode == KeyEvent.VK_UP) {
            upPressed = false;
        }
        if (keyCode == KeyEvent.VK_DOWN){
            downPressed = false;
        }
        if(keyCode == KeyEvent.VK_LEFT){
            leftPressed = false;
        }
        if(keyCode == KeyEvent.VK_RIGHT){
            rightPressed = false;
        }
        if(keyCode == KeyEvent.VK_Z){
            zPressed = false;
        }
    }

}
