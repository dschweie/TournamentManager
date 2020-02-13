package org.dos.tournament.branch.petanque.tournament.utils;

import java.util.ArrayList;

import org.dos.tournament.branch.petanque.tournament.matchday.Matchday;
import org.dos.tournament.branch.petanque.tournament.movement.SuperMelee;
import org.dos.tournament.branch.petanque.tournament.partie.Partie;

public class TournamentGrid
{
  @SuppressWarnings("rawtypes")
  private ArrayList<ArrayList> grid;

  @SuppressWarnings("rawtypes")
  public TournamentGrid()
  {
    this.grid = new ArrayList<>();
  }

  @SuppressWarnings("unchecked")
  public void buildGrid(SuperMelee tournament)
  {
    this.grid.ensureCapacity(tournament.countMatchdays());

    for(int i=0; i<this.grid.size(); ++i)
    {
      Matchday _currentMatchday = tournament.getMatchday(i);
      this.grid.set(i, new ArrayList<>());
      this.grid.get(i).ensureCapacity(_currentMatchday.countMatches());
      for(int j=0; j<this.grid.get(i).size(); ++j)
      {
        Partie _currentPartie = _currentMatchday.getMatch(j);
        this.grid.get(i).set(j, new ArrayList<>());
      }
    }
  }
}
