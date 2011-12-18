package projetcvmplayer;

import java.io.File;

import java.util.Vector;

public class Playlist {
    private Vector<File> chansonsList;
    private String nom;
    public Playlist(String nom) {
        this.nom = nom;
        chansonsList = new Vector<File>(10, 5);
    }
    
    public void ajouterChanson(File f){
        chansonsList.addElement(f);
    }
    
    public void supprimerChanson(File f){
        chansonsList.remove(f);
    }

    public String getNom() {
        return nom;
    }
}
