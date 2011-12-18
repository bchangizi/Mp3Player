package projetcvmplayer;

import java.awt.Dimension;
import java.awt.Frame;

import java.awt.Rectangle;

import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class DlgPlaylist extends JDialog {
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tabPlayList = new JTable();
    private JComboBox comboPlaylist = new JComboBox();
    private JButton btnNouvellePlaylist = new JButton();
    private CVMPlayer cvmPlayer;
    private JButton btnAjouterChanson = new JButton();

    public DlgPlaylist() {
        this(null, "", false);
    }

    public DlgPlaylist(CVMPlayer cvmPlayer, String title, boolean modal) {
        super(cvmPlayer, title, modal);
        this.cvmPlayer = cvmPlayer;
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(400, 311));
        this.getContentPane().setLayout( null );
        jScrollPane1.setBounds(new Rectangle(25, 45, 345, 180));
        comboPlaylist.setBounds(new Rectangle(25, 10, 180, 25));
        btnNouvellePlaylist.setText("Nouvelle Liste");
        btnNouvellePlaylist.setBounds(new Rectangle(225, 10, 140, 25));
        btnAjouterChanson.setText("Ajouter");
        btnAjouterChanson.setBounds(new Rectangle(25, 240, 105, 30));
        jScrollPane1.getViewport().add(tabPlayList, null);
        this.getContentPane().add(btnAjouterChanson, null);
        this.getContentPane().add(btnNouvellePlaylist, null);
        this.getContentPane().add(comboPlaylist, null);
        this.getContentPane().add(jScrollPane1, null);
    }

    public void updateListePlaylists() {
        Enumeration<String> playlistes = cvmPlayer.getPlaylistes().keys();
        comboPlaylist.removeAllItems();
        while(playlistes.hasMoreElements())
            comboPlaylist.addItem(playlistes.nextElement());    
    }
    
    public void setSelectedPlaylist(String playlist) {
        comboPlaylist.setSelectedItem(playlist);
    }
    
    public JButton getBtnNouvellePlaylist() {
        return btnNouvellePlaylist;
    }


    
}
