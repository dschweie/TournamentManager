package org.dos.tournament.branch.petanque.tournament.partie;

import org.dos.tournament.common.player.IParticipant;

public class PartieTerrain extends Partie
{
  private Terrain terrain;

  public PartieTerrain()
  {
    super();
    this.terrain = Terrain.getTerraLibre();
  }

  public PartieTerrain(IParticipant home, IParticipant guest)
  {
    super(home, guest);
    this.terrain = Terrain.getTerraLibre();
  }

  public PartieTerrain(Terrain terrain, IParticipant home, IParticipant guest)
  {
    super(home, guest);
    this.terrain = terrain;
  }

  @Override
  public String toString()
  {
    String _retval = super.toString();
    return this.terrain.getCode().concat(" : ").concat(_retval);
  }

  @Override
  public String toStringWithNames()
  {
    String _retval = super.toStringWithNames();
    return this.terrain.getCode().concat(" : ").concat(_retval);
  }
}
