package org.dos.tournament.application.branch.petanque.panels.components.tables;

import java.util.ArrayList;
import java.util.List;

import org.dos.tournament.application.common.controls.TableHeaderColumnContent;
import org.dos.tournament.application.common.panels.components.SuperMeleeMatchdayTable;
import org.dos.tournament.application.common.panels.components.SuperMeleeMatchdayTable.TableCellRendererCenteredText;
import org.dos.tournament.application.common.panels.components.SuperMeleeMatchdayTable.TableModel;
import org.dos.tournament.branch.petanque.tournament.movement.SuperMelee;
import org.dos.tournament.branch.petanque.tournament.partie.Partie;

public class SuperMeleeResultsTable extends SuperMeleeMatchdayTable
{
  final static public String COL_ROUND        = "ROUND";

  public SuperMeleeResultsTable(SuperMelee tournament)
  {
    this(tournament, 0);
  }

  public SuperMeleeResultsTable(SuperMelee tournament, int matchday)
  {
    super(tournament);
    TableModel _model = new TableModel();
    this.setModel(_model);
    _model.update(tournament, null);
    this.xTournament.addObserver(_model);
  }

  @Override
  public boolean isCellEditable(int row, int column)
  {
    return false;
  }

  @Override
  protected void setTableCellRenderer() {
    // TODO Auto-generated method stub
    super.setTableCellRenderer();
    if(null != this.getColumn(COL_ROUND))
      this.getColumn(COL_ROUND).setCellRenderer(new TableCellRendererCenteredText());

  }

  private class TableModel extends org.dos.tournament.application.common.panels.components.SuperMeleeMatchdayTable.TableModel
  {

    public TableModel()
    {
      this(0);
    }

    protected TableModel(int matchday) {
      super(matchday);
      this.astrHeader.add(0, SuperMeleeResultsTable.COL_ROUND);
      // TODO Auto-generated constructor stub
    }

    protected void update(SuperMelee tournament, Object arg)
    {
      this.xTournament = tournament;

      ArrayList<ArrayList<String>> _matchdayData = new ArrayList<>();

      //if((0 == this.getDataVector().size()) || this.matchdayChanged(arg))
      for(int iMD=0; iMD<tournament.countMatchdays(); ++iMD)
      {
        int iMatches = tournament.getMatchday(iMD).countMatches();

        for(int i=0; i<iMatches; ++i)
        {
          ArrayList<String> _row = new ArrayList<>();

          for(String _column : this.astrHeader)
          {
            Partie _match = this.xTournament.getMatchday(iMD).getMatch(i);
            switch(_column)
            {
              case SuperMeleeResultsTable.COL_ROUND        : _row.add(String.valueOf(iMD+1)); break;
              case SuperMeleeMatchdayTable.COL_INDEX       : _row.add(String.valueOf(i+1)); break;
              case SuperMeleeMatchdayTable.COL_MATCH       : _row.add( _match.getId()); break;
              case SuperMeleeMatchdayTable.COL_HOME_TEAM   : _row.add( this.bOutputNumeric?  _match.getCompetitor(0).getDescriptionByCode():
                                                                                _match.getCompetitor(0).getDescription());
                                                break;
              case SuperMeleeMatchdayTable.COL_HOME_SCORE  : _row.add( _match.isScored()?    String.valueOf(_match.getHomeScore()):""); break;
              case SuperMeleeMatchdayTable.COL_GUEST_TEAM  : _row.add( this.bOutputNumeric?  _match.getCompetitor(1).getDescriptionByCode():
                                                                                _match.getCompetitor(1).getDescription());
                                                break;
              case SuperMeleeMatchdayTable.COL_GUEST_SCORE : _row.add( _match.isScored()?    String.valueOf(_match.getGuestScore()):""); break;
              case SuperMeleeMatchdayTable.COL_VS_SCORE    : _row.add(":"); break;
              case SuperMeleeMatchdayTable.COL_COURT       : _row.add("Platz"); break;
              default                         : _row.add("");
            }
          }
          _matchdayData.add(_row);
        }


        ArrayList<TableHeaderColumnContent> _header = (ArrayList<TableHeaderColumnContent>) this.compileHeader();
        Object[][] _matchdayArray = new Object[_matchdayData.size()][_matchdayData.get(0).size()];
        for(int i=0; i<_matchdayData.size(); ++i)
          _matchdayArray[i] = _matchdayData.get(i).toArray(_matchdayArray[i]);
        this.setDataVector(_matchdayArray, _header.toArray());

        for(int i=0; i < _header.size(); ++i)
          SuperMeleeResultsTable.this.getTableHeader().getColumnModel().getColumn(i).setHeaderValue(_header.get(i));


        SuperMeleeResultsTable.this.updateColumnSize();
        SuperMeleeResultsTable.this.setTableCellRenderer();

        this.fireTableDataChanged();
      }
    }

    @Override
    protected List<TableHeaderColumnContent> compileHeader()
    {
      ArrayList<TableHeaderColumnContent> _header = new ArrayList<>();

      for(String _column : this.astrHeader)
      {
        switch(_column)
        {
          case SuperMeleeResultsTable.COL_ROUND        : _header.add(new TableHeaderColumnContent(SuperMeleeResultsTable.COL_ROUND, "Rd.", 30)); break;
          case SuperMeleeMatchdayTable.COL_INDEX       : _header.add(new TableHeaderColumnContent(SuperMeleeMatchdayTable.COL_INDEX, "Nr.", 30)); break;
          case SuperMeleeMatchdayTable.COL_MATCH       : _header.add(new TableHeaderColumnContent(SuperMeleeMatchdayTable.COL_MATCH, "Id", 30)); break;
          case SuperMeleeMatchdayTable.COL_HOME_TEAM   : _header.add(new TableHeaderColumnContent(SuperMeleeMatchdayTable.COL_HOME_TEAM, "Heim", 250)); break;
          case SuperMeleeMatchdayTable.COL_HOME_SCORE  : _header.add(new TableHeaderColumnContent(SuperMeleeMatchdayTable.COL_HOME_SCORE, "Punkte", 60)); break;
          case SuperMeleeMatchdayTable.COL_GUEST_TEAM  : _header.add(new TableHeaderColumnContent(SuperMeleeMatchdayTable.COL_GUEST_TEAM, "Gast", 250)); break;
          case SuperMeleeMatchdayTable.COL_GUEST_SCORE : _header.add(new TableHeaderColumnContent(SuperMeleeMatchdayTable.COL_GUEST_SCORE, "Punkte", 60)); break;
          case SuperMeleeMatchdayTable.COL_VS_SCORE    : _header.add(new TableHeaderColumnContent(SuperMeleeMatchdayTable.COL_VS_SCORE, "vs", 10)); break;
          case SuperMeleeMatchdayTable.COL_COURT       : _header.add(new TableHeaderColumnContent(SuperMeleeMatchdayTable.COL_COURT, "Platz", 30)); break;
          default                         : _header.add(new TableHeaderColumnContent("", "", 100));
        }
      }

      return _header;
    }
  }

}
