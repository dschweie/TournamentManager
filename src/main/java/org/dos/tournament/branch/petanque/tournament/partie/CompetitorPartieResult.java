package org.dos.tournament.branch.petanque.tournament.partie;

import org.dos.tournament.common.player.IParticipant;

public class CompetitorPartieResult implements Comparable<CompetitorPartieResult>
{
  public final static int SCORE_PARTIE_WON  = 1;
  public final static int SCORE_PARTIE_LOST = 0;
  
  private int           partieScore;
  private int           competitorValue;
  private int           opponentValue;
  private IParticipant  opponent;

  public CompetitorPartieResult(int competitorValue, int opponentValue, IParticipant opponent)
  {
    this.competitorValue = competitorValue;
    this.opponentValue = opponentValue;
    
    this.partieScore = competitorValue>opponentValue?SCORE_PARTIE_WON:SCORE_PARTIE_LOST;
    
    this.opponent = opponent;
  }
  
  /**
   * @return the partieScore
   */
  public int getPartieScore()
  {
    return partieScore;
  }

  /**
   * @param partieScore the partieScore to set
   */
  protected void setPartieScore(int partieScore)
  {
    this.partieScore = partieScore;
  }

  /**
   * @return the competitorValue
   */
  public int getCompetitorValue()
  {
    return competitorValue;
  }

  /**
   * @param competitorValue the competitorValue to set
   */
  public void setCompetitorValue(int competitorValue)
  {
    this.competitorValue = competitorValue;
  }

  /**
   * @return the opponentValue
   */
  public int getOpponentValue()
  {
    return opponentValue;
  }

  /**
   * @param opponentValue the opponentValue to set
   */
  public void setOpponentValue(int opponentValue)
  {
    this.opponentValue = opponentValue;
  }

  public int getValueDifference()
  {
    return this.competitorValue - this.opponentValue;
  }
  
  
  public IParticipant getOpponent()
  {
    return this.opponent;
  }

  
  
  
  @Override
  public int compareTo(CompetitorPartieResult o)
  {
    // TODO Auto-generated method stub
    return 0;
  }

}
