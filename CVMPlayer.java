package projetcvmplayer;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.Timer;

public class CVMPlayer extends JFrame {
    private JButton btnOuvrirFichier = new JButton();
    private JLabel lblTempsEcoule = new JLabel();
    private JButton btnPlayPause = new JButton();
    private JLabel lblTitreChanson = new JLabel();
    private File currentFile;
    private WavInfo wavInfo;
    private WavDiffuseur2 wavDiff;
    private Timer timerTempsEcoule;
    private int secondesEcoule;
    private NumberFormat fmtMinSec;
    private String titreChanson;
    private String titreChansonAffichee;
    private final int LONG_TITRE = 20;
    private JButton btnStop = new JButton();
    private JSlider sliderTemps = new JSlider();
    private JSlider sliderVolume = new JSlider();

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
        lblTempsEcoule.setBounds(new Rectangle(35, 25, 155, 95));
        lblTitreChanson.setBounds(new Rectangle(255, 25, 400, 55));
        btnStop.setText("Stop");
        btnStop.setBounds(new Rectangle(305, 175, 75, 40));
        sliderTemps.setBounds(new Rectangle(30, 135, 620, 25));
        sliderTemps.setValue(0);
        sliderVolume.setBounds(new Rectangle(350, 85, 295, 30));
        this.getContentPane().add(sliderVolume, null);
        this.getContentPane().add(sliderTemps, null);
        this.getContentPane().add(btnStop, null);
        this.getContentPane().add(lblTitreChanson, null);
        this.getContentPane().add(btnPlayPause, null);
        this.getContentPane().add(lblTempsEcoule, null);
        this.getContentPane().add(btnOuvrirFichier, null);
        timerTempsEcoule = new Timer(1000, null);
        fmtMinSec = new DecimalFormat("00");
        //1.
        GestEvent ec = new GestEvent(this);
        //2.
        btnOuvrirFichier.addActionListener(ec);
        timerTempsEcoule.addActionListener(ec);
        btnPlayPause.addActionListener(ec);
        btnStop.addActionListener(ec);
    }

    public void jouerChanson(File file) {
        currentFile = file;
        wavInfo = new WavInfo(currentFile);
        wavDiff = new WavDiffuseur2(wavInfo);
        wavDiff.start();
        initialiserChanson();
    }
    
    private void initialiserChanson(){
        secondesEcoule = 0;
        timerTempsEcoule.start();
        btnPlayPause.setText(" | | ");
        titreChanson = currentFile.getName();
        titreChansonAffichee = titreChanson;
        while(titreChansonAffichee.length() < LONG_TITRE)
            titreChansonAffichee+="-";
        titreChansonAffichee+="----";
        sliderTemps.setMaximum(wavInfo.getNbSec());
        sliderTemps.setValue(0);
    }
    
    public void pauseMusic() {
        wavDiff.setStatut(WavDiffuseur2.PAUSE);
        timerTempsEcoule.stop();
        btnPlayPause.setText(" > ");
    }

    public void playMusic() {
        wavDiff.setStatut(WavDiffuseur2.PLAY);
        timerTempsEcoule.start();
        btnPlayPause.setText(" | | ");
    }
    
    public void stopMusic() {
        wavDiff.setStatut(WavDiffuseur2.STOP);
        timerTempsEcoule.stop();
        resetChanson();
    }
    
    private void resetChanson(){
        lblTempsEcoule.setText("");
        lblTitreChanson.setText("");
        sliderTemps.setValue(0);
    }
    
    public void misajourAffichage() {
        if(wavInfo.getNbSec()>secondesEcoule){
            ajusterTempsEcoule();
            animerTitreChanson();
        }else{
            timerTempsEcoule.stop();
            resetChanson();
        }
    }
    
    private void ajusterTempsEcoule(){
        secondesEcoule++;
        lblTempsEcoule.setText(String.valueOf(secondesEcoule/60)+":"+String.valueOf(fmtMinSec.format(secondesEcoule%60)));
        sliderTemps.setValue(secondesEcoule);
    }
    
    private void animerTitreChanson(){
        titreChansonAffichee = titreChansonAffichee.charAt(titreChansonAffichee.length()-1)+titreChansonAffichee;
        titreChansonAffichee = titreChansonAffichee.substring(0, titreChansonAffichee.length()-1);
        lblTitreChanson.setText(titreChansonAffichee);
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

}
