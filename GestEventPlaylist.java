package projetcvmplayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class GestEventPlaylist implements ActionListener {
    private DlgPlaylist dlgPlaylist;
    private CVMPlayer cvmPlayer;
    
    public GestEventPlaylist(DlgPlaylist dlgPlaylist, CVMPlayer cvmPlayer){
        this.dlgPlaylist = dlgPlaylist;
        this.cvmPlayer = cvmPlayer;
    }
    
    @Override    
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(dlgPlaylist.getBtnNouvellePlaylist())){
            String nomPlaylist = JOptionPane.showInputDialog(null, "Nom de la playlist");
            if (nomPlaylist != null){
                cvmPlayer.ajouterPlaylist(new Playlist(nomPlaylist));
                dlgPlaylist.updateComboListePlaylists();
                dlgPlaylist.getComboPlaylist().setSelectedItem(nomPlaylist);
            }
        }else if (e.getSource().equals(dlgPlaylist.getBtnAjouterChanson())){
            if (dlgPlaylist.getComboPlaylist().getSelectedIndex()!=-1){
                dlgPlaylist.ajouterChansonPlaylist();
            }else{
                JOptionPane.showMessageDialog(null, "Choir un playlist auquel vous désirer ajouter une chanson");
            }
        }else if (e.getSource().equals(dlgPlaylist.getComboPlaylist())){
            String selectedPlaylist = (String)dlgPlaylist.getComboPlaylist().getSelectedItem();
            dlgPlaylist.setNomPlaylistCourant(selectedPlaylist);
            dlgPlaylist.updateListChansons((cvmPlayer.getPlaylist(selectedPlaylist).getChansonsListe()));
        }
    }
}
