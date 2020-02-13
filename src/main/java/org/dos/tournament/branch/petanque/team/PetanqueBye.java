package org.dos.tournament.branch.petanque.team;

import org.dos.tournament.common.player.IParticipant;
import org.dos.tournament.common.player.utils.ByeParticipantId;
import org.dos.tournament.common.player.utils.IParticipantId;

public class PetanqueBye extends AbstractPetanqueTeam
{
  public static PetanqueBye getBye()
  {
    return new PetanqueBye(new ByeParticipantId(), null, null, null);
  }

  public PetanqueBye(IParticipantId id, IParticipant pointeur, IParticipant milieu, IParticipant tireur) {
    super(id, pointeur, milieu, tireur);
    // TODO Auto-generated constructor stub
  }

  @Override
  public String getDescriptionByCode()
  {
    return "Freilos";
  }

  @Override
  public String getCode()
  {
    return this.id.getCode();
  }

  @Override
  public String getName()
  {
    return "Freilos";
  }

  @Override
  public String getDescription() {
    return "Freilos";
  }

}
