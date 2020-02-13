package org.dos.tournament.common.result;

import org.bson.Document;

public abstract class AbstractResult implements IResult
{

  @Override
  public int compareTo(Object o)
  {
    return 0;
  }

  @Override
  public Document toBsonDocument()
  {
    return new Document("_class", this.getClass().getName());
  }



}
