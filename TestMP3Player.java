package projetcvmplayer;

import javax.swing.JFrame;

public class TestMP3Player {
    public static void main(String[] args) {
        CVMPlayer cvmPlayer = new CVMPlayer();
//        DlgPlaylist dlgPlaylist = new DlgPlaylist(cvmPlayer, "Playliste", false);
//        GestEvent ge = new GestEvent(cvmPlayer, dlgPlaylist);
        cvmPlayer.setVisible(true);
        cvmPlayer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
