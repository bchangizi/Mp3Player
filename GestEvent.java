package projetcvmplayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

//3.
public class GestEvent implements ActionListener{
    private CVMPlayer cvmPlayer;
    private boolean playPause;
    GestEvent(){
    }
    GestEvent(CVMPlayer cvmPlayer) {
        this.cvmPlayer = cvmPlayer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(cvmPlayer.getBtnOuvrirFichier())){
            JFileChooser fileChooser = new JFileChooser(new File(".").getAbsolutePath()+"/mp3");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Fichier audio", "mp3"));
            if(fileChooser.showOpenDialog(cvmPlayer)== JFileChooser.APPROVE_OPTION){
                //TODO make sure the file is valid
                cvmPlayer.jouerChanson(fileChooser.getSelectedFile());
            }
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
        }
       
    }
}
