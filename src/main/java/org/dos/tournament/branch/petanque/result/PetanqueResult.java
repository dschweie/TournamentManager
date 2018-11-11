package org.dos.tournament.branch.petanque.result;

import org.dos.tournament.common.result.AbstractResult;

public class PetanqueResult extends AbstractResult
{
  private int iScore = -1;
  private int iOppsScore = -1;
  
  public PetanqueResult(int score, int oppsScore)
  {
    this.iScore = score;
    this.iOppsScore = oppsScore;
  }
  
  /** 
   *  \brief  Vergleicht zwei Ergebnisse.
   *  
   *  \return Die Methode liefert 
   *          \li   einen Wert gr��er 0, wenn <code>this</code> gr��er ist als <code>o</code>,
   *          \li   den Wert 0, wenn beide Ergebnisse die gleiche Anzahl an Punkten und gleiche Differenz haben,
   *          \li   einen Wert kleiner 0, wenn <code>o</code> gr��er ist als <code>this</code>.
   */
  @Override
  public int compareTo(Object o)
  {
    PetanqueResult _opps = (PetanqueResult)o;
    return (this.getScore() == _opps.getScore()?this.getDifference()-_opps.getDifference():this.getScore()-_opps.getScore());
  }
  
  public int getScore()
  {
    return this.iScore;
  }
  
  public int getDifference()
  {
    return this.iScore - this.iOppsScore;
  }
  
  protected void setScore(int value)
  {
    this.iScore = value;
  }

  protected void setOppsScore(int value)
  {
    this.iOppsScore = value;
  }
  
  /**
   *  \brief  Methode pr�ft, ob es sich um ein g�ltiges Petanque-Ergebnis handelt.
   *  
   *  Bei einem g�ltigen Ergebnis m�ssen beide Ergebniswerte im Intervall 
   *  [0;13] liegen und die beiden Werte d�rfen nicht gleich sein.
   *  
   *  Damit sind grunds�tzlich auch Ergebnisse erlaubt, bei denen nicht bis 13
   *  gespielt wurde, wenn z.B. bei Spielen auf Zeit, diese abgelaufen ist.
   *  
   *  @return Die Methode liefert den Wert <code>true</code>, wen das Ergebnis
   *          ein g�ltiges Spielergebnis ist.
   */
  public boolean isValid()
  {
    return  (     (0 <= this.iScore && this.iScore <= 13)
              &&  (0 <= this.iOppsScore && this.iOppsScore <= 13)
              &&  (this.iScore != this.iOppsScore)
             );
  }

  public int getOppsScore()
  {
    return this.iOppsScore;
  }
}
