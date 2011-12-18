package projetcvmplayer;

import javax.sound.sampled.*;
import java.io.*;

public class WavInfo 
{
private File fichier; // nom de la chanson à jouer
private AudioInputStream audioInputStream = null; // on lira le fichier musical à partir d'un stream AudioInputStream, on lira les octets du fichier à partir de ce stream
private AudioInputStream audioInputStreamDecode = null; // stream supplémentaire nécessaire pour lire des mp3 ( encodage ) 
private AudioFormat audioFormat;
private AudioFormat audioFormatDecode;
private int nbSec; // nb de secondes que dure la chanson


public WavInfo(File fichier)
{
  try
    {
    this.fichier = fichier;
    audioInputStream = AudioSystem.getAudioInputStream(fichier); // obtient un audioInputStream à partir d'un fichier audio VALIDE
    audioFormat = audioInputStream.getFormat(); // on va chercher le format de notre stream ( notre fichier son ) comme la fréquence, on aura besoin de ces infos pour obtenir une ligne d'output
    audioFormatDecode = new AudioFormat(
                     AudioFormat.Encoding.PCM_SIGNED /*technique d'encodage audio */,
                     audioFormat.getSampleRate()/* nbre d'échantillons par seconde*/, 16/* nbre de bits ds chaque échantillon*/, audioFormat.getChannels()/* canaux à utiliser*/,
                     audioFormat.getChannels() * 2/*nbre d'octets ds chaque frame */, audioFormat.getSampleRate()/* nbre de frames par seconde*/,
                     false);
    audioInputStreamDecode = AudioSystem.getAudioInputStream(audioFormatDecode, audioInputStream);//conversion ds le format Décodé ( mp3 ) de l'inputStream initial
    
     double oct = (Integer)audioFormat.properties().get("bitrate")/8.0;  // nb d'octets de la chanson par seconde ( 8 bits ds un octet )
     boolean bitRateVariable = (Boolean)audioFormat.properties().get("vbr");
     
     long longueur = fichier.length(); // longueur du fichier en octets 
    
    nbSec =  (int ) (longueur / oct); // nb d'octets total divisé par le nb d'octets par seconde = durée de la chanson

    System.out.println ( audioFormatDecode);
    System.out.println(oct);
    System.out.println(bitRateVariable);
    }
  catch (Exception e)
    {
    /*si ca ne marche pas*/
    e.printStackTrace();
    System.exit(1);
    }
}

public File getFichier ()
  {
  return fichier ;
  }

public AudioInputStream getAudioInputStream ()
  {
  return audioInputStream;
  }
  
public AudioFormat getAudioFormat()
  {
  return audioFormat;
  }

public  int getNbSec()
  {
  return nbSec;
  }
  
  public boolean equals ( Object  o )
  {
    WavInfo w = ( WavInfo ) o;
    if ( w.getFichier().equals(this.getFichier()))
      {
        if ( w.getNbSec() == this.getNbSec())
          return true;
        else
          return false;
      }
    else
      return false;
  }


  public void setAudioFormatDecode(AudioFormat audioFormatDecode)
  {
    this.audioFormatDecode = audioFormatDecode;
  }

  public AudioFormat getAudioFormatDecode()
  {
    return audioFormatDecode;
  }

  public void setAudioInputStreamDecode(AudioInputStream audioInputStreamDecode)
  {
    this.audioInputStreamDecode = audioInputStreamDecode;
  }

  public AudioInputStream getAudioInputStreamDecode()
  {
    return audioInputStreamDecode;
  }
}


