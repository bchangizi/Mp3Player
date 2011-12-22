package projetcvmplayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.WindowAdapter;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import java.io.ObjectOutputStream;

import java.io.OutputStream;

import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

//3.
public class GestEventPlayer extends WindowAdapter implements ActionListener  {
    private CVMPlayer cvmPlayer;
    private DlgPlaylist dlgPlaylist;
    private boolean playPause;
    GestEventPlayer(){
    }
    GestEventPlayer(CVMPlayer cvmPlayer, DlgPlaylist dlgPlaylist) {
        this.cvmPlayer = cvmPlayer;
        this.dlgPlaylist = dlgPlaylist;
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(cvmPlayer.getBtnOuvrirFichier())){
            File chanson = cvmPlayer.choisirChanson();
            if (chanson != null){
                cvmPlayer.stopMusic();
                cvmPlayer.setChansonCourante(chanson);
                dlgPlaylist.updateListChansons(chanson.getName());
                cvmPlayer.setIndexChansonCourante(-1);
                cvmPlayer.playMusic();
            }
        }else if(e.getSource().equals(cvmPlayer.getTimerTempsEcoule())){
            cvmPlayer.misajourAffichage();
        }else if(e.getSource().equals(cvmPlayer.getBtnPlayPause())){
            if(cvmPlayer.getChansonCourante() != null){
                if (playPause) // Play music 
                    cvmPlayer.playMusic();
                else // Pause music
                    cvmPlayer.pauseMusic();
                playPause = !playPause;
            }else if(dlgPlaylist.getComboPlaylist().getSelectedIndex()!=-1){
                cvmPlayer.playNextChanson();
            }
        }else if(e.getSource().equals(cvmPlayer.getBtnStop())){
            cvmPlayer.setIndexChansonCourante(-1);
            cvmPlayer.stopMusic();
        }else if(e.getSource().equals(cvmPlayer.getBtnPlaylist())){
            if (dlgPlaylist.isVisible()){
                dlgPlaylist.setVisible(false);
            }else{
                dlgPlaylist.setVisible(true);
            }
        }else if(e.getSource().equals(cvmPlayer.getBtnNextChanson())){
            cvmPlayer.playNextChanson();
        }else if(e.getSource().equals(cvmPlayer.getBtnPreviousChanson())){
            cvmPlayer.playPreviousChanson();
        }
       
    }

    @Override
    public void windowClosing(WindowEvent e) {
        ObjectOutputStream oos = null;
        try{
            oos = new ObjectOutputStream(new FileOutputStream("playlists.ser"));
            oos.writeObject(cvmPlayer.getPlaylistes()); //le hashtable
            oos.writeObject(dlgPlaylist.getComboPlaylist()); // combo playlists
            oos.writeObject(dlgPlaylist.getListModel()); // le DefaultListModel pour la liste
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            fermetureFlux (oos);
        }
    }

    private void fermetureFlux(OutputStream os) {
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
