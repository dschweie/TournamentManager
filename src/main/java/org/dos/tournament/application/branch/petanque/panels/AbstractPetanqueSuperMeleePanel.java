package org.dos.tournament.application.branch.petanque.panels;

import org.dos.tournament.application.common.panels.AbstractTournamentPanel;
import org.dos.tournament.branch.petanque.tournament.movement.SuperMelee;

public abstract class AbstractPetanqueSuperMeleePanel extends AbstractTournamentPanel
{

  private SuperMelee tournament = null;
  
  
  public AbstractPetanqueSuperMeleePanel(SuperMelee tournament)
  {
    this.tournament = tournament;
  }

  public void setTournament(SuperMelee tournament)
  {
    this.tournament = tournament;
  }

  public SuperMelee getTournament()
  {
    return this.tournament;
  }
  
  public String getTournamentIdAsString()
  {
    return this.tournament.getTournamentId().toString();
  }

  
}
