package org.dos.tournament.branch.petanque.tournament.utils;

import java.util.ArrayList;
import java.util.List;

import org.dos.tournament.common.player.IParticipant;
import org.dos.tournament.common.player.utils.ParticipantStatus;

public abstract class TournamentUtils
{
  public static List<IParticipant> filterParticipantsByStatus(List<IParticipant> participants, ParticipantStatus status)
  {
    ArrayList<IParticipant> _retval = new ArrayList<>();

    for(IParticipant it : participants)
      if(status == it.getStatus())
        _retval.add(it);

    return _retval;
  }
}
