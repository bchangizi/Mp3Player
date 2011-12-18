package projetcvmplayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

//3.
public class GestEvent implements ActionListener{
    private CVMPlayer cvmPlayer;
    private DlgPlaylist dlgPlaylist;
    private boolean playPause;
    GestEvent(){
    }
    GestEvent(CVMPlayer cvmPlayer, DlgPlaylist dlgPlaylist) {
        this.cvmPlayer = cvmPlayer;
        this.dlgPlaylist = dlgPlaylist;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(cvmPlayer.getBtnOuvrirFichier())){
            cvmPlayer.choisirChanson();
            cvmPlayer.playMusic();
        }else if(e.getSource().equals(cvmPlayer.getTimerTempsEcoule())){
            cvmPlayer.misajourAffichage();
        }else if(e.getSource().equals(cvmPlayer.getBtnPlayPause())){
            if (playPause) // Play music 
                cvmPlayer.playMusic();
            else // Pause music
                cvmPlayer.pauseMusic();
            playPause = !playPause;
        }else if(e.getSource().equals(cvmPlayer.getBtnStop())){
            cvmPlayer.stopMusic();
        }else if(e.getSource().equals(cvmPlayer.getBtnPlaylist())){
            dlgPlaylist.setVisible(true);
        }else if(e.getSource().equals(dlgPlaylist.getBtnNouvellePlaylist())){
            String nomPlaylist = JOptionPane.showInputDialog(null, "Nom de la playlist");
            cvmPlayer.ajouterPlaylist(new Playlist(nomPlaylist));
            dlgPlaylist.updateListePlaylists();
            dlgPlaylist.setSelectedPlaylist(nomPlaylist);
        }
       
    }
}
