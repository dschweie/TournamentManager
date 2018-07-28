package org.dos.tournament.competition;

public class DefaultCompetitorResult implements Comparable<DefaultCompetitorResult>
{

  private int resultValue;
  
  public DefaultCompetitorResult(int value)
  {
    this.resultValue = value;
  }

  @Override
  public int compareTo(DefaultCompetitorResult o)
  {
    return null!=o?this.resultValue - o.getValue():1;
  }
  
  public int getValue()
  {
    return this.resultValue;
  }
  
  public void setValue(int value)
  {
    this.resultValue = value;
  }

}
