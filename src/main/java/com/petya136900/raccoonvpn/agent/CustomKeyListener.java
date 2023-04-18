package com.petya136900.raccoonvpn.agent;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CustomKeyListener {
    public static KeyListener create(KeyPressedAction action) {
        return new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                action.keyAction(e);
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        };
    }
}
