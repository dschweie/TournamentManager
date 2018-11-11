package org.dos.tournament.application.petanque.panels.tablemodels;

import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.dos.tournament.branch.petanque.tournament.movement.SuperMelee;
import org.dos.tournament.common.player.IParticipant;
import org.dos.tournament.common.player.utils.ParticipantStatus;


public class LeaderboardTableModel extends DefaultTableModel implements Observer
{
  
  /* (non-Javadoc)
   * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
   */
  @Override
  public boolean isCellEditable(int row, int column)
  {
    // The leaderboard must not be edited.
    return false;
  }


  public LeaderboardTableModel()
  {
    super();
  }


  @Override
  public void update(Observable o, Object arg)
  {
    Vector<IParticipant> _participants = ((SuperMelee)o).getCompetitors();
    Iterator<IParticipant> _it = _participants.iterator();
    TreeSet<IParticipant> _leaderboard = new TreeSet<IParticipant>();
    Vector<Object> _columnIdentifiers = new Vector<Object>();
    
    _columnIdentifiers.add("Rang");
    _columnIdentifiers.add("Name");
    if(0 < _participants.size())
      _columnIdentifiers.addAll(_participants.get(0).getTotalResultIdentifiers());
    this.setColumnIdentifiers(_columnIdentifiers);
    
    while(_it.hasNext())
    {
      IParticipant _current = _it.next();
      if(ParticipantStatus.DISQUALIFIED != _current.getStatus())
        _leaderboard.add(_current);
    }
    
    this.dataVector.removeAllElements();
    _it = _leaderboard.descendingIterator();
    int rank = 0;
    while(_it.hasNext())
    {
      IParticipant _current = _it.next();
      Vector<Object> _row = new Vector<Object>();
      _row.add(new Integer(++rank));
      _row.add(_current.getName());
      
      int[] _values = _current.getTotalResult();
      for(int i=0; i < _values.length; ++i)
      {
        _row.add(_values[i]);
      }
      
      this.addRow(_row);
    }
    
    this.fireTableDataChanged();
  }

}
