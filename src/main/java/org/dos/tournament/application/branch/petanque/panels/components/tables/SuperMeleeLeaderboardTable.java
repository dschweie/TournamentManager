package org.dos.tournament.application.branch.petanque.panels.components.tables;

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.TreeSet;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.dos.tournament.application.common.controls.TableHeaderColumnContent;
import org.dos.tournament.application.common.panels.components.SuperMeleeMatchdayTable.TableModel;
import org.dos.tournament.branch.petanque.team.JoueurIndividuel.JoueurIndividuelTotalResult;
import org.dos.tournament.branch.petanque.tournament.movement.SuperMelee;
import org.dos.tournament.common.player.IParticipant;
import org.dos.tournament.common.player.utils.ParticipantStatus;

public class SuperMeleeLeaderboardTable  extends JTable
{
  protected SuperMelee xTournament = null;

  public SuperMeleeLeaderboardTable(SuperMelee tournament)
  {
    super();
    this.xTournament = tournament;
    TableModel _model = new TableModel();
    this.setModel(_model);
    _model.update(this.xTournament, null);
    this.xTournament.addObserver(_model);
  }

  @Override
  public boolean isCellEditable(int row, int column)
  {
    return false;
  }

  public void updateColumnSize()
  {
    int _cols = this.getColumnModel().getColumnCount();

    for(int i = 0; i < _cols; ++i)
    {
      Object obj = this.getTableHeader().getColumnModel().getColumn(i).getHeaderValue();
      this.getColumnModel().getColumn(i).setPreferredWidth(((TableHeaderColumnContent)this.getTableHeader().getColumnModel().getColumn(i).getHeaderValue()).getPreferredWidth());
    }
  }

  private class TableModel extends DefaultTableModel implements Observer
  {
    public void update(Observable o, Object arg)
    {

      ArrayList<ArrayList<Object>> _lines = new ArrayList<>();

      IParticipant _leader = null;

      ArrayList<IParticipant> _participants = ((SuperMelee)o).getCompetitors();
      Iterator<IParticipant> _it = _participants.iterator();
      TreeSet<IParticipant> _leaderboard = new TreeSet<>();

      _participants.forEach(it -> it.unsetWinnerOfTheDayTrophy());

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

        ArrayList<Object> _row = new ArrayList<>();
        _row.add(new Integer(++rank));
        //_row.add(_current.getName());
        _row.add(_current.getParticipantId().getCode());
        _row.add(_current.getTotalScore());
        _row.add(_current.getTotalResultValue(JoueurIndividuelTotalResult.MATCHES));
        _row.add(_current.getTotalResultValue(JoueurIndividuelTotalResult.WINS));
        _row.add(_current.getTotalResultValue(JoueurIndividuelTotalResult.DIFFERENCE));
        for(int i=0; i < ((SuperMelee)o).countMatchdays(); ++i)
          _row.add(_current.getResultValue(i, JoueurIndividuelTotalResult.DIFFERENCE));


        int[] _values = _current.getTotalResult();
        for(int i=0; i < _values.length; ++i)
        {
          _row.add(_values[i]);
        }

        _lines.add(_row);
//        this.addRow(_row);

      }


      ArrayList<TableHeaderColumnContent> _header = this.compileHeader((SuperMelee)o);

      Object[][] _linesMatrix = new Object[_lines.size()][_lines.get(0).size()];
      for(int i=0; i < _lines.size(); ++i)
        _linesMatrix[i] = _lines.get(i).toArray(_linesMatrix[i]);
      this.setDataVector(_linesMatrix, _header.toArray());

      this.fireTableDataChanged();
    }

    private ArrayList<TableHeaderColumnContent> compileHeader(SuperMelee tournament)
    {
      ArrayList<TableHeaderColumnContent> _header = new ArrayList<>();
      // int _width = ParticipantTable.this.getWidth();
      _header.add(new TableHeaderColumnContent("RANK", ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("Glossary.Rank"), 10));
      _header.add(new TableHeaderColumnContent("ID", ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("Glossary.ID"), 100));
      //_header.add(new TableHeaderColumnContent("PARTICIPANT", ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("Glossary.Name"), 100));
      _header.add(new TableHeaderColumnContent("TOTALSCORE", ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("Glossary.TotalScore"), 10));
      _header.add(new TableHeaderColumnContent("MATCHES", ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("Glossary.Matches"), 10));
      _header.add(new TableHeaderColumnContent("WINS", ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("Glossary.Wins"), 10));
      _header.add(new TableHeaderColumnContent("TOTALDIFFERENCE", ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("Glossary.Abreviation.TotalDifference"), 10));
      for(int i=0; i < tournament.countMatchdays();++i)
      {
        _header.add(new TableHeaderColumnContent("MD".concat(String.valueOf(i+1)), ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("Glossary.Abreviation.Matchday").concat(String.valueOf(i+1)), 10));
      }
      return _header;
    }
  }


}
