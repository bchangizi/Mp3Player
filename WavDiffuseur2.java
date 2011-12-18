package projetcvmplayer;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.Control;

import javax.swing.*;


public class WavDiffuseur2 extends Thread 
{
private static final int GRANDEUR_BUFFER = 12800 ; // grandeur du buffer 12800

public static final int PLAY = 0;
public static final int PAUSE = 1;
public static final int STOP = 2;
         
       
private int statut=PLAY;  // valeurs possibles 
private WavInfo w;
private SourceDataLine ligne = null; // ligne communiquant avec la carte de son

public WavDiffuseur2 ( WavInfo w)
{
this.w = w;
}


public WavInfo getWavInfo ( )
{
return w;
}

public void run() // méthode appelée automatiquement lorsqu'on démarre un thread
	{
    // Nous devons obtenir une ligne ( Line ) afin de disposer de notre fichier lu; la line fait le lien entre notre programme et la carte de son de l'ordinateur.
    // Nous avons choisi une SourceDataLine car nous voulons écouter et non pas enregistrer de la musique
    // l'obtention de la ligne nécessite l'audioFormat car on a besoin de savoir quel format les données passées à la ligne auront.
	
        DataLine.Info	info = new DataLine.Info(SourceDataLine.class,w.getAudioFormatDecode(), AudioSystem.NOT_SPECIFIED);
        try
            {
            ligne = (SourceDataLine) AudioSystem.getLine(info);
            /*La line est créée nous devons l'ouvrir pour qu'elle puisse recevoir des données audio*/
            ligne.open(w.getAudioFormatDecode());
         
            }
        catch (LineUnavailableException e)
            {
            e.printStackTrace();
            System.exit(1);
            }
        catch (Exception e)
            {
              
            e.printStackTrace();
            System.exit(1);
            }

        /*Ce n'est pas encore assez. La line doit recevoir des données, mais ne peut pas les passer à la carte audio de l'ordi, il faut l'activer*/
        ligne.start();
        
      
        /*
        Maintenant que la ligne est préparée, on doit écrire les données à cette ligne grâce à une boucle.On lit les données du stream à un buffer
        et ensuite on écrit les données du buffer ( tampon ) à la line. Quand la méthode read retourne -1 , cela veut dire que tout le fichier a été lu   
        */
        int nBytesRead = 0;
        byte[]	abData = new byte[GRANDEUR_BUFFER];
       
        //synchronized
        synchronized (w.getAudioInputStreamDecode())
         {
         while ( (nBytesRead != -1)&&(statut != STOP)  )
            { 
                
            if ( statut == PLAY) // je peux lire
		{
                try
                    {
                 
                    nBytesRead = w.getAudioInputStreamDecode().read(abData, 0, abData.length);  
                    if(nBytesRead >= 0 )  // on a eu des octets lus en banque
                        {
                        int   nBytesWritten = ligne.write(abData, 0, nBytesRead);
                        }
                    }
                catch (IOException e)
                    {
                    e.printStackTrace();
                    }
                }
            else //if ( statut == PAUSE )// je veux faire pause
                {
                try
                {
                Thread.sleep(1000); // le thread est en sommeil, le sera tant qu'on remet pas le statut à play
                }
                catch (InterruptedException ie) {
                    ie.printStackTrace();}
                }
            } // fin du while

/*Toutes les données ont été lues et jouées ( ou on a appuyer sur stop ), on peut fermer la line*/
 
ligne.drain();
ligne.close(); // on ferme la ligne , lecture terminée
 //w.getAudioInputStreamDecode().close()
        }
    }


  public void setLigne(SourceDataLine ligne)
  {
    this.ligne = ligne;
  }


  public SourceDataLine getLigne()
  {
    return ligne;
  }


    public void setStatut(int statut) {
        this.statut = statut;
    }

    public int getStatut() {
        return statut;
    }
}


