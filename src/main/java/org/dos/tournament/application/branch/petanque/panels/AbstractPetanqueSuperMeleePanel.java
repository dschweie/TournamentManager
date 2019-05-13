package org.dos.tournament.application.branch.petanque.panels;

import javax.swing.JPanel;

import org.dos.tournament.branch.petanque.tournament.movement.SuperMelee;

public abstract class AbstractPetanqueSuperMeleePanel extends JPanel{

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
}
