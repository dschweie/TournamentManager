package org.dos.tournament.branch.petanque.team;

import org.dos.tournament.branch.petanque.result.PetanqueTotalResult;
import org.dos.tournament.common.player.AbstractTeamParticipant;
import org.dos.tournament.common.player.IParticipant;
import org.dos.tournament.common.player.utils.IParticipantId;

public abstract class AbstractPetanqueTeam extends AbstractTeamParticipant
{
  public static final int POINTEUR = 0;
  public static final int TIREUR = 1;
  public static final int MILIEU = 2;

  public AbstractPetanqueTeam(IParticipantId id, IParticipant pointeur, IParticipant tireur)
  {
    this(id, pointeur, null, tireur);
  }

  public AbstractPetanqueTeam(IParticipantId id, IParticipant pointeur, IParticipant milieu, IParticipant tireur)
  {
    super();

    this.id = id;

    for(int i=0; i<3; ++i)
      this.participants.add(null);

    this.setPointeur(pointeur);
    this.setMilieu(milieu);
    this.setTireur(tireur);

    this.result = new PetanqueTeamTotalResult();
  }

  /**
   * @return the pointeur
   */
  public IParticipant getPointeur()
  {
    return this.getParticipants().get(POINTEUR);
  }

  /**
   * @param pointeur the pointeur to set
   */
  public void setPointeur(IParticipant pointeur)
  {
    this.getParticipants().set(POINTEUR, pointeur);
  }

  /**
   * @return the milieu
   */
  public IParticipant getMilieu()
  {
    return this.getParticipants().get(MILIEU);
  }

  /**
   * @param milieu the milieu to set
   */
  public void setMilieu(IParticipant milieu)
  {
    this.getParticipants().set(MILIEU, milieu);
  }

  /**
   * @return the tireur
   */
  public IParticipant getTireur()
  {
    return this.getParticipants().get(TIREUR);
  }

  /**
   * @param tireur the tireur to set
   */
  public void setTireur(IParticipant tireur)
  {
    this.getParticipants().set(TIREUR, tireur);
  }


  @Override
  public void setName(String name)
  {
    // TODO Auto-generated method stub

  }

  public class PetanqueTeamTotalResult extends PetanqueTotalResult
  {
    public String getName()
    {
      return AbstractPetanqueTeam.this.getName();
    }
  }
}
