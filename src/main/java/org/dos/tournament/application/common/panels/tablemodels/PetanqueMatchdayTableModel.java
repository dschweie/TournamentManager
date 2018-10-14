package org.dos.tournament.application.common.panels.tablemodels;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.dos.tournament.petanque.tournament.movement.SuperMelee;

public class PetanqueMatchdayTableModel extends DefaultTableModel implements Observer
{
  private int iMatchdayIndex;
  private boolean bInit = true;
  private SuperMelee xTournament = null;
  
  public PetanqueMatchdayTableModel(int matchday)
  {
    this.iMatchdayIndex = matchday;
  }

  
  @Override
  public void update(Observable o, Object arg)
  {
    // TODO Auto-generated method stub
    switch(o.getClass().getName())
    {
      case "org.dos.tournament.petanque.tournament.movement.SuperMeleeClubChampionship":
      case "org.dos.tournament.petanque.tournament.movement.SuperMelee":                  this.update((SuperMelee)o, arg);
                                                                                          break;
    }
  }
  
  protected void update(SuperMelee tournament, Object arg)
  {
    if(0 == this.getDataVector().size())
    {
      int iMatches = tournament.getMatchday(iMatchdayIndex).countMatches();
      Vector<Vector<String>> _matchdayData = new Vector<Vector<String>>();
  
      for(int i=0; i<iMatches; ++i)
      {
        Vector<String> _row = new Vector<String>();
        _row.add(String.valueOf(i+1));
        _row.add(tournament.getMatchday(iMatchdayIndex).getMatch(i).getCompetitor(0).getDescription());
        _row.add(tournament.getMatchday(iMatchdayIndex).getMatch(i).getCompetitor(1).getDescription());
        _row.add("");
        _row.add(":");
        _row.add("");
        
        _matchdayData.add(_row);
      }
      
      this.setDataVector(_matchdayData, PetanqueMatchdayTableModel.STANDARDHEADER_PETANQUE());
      
      this.xTournament = tournament;
      
      this.fireTableDataChanged();
    }
  } 

  /* (non-Javadoc)
   * @see javax.swing.table.DefaultTableModel#setValueAt(java.lang.Object, int, int)
   */
  @Override
  public void setValueAt(Object aValue, int row, int column)
  {
    // TODO Auto-generated method stub
    super.setValueAt(aValue, row, column);
    if((""!=this.getValueAt(row, 3))&&(""!=this.getValueAt(row, 5))&&(null!=this.xTournament))
    { // Begegnung auswerten
      this.xTournament.setResult(iMatchdayIndex, row, Integer.parseInt(this.getValueAt(row, 3).toString()), Integer.parseInt(this.getValueAt(row, 5).toString()));
    }
  }


  static private Vector<String> STANDARDHEADER_PETANQUE()
  {
    Vector<String> _header = new Vector<String>();
    _header.add("Partie");
    _header.add("Heim");
    _header.add("Gast");
    _header.add("");
    _header.add("");
    _header.add("");
    
    return _header;
  }


  @Override
  public boolean isCellEditable(int row, int column)
  {
    return ((3 == column)||(5 == column));
  }
  
}
