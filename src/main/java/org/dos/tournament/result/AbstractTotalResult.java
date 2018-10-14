package org.dos.tournament.result;

import java.util.HashMap;

public abstract class AbstractTotalResult implements ITotalResult
{
  protected HashMap<Integer,IResult> mapResults = new HashMap<Integer,IResult>();

  /* (non-Javadoc)
   * @see org.dos.tournament.result.ITotalResult#addResultOfMatchday(int, org.dos.tournament.result.IResult)
   */
  @Override
  public void addResultOfMatchday(int matchday, IResult result)
  {
    this.mapResults.put(new Integer(matchday), result);
  }

  /* (non-Javadoc)
   * @see org.dos.tournament.result.ITotalResult#getResultOfMatchday(int)
   */
  @Override
  public IResult getResultOfMatchday(int matchday)
  {
    return this.mapResults.get(new Integer(matchday));
  }

  /* (non-Javadoc)
   * @see org.dos.tournament.result.ITotalResult#countMatchdays()
   */
  @Override
  public int countMatches()
  {
    return this.mapResults.size();
  }
}
