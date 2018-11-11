package org.dos.tournament.common.result;

import java.util.Collection;

public interface ITotalResult extends Comparable
{
  public void addResultOfMatchday(int matchday, IResult result);
  public IResult getResultOfMatchday(int matchday);
  
  public int countMatches();
  
  public int getTotalScore();
  public int[] getTotalResult();
  
  public int getValueForCategory(int category);
  public Collection<Object> getTotalResultIdentifiers();
}
