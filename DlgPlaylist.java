package projetcvmplayer;

import java.awt.Dimension;
import java.awt.Frame;

import java.awt.Rectangle;

import java.io.File;

import java.util.Enumeration;

import java.util.Iterator;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.table.DefaultTableModel;

public class DlgPlaylist extends JDialog {
    private final String COL[] = { "Titre" };
    private DefaultListModel listModel = new DefaultListModel();
    private JList listChansons = new JList(listModel);
    private JComboBox comboPlaylist = new JComboBox();
    private JButton btnNouvellePlaylist = new JButton();
    private CVMPlayer cvmPlayer;
    private GestEventPlaylist ec;
    private JButton btnAjouterChanson = new JButton();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private String nomPlaylistCourant;

    public DlgPlaylist() {
        this(null, "", false);
    }

    public DlgPlaylist(CVMPlayer cvmPlayer,  String title, boolean modal) {
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
        comboPlaylist.setBounds(new Rectangle(25, 10, 180, 25));
        btnNouvellePlaylist.setText("Nouvelle Liste");
        btnNouvellePlaylist.setBounds(new Rectangle(225, 10, 140, 25));
        btnAjouterChanson.setText("Ajouter");
        btnAjouterChanson.setBounds(new Rectangle(25, 240, 105, 30));
        jScrollPane1.setBounds(new Rectangle(25, 55, 340, 165));
        jScrollPane1.getViewport().add(listChansons, null);
        this.getContentPane().add(jScrollPane1, null);
        this.getContentPane().add(btnAjouterChanson, null);
        this.getContentPane().add(btnNouvellePlaylist, null);
        this.getContentPane().add(comboPlaylist, null);
        // 1.
        ec = new GestEventPlaylist(this,cvmPlayer);
        // 2. 
        btnNouvellePlaylist.addActionListener(ec);
        btnAjouterChanson.addActionListener(ec);
        comboPlaylist.addActionListener(ec);
    }

    public void updateComboListePlaylists() {
        Enumeration<String> playlistes = cvmPlayer.getPlaylistes().keys();
        comboPlaylist.removeActionListener(ec);
        comboPlaylist.removeAllItems();
        while(playlistes.hasMoreElements())
            comboPlaylist.addItem(playlistes.nextElement());
        comboPlaylist.addActionListener(ec);
    }
    
    public void ajouterChansonPlaylist() {
        File chanson = cvmPlayer.choisirChanson();
        String nomPlaylist = (String)comboPlaylist.getSelectedItem();
        Playlist pl = cvmPlayer.getPlaylist(nomPlaylist);
        pl.ajouterChanson(chanson);
        updateListChansons(pl.getChansonsListe());
    }
    
    public void updateListChansons(Vector<File> chansonListe){
        listModel.removeAllElements();
        for (File uneChanson: chansonListe){
            listModel.addElement(uneChanson.getName());
        }
        //listChansons.setModel(listModel);
    }
    
    public void viderListchansons(){
        listModel.removeAllElements();
        //listChansons.setModel(listModel);
    }
    
    public void updateListChansons(String nomChanson){
        comboPlaylist.removeActionListener(ec);
        comboPlaylist.setSelectedIndex(-1);
        comboPlaylist.addActionListener(ec);
        nomPlaylistCourant = null;
        listModel.removeAllElements();
        listModel.addElement(nomChanson);
        listChansons.setModel(listModel);
    }
    
    public JButton getBtnNouvellePlaylist() {
        return btnNouvellePlaylist;
    }

    public JButton getBtnAjouterChanson() {
        return btnAjouterChanson;
    }

    public JComboBox getComboPlaylist() {
        return comboPlaylist;
    }

    public void setNomPlaylistCourant(String nomPlaylistCourant) {
        this.nomPlaylistCourant = nomPlaylistCourant;
    }

    public String getNomPlaylistCourant() {
        return nomPlaylistCourant;
    }

    public DefaultListModel getListModel() {
        return listModel;
    }
}
