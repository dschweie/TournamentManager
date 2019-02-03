package org.dos.tournament.common.competition;

import org.bson.Document;

/**
 *  \brief      Das Interface dient als allgemeine Schittstelle für Turniere
 *  
 *  Es gibt eine Reihe von Aktionen, die ganz allgemein auf einem Turnier
 *  ausgeführt werden können. Diese Aktionen werden durch diese Schnittstelle
 *  deklariert.
 *  
 *  @author dschweie
 *
 */
public interface ITournament
{
  public Document toBsonDocument();
}
