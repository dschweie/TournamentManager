/**
 * 
 */
package org.dos.tournament.common.storage;

import java.util.HashMap;
import java.util.Vector;

import org.bson.Document;
import org.dos.tournament.branch.petanque.team.JoueurIndividuel;
import org.dos.tournament.common.competition.ITournament;
import org.dos.tournament.common.player.AbstractParticipant;
import org.dos.tournament.common.player.IParticipant;

/**
 *  \brief      Diese Schnittstelle ist Fassade f�r unterschiedliche Implementierungen zum Speichern von Daten.
 *  
 */
public interface IStorage
{
  /**
   *  \brief    Methode zum �ffnen der Verbindung zum Speichermedium
   *  @param    parameters    In dem Parameter werden spezifische Informationen
   *                          erwartet, mit denen eine Vebindung zum 
   *                          Speichermedium aufgebaut werden kann. 
   *  @return   Die Methode liefert im R�ckgabewert eine Information, ob die 
   *            Verbindung aufgebaut werden konnte.
   */
  public boolean open(HashMap<String,Object> parameters);
  
  /**
   *  \brief    Methode zum Schlie�en der Verbindung zum Speichermedium
   */
  public void close();
  
  public boolean isConnected();
  
  public boolean isFailed();
  
  /**
   *  \brief    Methode zum Speichern eines allgemeinen Mitpielers
   *  @param    participant   In dem Parameter ist ein Objekt vom Typ 
   *                          IParticipant zu �bergeben, dass gespeichtert
   *                          werden soll.
   *  @param    overwrite     In dem Parameter wird ein Flag erwartet, das 
   *                          signalisiert, ob bestehende Daten �berschrieben
   *                          oder erg�nzt werden sollen. Wenn bereits ein
   *                          Datensatz vorhanden ist, dann kann mit dem Wert
   *                          <code>false</code> erreicht werden, dass in dem 
   *                          Datensatz nur die neuen Informationen aktualisiert
   *                          werden. Wenn <code>true</code> �bergeben wird, 
   *                          dann wird der Datensatz zun�chst gel�scht und nur
   *                          mit den Informationen gespeichert, die in der
   *                          Instanz vorhanden sind.
   *  @return   Die Methode liefert im R�ckgabewert die Information, ob das 
   *            Speichern erfolgreich war.
   */
  public boolean saveParticipant(IParticipant participant, boolean overwrite);
  
  public boolean saveTournament(ITournament tournament, boolean overwrite);
  
  public Vector<JoueurIndividuel> findParticipantAsJoueurIndividuel(String forname, String surename, String association);

  public Vector<JoueurIndividuel> findParticipantAsJoueurIndividuel(String forname, String surename, String association, Vector<IParticipant> excluded);
  
  public IParticipant findParticipantById(String id);
  
  /**
   *  \brief    Methode liefert Informationen zu den aktuell verf�gbaren Turnieren
   *  
   *  @return   Die Turnierinformationen werden als Hashmap geliefert.
   */
  public Vector<HashMap<String,String>> getTournamentData();
  
  /**
   *  \brief    Die Methode liefert eine Instanz vom Typ ITournament
   *  
   *  @param    tid           In dem Parameter ist die ID des Turniers zu �bergeben.
   *  
   *  @return
   */
  public ITournament loadTournament(String tid);
  public Document loadTournamentAsDocument(String tid);
}
