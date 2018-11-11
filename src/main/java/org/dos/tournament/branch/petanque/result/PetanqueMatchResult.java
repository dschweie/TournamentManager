package org.dos.tournament.branch.petanque.result;

public class PetanqueMatchResult extends PetanqueResult
{
  private int matchScore;

  public PetanqueMatchResult(int score, int oppsScore)
  {
    super(score, oppsScore);
    this.matchScore = this.compileGameScore();
  }

  public int getMatchScore()
  {
    return this.matchScore;
  }
  
  /* (non-Javadoc)
   * @see org.dos.tournament.petanque.result.PetanqueResult#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(Object o)
  {
    PetanqueMatchResult _opps = (PetanqueMatchResult) o;
    return (this.getMatchScore() == _opps.getMatchScore()?super.compareTo(o):this.getMatchScore()-_opps.getMatchScore());
  }

  /**
   *  \brief  Die Methode errechnet die Siegpunkte für das Ergebnis.
   *  
   *  Üblicher Weise gibt es im Petanque einen Punkt für ein gewonnenens Spiel.
   *  
   *  @return Die Methode liefert den Wert 1, wenn das Spiel gewonnen wurde und
   *          andernfalls den Wert 0.
   */
  protected int compileGameScore()
  {
    return (0 < this.getDifference()?1:0);
  }

}
