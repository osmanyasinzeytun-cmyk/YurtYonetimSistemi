package com.yurt.view;

import javax.swing.SwingUtilities;

public class TeatMain {
    public static void main(String[] args) {
        // Arayüzü güvenli modda başlat
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginView();
            }
        });
    }
}