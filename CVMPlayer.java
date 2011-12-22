package projetcvmplayer;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import java.util.Hashtable;

import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CVMPlayer extends JFrame {
    private GestEventPlayer ec;
    private JButton btnOuvrirFichier = new JButton();
    private JLabel lblTempsEcoule = new JLabel();
    private JButton btnPlayPause = new JButton();
    private JLabel lblTitreChanson = new JLabel();
    private File chansonCourante;
    private WavInfo wavInfo;
    private WavDiffuseur2 wavDiff;
    private Timer timerTempsEcoule;
    private int secondesEcoule;
    private NumberFormat fmtMinSec;
    private String titreChanson;
    private String titreChansonAffichee;
    private final int LONG_TITRE = 75;
    private JButton btnStop = new JButton();
    private JSlider sliderTemps = new JSlider();
    private JSlider sliderVolume = new JSlider();
    private JButton btnPlaylist = new JButton();
    private DlgPlaylist dlgPlaylist;
    private Hashtable<String, Playlist> playlistes;
    private int indexChansonCourante;
    private JButton btnNextChanson = new JButton();
    private JButton btnPreviousChanson = new JButton();

    public CVMPlayer() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout( null );
        this.setSize(new Dimension(682, 266));
        this.setTitle( "CVM Player" );
        btnOuvrirFichier.setText("Browse");
        btnOuvrirFichier.setBounds(new Rectangle(505, 165, 70, 50));
        btnPlayPause.setText(" > ");
        btnPlayPause.setBounds(new Rectangle(405, 170, 75, 45));
        lblTempsEcoule.setText("");
        lblTempsEcoule.setBounds(new Rectangle(35, 25, 180, 95));
        lblTempsEcoule.setFont(new Font("Arial Rounded MT Bold", 0, 50));
        lblTitreChanson.setBounds(new Rectangle(275, 25, 380, 55));
        btnStop.setText("Stop");
        btnStop.setBounds(new Rectangle(305, 175, 75, 40));
        sliderTemps.setBounds(new Rectangle(30, 135, 620, 25));
        sliderTemps.setValue(0);
        sliderVolume.setBounds(new Rectangle(395, 90, 255, 30));
        btnPlaylist.setText("PL");
        btnPlaylist.setBounds(new Rectangle(600, 175, 65, 45));
        btnNextChanson.setText("> >");
        btnNextChanson.setBounds(new Rectangle(225, 180, 70, 40));
        btnPreviousChanson.setText("< <");
        btnPreviousChanson.setBounds(new Rectangle(135, 180, 70, 45));
        this.getContentPane().add(btnPreviousChanson, null);
        this.getContentPane().add(btnNextChanson, null);
        this.getContentPane().add(btnPlaylist, null);
        this.getContentPane().add(sliderVolume, null);
        this.getContentPane().add(sliderTemps, null);
        this.getContentPane().add(btnStop, null);
        this.getContentPane().add(lblTitreChanson, null);
        this.getContentPane().add(btnPlayPause, null);
        this.getContentPane().add(lblTempsEcoule, null);
        this.getContentPane().add(btnOuvrirFichier, null);
        timerTempsEcoule = new Timer(1000, null);
        fmtMinSec = new DecimalFormat("00");
        indexChansonCourante = -1;
        playlistes = new Hashtable<String, Playlist>(10);
        dlgPlaylist = new DlgPlaylist(this, "Playliste", false);
        //1.
        ec = new GestEventPlayer(this, dlgPlaylist);
        //2.
        btnOuvrirFichier.addActionListener(ec);
        timerTempsEcoule.addActionListener(ec);
        btnPlayPause.addActionListener(ec);
        btnStop.addActionListener(ec);
        btnPlaylist.addActionListener(ec);
        btnNextChanson.addActionListener(ec);
        btnPreviousChanson.addActionListener(ec);
        this.addWindowListener(ec);
    }

    public File choisirChanson() {
        JFileChooser fileChooser = new JFileChooser(new File(".").getAbsolutePath()+"/mp3");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Fichier audio", "mp3"));
        if(fileChooser.showOpenDialog(this)== JFileChooser.APPROVE_OPTION){
            //TODO make sure the file is valid
            return fileChooser.getSelectedFile();
        }else
            return null;
    }
    
    private void initialiserChanson(){
        secondesEcoule = 0;
        timerTempsEcoule.start();
        btnPlayPause.setText(" | | ");
        titreChanson = chansonCourante.getName();
        titreChansonAffichee = titreChanson;
        while(titreChansonAffichee.length() < LONG_TITRE)
            titreChansonAffichee+="-";
        titreChansonAffichee+="----";
        sliderTemps.setMaximum(wavInfo.getNbSec());
        sliderTemps.setValue(0);
    }
    
    public void pauseMusic() {
        try {
            wavDiff.setStatut(WavDiffuseur2.PAUSE);
            timerTempsEcoule.stop();
            btnPlayPause.setText(" > ");
        } catch (Exception e) {
            // TODO: Add catch code
            JOptionPane.showMessageDialog(this, "Musique ne joue pas");
        }
    }

    public void playMusic() {
        if (wavDiff == null) {
            wavInfo = new WavInfo(chansonCourante);
            wavDiff = new WavDiffuseur2(wavInfo);
            initialiserChanson();
            wavDiff.start();
        } else if (wavDiff.getStatut() == WavDiffuseur2.PAUSE) {
            wavDiff.setStatut(WavDiffuseur2.PLAY);
        }
        timerTempsEcoule.start();
        btnPlayPause.setText(" | | ");
    }
    
    public void stopMusic() {
        if(wavDiff != null)
            wavDiff.setStatut(WavDiffuseur2.STOP);
        if(timerTempsEcoule.isRunning())
            timerTempsEcoule.stop();
        resetChanson();
    }
    
    private void resetChanson(){
        chansonCourante = null;
        wavInfo = null;
        wavDiff = null;
        lblTempsEcoule.setText("");
        lblTitreChanson.setText("");
        sliderTemps.setValue(0);
        if(dlgPlaylist.getComboPlaylist().getSelectedIndex()== -1)
            dlgPlaylist.viderListchansons();
        btnPlayPause.setText(" > ");
        updateEtatBoutons();
    }
    
    private void updateEtatBoutons(){
        if (dlgPlaylist.getNomPlaylistCourant()!= null){
            btnPlayPause.setEnabled(true);
            btnPreviousChanson.setEnabled(true);
            btnNextChanson.setEnabled(true);
            dlgPlaylist.getBtnNouvellePlaylist().setEnabled(true);
            if(indexChansonCourante < 1){
                btnPreviousChanson.setEnabled(false);
            }
            if (indexChansonCourante >= playlistes.get(dlgPlaylist.getNomPlaylistCourant()).getChansonsListe().size()-1){
                btnNextChanson.setEnabled(false);
            }
            if (wavDiff != null && dlgPlaylist.getNomPlaylistCourant()!= null){
                dlgPlaylist.getBtnNouvellePlaylist().setEnabled(false);
            }
        }    
/*         btnNextChanson;
        btnStop
        dlgPlaylist.getBtnAjouterChanson()
        dlgPlaylist.getBtnAjouterChanson() */
            
            
    }
    
    public void playNextChanson() {
        if (dlgPlaylist.getNomPlaylistCourant()!= null){
            indexChansonCourante++;
            Vector<File> chansons = playlistes.get(dlgPlaylist.getNomPlaylistCourant()).getChansonsListe();
            if (indexChansonCourante < chansons.size()){
                stopMusic();
                chansonCourante = chansons.get(indexChansonCourante);
                playMusic();
                updateEtatBoutons();
            }else{
                indexChansonCourante = -1;
            }
        }
    }
    
    public void playPreviousChanson() {
        if (dlgPlaylist.getNomPlaylistCourant()!= null){
            indexChansonCourante--;
            Vector<File> chansons = playlistes.get(dlgPlaylist.getNomPlaylistCourant()).getChansonsListe();
            if (indexChansonCourante >= 0){
                stopMusic();
                chansonCourante = chansons.get(indexChansonCourante);
                playMusic();
                updateEtatBoutons();
            }else{
                indexChansonCourante = -1;
            }
        }
    }
    
    public void misajourAffichage() {
        if(wavInfo.getNbSec()>secondesEcoule){
            ajusterTempsEcoule();
            animerTitreChanson();
        }else{
            timerTempsEcoule.stop();
            resetChanson();
            playNextChanson();
        }
    }
    
    private void ajusterTempsEcoule(){
        secondesEcoule++;
        lblTempsEcoule.setText(String.valueOf(secondesEcoule/60)+":"+String.valueOf(fmtMinSec.format(secondesEcoule%60)));
        sliderTemps.setValue(secondesEcoule);
    }
    
    private void animerTitreChanson(){
        String temp;
        titreChansonAffichee = titreChansonAffichee.charAt(titreChansonAffichee.length()-1)+titreChansonAffichee;
        titreChansonAffichee = titreChansonAffichee.substring(0, titreChansonAffichee.length()-1);
        temp = titreChansonAffichee.substring(0, LONG_TITRE);
        lblTitreChanson.setText(titreChansonAffichee);
    }
    
    public void ajouterPlaylist(Playlist playlist) {
        playlistes.put(playlist.getNom(), playlist);
    }
    
    public JButton getBtnOuvrirFichier() {
        return btnOuvrirFichier;
    }
    
    public Timer getTimerTempsEcoule() {
        return timerTempsEcoule;
    }
    
    public JButton getBtnPlayPause() {
        return btnPlayPause;
    }

    public JButton getBtnStop() {
        return btnStop;
    }

    public JButton getBtnPlaylist() {
        return btnPlaylist;
    }

    public GestEventPlayer getEc() {
        return ec;
    }

    public JButton getBtnNouvellePlaylist() {
        return dlgPlaylist.getBtnNouvellePlaylist();
    }

    public Hashtable<String, Playlist> getPlaylistes() {
        return playlistes;
    }
    
    public Playlist getPlaylist(String nomPlaylist) {
        return playlistes.get(nomPlaylist);
    }

    public void setChansonCourante(File currentFile) {
        this.chansonCourante = currentFile;
    }

    public File getChansonCourante() {
        return chansonCourante;
    }

    public void setIndexChansonCourante(int indexChansonCourante) {
        this.indexChansonCourante = indexChansonCourante;
    }

    public int getIndexChansonCourante() {
        return indexChansonCourante;
    }

    public JButton getBtnNextChanson() {
        return btnNextChanson;
    }

    public JButton getBtnPreviousChanson() {
        return btnPreviousChanson;
    }

}
