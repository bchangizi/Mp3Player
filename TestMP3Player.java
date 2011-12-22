package projetcvmplayer;

import javax.swing.JFrame;

public class TestMP3Player {
    public static void main(String[] args) {
        CVMPlayer cvmPlayer = new CVMPlayer();
        cvmPlayer.setVisible(true);
        cvmPlayer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
