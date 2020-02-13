package org.dos.tournament.branch.petanque.team;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bson.Document;
import org.dos.tournament.common.player.IParticipant;
import org.dos.tournament.common.player.utils.IParticipantId;

public class Triplette extends AbstractPetanqueTeam
{

  public Triplette(IParticipantId id, IParticipant pointeur, IParticipant tireur)
  {
    this(id, pointeur, null, tireur);
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
    List<Integer> _numbers = Arrays.asList(Integer.parseInt(this.getPointeur().getCode().trim()), Integer.parseInt(this.getMilieu().getCode().trim()), Integer.parseInt(this.getTireur().getCode().trim()));
    Collections.sort(_numbers);

    return String.format("%d, %d, %d",  _numbers.get(0), _numbers.get(1), _numbers.get(2));
  }

  @Override
  public Document toBsonDocument()
  {
    return super.toBsonDocument().append("pointeur", new Integer(this.getTireur().getCode().trim())).append("milieu", new Integer(this.getMilieu().getCode().trim())).append("tireur", new Integer(this.getPointeur().getCode().trim()));
  }

}
