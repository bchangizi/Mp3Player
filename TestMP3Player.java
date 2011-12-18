package projetcvmplayer;

import javax.swing.JFrame;

public class TestMP3Player {
    public static void main(String[] args) {
        CVMPlayer cvmp = new CVMPlayer();
        cvmp.setVisible(true);
        cvmp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
