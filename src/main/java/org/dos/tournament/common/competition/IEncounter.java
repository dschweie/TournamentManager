package org.dos.tournament.common.competition;

import org.dos.tournament.common.player.IParticipant;
import org.dos.tournament.common.result.IResult;

public interface IEncounter
{
  public void addParticipant(IParticipant competitor);
  
  /**
   *  \brief    Die Methode liefert das Ergebnis der Begegnung aus Sicht der Heimmannschaft
   *  
   *  @return   Die Methode liefert das Ergebnis der Begegnung aus Sicht der 
   *            Heimmannschaft als Instanz von IResult. Sollte noch kein 
   *            Ergebnis vorliegen, wird <code>null</code> zurückgegeben.
   */
  public IResult getResult();
  
  /**
   *  \brief    Setter-Methode für das Ergebnis der Begegnung.
   *  @param    result
   */
  public void setResult(IResult result);
  
  /**
   *  \brief    Die Methode liefert das Ergebnis aus Sicht einer Teilnehmers
   *  
   *  @param    competitor              In diesem Parameter wird eine Instanz
   *                                    erwartet, für den das Ergebnis 
   *                                    ausgegeben werden soll.
   *                                    
   *  @return   Die Methode liefert das Ergebnis aus Sicht einer Teilnehmers
   *            als Instanz von IResult, falls dieser an der Begegnung 
   *            teilgenommen hat und ein Ergebnis vorliegt.
   *            Andernfalls wird <code>null</code> zurückgegeben.
   */
  public IResult getCompetitorResult(IParticipant competitor);
  
  /**
   *  \brief    Die Methode liefert die Information, ob die Begegnung vollständig ist
   *  
   *  @return   Die Methode liefert den Wert <code>false</code>, wenn weitere
   *            Teilnehmer zu der Begegnung hinzugefügt werden müssen. 
   *            Andernfalls wird <code>true</code> zurückgegeben.
   */
  public boolean isComplete();
  /**
   *  \brief    Die Methode liefert die Information, ob ein Ergebnis erfasst ist
   *  
   *  @return   Die Methode liefert den Wert <code>true</code>, wenn ein 
   *            Ergebnis für die Begegnung vorliegt.
   */
  public boolean isScored();
  
  public IParticipant getWinner();
  public IParticipant getRank(int i);
}
