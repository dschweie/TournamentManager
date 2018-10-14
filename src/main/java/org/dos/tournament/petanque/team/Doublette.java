package org.dos.tournament.petanque.team;

import java.util.Vector;

import org.dos.tournament.player.IParticipant;
import org.dos.tournament.player.utils.IParticipantId;

public class Doublette extends AbstractPetanqueTeam
{
  
  public Doublette(IParticipantId id, IParticipant pointeur, IParticipant tireur)
  {
    super(id, pointeur, tireur);
  }
  
  /* (non-Javadoc)
   * @see org.dos.tournament.players.AbstractParticipant#getDescription()
   */
  @Override
  public String getDescription()
  {
    String _pointeurName = this.getPointeur().getName();
    String _tireurName = this.getTireur().getName();
    return _pointeurName.concat(", ").concat(_tireurName);
  }

  /* (non-Javadoc)
   * @see org.dos.tournament.player.AbstractTeamParticipant#getAttendeesToArray()
   */
  @Override
  public IParticipant[] getAttendeesToArray()
  {
    IParticipant[] _retval = { this.getPointeur(), this.getTireur() };
    return _retval;
  }


  
}

