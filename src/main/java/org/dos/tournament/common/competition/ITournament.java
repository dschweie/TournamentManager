package org.dos.tournament.common.competition;

import org.bson.Document;
import org.dos.tournament.application.common.panels.AbstractTournamentPanel;

/**
 *  \brief      Das Interface dient als allgemeine Schittstelle f�r Turniere
 *  
 *  Es gibt eine Reihe von Aktionen, die ganz allgemein auf einem Turnier
 *  ausgef�hrt werden k�nnen. Diese Aktionen werden durch diese Schnittstelle
 *  deklariert.
 *  
 *  @author dschweie
 *
 */
public interface ITournament
{
  public Document toBsonDocument();
  
  /**
   * \brief     Die Methode soll ein Panel bereitstellen, mit dem das Turnier verwaltet werden kann.
   * 
   * 
   * 
   * @return    Die Methode liefert das spezialisierte Panel, mit dem der 
   *            Anwender die Turnierinstanz bearbeiten kann.
   */
  public AbstractTournamentPanel getManagementPanel();
}
