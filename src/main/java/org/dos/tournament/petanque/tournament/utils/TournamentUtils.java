package org.dos.tournament.petanque.tournament.utils;

import java.util.Vector;

import org.dos.tournament.player.IParticipant;
import org.dos.tournament.player.utils.ParticipantStatus;

public abstract class TournamentUtils
{
  static public Vector<IParticipant> filterParticipantsByStatus(Vector<IParticipant> participants, ParticipantStatus status)
  {
    Vector<IParticipant> _retval = new Vector<IParticipant>();
    
    for(IParticipant it : participants)
      if(status == it.getStatus())
        _retval.addElement(it);

    return _retval;
  }
}
