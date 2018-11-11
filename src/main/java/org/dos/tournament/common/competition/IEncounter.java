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
   *            Ergebnis vorliegen, wird <code>null</code> zur�ckgegeben.
   */
  public IResult getResult();
  
  /**
   *  \brief    Setter-Methode f�r das Ergebnis der Begegnung.
   *  @param    result
   */
  public void setResult(IResult result);
  
  /**
   *  \brief    Die Methode liefert das Ergebnis aus Sicht einer Teilnehmers
   *  
   *  @param    competitor              In diesem Parameter wird eine Instanz
   *                                    erwartet, f�r den das Ergebnis 
   *                                    ausgegeben werden soll.
   *                                    
   *  @return   Die Methode liefert das Ergebnis aus Sicht einer Teilnehmers
   *            als Instanz von IResult, falls dieser an der Begegnung 
   *            teilgenommen hat und ein Ergebnis vorliegt.
   *            Andernfalls wird <code>null</code> zur�ckgegeben.
   */
  public IResult getCompetitorResult(IParticipant competitor);
  
  /**
   *  \brief    Die Methode liefert die Information, ob die Begegnung vollst�ndig ist
   *  
   *  @return   Die Methode liefert den Wert <code>false</code>, wenn weitere
   *            Teilnehmer zu der Begegnung hinzugef�gt werden m�ssen. 
   *            Andernfalls wird <code>true</code> zur�ckgegeben.
   */
  public boolean isComplete();
  /**
   *  \brief    Die Methode liefert die Information, ob ein Ergebnis erfasst ist
   *  
   *  @return   Die Methode liefert den Wert <code>true</code>, wenn ein 
   *            Ergebnis f�r die Begegnung vorliegt.
   */
  public boolean isScored();
  
  public IParticipant getWinner();
  public IParticipant getRank(int i);
}
