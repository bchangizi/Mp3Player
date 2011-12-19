package projetcvmplayer;

import java.io.File;

import java.util.Vector;

public class Playlist {
    private Vector<File> chansonsListe;
    private String nom;
    public Playlist(String nom) {
        this.nom = nom;
        chansonsListe = new Vector<File>(10, 5);
    }
    
    public void ajouterChanson(File f){
        chansonsListe.addElement(f);
    }
    
    public void supprimerChanson(File f){
        chansonsListe.remove(f);
    }
    
    public Vector<File> getChansonsListe(){
        return chansonsListe;    
    }
    
    public String getNom() {
        return nom;
    }
}
