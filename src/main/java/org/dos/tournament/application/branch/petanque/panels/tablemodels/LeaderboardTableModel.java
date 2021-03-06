package org.dos.tournament.application.branch.petanque.panels.tablemodels;

import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeSet;
import java.util.ArrayList;

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
    IParticipant _leader = null;

    ArrayList<IParticipant> _participants = ((SuperMelee)o).getCompetitors();
    Iterator<IParticipant> _it = _participants.iterator();
    TreeSet<IParticipant> _leaderboard = new TreeSet<IParticipant>();
    ArrayList<Object> _columnIdentifiers = new ArrayList<>();

    _columnIdentifiers.add("Rang");
    _columnIdentifiers.add("Nummer");
    //_columnIdentifiers.add("Name");

    _participants.forEach(it -> it.unsetWinnerOfTheDayTrophy());

    if(0 < _participants.size())
      _columnIdentifiers.addAll(_participants.get(0).getTotalResultIdentifiers());
    this.setColumnIdentifiers(_columnIdentifiers.toArray());

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
      if(     ( 0 == rank )
          &&  ( 0 < _current.getTotalScore())  )
      {
        _current.setWinnerOfTheDayTrophy(((SuperMelee)o).getTrophy());
        _leader = _current;
      }
      else
        if(     ( null != _leader                                 )
            &&  ( 0 == _current.compareToByResult(_leader, true)  ) )
          _current.setWinnerOfTheDayTrophy(((SuperMelee)o).getTrophy());

      ArrayList<Object> _row = new ArrayList<Object>();
      _row.add(new Integer(++rank));
      _row.add(_current.getParticipantId().getCode());
      //_row.add(_current.getName());

      int[] _values = _current.getTotalResult();
      for(int i=0; i < _values.length; ++i)
      {
        _row.add(_values[i]);
      }

      this.addRow(_row.toArray());

    }

    this.fireTableDataChanged();
  }

}
