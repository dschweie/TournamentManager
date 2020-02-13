package org.dos.tournament.application.branch.petanque.panels.components.tables;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.dos.tournament.application.common.controls.TableHeaderColumnContent;
import org.dos.tournament.application.common.panels.components.SuperMeleeMatchdayTable;
import org.dos.tournament.application.common.utils.tablecelleditor.PetanqueTableCellEditor;
import org.dos.tournament.branch.petanque.team.JoueurIndividuel;
import org.dos.tournament.branch.petanque.tournament.movement.SuperMelee;
import org.dos.tournament.branch.petanque.tournament.partie.Partie;

public class ParticipantTable extends JTable
{
  private TableModel tableModel = new TableModel();
  private SuperMelee tournament = null;

  public ParticipantTable(SuperMelee tournament)
  {
    super();

    this.tournament = tournament;
    this.tournament.addObserver(tableModel);

    this.setModel(tableModel);
    this.setDefaultEditor(Object.class, new PetanqueTableCellEditor());
  }

  @Override
  public boolean isCellEditable(int row, int column)
  {
    return false;
  }

  public void updateColumnSize() {
    int _cols = this.getColumnModel().getColumnCount();

    for(int i = 0; i < _cols; ++i)
    {
      Object obj = this.getTableHeader().getColumnModel().getColumn(i).getHeaderValue();
      this.getColumnModel().getColumn(i).setPreferredWidth(((TableHeaderColumnContent)this.getTableHeader().getColumnModel().getColumn(i).getHeaderValue()).getPreferredWidth());
    }

  }

  private class TableModel extends DefaultTableModel implements Observer
  {

    @Override
    public void update(Observable o, Object arg)
    {
      switch(o.getClass().getName())
      {
        case "org.dos.tournament.branch.petanque.tournament.movement.SuperMeleeClubChampionship":
        case "org.dos.tournament.branch.petanque.tournament.movement.SuperMelee":                   this.update((SuperMelee)o, arg);
                                                                                                    break;
      }

    }

    protected void update(SuperMelee tournament, Object arg)
    {
      if(null == ParticipantTable.this.tournament)
        ParticipantTable.this.tournament = tournament;

      if(true)
      {
        ArrayList<ArrayList<String>> _participants = new ArrayList<>();

        for(int i=0; i < ParticipantTable.this.tournament.countCompetitors(); ++i)
        {
          JoueurIndividuel _participant = (JoueurIndividuel) ParticipantTable.this.tournament.getCompetitors().get(i);
          ArrayList<String> _row = new ArrayList<>();
          _row.add(_participant.getCode());
          _row.add(_participant.getName());
          _row.add(_participant.getAssociation());

          _participants.add(_row);
        }

        ArrayList<TableHeaderColumnContent> _header = this.compileHeader();
        this.fireTableDataChanged();
        Object[][] _participantsMatrix = new Object[_participants.size()][_participants.get(0).size()];
        for(int i=0; i<_participants.size(); ++i)
          _participants.get(i).toArray(_participantsMatrix[i]);

        this.setDataVector(_participantsMatrix, _header.toArray());


        for(int i=0; i < _header.size(); ++i)
          ParticipantTable.this.getTableHeader().getColumnModel().getColumn(i).setHeaderValue(_header.get(i));

        ParticipantTable.this.updateColumnSize();
        this.fireTableDataChanged();
      }
    }

    private ArrayList<TableHeaderColumnContent> compileHeader()
    {
      ArrayList<TableHeaderColumnContent> _header = new ArrayList<>();

      _header.add(new TableHeaderColumnContent("ID", ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("Glossary.ID"), 10));
      _header.add(new TableHeaderColumnContent("PARTICIPANT", ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("Glossary.Name"), 100));
      _header.add(new TableHeaderColumnContent("ASSOCIATION", ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("Glossary.Association"), 100));
      return _header;
    }
 }

}
