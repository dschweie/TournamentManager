package org.dos.tournament.petanque.tournament.utils;

import java.util.Vector;

import org.dos.tournament.petanque.tournament.matchday.Matchday;
import org.dos.tournament.petanque.tournament.movement.SuperMelee;
import org.dos.tournament.petanque.tournament.partie.Partie;

public class TournamentGrid
{
  @SuppressWarnings("rawtypes")
  private Vector<Vector> grid;
  
  @SuppressWarnings("rawtypes")
  public TournamentGrid()
  {
    this.grid = new Vector<Vector>();
  }
  
  @SuppressWarnings("unchecked")
  public void buildGrid(SuperMelee tournament)
  {
    this.grid.setSize(tournament.countMatchdays());
    
    for(int i=0; i<this.grid.size(); ++i)
    {
      Matchday _currentMatchday = tournament.getMatchday(i);
      this.grid.set(i, new Vector<Vector>());
      this.grid.get(i).setSize(_currentMatchday.countMatches());
      for(int j=0; j<this.grid.get(i).size(); ++j)
      {
        Partie _currentPartie = _currentMatchday.getMatch(j);
        this.grid.get(i).set(j, new Vector<Vector>());
       // this.grid.get(i).get(j)).setSize(2);
      }
    }
  }
}
