package org.dos.tournament.common.result;

import org.bson.Document;

public interface IResult extends Comparable
{
  public int getScore();

  public Document toBsonDocument();
}
