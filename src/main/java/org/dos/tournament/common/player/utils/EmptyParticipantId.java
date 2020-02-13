package org.dos.tournament.common.player.utils;

public class EmptyParticipantId implements IParticipantId
{


  @Override
  public String getCode() {
    return "-";
  }

  @Override
  public String getName() {
    return "-";
  }

  @Override
  public String getDescription() {
    return null;
  }

}
