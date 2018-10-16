package org.dos.tournament.petanque.team;

import org.dos.tournament.player.IParticipant;
import org.dos.tournament.player.utils.IParticipantId;

public class Triplette extends AbstractPetanqueTeam
{
  
  public Triplette(IParticipantId id, IParticipant pointeur, IParticipant tireur)
  {
    this(id, pointeur, null, tireur);
    // TODO Auto-generated constructor stub
  }

  public Triplette(IParticipantId id, IParticipant pointeur, IParticipant milieu, IParticipant tireur)
  {
    super(id, pointeur, milieu, tireur);
  }

  /* (non-Javadoc)
   * @see org.dos.tournament.players.AbstractParticipant#getDescription()
   */
  @Override
  public String getDescription()
  {
    String _pointeurName = this.getPointeur().getName();
    String _milieuName = this.getMilieu().getName();
    String _tireurName = this.getTireur().getName();
    return _pointeurName.concat(", ").concat(_milieuName).concat(", ").concat(_tireurName);
  }

  /* (non-Javadoc)
   * @see org.dos.tournament.player.AbstractTeamParticipant#getAttendeesToArray()
   */
  @Override
  public IParticipant[] getAttendeesToArray()
  {
    IParticipant[] _retval = { this.getPointeur(), this.getMilieu(), this.getTireur() };
    return _retval;
  }

  @Override
  public String getDescriptionByCode()
  {
    String _pointeurName = this.getPointeur().getCode();
    String _milieuName = this.getMilieu().getCode();
    String _tireurName = this.getTireur().getCode();
    return _pointeurName.concat(", ").concat(_milieuName).concat(", ").concat(_tireurName);
  }
}
