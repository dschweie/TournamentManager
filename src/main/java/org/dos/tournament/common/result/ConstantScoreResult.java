package org.dos.tournament.common.result;

public class ConstantScoreResult implements IResult
{
  
  private int iScore = 0;
  
  public ConstantScoreResult(int score)
  {
    this.iScore = score;
  }

  @Override
  public int compareTo(Object o)
  {
    try
    {
      return this.getScore() - ((IResult) o).getScore();
    }
    catch(Exception e)
    {
      return 0;
    }
  }

  @Override
  public int getScore()
  {
    return this.iScore;
  }

}
